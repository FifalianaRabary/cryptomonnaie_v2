package mg.working.cryptomonnaie.controller.transaction;

import jakarta.servlet.http.HttpSession;
import mg.working.cryptomonnaie.model.analyse.MvtCommission;
import mg.working.cryptomonnaie.model.crypto.CryptoMonnaie;
import mg.working.cryptomonnaie.model.transaction.Portefeuille;
import mg.working.cryptomonnaie.model.transaction.TransactionCrypto;
import mg.working.cryptomonnaie.model.user.Utilisateur;
import mg.working.cryptomonnaie.services.analyse.MvtCommissionService;
import mg.working.cryptomonnaie.services.crypto.CryptoMonnaieService;
import mg.working.cryptomonnaie.services.firebase.FirestoreService;
import mg.working.cryptomonnaie.services.firebase.UtilisateurSync;
import mg.working.cryptomonnaie.services.transaction.MvtSoldeService;
import mg.working.cryptomonnaie.services.transaction.PortefeuilleService;
import mg.working.cryptomonnaie.services.transaction.TransactionCryptoService;
import mg.working.cryptomonnaie.services.utilisateur.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/api/vente")
public class VenteController {

    @Autowired
    UtilisateurSync utilisateurSync;


    @Autowired
    FirestoreService firestoreService;

    @Autowired
    CryptoMonnaieService cryptoMonnaieService;
    @Autowired
    UtilisateurService utilisateurService;
    @Autowired
    TransactionCryptoService transactionCryptoService;
    @Autowired
    PortefeuilleService portefeuilleService;
    @Autowired
    MvtSoldeService mvtSoldeService;

    @Autowired
    MvtCommissionService mvtCommissionService;

    @GetMapping("/ventePage")
    public String goToVentePage(Model model , HttpSession session) {
        List<CryptoMonnaie> cryptoMonnaies = this.cryptoMonnaieService.getAllCrypto();
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        Utilisateur newUtilisateur = utilisateurService.getUtilisateurById(utilisateur.getId());
        session.setAttribute("utilisateur", newUtilisateur);
        List<Portefeuille> portefeuilleUser = this.portefeuilleService.getPortefeuilleByUser(utilisateur);
        session.setAttribute("portefeuilleUser", portefeuilleUser);

        model.addAttribute("cryptoMonnaies", cryptoMonnaies);
        return "transaction/vente";
    }

    @PostMapping("/venteCrypto")
    public String vendreCrypto(@RequestParam("userId") Integer userId,
                               @RequestParam("cryptoId") Integer cryptoId,
                               @RequestParam("quantite") BigDecimal quantite) throws Exception {
        Utilisateur utilisateur = this.utilisateurService.getUtilisateurById(userId);
        CryptoMonnaie cryptoMonnaie = this.cryptoMonnaieService.getCryptoById(cryptoId);
        if (utilisateur == null || cryptoMonnaie == null) {
            return "Utilisateur ou Cryptomonnaie introuvable";
        }
        BigDecimal valeurTotalVente = cryptoMonnaie.calculTotalVente(quantite);
        double newSoldeUser = utilisateur.calculNewSolde(Double.parseDouble(valeurTotalVente.toString()));
        utilisateur.setSolde(newSoldeUser);

        utilisateurService.insertUtilisateur(utilisateur);

        //sync to firebase utilisateur
        utilisateurSync.syncToFirebase(utilisateur);

        Portefeuille portefeuille = this.portefeuilleService.getPortefeuilleByCrypto(cryptoMonnaie);
        this.portefeuilleService.updateQuantiteCrypto(portefeuille , quantite);
        this.mvtSoldeService.insertNewMvtSolde(utilisateur,valeurTotalVente, BigDecimal.valueOf(newSoldeUser));
        this.transactionCryptoService.insertNewTransactionCrypto(utilisateur,cryptoMonnaie , quantite , valeurTotalVente);

        MvtCommission.TypeTransaction typeTransaction = MvtCommission.TypeTransaction.VENTE;
        MvtCommission commission = mvtCommissionService.getLastMvtCommissionByTypeTransaction(typeTransaction);

        TransactionCrypto transactionCrypto = new TransactionCrypto();
        transactionCrypto.setUtilisateur(utilisateur);
        transactionCrypto.setCryptoMonnaie(cryptoMonnaie);
        transactionCrypto.setQuantite(quantite);
        transactionCrypto.setPrixTotal(valeurTotalVente);
        transactionCrypto.setDateHeure(LocalDateTime.now());
        transactionCrypto.setTypeTransaction(TransactionCrypto.TypeTransaction.VENTE);
        transactionCrypto.setPourcentage_commission(commission.getPourcentage_commission());
        transactionCrypto.setValeur_commission();

        
        firestoreService.syncTransactionToFirestore(transactionCrypto);

        return "redirect:/api/vente/ventePage";
    }
}
