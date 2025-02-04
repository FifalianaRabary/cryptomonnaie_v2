package mg.working.cryptomonnaie.controller.transaction;

import jakarta.servlet.http.HttpSession;
import mg.working.cryptomonnaie.model.analyse.MvtCommission;
import mg.working.cryptomonnaie.model.transaction.Operation;
import mg.working.cryptomonnaie.model.user.Utilisateur;
import mg.working.cryptomonnaie.services.transaction.OperationService;
import mg.working.cryptomonnaie.services.utilisateur.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Controller
public class OperationController {

    @Autowired
    OperationService operationService;

    @Autowired
    UtilisateurService utilisateurService;

    @GetMapping("operation")
    public String operation (HttpSession session) {
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        Utilisateur newUtilisateur = utilisateurService.getUtilisateurById(utilisateur.getId());
        session.setAttribute("utilisateur", newUtilisateur);
        return "transaction/operation";
    }

    @PostMapping("insertOperation")
    public String insertOperation (@RequestParam String typeOperation, @RequestParam double montant, @RequestParam int utilisateurId) {
        Operation operation = new Operation();

        operation.setTypeOperation(Operation.TypeOperation.valueOf(typeOperation));
        operation.setDateHeureOperation(LocalDateTime.now());
        operation.setMontant(BigDecimal.valueOf(montant));
        operation.setUtilisateur(utilisateurService.getUtilisateurById(utilisateurId));
        operationService.save(operation);

        return "redirect:/operation";
    }
}
