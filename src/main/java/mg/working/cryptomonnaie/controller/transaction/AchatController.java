package mg.working.cryptomonnaie.controller.transaction;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import mg.working.cryptomonnaie.model.analyse.MvtCommission;
import mg.working.cryptomonnaie.model.crypto.CryptoMonnaie;
import mg.working.cryptomonnaie.model.transaction.PendingTransaction;
import mg.working.cryptomonnaie.model.transaction.Portefeuille;
import mg.working.cryptomonnaie.model.transaction.TransactionCrypto;
import mg.working.cryptomonnaie.repository.transaction.PendingTransactionRepository;
import mg.working.cryptomonnaie.repository.utilisateur.UtilisateurRepository;
import mg.working.cryptomonnaie.model.user.Utilisateur;
import mg.working.cryptomonnaie.services.analyse.MvtCommissionService;
import mg.working.cryptomonnaie.services.crypto.CryptoMonnaieService;
import mg.working.cryptomonnaie.services.firebase.UtilisateurSync;
import mg.working.cryptomonnaie.services.mail.EmailService;
import mg.working.cryptomonnaie.services.transaction.MvtCryptoService;
import mg.working.cryptomonnaie.services.transaction.PortefeuilleService;
import mg.working.cryptomonnaie.services.transaction.TransactionCryptoService;
import mg.working.cryptomonnaie.services.utilisateur.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("api/achat")
public class AchatController {

    @Autowired
    UtilisateurSync utilisateurSync;

    @Autowired
    private PendingTransactionRepository pendingTransactionRepository;

    @Autowired
    private EmailService emailService;

    @Autowired 
    private CryptoMonnaieService cryptoMonnaieService;

    @Autowired
    private TransactionCryptoService transactionCryptoService;

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private PortefeuilleService portefeuilleService;

    @Autowired
    private MvtCommissionService mvtCommissionService;

    /**
     * Affiche la page d'achat.
     */
    @GetMapping("/achat")
    public String afficherAchatPage(Model model , HttpSession session) {
        // Récupérer la liste des cryptomonnaies depuis le service
        List<CryptoMonnaie> cryptoMonnaies = cryptoMonnaieService.getAllCrypto();

        // Ajouter les cryptomonnaies au modèle
        model.addAttribute("cryptos", cryptoMonnaies);

        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        Utilisateur newUtilisateur = utilisateurService.getUtilisateurById(utilisateur.getId());
        session.setAttribute("utilisateur", newUtilisateur);
        List<Portefeuille> portefeuilles = portefeuilleService.getPortefeuilleByUser(utilisateur);
        model.addAttribute("portefeuilles", portefeuilles);


        // Retourne la vue JSP
        return "transaction/achat"; // Renvoie vers WEB-INF/views/transaction/achat.jsp
    }


    /**
     * Traite une demande d'achat de cryptomonnaie.
     */
    @PostMapping("/achat")
    @ResponseBody
    public String acheterCrypto(@RequestParam("userId") Integer userId,
                                @RequestParam("cryptoId") Integer cryptoId,
                                @RequestParam("quantite") BigDecimal quantite) {
        Utilisateur utilisateur = utilisateurService.getUtilisateurById(userId);
        CryptoMonnaie crypto = cryptoMonnaieService.getCryptoById(cryptoId);

        if (utilisateur == null || crypto == null) {
            return "Utilisateur ou cryptomonnaie introuvable.";
        }

        String validationToken = UUID.randomUUID().toString();

        PendingTransaction transaction = new PendingTransaction();
        transaction.setUserId(userId);
        transaction.setCryptoId(cryptoId);
        transaction.setQuantite(quantite);
        transaction.setValidationToken(validationToken);
        transaction.setValidated(false); // L'achat n'est pas encore validé

        // Définir l'expiration à 60 minutes après la création du token
        transaction.setExpiration(-1);

        // Sauvegarder la transaction
        pendingTransactionRepository.save(transaction);

        // Préparer l'URL de validation et envoyer l'email
        String validationUrl = "http://localhost:8080/api/achat/validation/" + validationToken;
        String subject = "Validation de votre achat de cryptomonnaie";
        String body = "Bonjour, veuillez cliquer sur le lien suivant pour valider votre achat : " + validationUrl;
        emailService.sendEmail(utilisateur.getMail(), subject, body);

        return "Achat en attente de validation. Veuillez vérifier votre email.";
    }


    /**
     * Valide un achat de cryptomonnaie.
     */
    @GetMapping("/validation/{token}")
    @ResponseBody
    public String validerAchat(@PathVariable("token") String token) {
        // Récupérer la transaction en utilisant le token de validation
        PendingTransaction transaction = pendingTransactionRepository.findByValidationToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Token de validation invalide"));

        // Vérifier si la transaction a déjà été validée
        if (transaction.isValidated()) {
            return "Cette transaction a déjà été validée.";
        }

        // Vérifier si le token a expiré
        if (!transaction.isTokenValid()) {
            return "Le lien de validation a expiré.";
        }

        // Récupérer l'utilisateur et la cryptomonnaie associés à la transaction
        Utilisateur utilisateur = utilisateurService.getUtilisateurById(transaction.getUserId());
        CryptoMonnaie crypto = cryptoMonnaieService.getCryptoById(transaction.getCryptoId());

        if (utilisateur == null || crypto == null) {
            return "Utilisateur ou cryptomonnaie introuvable.";
        }

        // Calculer la valeur totale de l'achat
        BigDecimal valeurTotalAchat = crypto.getPrixUnitaire().multiply(transaction.getQuantite());

        // Vérifier si l'utilisateur a un solde suffisant
        if (!transactionCryptoService.estSuffisantSolde(utilisateur.getId(), valeurTotalAchat)) {
            return "Solde insuffisant pour effectuer cet achat.";
        }

        // Mettre à jour le solde de l'utilisateur
        BigDecimal soldeUtilisateur = BigDecimal.valueOf(utilisateur.getSolde());
        BigDecimal soldeRestant = soldeUtilisateur.subtract(valeurTotalAchat);

        // Valider la transaction
        transaction.setValidated(true);
        pendingTransactionRepository.save(transaction);

        MvtCommission.TypeTransaction typeTransaction = MvtCommission.TypeTransaction.ACHAT;
        MvtCommission commission = mvtCommissionService.getLastMvtCommissionByTypeTransaction(typeTransaction);


        // Créer et enregistrer la transaction d'achat
        TransactionCrypto transactionCrypto = new TransactionCrypto();
        transactionCrypto.setUtilisateur(utilisateur);
        transactionCrypto.setCryptoMonnaie(crypto);
        transactionCrypto.setQuantite(transaction.getQuantite());
        transactionCrypto.setPrixTotal(valeurTotalAchat);
        transactionCrypto.setDateHeure(LocalDateTime.now());
        transactionCrypto.setTypeTransaction(TransactionCrypto.TypeTransaction.ACHAT);
        transactionCrypto.setPourcentage_commission(commission.getPourcentage_commission());
        transactionCrypto.setValeur_commission();
        transactionCryptoService.insertTransactionCrypto(transactionCrypto);

        // Mettre à jour le solde de l'utilisateur et enregistrer les modifications
        utilisateur.setSolde(soldeRestant.doubleValue());
        utilisateurRepository.save(utilisateur);

        // Ajouter la quantité de crypto à l'utilisateur
        cryptoMonnaieService.ajouterQuantiteCrypto(utilisateur.getId(), crypto.getId(), transaction.getQuantite());

        //sync to firebase utilisateur
        utilisateurSync.syncToFirebase(utilisateur);

        return "Votre achat a été validé avec succès.";
    }

}
