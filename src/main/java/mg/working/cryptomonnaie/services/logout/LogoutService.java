package mg.working.cryptomonnaie.services.logout;

import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;


@Service
public class LogoutService {

    private final RestTemplate restTemplate;
    private final String SYMFONY_API_URL = "http://localhost:8000/deconnexion"; // URL correcte

    public LogoutService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean deconnexion(String jeton) {
        try {
            // Construire le JSON avec le jeton
            JSONObject requestBody = new JSONObject();
            requestBody.put("jeton", jeton);

            // Construire les headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Construire la requête HTTP
            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody.toString(), headers);

            // Appeler l'API Symfony
            ResponseEntity<String> response = restTemplate.exchange(
                SYMFONY_API_URL, HttpMethod.POST, requestEntity, String.class
            );

            // Vérifier si la déconnexion a réussi (HTTP 200)
            return response.getStatusCode() == HttpStatus.OK;
        } catch (Exception e) {
            System.err.println("Erreur lors de la déconnexion : " + e.getMessage());
            return false;
        }
    }

}
