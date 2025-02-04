package mg.working.cryptomonnaie.repository.analyse;

import mg.working.cryptomonnaie.model.analyse.MvtCommission;
import mg.working.cryptomonnaie.model.transaction.TransactionCrypto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MvtCommissionRepository extends JpaRepository<MvtCommission, Integer> {

    @Query("SELECT m FROM MvtCommission m WHERE m.typeTransaction = :typeTransaction ORDER BY m.id DESC")
    MvtCommission getLastMvtCommissionByTypeTransaction(MvtCommission.TypeTransaction typeTransaction);

}
