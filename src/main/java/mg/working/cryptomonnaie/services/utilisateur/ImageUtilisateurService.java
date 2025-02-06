package mg.working.cryptomonnaie.services.utilisateur;

import mg.working.cryptomonnaie.model.user.ImageUtilisateur;
import mg.working.cryptomonnaie.model.user.Utilisateur;
import mg.working.cryptomonnaie.repository.utilisateur.ImageUtilisateurRepository;
import mg.working.cryptomonnaie.repository.utilisateur.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ImageUtilisateurService {

    String URL_DEFAULT_PROFILE_IMAGE = "https://i.imgur.com/tWfEHUb.jpeg";

    @Autowired
    private ImageUtilisateurRepository imageUtilisateurRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private UtilisateurService utilisateurService;

    public void saveImageUtilisateur(ImageUtilisateur imageUtilisateur) {
        imageUtilisateurRepository.save(imageUtilisateur);
    }

    public void createDefaultProfileImage(Utilisateur utilisateur) {
        ImageUtilisateur imageUtilisateur = new ImageUtilisateur(utilisateur, URL_DEFAULT_PROFILE_IMAGE);
        imageUtilisateurRepository.save(imageUtilisateur);
    }

    public String getImageByUtilisateur(Utilisateur utilisateur) {
        Optional<ImageUtilisateur> image = imageUtilisateurRepository.findByUtilisateur(utilisateur);
        return image.map(ImageUtilisateur::getUrl).orElse(URL_DEFAULT_PROFILE_IMAGE);
    }

    public void updateImageUtilisateur(ImageUtilisateur imageUtilisateur) {
        if (imageUtilisateurRepository.existsById(imageUtilisateur.getId())) {
            // Si l'image existe déjà, la mettre à jour
            imageUtilisateurRepository.save(imageUtilisateur);
        } else {
            // Si l'image n'existe pas, vous pourriez soit lancer une exception, soit la créer
            throw new EntityNotFoundException("ImageUtilisateur with ID " + imageUtilisateur.getId() + " not found");
        }

    }

    public ImageUtilisateur getImageByIdUtilisateur(int utilisateurId) {
        // Recherche l'image associée à l'utilisateur par son ID
        return imageUtilisateurRepository.findByUtilisateurId(utilisateurId);
    }

    public void updateImageByIdUtilisateur(int utilisateurId, String newImageUrl) {
        // Récupérer l'image existante associée à l'utilisateur par son ID
        ImageUtilisateur imageUtilisateur = imageUtilisateurRepository.findByUtilisateurId(utilisateurId);

        // Si l'image existe, mettre à jour son URL
        if (imageUtilisateur != null) {
            imageUtilisateur.setUrl(newImageUrl);  // Mise à jour de l'URL de l'image
            imageUtilisateurRepository.save(imageUtilisateur);  // Sauvegarde dans la base de données
            System.out.println("Image mise à jour avec succès !");
        } else {
            System.out.println("Aucune image trouvée pour cet utilisateur.");
        }
    }

    public Map<Integer, String> getImageByUtilisateur () {
        Map<Integer, String> imageUtilisateur = new HashMap<Integer, String>();
        List<Utilisateur> utilisateurs = utilisateurService.getAllUtilisateur();
        for (Utilisateur utilisateur : utilisateurs) {
            String url = getImageByUtilisateur(utilisateur);
            imageUtilisateur.put(utilisateur.getId(), url);
        }
        return imageUtilisateur;
    }




}
