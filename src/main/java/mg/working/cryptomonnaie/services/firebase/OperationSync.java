package mg.working.cryptomonnaie.services.firebase;

import com.google.cloud.firestore.*;
import mg.working.cryptomonnaie.model.transaction.Operation;
import mg.working.cryptomonnaie.model.user.Utilisateur;
import mg.working.cryptomonnaie.services.transaction.OperationService;
import mg.working.cryptomonnaie.services.utilisateur.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class OperationSync {

    private static final String OPERATION_COLLECTION_PATH = "operation";

    @Autowired
    private FirebaseService firebaseService;

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private OperationService operationService;

    @PostConstruct
    public void init() {
        listenForFirestoreUpdates();
    }

    public void listenForFirestoreUpdates() {
        Firestore db = firebaseService.getFirestore();
        System.out.println("✅ Écoute Firebase activée pour les ajouts d'opérations...");

        db.collection(OPERATION_COLLECTION_PATH)
            .addSnapshotListener((snapshots, e) -> {
                if (e != null) {
                    System.err.println(" Erreur d'écoute Firebase : " + e.getMessage());
                    return;
                }

                if (snapshots != null && !snapshots.isEmpty()) {
                    for (DocumentChange dc : snapshots.getDocumentChanges()) {
                        if (dc.getType() == DocumentChange.Type.ADDED) {
                            DocumentSnapshot doc = dc.getDocument();
                            String documentId = doc.getId();
                            String operationType = doc.getString("typeOperation");
                            // Integer utilisateurId = doc.getLong("utilisateurId").intValue();
                            String mail = doc.getString("mail");
                            BigDecimal montant = BigDecimal.valueOf(doc.getLong("montant"));

                            System.out.println("\n🔹 Nouvelle opération détectée !");
                            System.out.println(" ID Document : " + documentId);
                            System.out.println(" Type : " + operationType);
                            System.out.println("Montant : " + montant);


                            Utilisateur utilisateur = utilisateurService.getUtilisateurByEmail(mail);

                            if (utilisateur != null) {
                                Operation operationEntity = new Operation();
                                operationEntity.setUtilisateur(utilisateur);
                                operationEntity.setDateHeureOperation(LocalDateTime.now());
                                operationEntity.setMontant(montant);
                                operationEntity.setStatus(null); // À définir si nécessaire
                                operationEntity.setTypeOperation(Operation.TypeOperation.valueOf(operationType.toUpperCase()));

                                operationService.save(operationEntity);
                                System.out.println("✅ Opération enregistrée localement.");

                                // 🔥 Suppression immédiate du document Firestore après traitement
                                deleteFirestoreDocument(documentId);
                            } else {
                                System.out.println("⚠ Utilisateur non trouvé pour le mail : " +mail );
                            }
                        }
                    }
                }
            });
    }

    private void deleteFirestoreDocument(String documentId) {
        Firestore db = firebaseService.getFirestore();
        db.collection(OPERATION_COLLECTION_PATH).document(documentId).delete()
            .addListener(() -> System.out.println("🗑 Document supprimé de Firestore : " + documentId), 
                        Runnable::run);
    }
}
