package mg.working.cryptomonnaie.controller.transaction;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import mg.working.cryptomonnaie.model.transaction.Portefeuille;
import mg.working.cryptomonnaie.services.transaction.PortefeuilleService;
import mg.working.cryptomonnaie.services.utilisateur.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/portefeuille")
public class PortefeuilleController {
    @Autowired
    PortefeuilleService portefeuilleService;
    @Autowired
    UtilisateurService utilisateurService;

    @GetMapping("/listPortefeuille")
    public String getPortefeuilleUser(HttpSession session) {
        List<Portefeuille> portefeuilleUser = this.portefeuilleService.getPortefeuilleByUser(this.utilisateurService.getUtilisateurById(1));
        session.setAttribute("portefeuilleUser", portefeuilleUser);
        return "crypto/portefeuilleUser";
    }

    @GetMapping("/portefeuilleUser")
    public String goToListPortefeuilleUser() {
        return "crypto/portefeuilleUser";
    }
}
