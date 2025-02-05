package mg.working.cryptomonnaie.services.logout;

import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

@Service
public class LogoutService {

    private final RestTemplate restTemplate;
    private final String SYMFONY_API_URL = "http://localhost:8000/deconnexion"; // Assurez-vous que l'URL est correcte

    public LogoutService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean deconnexion(String jeton, int id_utilisateur) {
        try {
            // Construire le corps de la requête en JSON
            JSONObject requestBody = new JSONObject();
            requestBody.put("jeton", jeton);
            requestBody.put("id_utilisateur", id_utilisateur);

            // Créer les en-têtes HTTP
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Créer l'entité HTTP avec le corps et les en-têtes
            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody.toString(), headers);

            // Log de la requête envoyée
            System.out.println("Envoi de la requête à Symfony: " + requestBody.toString());

            // Appeler l'API Symfony pour la déconnexion
            ResponseEntity<String> response = restTemplate.exchange(
                SYMFONY_API_URL, HttpMethod.POST, requestEntity, String.class
            );

            // Log de la réponse reçue
            System.out.println("Réponse reçue: " + response.getBody());

            // Vérifier le statut de la réponse pour une déconnexion réussie
            if (response.getStatusCode() == HttpStatus.OK) {
                return true; // La déconnexion a réussi
            } else {
                // Si le code de statut n'est pas 200 OK, échouer
                System.err.println("Erreur lors de la déconnexion: " + response.getStatusCode());
                return false;
            }
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            // Gestion des erreurs HTTP (4xx et 5xx)
            System.err.println("Erreur HTTP lors de la déconnexion: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            return false;
        } catch (Exception e) {
            // Gestion des autres exceptions générales
            System.err.println("Erreur lors de la déconnexion : " + e.getMessage());
            return false;
        }
    }
}
