package mg.working.cryptomonnaie.services.firebase;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import mg.working.cryptomonnaie.model.user.ImageUtilisateur;
import mg.working.cryptomonnaie.repository.utilisateur.ImageUtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;


@Service
public class ImageUtilisateurSync {

    private static final String IMAGE_COLLECTION_PATH = "image-utilisateur";

    @Autowired
    private FirebaseService firebaseService;

    @Autowired
    private ImageUtilisateurRepository imageUtilisateurRepository;




    public void syncToFirebase(ImageUtilisateur imageUtilisateur) {
        System.out.println("ENTRAIN DE SYNC IMAGEEEEEEEEEEEEEE");
        Firestore db = firebaseService.getFirestore();

        // Création d'une map avec uniquement les champs nécessaires
        Map<String, Object> imageData = new HashMap<>();
        imageData.put("id", imageUtilisateur.getId());
        imageData.put("mail", imageUtilisateur.getMail());
        imageData.put("url", imageUtilisateur.getUrl());
        imageData.put("id_utilisateur", imageUtilisateur.getUtilisateur().getId());

        ApiFuture<WriteResult> future = db.collection(IMAGE_COLLECTION_PATH)
                .document(imageUtilisateur.getMail()) // Utilisation du mail comme identifiant
                .set(imageData);

        try {
            WriteResult result = future.get(); // Attente de l'exécution
            System.out.println("Synchronisé avec Firebase : " + imageUtilisateur.getMail() +
                    " (mise à jour à : " + result.getUpdateTime() + ")");
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Erreur de synchronisation Firebase : " + e.getMessage());
        }
    }


    @PostConstruct
    public void init() {
        listenForFirebaseUpdates();
    }
    
    public void listenForFirebaseUpdates() 
    {
        Firestore db = firebaseService.getFirestore();
        System.out.println("Écoute Firebase activée...");

        db.collection(IMAGE_COLLECTION_PATH)
            .addSnapshotListener((snapshots, e) -> {
                if (e != null) {
                    System.err.println("Erreur d'écoute Firebase : " + e.getMessage());
                    return;
                }

                if (snapshots != null && !snapshots.isEmpty()) {
                    System.out.println("Changement détecté dans Firebase !");

                    for (DocumentChange dc : snapshots.getDocumentChanges()) {
                        DocumentSnapshot doc = dc.getDocument();
                        String mail = doc.getId();
                        String url = doc.getString("url");

                        System.out.println("Type de changement détecté : " + dc.getType());
                        System.out.println("Mail: " + mail + ", URL: " + url);

                        ImageUtilisateur imageUtilisateur = imageUtilisateurRepository.findByMail(mail).orElse(null);

                        if (dc.getType() == DocumentChange.Type.ADDED) {
                            if (imageUtilisateur == null) {
                                ImageUtilisateur newImage = new ImageUtilisateur();
                                newImage.setMail(mail);
                                newImage.setUrl(url);
                                imageUtilisateurRepository.save(newImage);
                                System.out.println("Nouvelle image ajoutée localement pour : " + mail);
                            }
                        } else if (dc.getType() == DocumentChange.Type.MODIFIED) {
                            if (imageUtilisateur != null) {
                                imageUtilisateur.setUrl(url);
                                imageUtilisateurRepository.save(imageUtilisateur);
                                System.out.println("Image mise à jour localement pour : " + mail);
                            } else {
                                System.out.println("⚠ Avertissement : Modification détectée mais l'utilisateur n'existe pas localement !");
                            }
                        } else if (dc.getType() == DocumentChange.Type.REMOVED) {
                            if (imageUtilisateur != null) {
                                imageUtilisateurRepository.delete(imageUtilisateur);
                                System.out.println("Image supprimée localement pour : " + mail);
                            }
                        }
                    }
                }
            });

        // Vérification initiale pour voir si Firebase est accessible
        try {
            QuerySnapshot snapshots = db.collection(IMAGE_COLLECTION_PATH).get().get();
            if (!snapshots.isEmpty()) {
                System.out.println("Connexion Firebase réussie, documents trouvés.");
            } else {
                System.out.println("Aucun document trouvé dans Firebase.");
            }
        } catch (InterruptedException | ExecutionException ex) {
            System.err.println("Erreur lors de la connexion à Firebase : " + ex.getMessage());
        }
        
    }

}
