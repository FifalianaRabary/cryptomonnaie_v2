package mg.working.cryptomonnaie.controller.transaction;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mg.working.cryptomonnaie.model.analyse.MvtCommission;
import mg.working.cryptomonnaie.model.transaction.Operation;
import mg.working.cryptomonnaie.model.user.Utilisateur;
import mg.working.cryptomonnaie.services.firebase.UtilisateurSync;
import mg.working.cryptomonnaie.services.transaction.OperationService;
import mg.working.cryptomonnaie.services.utilisateur.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class OperationController {
    @Autowired
    UtilisateurSync utilisateurSync;

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
    public void insertOperation (@RequestParam String typeOperation, @RequestParam double montant, @RequestParam int utilisateurId, HttpServletResponse response) throws IOException {
        Operation operation = new Operation();
        Utilisateur utilisateur = utilisateurService.getUtilisateurById(utilisateurId);

        if (Operation.TypeOperation.valueOf(typeOperation).equals(Operation.TypeOperation.RETRAIT)) {
            if (!utilisateurService.checkSoldeBeforeRetrait(utilisateur, montant)) {
                String redirectUrl = "operation";
                response.getWriter().write("<script type='text/javascript'>alert('Solde insuffisant ');window.location.href = '" + redirectUrl + "';</script>");
                return;
            }
        }

        operation.setTypeOperation(Operation.TypeOperation.valueOf(typeOperation));
        operation.setDateHeureOperation(LocalDateTime.now());
        operation.setMontant(BigDecimal.valueOf(montant));
        operation.setUtilisateur(utilisateurService.getUtilisateurById(utilisateurId));
        operationService.save(operation);

        //sync solde firebase

        String redirectUrl = "operation";
        response.getWriter().write("<script type='text/javascript'>alert(' Demande de " + typeOperation + " envoyer ');window.location.href = '" + redirectUrl + "';</script>");
    }

    @GetMapping("historiqueOperation")
    public String historiqueOperation (HttpServletRequest request) {
        List<Operation> operations = operationService.findAll();
        request.setAttribute("operations", operations);
        return "transaction/historiqueOperation";
    }

    @PostMapping("historiqueOperationFiltreDate")
    public String historiqueOperationFiltreDate (HttpServletRequest request, @RequestParam String dateOperation) {
        List<Operation> operations = operationService.findByDateHeureOperation(dateOperation);
        request.setAttribute("operations", operations);
        return "transaction/historiqueOperation";
    }

    @GetMapping("historiqueOperationFiltreUtilisateur")
    public String historiqueOperationFiltreUtilisateur (HttpServletRequest request, @RequestParam int idUtilisateur) {
        List<Operation> operations = operationService.findByIdUtilisateur(idUtilisateur);
        request.setAttribute("operations", operations);
        return "transaction/historiqueOperation";
    }
}
