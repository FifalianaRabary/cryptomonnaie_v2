package mg.working.cryptomonnaie.controller.mobile;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mg.working.cryptomonnaie.services.mobile.AuthMobileService;

@RestController
public class AuthMobileController {

    private final AuthMobileService authMobileService;


    public AuthMobileController(AuthMobileService authMobileService) {
        this.authMobileService = authMobileService;
    }
    
    @PostMapping("/authMobile")
    public ResponseEntity<String> login(@RequestParam("mail") String mail, @RequestParam("mdp") String mdp) {
        try {
            // Authentifier l'utilisateur via Firebase Auth
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(mdp);
            String uid = decodedToken.getUid();
            
            // Vérifier si l'email est bien associé au bon utilisateur Firebase
            boolean isUserValid = authMobileService.isValidUser(mail, uid);
            
            if (isUserValid) {
                return ResponseEntity.status(HttpStatus.OK).body("Authentification réussie");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Utilisateur non autorisé");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Erreur d'authentification : " + e.getMessage());
        }
    }
}
