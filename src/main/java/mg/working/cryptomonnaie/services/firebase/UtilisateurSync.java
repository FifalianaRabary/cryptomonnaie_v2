package mg.working.cryptomonnaie.services.firebase;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import mg.working.cryptomonnaie.model.user.Utilisateur;
import mg.working.cryptomonnaie.repository.utilisateur.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.annotation.PostConstruct;

@Service
public class UtilisateurSync {

    private static final String UTILISATEUR_COLLECTION_PATH = "utilisateurs";

    @Autowired
    private FirebaseService firebaseService;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    // Méthode de synchronisation appelée lors de l'ajout d'un utilisateur
    public void syncToFirebase(Utilisateur utilisateur) {
        System.out.println("Synchronisation de l'utilisateur avec Firebase...");

        Firestore db = firebaseService.getFirestore();

        // Création d'une map avec uniquement les champs nécessaires
        Map<String, Object> utilisateurData = new HashMap<>();
        utilisateurData.put("id", utilisateur.getId());
        utilisateurData.put("nom", utilisateur.getNom());
        utilisateurData.put("mail", utilisateur.getMail());
        utilisateurData.put("mdp", utilisateur.getMdp());
        utilisateurData.put("dtn", utilisateur.getDtn());
        utilisateurData.put("solde", utilisateur.getSolde());

        ApiFuture<WriteResult> future = db.collection(UTILISATEUR_COLLECTION_PATH)
                .document(utilisateur.getMail()) // Utilisation de l'email comme identifiant
                .set(utilisateurData);

        try {
            WriteResult result = future.get(); // Attente de l'exécution
            System.out.println("Utilisateur synchronisé avec Firebase : " + utilisateur.getNom() +
                    " (mise à jour à : " + result.getUpdateTime() + ")");
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Erreur de synchronisation Firebase : " + e.getMessage());
        }
    }

    // Méthode pour écouter les ajouts de nouveaux utilisateurs
    // @PostConstruct
    public void listenForUtilisateurAdditions() {
        Firestore db = firebaseService.getFirestore();
        System.out.println("Écoute des ajouts d'utilisateurs activée...");

        db.collection("utilisateurs")
                .addSnapshotListener((snapshots, e) -> {
                    if (e != null) {
                        System.err.println("Erreur d'écoute Firebase : " + e.getMessage());
                        return;
                    }

                    if (snapshots != null && !snapshots.isEmpty()) {
                        System.out.println("Changement détecté dans la collection utilisateurs !");

                        for (DocumentChange dc : snapshots.getDocumentChanges()) {
                            DocumentSnapshot doc = dc.getDocument();
                            String mail = doc.getId(); // Utilisation de l'ID (l'email) comme clé
                            String nom = doc.getString("nom");

                            System.out.println("Type de changement détecté : " + dc.getType());
                            System.out.println("Utilisateur : " + nom + ", Mail: " + mail);

                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                // Si l'utilisateur est ajouté dans Firebase, on l'ajoute aussi à la base locale
                                Utilisateur newUser = new Utilisateur();
                                newUser.setMail(mail);
                                newUser.setNom(nom);
                                utilisateurRepository.save(newUser);
                                System.out.println("Utilisateur ajouté localement : " + nom);
                            }
                        }
                    }
                });
    }
}
