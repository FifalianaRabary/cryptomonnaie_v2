package mg.working.cryptomonnaie.controller.user;

import mg.working.cryptomonnaie.model.user.ImageUtilisateur;
import mg.working.cryptomonnaie.model.user.Utilisateur;
import mg.working.cryptomonnaie.utils.ImgurUploader;
import mg.working.cryptomonnaie.services.utilisateur.UtilisateurService;
import mg.working.cryptomonnaie.services.utilisateur.ImageUtilisateurService;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class ProfileUtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private ImageUtilisateurService imageUtilisateurService;

     @Autowired
    public ProfileUtilisateurController(UtilisateurService utilisateurService, ImageUtilisateurService imageUtilisateurService) {
        this.utilisateurService = utilisateurService;
        this.imageUtilisateurService = imageUtilisateurService;
    }


    @GetMapping("/profile")
    public String showProfile(Model model) {
        return "user/profile";
    }

    @GetMapping("/pageUpdateImageProfile")
    public String showPageUpdateImageProfile(Model model) {
        return "user/imageProfile";
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


    @PostMapping("/uploadProfileImage")
    public String uploadProfileImage(@RequestParam("image") MultipartFile file, HttpSession session) {
        if (file.isEmpty()) {
            return "redirect:/graph";
        }
    
        try {
            String imageUrl = ImgurUploader.uploadImage(file);
            Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
    
            if (utilisateur != null) {
                imageUtilisateurService.updateImageByIdUtilisateur(utilisateur.getId(), imageUrl);
                session.setAttribute("urlImage", imageUrl);  
                }
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/graph";
        }
    
        return "redirect:/graph";
    }
    

    
}
