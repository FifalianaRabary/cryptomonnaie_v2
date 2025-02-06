package mg.working.cryptomonnaie.services.login;

import mg.working.cryptomonnaie.model.user.Utilisateur;
import mg.working.cryptomonnaie.model.util.ConfirmationAuth;
import mg.working.cryptomonnaie.services.utilisateur.UtilisateurService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

@Service
public class InscriptionService {

    @Autowired
    UtilisateurService utilisateurService;

    private final RestTemplate restTemplate;

    private final String SYMFONY_API_URL = "http://localhost:8000/inscription"; // URL de l'API Symfony

    private final String SYMFONY_AUTH_URL = "http://localhost:8000/authentification"; // URL de l'API Symfony

    private final String SYMFONY_PIN_URL = "http://localhost:8000/confirmPin"; // URL du service Symfony



    public InscriptionService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ConfirmationAuth confirmerPin(int idUtilisateur, String pin) {
        // Construire les données pour l'appel à l'API Symfony
        String jsonPayload = String.format(
                "{\"id_utilisateur\": \"%d\", \"pin\": \"%s\"}",
                idUtilisateur, pin
        );

        // Créer un en-tête HTTP pour l'envoi
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        // Créer une entité HTTP avec les données et les en-têtes
        HttpEntity<String> requestEntity = new HttpEntity<>(jsonPayload, headers);

        // Appeler l'API Symfony pour vérifier le PIN
        ResponseEntity<String> response = restTemplate.exchange(SYMFONY_PIN_URL, HttpMethod.POST, requestEntity, String.class);

        // Vérifier la réponse de l'API Symfony
        if (response.getStatusCode() == HttpStatus.OK) {
            // Si le PIN est validé et qu'un jeton a été créé
            String responseBody = response.getBody();
            JSONObject jsonResponse = new JSONObject(responseBody);

            // Vérifier le statut et le message retournés par l'API
            if ("success".equals(jsonResponse.getString("status"))) {
                String jeton = jsonResponse.getJSONObject("data").getString("jeton");
                return new ConfirmationAuth(
                        jeton,
                        true
                );
            } else {
                String jeton = "Erreur lors de la validation du PIN : " + jsonResponse.getJSONObject("error").getString("message");
                return new ConfirmationAuth(
                        jeton,
                        false
                );
            }
        } else {
            // Si l'API Symfony a renvoyé une erreur
            String jeton = "Erreur lors de l'appel à l'API Symfony : " + response.getBody();
            return new ConfirmationAuth(
                    jeton,
                    false
            );
        }
    }

    public String authentifierUtilisateur(String email, String motDePasse) {
        try {
            // Préparer les données d'authentification
            String jsonPayload = String.format(
                    "{\"mail\": \"%s\", \"mdp\": \"%s\"}",
                    email, motDePasse
            );

            // Créer les en-têtes HTTP
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");

            // Créer une entité HTTP avec le payload et les en-têtes
            HttpEntity<String> requestEntity = new HttpEntity<>(jsonPayload, headers);

            // Envoyer la requête POST à l'API Symfony
            ResponseEntity<String> response = restTemplate.exchange(
                    SYMFONY_AUTH_URL,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );

            // Vérifier la réponse
            if (response.getStatusCode() == HttpStatus.OK) {
                // Réponse réussie
                return "success"; // Retourne le JSON en tant que réponse
            } else {
                // Erreur retournée par Symfony
                return "Erreur d'authentification : " + response.getBody();
            }
        } catch (Exception e) {
            // Gérer les exceptions côté Spring Boot
            return "Erreur lors de l'appel à l'API Symfony : " + e.getMessage();
        }
    }

    public String inscrireUtilisateur(String nom, String dateNaissance, String email, String motDePasse) {
        // Préparer les données de l'utilisateur à envoyer à l'API Symfony
        String jsonPayload = String.format(
                "{\"nom\": \"%s\", \"dateNaissance\": \"%s\", \"email\": \"%s\", \"motDePasse\": \"%s\"}",
                nom, dateNaissance, email, motDePasse
        );

        // Créer un en-tête HTTP pour l'envoi
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        // Créer une entité HTTP avec les données et les en-têtes
        HttpEntity<String> requestEntity = new HttpEntity<>(jsonPayload, headers);

        // Appeler l'API Symfony et récupérer la réponse
        ResponseEntity<String> response = restTemplate.exchange(SYMFONY_API_URL, HttpMethod.POST, requestEntity, String.class);

        // Vérifier si l'API a renvoyé une réponse réussie
        if (response.getStatusCode() == HttpStatus.OK) {
            return "Inscription réussie, veuillez vérifier votre e-mail.";
        } else {
            return "Erreur lors de l'inscription : " + response.getBody();
        }
    }

    // public String confirmerInscription(String jeton) {
    //     // Construire l'URL pour confirmer l'inscription avec le jeton
    //     String confirmUrl = "http://localhost:8000/confirm/" + jeton;

    //     // Appeler l'API Symfony pour confirmer l'inscription
    //     ResponseEntity<String> response = restTemplate.exchange(confirmUrl, HttpMethod.GET, null, String.class);

    //     // Vérifier si l'API a renvoyé une réponse réussie
    //     if (response.getStatusCode() == HttpStatus.OK) {
    //         // Extraire les informations de l'utilisateur à partir de la réponse JSON
    //         String responseBody = response.getBody();

    //         // Par exemple, on suppose que la réponse est un JSON avec un champ "utilisateur" qui contient les informations
    //         // Vous pouvez utiliser une bibliothèque comme Jackson ou Gson pour extraire les données du JSON
    //         // Ici, c'est une simplification de l'extraction
    //         JSONObject jsonResponse = new JSONObject(responseBody);
    //         String utilisateurEmail = jsonResponse.getJSONObject("data").getJSONObject("utilisateur").getString("email");
    //         String utilisateurNom = jsonResponse.getJSONObject("data").getJSONObject("utilisateur").getString("nom");
    //         String utilisateurMdp = jsonResponse.getJSONObject("data").getJSONObject("utilisateur").getString("mdp");
    //         String utilisateurDtn = jsonResponse.getJSONObject("data").getJSONObject("utilisateur").getString("dateNaissance");

    //         Utilisateur utilisateur = new Utilisateur();
    //         utilisateur.setDtn(utilisateurDtn);
    //         utilisateur.setNom(utilisateurNom);
    //         utilisateur.setMdp(utilisateurMdp);
    //         utilisateur.setSolde("100000");
    //         utilisateur.setMail(utilisateurEmail);
    //         utilisateurService.insertUtilisateur(utilisateur);

    //         // Retourner les informations de l'utilisateur (ou un autre traitement)
    //         return "Inscription confirmée avec succès. Utilisateur: " + utilisateurNom + " (" + utilisateurEmail + ")";
    //     } else {
    //         return "Erreur lors de la confirmation du jeton : " + response.getBody();
    //     }

    // }


    public Utilisateur confirmerInscription(String jeton) {
        // Construire l'URL pour confirmer l'inscription avec le jeton
        String confirmUrl = "http://localhost:8000/confirm/" + jeton;
    
        // Appeler l'API Symfony pour confirmer l'inscription
        ResponseEntity<String> response = restTemplate.exchange(confirmUrl, HttpMethod.GET, null, String.class);
    
        // Vérifier si l'API a renvoyé une réponse réussie
        if (response.getStatusCode() == HttpStatus.OK) {
            // Extraire les informations de l'utilisateur à partir de la réponse JSON
            String responseBody = response.getBody();
    
            // Parse le JSON de la réponse
            JSONObject jsonResponse = new JSONObject(responseBody);
            String utilisateurEmail = jsonResponse.getJSONObject("data").getJSONObject("utilisateur").getString("email");
            String utilisateurNom = jsonResponse.getJSONObject("data").getJSONObject("utilisateur").getString("nom");
            String utilisateurMdp = jsonResponse.getJSONObject("data").getJSONObject("utilisateur").getString("mdp");
            String utilisateurDtn = jsonResponse.getJSONObject("data").getJSONObject("utilisateur").getString("dateNaissance");
    
            // Création et sauvegarde de l'utilisateur
            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setDtn(utilisateurDtn);
            utilisateur.setNom(utilisateurNom);
            utilisateur.setMdp(utilisateurMdp);
            utilisateur.setSolde("100000");
            utilisateur.setMail(utilisateurEmail);
    
            Utilisateur utilisateurInsere = utilisateurService.getInsertedUtilisateur(utilisateur);
    
            // Retourne l'utilisateur inséré avec l'ID attribué
            return utilisateurInsere;
        } else {
            throw new RuntimeException("Erreur lors de la confirmation du jeton : " + response.getBody());
        }
    }
    

}

