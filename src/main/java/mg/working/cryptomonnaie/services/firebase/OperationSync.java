package mg.working.cryptomonnaie.services.firebase;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import mg.working.cryptomonnaie.model.transaction.Operation;
import mg.working.cryptomonnaie.model.user.Utilisateur;
import mg.working.cryptomonnaie.services.transaction.OperationService;
import mg.working.cryptomonnaie.services.utilisateur.UtilisateurService;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.beans.factory.annotation.Autowired;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class OperationSync {

    private static final String API_KEY = "AIzaSyDqJcwmsb1DXwPT3-v-8G7MKpMmetSBIgk";
    private static final String FIREBASE_PROJECT_ID = "cryptosyncs5";
    private static final String FIRESTORE_URL = "https://firestore.googleapis.com/v1/projects/";
    private static final String DATABASE_PATH = "/databases/(default)/documents/operation?key=";

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private OperationService operationService;

    @Autowired
    private FirestoreService firestoreSyncUtil;  // Déclaration de FirestoreSyncUtil

    private final RestTemplate restTemplate;

    @Autowired
    public OperationSync(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // @Scheduled(fixedRate = 30000)  // Interroge toutes les minutes
    public void syncFirestore() {
        try {
            // Construisez l'URL pour interroger Firestore
            String url = FIRESTORE_URL + FIREBASE_PROJECT_ID + DATABASE_PATH + "?key=" + API_KEY;
    
            // Récupérez la réponse de Firestore au format JSON
            String jsonResponse = restTemplate.getForObject(url, String.class);
    
            // Affichez la réponse JSON pour vérifier son contenu
            System.out.println("Réponse JSON de Firestore : " + jsonResponse);
    
            // Utilisez ObjectMapper pour désérialiser la réponse JSON en un JsonNode
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode firestoreResponse = objectMapper.readTree(jsonResponse);
    
            // Accédez directement aux documents
            JsonNode documents = firestoreResponse.path("documents");
    
            // Parcourez les documents
            for (JsonNode document : documents) {
                // Récupérer le nom du document pour suppression
                String documentName = document.path("name").asText();
    
                // Extraire l'ID du document à partir du nom
                String documentId = documentName.substring(documentName.lastIndexOf("/") + 1);
    
                System.out.println("DOCUMENT ID : " + documentId);
                // Récupérez le champ 'fields' du document
                JsonNode fields = document.path("fields");
    
                // Récupérez le montant en tant que BigDecimal
                JsonNode montantNode = fields.path("montant").path("integerValue");
                BigDecimal montant = new BigDecimal(montantNode.asDouble());  // Convertir le montant en BigDecimal
    
                // Récupérez l'opération (Depot ou Retrait)
                JsonNode operationNode = fields.path("operation").path("stringValue");
                String operation = operationNode.asText();
    
                // Récupérez le type d'opération (DEPOT ou RETRAIT)
                JsonNode typeOperationNode = fields.path("typeOperation").path("stringValue");
                String typeOperation = typeOperationNode.asText();
    
                // Récupérez l'utilisateurId
                JsonNode utilisateurIdNode = fields.path("utilisateurId").path("integerValue");
                Integer utilisateurId = utilisateurIdNode.asInt();
    
                // Affichage pour débogage
                System.out.println("Opération : " + operation);
                System.out.println("Type Opération : " + typeOperation);
                System.out.println("Utilisateur ID : " + utilisateurId);
                System.out.println("Montant : " + montant);
    
                // Créer une nouvelle instance d'Operation
                Operation operationEntity = new Operation();
                Utilisateur utilisateur = utilisateurService.getUtilisateurById(utilisateurId);
    
                if (utilisateur != null) {
                    operationEntity.setUtilisateur(utilisateur);
                    operationEntity.setDateHeureOperation(LocalDateTime.now()); // Peut être ajusté selon dateHeureOperation
                    operationEntity.setMontant(montant);
                    operationEntity.setStatus(null); // Utiliser true ou une valeur par défaut si nécessaire
                    operationEntity.setTypeOperation(Operation.TypeOperation.valueOf(typeOperation.toUpperCase())); // Utilisation d'un enum pour TypeOperation
    
                    // Enregistrer l'opération dans la base de données locale
                    operationService.save(operationEntity); 
    
                    // Supprimer le document Firestore après l'avoir enregistré localement
                    firestoreSyncUtil.deleteFirestoreDocument(documentId);
                } else {
                    // Gérer le cas où l'utilisateur n'est pas trouvé
                    System.out.println("Utilisateur non trouvé pour l'ID : " + utilisateurId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
