package mg.working.cryptomonnaie.repository.utilisateur;

import mg.working.cryptomonnaie.model.user.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

    @Query("select a from Admin a where a.mdp = :password and a.nom = :username")
    Admin login(String password, String username);
}
