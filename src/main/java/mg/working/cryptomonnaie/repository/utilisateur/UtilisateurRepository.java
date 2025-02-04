package mg.working.cryptomonnaie.repository.utilisateur;

import mg.working.cryptomonnaie.model.user.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {
    @Query("select u from Utilisateur u where u.mail = :email and u.mdp = :mdp")
    Utilisateur findUserByEmailMdp(String email , String mdp);

    @Query("select u from Utilisateur u where u.mail = :email")
    Utilisateur findUserByEmail(String email);
}
