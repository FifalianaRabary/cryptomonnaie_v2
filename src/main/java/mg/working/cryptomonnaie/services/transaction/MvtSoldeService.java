package mg.working.cryptomonnaie.services.transaction;

import mg.working.cryptomonnaie.model.transaction.MvtSolde;
import mg.working.cryptomonnaie.model.user.Utilisateur;
import mg.working.cryptomonnaie.repository.transaction.MvtSoldeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MvtSoldeService {
    @Autowired
    MvtSoldeRepository mvtSoldeRepository;

    public void insertMvtSolde(MvtSolde mvtSolde) {
        this.mvtSoldeRepository.save(mvtSolde);
    }

    public List<MvtSolde> getAllMvtSolde() {
        return this.mvtSoldeRepository.findAll();
    }

    public MvtSolde getMvtSoldeByUser(Utilisateur utilisateur) {
        return this.mvtSoldeRepository.findMvtSoldeByUser(utilisateur);
    }

    public void insertNewMvtSolde(Utilisateur utilisateur, BigDecimal valeurTotalVente , BigDecimal valeurSoldeActuel) {
        MvtSolde mvtSolde = new MvtSolde();
        mvtSolde.setUtilisateur(utilisateur);
        mvtSolde.setDepot(valeurTotalVente);
        mvtSolde.setSoldeRestant(valeurSoldeActuel);
        mvtSolde.setDateHeure(LocalDateTime.now());

        this.insertMvtSolde(mvtSolde);
    }
}
