package mg.working.cryptomonnaie.repository.transaction;

import mg.working.cryptomonnaie.model.transaction.TransactionCrypto;
import mg.working.cryptomonnaie.model.user.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface TransactionCryptoRepository extends JpaRepository<TransactionCrypto, Integer> {
    @Query("SELECT SUM(t.prixTotal) FROM TransactionCrypto t WHERE t.utilisateur = :utilisateur AND t.dateHeure <= :dateMax AND t.typeTransaction = 'ACHAT'")
    int findTotalAchatByUserAndDateMax(Utilisateur utilisateur , String dateMax);

    @Query("SELECT SUM(t.prixTotal) FROM TransactionCrypto t WHERE t.utilisateur = :utilisateur AND t.dateHeure <= :dateMax AND t.typeTransaction = 'VENTE'")
    int findTotalVenteByUserAndDateMax(Utilisateur utilisateur , String dateMax);

    @Query("SELECT (COALESCE(SUM(CASE WHEN t.typeTransaction = 'VENTE' THEN t.prixTotal ELSE 0 END), 0) - COALESCE(SUM(CASE WHEN t.typeTransaction = 'ACHAT' THEN t.prixTotal ELSE 0 END), 0)) FROM TransactionCrypto t WHERE t.utilisateur = :utilisateur AND t.dateHeure <= :dateMax")
    double findTotalSoldeByUserAndDateMax(Utilisateur utilisateur , String dateMax);

    @Query("SELECT SUM(t.quantite) FROM TransactionCrypto t WHERE t.utilisateur = :utilisateur AND t.dateHeure <= :dateMax")
    BigDecimal findTotalCryptoByUserAndDateMax(Utilisateur utilisateur , String dateMax);

    @Query("select t from TransactionCrypto t where t.cryptoMonnaie.id = :idCrypto")
    List<TransactionCrypto> findByIdCrypto(@Param("idCrypto") int idCrypto);

    @Query("select t from TransactionCrypto t where t.utilisateur.id = :idUtilisateur")
    List<TransactionCrypto> findByIdUtilisateur(@Param("idUtilisateur") int idUtilisateur);

}

