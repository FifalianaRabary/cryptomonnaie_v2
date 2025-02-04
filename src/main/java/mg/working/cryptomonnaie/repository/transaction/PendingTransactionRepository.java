package mg.working.cryptomonnaie.repository.transaction;

import mg.working.cryptomonnaie.model.transaction.MvtSolde;
import mg.working.cryptomonnaie.model.transaction.PendingTransaction;
import mg.working.cryptomonnaie.model.user.Utilisateur;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PendingTransactionRepository extends JpaRepository<PendingTransaction, Long> { 

    Optional<PendingTransaction> findByValidationToken(String token);

}