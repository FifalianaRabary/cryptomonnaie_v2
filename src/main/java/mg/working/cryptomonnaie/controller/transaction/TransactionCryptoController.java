package mg.working.cryptomonnaie.controller.transaction;

import jakarta.servlet.http.HttpServletRequest;
import mg.working.cryptomonnaie.model.transaction.TransactionCrypto;
import mg.working.cryptomonnaie.services.firebase.UtilisateurSync;
import mg.working.cryptomonnaie.services.transaction.TransactionCryptoService;
import mg.working.cryptomonnaie.services.utilisateur.ImageUtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class TransactionCryptoController {

    @Autowired
    UtilisateurSync utilisateurSync;

    @Autowired
    TransactionCryptoService transactionCryptoService;

    @Autowired
    ImageUtilisateurService imageUtilisateurService;

    @GetMapping("historiqueTransaction")
    public String historiqueTransaction (HttpServletRequest request) {
        List<TransactionCrypto> transactionCryptos = transactionCryptoService.getAllTransactionCrypto();
        request.setAttribute("transactionCryptos", transactionCryptos);
        Map<Integer, String> imageUtilisateurs = imageUtilisateurService.getImageByUtilisateur();
        request.setAttribute("imageUtilisateurs", imageUtilisateurs);
        return "transaction/historiqueTransaction";
    }

    @PostMapping("historiqueTransactionFiltreDate")
    public String historiqueTransactionFiltreDate (HttpServletRequest request, @RequestParam String dateTransaction) {
        List<TransactionCrypto> transactionCryptos = transactionCryptoService.findByDateTransaction(dateTransaction);
        request.setAttribute("transactionCryptos", transactionCryptos);
        Map<Integer, String> imageUtilisateurs = imageUtilisateurService.getImageByUtilisateur();
        request.setAttribute("imageUtilisateurs", imageUtilisateurs);
        return "transaction/historiqueTransaction";
    }

    @GetMapping("historiqueTransactionFiltreUtilisateur")
    public String historiqueTransactionFiltreUtilisateur (HttpServletRequest request, @RequestParam int idUtilisateur) {
        List<TransactionCrypto> transactionCryptos = transactionCryptoService.findByIdUtilisateur(idUtilisateur);
        request.setAttribute("transactionCryptos", transactionCryptos);
        Map<Integer, String> imageUtilisateurs = imageUtilisateurService.getImageByUtilisateur();
        request.setAttribute("imageUtilisateurs", imageUtilisateurs);
        return "transaction/historiqueTransaction";
    }
}
