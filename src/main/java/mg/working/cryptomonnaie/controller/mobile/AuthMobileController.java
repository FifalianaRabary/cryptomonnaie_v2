package mg.working.cryptomonnaie.controller.mobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import mg.working.cryptomonnaie.services.mobile.AuthMobileService;

@RestController
public class AuthMobileController {

    private final AuthMobileService authMobileService;

    @Autowired
    public AuthMobileController(AuthMobileService authMobileService) {
        this.authMobileService = authMobileService;
    }
    
    @PostMapping("/authMobile")
    public ResponseEntity<String> login(@RequestParam("mail") String mail, @RequestParam("mdp") String mdp) {
        ResponseEntity<String> response = authMobileService.authMobile(mail, mdp);
        
        return response;
    }
}
