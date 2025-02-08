package mg.working.cryptomonnaie.services.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import mg.working.cryptomonnaie.model.crypto.CryptoMonnaie;
import mg.working.cryptomonnaie.repository.crypto.CryptoMonnaieRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class CryptomonnaieSync {

    private final Firestore db;
    private final CryptoMonnaieRepository cryptoMonnaieRepository;
    private static final String CRYPTO_COLLECTION_PATH = "crypto-monnaies";


    // Injection du service Firebase pour récupérer la référence Firestore
    @Autowired
    public CryptomonnaieSync(FirebaseService firebaseService, CryptoMonnaieRepository cryptoMonnaieRepository) {
        this.db = firebaseService.getFirestore();  // Obtient Firestore via FirebaseService
        this.cryptoMonnaieRepository = cryptoMonnaieRepository;
    }

    @Scheduled(fixedRate = 10000)  // Exécution toutes les 10 secondes
    public void syncCryptoMonnaiesToFirestore() {
        System.out.println("SYNC CRYPTO DEBUT ");
        try {
            // Récupérer toutes les cryptomonnaies mises à jour
            List<CryptoMonnaie> cryptoMonnaies = cryptoMonnaieRepository.findAll();

            for (CryptoMonnaie cryptoMonnaie : cryptoMonnaies) {
                // Récupérer la référence du document Firestore
                DocumentReference docRef = db.collection(CRYPTO_COLLECTION_PATH).document(String.valueOf(cryptoMonnaie.getId()));

                // Construire les données à envoyer (map)
                Map<String, Object> updates = new HashMap<>();
                updates.put("designation", cryptoMonnaie.getDesignation());
                updates.put("symbol", cryptoMonnaie.getSymbol());
                updates.put("prix_unitaire", cryptoMonnaie.getPrixUnitaire());

                // Mettre à jour les données dans Firestore
                WriteResult result = docRef.set(updates).get();  // .set() permet de remplacer ou créer un document

                // Log de la mise à jour
                System.out.println("Données synchronisées pour : " + cryptoMonnaie.getDesignation() + " à " + result.getUpdateTime());
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
