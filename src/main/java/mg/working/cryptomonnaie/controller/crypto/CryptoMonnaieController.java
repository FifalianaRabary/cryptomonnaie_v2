package mg.working.cryptomonnaie.controller.crypto;

import jakarta.servlet.http.HttpServletRequest;
import mg.working.cryptomonnaie.services.crypto.CryptoMonnaieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CryptoMonnaieController {
    @Autowired
    CryptoMonnaieService cryptoMonnaieService;
    @GetMapping("/graph")
    public String index (HttpServletRequest request){
        request.setAttribute("cryptos", cryptoMonnaieService.getAllCrypto());
        return "crypto/graphCrypto";
    }
}
