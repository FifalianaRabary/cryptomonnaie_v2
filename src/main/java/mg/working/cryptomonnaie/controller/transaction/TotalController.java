package mg.working.cryptomonnaie.controller.transaction;

import jakarta.servlet.http.HttpServletRequest;
import mg.working.cryptomonnaie.model.user.Utilisateur;
import mg.working.cryptomonnaie.services.transaction.TransactionCryptoService;
import mg.working.cryptomonnaie.services.utilisateur.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
public class TotalController {
    @Autowired
    UtilisateurService utilisateurService;
    @Autowired
    TransactionCryptoService transactionCryptoService;

    @GetMapping("/getTotal")
    public String getListFiltre(HttpServletRequest request) {
        String dateMax = request.getParameter("dateMax");
        if (dateMax == null || dateMax.isEmpty()) {
            dateMax = LocalDate.now().toString();
        }
        request.setAttribute("dateMax", dateMax);
        List<Utilisateur> utilisateurs = this.utilisateurService.getAllUtilisateur();
        TransactionCryptoService transactionCryptoService = new TransactionCryptoService();
        request.setAttribute("transactionCryptoService", transactionCryptoService);
        request.setAttribute("utilisateurs", utilisateurs);
        return "/transaction/total";
    }
}
