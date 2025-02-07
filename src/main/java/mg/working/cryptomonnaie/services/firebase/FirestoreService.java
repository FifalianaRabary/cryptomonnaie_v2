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
import mg.working.cryptomonnaie.repository.crypto.CryptoMonnaieRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;


import com.google.cloud.firestore.DocumentReference;

import mg.working.cryptomonnaie.services.firebase.FirebaseService;

import java.util.Collections;

@Service
public class FirestoreService {
    private static final String API_KEY = "AIzaSyDqJcwmsb1DXwPT3-v-8G7MKpMmetSBIgk";
    private static final String FIREBASE_PROJECT_ID = "cryptosyncs5";
    private static final String FIRESTORE_URL = "https://firestore.googleapis.com/v1/projects/";
    private static final String DATABASE_PATH = "/databases/(default)/documents/operation/";
    private static final String CRYPTO_COLLECTION_PATH = "crypto-monnaies";
   
    private final CryptoMonnaieRepository cryptoMonnaieRepository;

    private final RestTemplate restTemplate;

    private final FirebaseService firebaseService;

    @Autowired
    public FirestoreService(FirebaseService firebaseService,CryptoMonnaieRepository cryptoMonnaieRepository,RestTemplate restTemplate) {
        this.cryptoMonnaieRepository = cryptoMonnaieRepository;

        this.restTemplate = restTemplate;
        this.firebaseService = firebaseService;
    }

    // Méthode pour supprimer un document de Firestore
    public void deleteFirestoreDocument(String documentId) {
        try {
            // Construire l'URL pour la suppression du document Firestore
            String deleteUrl = FIRESTORE_URL + FIREBASE_PROJECT_ID + DATABASE_PATH + documentId + "?key=" + API_KEY;

            // Log de l'URL pour vérifier qu'elle est correcte
            System.out.println("URL de suppression : " + deleteUrl);

            // Effectuer la requête DELETE pour supprimer le document
            ResponseEntity<String> response = restTemplate.exchange(deleteUrl, HttpMethod.DELETE, null, String.class);

            // Afficher la réponse du serveur pour débogage
            System.out.println("Réponse du serveur : " + response.getBody());

            // Si la suppression est réussie, cela devrait être loggé
            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("Document supprimé avec succès : " + documentId);
            } else {
                System.out.println("Erreur lors de la suppression du document (code : " + response.getStatusCode() + ") : " + documentId);
            }
        } catch (Exception e) {
            // Log détaillé en cas d'erreur
            System.out.println("Erreur lors de la suppression du document : " + documentId);
            e.printStackTrace();
        }
    }


    public void syncCryptoMonnaiesToFirestore() {
        System.out.println("ENTRAIN DE SYNC ");

        // Récupération de la base Firestore
        Firestore db = firebaseService.getFirestore();
        CollectionReference cryptoCollection = db.collection(CRYPTO_COLLECTION_PATH);

        // Récupérer toutes les crypto-monnaies depuis la base locale
        List<CryptoMonnaie> cryptoMonnaies = cryptoMonnaieRepository.findAll();

        for (CryptoMonnaie crypto : cryptoMonnaies) {
            // Préparer les données
            Map<String, Object> cryptoData = new HashMap<>();
            cryptoData.put("id_crypto_monnaie", crypto.getId());
            cryptoData.put("designation", crypto.getDesignation());
            cryptoData.put("prix_unitaire", crypto.getPrixUnitaire());
            cryptoData.put("symbol", crypto.getSymbol());

            try {
                // Ajouter ou mettre à jour le document Firestore
                WriteResult result = cryptoCollection.document(String.valueOf(crypto.getId())).set(cryptoData).get();
                System.out.println("Document ajouté/modifié à : " + result.getUpdateTime());
            } catch (InterruptedException | ExecutionException e) {
                System.err.println("Erreur Firestore : " + e.getMessage());
            }
        }
    }
    

}
