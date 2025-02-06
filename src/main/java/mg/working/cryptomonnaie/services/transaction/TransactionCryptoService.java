package mg.working.cryptomonnaie.services.transaction;

import mg.working.cryptomonnaie.model.analyse.MvtCommission;
import mg.working.cryptomonnaie.model.crypto.CryptoMonnaie;
import mg.working.cryptomonnaie.model.transaction.TransactionCrypto;
import mg.working.cryptomonnaie.model.user.Utilisateur;
import mg.working.cryptomonnaie.repository.transaction.TransactionCryptoRepository;
import mg.working.cryptomonnaie.services.analyse.MvtCommissionService;
import mg.working.cryptomonnaie.services.utilisateur.UtilisateurService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionCryptoService {
    @Autowired
    TransactionCryptoRepository transactionCryptoRepository;

    @Autowired
    MvtCommissionService mvtCommissionService;

    @Autowired
    private UtilisateurService utilisateurService;  // Injecter UtilisateurService

    public void insertTransactionCrypto(TransactionCrypto transactionCrypto) {
        this.transactionCryptoRepository.save(transactionCrypto);
    }

    public List<TransactionCrypto> getAllTransactionCrypto() {
        return this.transactionCryptoRepository.findAll();
    }

    public List<TransactionCrypto> findByIdCrypto (int idCrypto) { return this.transactionCryptoRepository.findByIdCrypto(idCrypto); }

    public List<TransactionCrypto> findByIdUtilisateur (int idUtilisateur) {
        return this.transactionCryptoRepository.findByIdUtilisateur(idUtilisateur);
    }

    public boolean estSuffisantSolde(int utilisateurId, BigDecimal montantTotal) {
        Utilisateur utilisateur = utilisateurService.getUtilisateurById(utilisateurId);  // Récupérer l'utilisateur
        if (utilisateur == null) {
            throw new IllegalArgumentException("Utilisateur non trouvé");
        }
        
        // Convertir le solde de l'utilisateur en BigDecimal
        BigDecimal soldeUtilisateur = BigDecimal.valueOf(utilisateur.getSolde()).setScale(2, RoundingMode.HALF_UP);

        // Comparer le solde de l'utilisateur avec le montant total
        return soldeUtilisateur.compareTo(montantTotal) >= 0;
    }

    public void insertNewTransactionCrypto(Utilisateur utilisateur,CryptoMonnaie cryptoMonnaie , BigDecimal quantiteAVendre , BigDecimal valeurTotalVente) {
        MvtCommission.TypeTransaction typeTransaction = MvtCommission.TypeTransaction.VENTE;
        MvtCommission commission = mvtCommissionService.getLastMvtCommissionByTypeTransaction(typeTransaction);
        TransactionCrypto transactionCrypto = new TransactionCrypto();
        transactionCrypto.setUtilisateur(utilisateur);
        transactionCrypto.setCryptoMonnaie(cryptoMonnaie);
        transactionCrypto.setQuantite(quantiteAVendre);
        transactionCrypto.setPrixTotal(valeurTotalVente);
        transactionCrypto.setDateHeure(LocalDateTime.now());
        transactionCrypto.setTypeTransaction(TransactionCrypto.TypeTransaction.VENTE);
        transactionCrypto.setPourcentage_commission(commission.getPourcentage_commission());
        transactionCrypto.setValeur_commission();

        this.insertTransactionCrypto(transactionCrypto);
    }

    // Appel à findTotalAchatByUserAndDateMax
    public int getTotalAchatByUserAndDateMax(Utilisateur utilisateur, String dateMax) {
        return transactionCryptoRepository.findTotalAchatByUserAndDateMax(utilisateur, dateMax);
    }

    // Appel à findTotalVenteByUserAndDateMax
    public int getTotalVenteByUserAndDateMax(Utilisateur utilisateur, String dateMax) {
        return transactionCryptoRepository.findTotalVenteByUserAndDateMax(utilisateur, dateMax);
    }

    // Appel à findTotalSoldeByUserAndDateMax
    public double getTotalSoldeByUserAndDateMax(Utilisateur utilisateur, String dateMax) {
        return transactionCryptoRepository.findTotalSoldeByUserAndDateMax(utilisateur, dateMax);
    }

    // Appel à findTotalCryptoByUserAndDateMax
    public BigDecimal getTotalCryptoByUserAndDateMax(Utilisateur utilisateur, String dateMax) {
        return transactionCryptoRepository.findTotalCryptoByUserAndDateMax(utilisateur, dateMax);
    }
}
