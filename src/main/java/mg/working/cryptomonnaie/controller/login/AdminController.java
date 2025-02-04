package mg.working.cryptomonnaie.controller.login;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import mg.working.cryptomonnaie.model.transaction.Operation;
import mg.working.cryptomonnaie.model.user.Admin;
import mg.working.cryptomonnaie.model.user.Utilisateur;
import mg.working.cryptomonnaie.services.transaction.OperationService;
import mg.working.cryptomonnaie.services.utilisateur.AdminService;
import mg.working.cryptomonnaie.services.utilisateur.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    OperationService operationService;

    @Autowired
    UtilisateurService utilisateurService;

    @GetMapping("loginAdminForm")
    public String loginAdminForm () {
        return "login/loginAdmin";
    }

    @PostMapping("loginAdmin")
    public String loginAdmin (@RequestParam String username, @RequestParam String password, HttpSession session) {
        String url = "redirect:/validateOperation";
        Admin admin = adminService.login(username, password);
        if (admin == null) {
            url = "redirect:/loginAdminForm";
        }
        session.setAttribute("admin", admin);
        return url;
    }

    @GetMapping("validateOperation")
    public String validateOperation (HttpServletRequest request) {
        List<Operation> operations = operationService.findEnAttente();
        request.setAttribute("operations", operations);
        return "admin/validateOperation";
    }

    @GetMapping("valideAnOperation")
    public String valideAnOperation(@RequestParam int idOperation, @RequestParam Boolean status) {
        Operation operation = operationService.findById(idOperation);
        operation.setStatus(status);
        operation.setDateHeureAction(LocalDateTime.now());
        operationService.save(operation);

        Utilisateur utilisateur = utilisateurService.getUtilisateurById(operation.getUtilisateur().getId());

        if (operation.getTypeOperation().name().equals("DEPOT")) {
            double newSolde = operation.getMontant().doubleValue() + utilisateur.getSolde();
            utilisateur.setSolde(newSolde);
        }
        else if (operation.getTypeOperation().name().equals("RETRAIT")) {
            double newSolde =utilisateur.getSolde() - operation.getMontant().doubleValue() ;
            utilisateur.setSolde(newSolde);
        }

        utilisateurService.insertUtilisateur(utilisateur);

        return "redirect:/validateOperation";
    }

}
