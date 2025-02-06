package mg.working.cryptomonnaie.controller.transaction;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import mg.working.cryptomonnaie.model.crypto.CryptoMonnaie;
import mg.working.cryptomonnaie.model.transaction.Portefeuille;
import mg.working.cryptomonnaie.model.transaction.Total;
import mg.working.cryptomonnaie.model.transaction.TransactionCrypto;
import mg.working.cryptomonnaie.model.user.Utilisateur;
import mg.working.cryptomonnaie.services.transaction.PortefeuilleService;
import mg.working.cryptomonnaie.services.transaction.TransactionCryptoService;
import mg.working.cryptomonnaie.services.utilisateur.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TotalController {
    @Autowired
    private UtilisateurService utilisateurService;
    
    @Autowired
    private TransactionCryptoService transactionCryptoService;

    @Autowired
    PortefeuilleService portefeuilleService;

    @GetMapping("/getTotal")
    public String getTotal(HttpServletRequest request) {
        List<Total> totals = new ArrayList<>();
        request.setAttribute("totals", totals);
        return "/transaction/total";
    }

    @PostMapping("getTotalFiltre")
    public String getTotalFiltre(@RequestParam String dateMax, HttpServletRequest request, HttpSession session) {
        List<Utilisateur> utilisateurs = utilisateurService.getAllUtilisateur();
        List<Total> totals = new ArrayList<>();
        for (Utilisateur utilisateur : utilisateurs) {
            Total total = new Total();
            List<TransactionCrypto> transactionCryptos = transactionCryptoService.findByIdUtilisateur(utilisateur.getId());
            double valeurPortefeuille = portefeuilleService.getValeurPortefeuilleByUtilisateur(utilisateur);
            total.setUtilisateur(utilisateur);
            total.setTransactions(transactionCryptos);
            total.setTotalAchat();
            total.setTotalVente();
            total.setValeurPortefeuille(valeurPortefeuille);
            totals.add(total);
        }
        request.setAttribute("totals", totals);
        return "/transaction/total";
    }
}

