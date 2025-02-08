package mg.working.cryptomonnaie.repository.utilisateur;

import mg.working.cryptomonnaie.model.user.ImageUtilisateur;
import mg.working.cryptomonnaie.model.user.Utilisateur;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageUtilisateurRepository extends JpaRepository<ImageUtilisateur, Integer> {
    ImageUtilisateur findByUtilisateurId(int utilisateurId);

    Optional<ImageUtilisateur> findByUtilisateur(Utilisateur utilisateur);

    Optional<ImageUtilisateur> findByMail(String mail);


}
