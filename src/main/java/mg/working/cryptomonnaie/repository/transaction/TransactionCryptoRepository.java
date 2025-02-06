package mg.working.cryptomonnaie.repository.transaction;

import mg.working.cryptomonnaie.model.transaction.TransactionCrypto;
import mg.working.cryptomonnaie.model.user.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionCryptoRepository extends JpaRepository<TransactionCrypto, Integer> {

    @Query("select t from TransactionCrypto t where t.cryptoMonnaie.id = :idCrypto")
    List<TransactionCrypto> findByIdCrypto(@Param("idCrypto") int idCrypto);

    @Query("select t from TransactionCrypto t where t.utilisateur.id = :idUtilisateur")
    List<TransactionCrypto> findByIdUtilisateur(@Param("idUtilisateur") int idUtilisateur);

    @Query("select t from TransactionCrypto t where t.dateHeure <= :dateTransaction")
    List<TransactionCrypto> findByDateTransaction (@Param("dateTransaction")LocalDateTime dateTransaction);

}

