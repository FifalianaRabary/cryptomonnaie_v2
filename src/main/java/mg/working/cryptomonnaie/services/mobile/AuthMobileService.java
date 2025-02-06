package mg.working.cryptomonnaie.services.mobile;

import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

@Service
public class AuthMobileService {

    private final RestTemplate restTemplate;
    private final String SYMFONY_API_URL = "http://localhost:8000/authMobile"; // Assurez-vous que l'URL est correcte

    public AuthMobileService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<String> authMobile(String mail, String mdp) {
        try {
            // Construire le corps de la requête en JSON
            JSONObject requestBody = new JSONObject();
            requestBody.put("mail", mail);
            requestBody.put("mdp", mdp);

            // Créer les en-têtes HTTP
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Créer l'entité HTTP avec le corps et les en-têtes
            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody.toString(), headers);

            // Log de la requête envoyée
            System.out.println("Envoi de la requête à Symfony: " + requestBody.toString());

            // Appeler l'API Symfony
            ResponseEntity<String> response = restTemplate.exchange(
                SYMFONY_API_URL, HttpMethod.POST, requestEntity, String.class
            );

            // Log de la réponse reçue
            System.out.println("Réponse reçue de Symfony: " + response.getBody());

            // Retourner exactement la réponse reçue depuis l'API Symfony
            return response;

        } catch (HttpClientErrorException | HttpServerErrorException e) {
            // Gestion des erreurs HTTP (4xx et 5xx)
            System.err.println("Erreur HTTP lors de l'appel à Symfony: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            return new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
        } catch (Exception e) {
            // Gestion des autres exceptions générales
            System.err.println("Erreur lors de l'appel à Symfony : " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
