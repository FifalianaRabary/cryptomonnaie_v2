package mg.working.cryptomonnaie.controller.firebase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;

import mg.working.cryptomonnaie.services.firebase.FirebaseService;

import java.util.Collections;




@RestController
public class FirestoreController {

    @Autowired
    private FirebaseService firebaseService;

    @GetMapping("/test")
    public String testFirestore() {
        try {
            Firestore db = firebaseService.getFirestore();
            DocumentReference docRef = db.collection("test").document("testDoc");
            docRef.set(Collections.singletonMap("message", "Hello Firebase"));
            return "Connexion Firestore OK";
        } catch (Exception e) {
            return "Erreur Firestore : " + e.getMessage();
        }
    }
}

