package mg.working.cryptomonnaie.services.utilisateur;

import jdk.jshell.execution.Util;
import mg.working.cryptomonnaie.model.user.Utilisateur;
import mg.working.cryptomonnaie.repository.utilisateur.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UtilisateurService {
    @Autowired
    UtilisateurRepository utilisateurRepository;

    
    private final RestTemplate restTemplate;
    private final String SYMFONY_MODIF_PROFIL_URL = "http://localhost:8000/utilisateur/modifier-complet"; // Assurez-vous que l'URL est correcte

    public UtilisateurService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Utilisateur getUtilisateurByEmail(String email) {
        return this.utilisateurRepository.findUserByEmail(email);
    }

    public void insertUtilisateur(Utilisateur utilisateur) {
        this.utilisateurRepository.save(utilisateur);
    }

    public List<Utilisateur> getAllUtilisateur() {
        return this.utilisateurRepository.findAll();
    }

    public Utilisateur getUtilisateurById(int id) {
        return this.utilisateurRepository.findById(id).orElse(null);
    }
    public Utilisateur getUtilisateurByIdLong(Long id) {
        // Cast Long to int
        int id_int = id.intValue();
        return this.utilisateurRepository.findById(id_int).orElse(null);
    }
    

    public Utilisateur getUtilisateurByEmailMdp(String email , String mdp) {
        return this.utilisateurRepository.findUserByEmailMdp(email, mdp);
    }

    public void updateSoldeUtilisateur(Utilisateur utilisateur , String solde) {
        utilisateur.setSolde(solde);
        this.insertUtilisateur(utilisateur);
    }


    public boolean updateUtilisateurProfileSymfony(Long id, String nom, String mdp, String dateNaissance) {
        // Create the request body
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("id", id);
        requestBody.put("nom", nom);
        requestBody.put("mdp", mdp);
        requestBody.put("dateNaissance", dateNaissance);

        // Send POST request to the Symfony API
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                SYMFONY_MODIF_PROFIL_URL,
                HttpMethod.POST,
                new HttpEntity<>(requestBody),
                String.class
            );
            
            // If the response status code is 2xx (success), return true
            return response.getStatusCode().is2xxSuccessful();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            // If there's an error from the server or client, return false
            return false;
        } catch (Exception e) {
            // If there's another error, return false
            return false;
        }
    }

    public boolean updateUtilisateurComplete(Utilisateur utilisateur) {
        // Mettre à jour l'utilisateur dans la base de données
        try {
            // Enregistrer ou mettre à jour l'utilisateur dans la base
            utilisateurRepository.save(utilisateur);
        } catch (Exception e) {
            // Si une erreur se produit lors de la mise à jour en base de données, retournez false
            return false;
        }

        // Appeler l'API Symfony pour mettre à jour les informations de l'utilisateur
        boolean isUpdatedInSymfony = updateUtilisateurProfileSymfony(
            Long.valueOf(utilisateur.getId()),  // Caster l'ID en Long
            utilisateur.getNom(),
            utilisateur.getMdp(),
            utilisateur.getDtn().toString() // Assurez-vous que le format de date est correct
        );

        return isUpdatedInSymfony;
    }



}
