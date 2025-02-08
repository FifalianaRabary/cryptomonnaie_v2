package mg.working.cryptomonnaie.services.firebase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;

import mg.working.cryptomonnaie.model.crypto.CryptoMonnaie;
import mg.working.cryptomonnaie.model.transaction.TransactionCrypto;
import mg.working.cryptomonnaie.repository.crypto.CryptoMonnaieRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

@Service
public class FirestoreService {
    private static final String API_KEY = "AIzaSyDqJcwmsb1DXwPT3-v-8G7MKpMmetSBIgk";
    private static final String FIREBASE_PROJECT_ID = "cryptosyncs5";
    private static final String FIRESTORE_URL = "https://firestore.googleapis.com/v1/projects/";
    private static final String DATABASE_PATH = "/databases/(default)/documents/operation/";
    private static final String CRYPTO_COLLECTION_PATH = "crypto-monnaies";
    private static final String TRANSACTION_COLLECTION_PATH = "transactions";

    private final CryptoMonnaieRepository cryptoMonnaieRepository;
    private final RestTemplate restTemplate;
    private final FirebaseService firebaseService;

    @Autowired
    public FirestoreService(FirebaseService firebaseService, CryptoMonnaieRepository cryptoMonnaieRepository, RestTemplate restTemplate) {
        this.cryptoMonnaieRepository = cryptoMonnaieRepository;
        this.restTemplate = restTemplate;
        this.firebaseService = firebaseService;
    }

    // Méthode pour supprimer un document Firestore
    public void deleteFirestoreDocument(String documentId) {
        try {
            String deleteUrl = FIRESTORE_URL + FIREBASE_PROJECT_ID + DATABASE_PATH + documentId + "?key=" + API_KEY;
            System.out.println("URL de suppression : " + deleteUrl);
            ResponseEntity<String> response = restTemplate.exchange(deleteUrl, HttpMethod.DELETE, null, String.class);
            System.out.println("Réponse du serveur : " + response.getBody());

            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("Document supprimé avec succès : " + documentId);
            } else {
                System.out.println("Erreur lors de la suppression du document (code : " + response.getStatusCode() + ") : " + documentId);
            }
        } catch (Exception e) {
            System.out.println("Erreur lors de la suppression du document : " + documentId);
            e.printStackTrace();
        }
    }

    public void syncCryptoMonnaiesToFirestore() {
        System.out.println("ENTRAIN DE SYNC ");
        Firestore db = firebaseService.getFirestore();
        CollectionReference cryptoCollection = db.collection(CRYPTO_COLLECTION_PATH);
        List<CryptoMonnaie> cryptoMonnaies = cryptoMonnaieRepository.findAll();

        for (CryptoMonnaie crypto : cryptoMonnaies) {
            Map<String, Object> cryptoData = new HashMap<>();
            cryptoData.put("id_crypto_monnaie", crypto.getId());
            cryptoData.put("designation", crypto.getDesignation());
            cryptoData.put("prix_unitaire", crypto.getPrixUnitaire());
            cryptoData.put("symbol", crypto.getSymbol());

            try {
                WriteResult result = cryptoCollection.document(String.valueOf(crypto.getId())).set(cryptoData).get();
                System.out.println("Document ajouté/modifié à : " + result.getUpdateTime());
            } catch (InterruptedException | ExecutionException e) {
                System.err.println("Erreur Firestore : " + e.getMessage());
            }
        }
    }

    // Nouvelle méthode pour insérer une transaction dans Firestore
    public void syncTransactionToFirestore(TransactionCrypto transactionCrypto) {
        Firestore db = firebaseService.getFirestore();
        CollectionReference transactionCollection = db.collection(TRANSACTION_COLLECTION_PATH);

        if (transactionCrypto == null || transactionCrypto.getUtilisateur() == null) {
            System.err.println("Transaction ou utilisateur invalide, annulation de la synchronisation.");
            return;
        }

        // Préparer les données de la transaction
        Map<String, Object> transactionData = new HashMap<>();
        transactionData.put("id", transactionCrypto.getId());
        transactionData.put("id_utilisateur", transactionCrypto.getUtilisateur().getId());
        transactionData.put("mail", transactionCrypto.getUtilisateur().getMail());
        transactionData.put("id_crypto", transactionCrypto.getCryptoMonnaie().getId());
        transactionData.put("quantite", transactionCrypto.getQuantite().doubleValue());
        transactionData.put("prix_total", transactionCrypto.getPrixTotal().doubleValue());
        transactionData.put("date_heure", transactionCrypto.getDateHeure().toString());
        transactionData.put("type_transaction", transactionCrypto.getTypeTransaction().toString());
        transactionData.put("pourcentage_commission", transactionCrypto.getPourcentage_commission().doubleValue());
        transactionData.put("valeur_commission", transactionCrypto.getValeur_commission().doubleValue());

        // Insérer dans Firestore
        try {
            WriteResult result = transactionCollection.document(String.valueOf(transactionCrypto.getId())).set(transactionData).get();
            System.out.println("Transaction insérée dans Firestore : " + transactionCrypto.getId() +
                    " (ajoutée à : " + result.getUpdateTime() + ")");
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Erreur Firestore lors de l'insertion de la transaction : " + e.getMessage());
        }
    }
}
