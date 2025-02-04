package mg.working.cryptomonnaie.repository.transaction;

import mg.working.cryptomonnaie.model.transaction.MvtSolde;
import mg.working.cryptomonnaie.model.user.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MvtSoldeRepository extends JpaRepository<MvtSolde, Integer> {
    @Query("select m from MvtSolde m where m.utilisateur = :utilisateur")
    MvtSolde findMvtSoldeByUser(Utilisateur utilisateur);
}
