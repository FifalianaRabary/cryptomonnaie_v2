package mg.working.cryptomonnaie.controller.logout;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import mg.working.cryptomonnaie.services.logout.LogoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class LogoutController {

    private final LogoutService logoutService;

    @Autowired
    public LogoutController(LogoutService logoutService) {
        this.logoutService = logoutService;
    }

    @GetMapping("logout")
    public String validateOperation(HttpServletRequest request, HttpSession session) {
        String jeton = (String) session.getAttribute("jeton");

        if (jeton == null) {
            return "redirect:/login";
        }

        boolean logout = logoutService.deconnexion(jeton);

        if (logout) {
            session.invalidate();
            return "redirect:/login/login";
        }

        return "redirect:/dashboard";
    }
}

