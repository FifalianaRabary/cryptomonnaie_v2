package mg.working.cryptomonnaie.services.analyse;

import mg.working.cryptomonnaie.model.analyse.MvtCommission;
import mg.working.cryptomonnaie.model.transaction.TransactionCrypto;
import mg.working.cryptomonnaie.repository.analyse.MvtCommissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MvtCommissionService {

    @Autowired
    MvtCommissionRepository commissionRepository;

    public MvtCommission getLastMvtCommissionByTypeTransaction (MvtCommission.TypeTransaction typeTransaction) { return this.commissionRepository.getLastMvtCommissionByTypeTransaction(typeTransaction); }

    public void save (MvtCommission mvtCommission) { this.commissionRepository.save(mvtCommission); }
}
