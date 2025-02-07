package mg.working.cryptomonnaie.controller.login;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mg.working.cryptomonnaie.model.user.Utilisateur;
import mg.working.cryptomonnaie.model.util.ConfirmationAuth;
import mg.working.cryptomonnaie.services.login.InscriptionService;
import mg.working.cryptomonnaie.services.utilisateur.ImageUtilisateurService;
import mg.working.cryptomonnaie.services.utilisateur.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class InscriptionController {

    @Autowired
    InscriptionService inscriptionService;

    @Autowired
    UtilisateurService utilisateurService;

    @Autowired
    ImageUtilisateurService imageUtilisateurService;

    @PostMapping("incription")
    public String inscription (HttpServletRequest request, HttpServletResponse response) {
        String nom = request.getParameter("nom");
        String dtn = request.getParameter("dtn");
        String mail = request.getParameter("mail");
        String mdp = request.getParameter("mdp");

        inscriptionService.inscrireUtilisateur(nom, dtn, mail, mdp);

        return "redirect:/";

    }

    @GetMapping("confirmInscription/{jeton}")
    public String confirmInscription(@PathVariable String jeton) {
        Utilisateur utilisateur = inscriptionService.confirmerInscription(jeton);
        imageUtilisateurService.createDefaultProfileImage(utilisateur);
        return "redirect:/";
    }

    @GetMapping("/")
    public String in() {
        return "login/inscription";
    }

    @GetMapping("loginForm")
    public String on() {
        return "login/login";
    }

    @PostMapping("login")
    public String login(HttpServletRequest request, HttpSession session) {
        String mail = request.getParameter("mail");
        String mdp = request.getParameter("mdp");
        if (inscriptionService.authentifierUtilisateur(mail, mdp).equals("success")) {
            Utilisateur u = utilisateurService.getUtilisateurByEmail(mail);
            session.setAttribute("utilisateur", u);

            return "login/pinForm";
        }
        return "login/login";
    }

    @PostMapping("checkPin")
    public String checkPin(@RequestParam String pin, HttpSession session) {

        Utilisateur u = (Utilisateur) session.getAttribute("utilisateur");
        String urlImage = imageUtilisateurService.getImageByUtilisateur(u);
        ConfirmationAuth confirmationAuth = inscriptionService.confirmerPin(u.getId(), pin);

        if (confirmationAuth.isConfirmed()) {
            session.setAttribute("jeton", confirmationAuth.getJeton());
            session.setAttribute("urlImage", urlImage);
            return "redirect:/graph";
        }
        return "pinForm";
    }

}
