package mg.working.cryptomonnaie.controller.user;

import mg.working.cryptomonnaie.model.user.Utilisateur;
import mg.working.cryptomonnaie.services.utilisateur.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class ProfileUtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;

     @Autowired
    public ProfileUtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }


    @GetMapping("/profile")
    public String showProfile(Model model) {
        return "user/profile";
    }

    @PostMapping("/updateProfile")
    public String updateProfile(@RequestParam("nom") String nom,
                                @RequestParam("mail") String mail,
                                @RequestParam("mdp") String mdp,
                                @RequestParam("dtn") String dtn,HttpServletRequest request, HttpSession session) {
        
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
      
        utilisateur.setNom(nom);
        utilisateur.setMail(mail);
        utilisateur.setDtn(dtn);
        utilisateur.setMdp(mdp);
        boolean estUpdated = utilisateurService.updateUtilisateurComplete(utilisateur);

        if (estUpdated){
            session.setAttribute("utilisateur", utilisateur);
            return "redirect:/profile";
        }
        session.setAttribute("utilisateur", utilisateur);
        return "redirect:/graph";
        
    }
}
