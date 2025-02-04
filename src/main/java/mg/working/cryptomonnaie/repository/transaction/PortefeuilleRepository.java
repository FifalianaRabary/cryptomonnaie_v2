package mg.working.cryptomonnaie.repository.transaction;

import mg.working.cryptomonnaie.model.transaction.Portefeuille;
import mg.working.cryptomonnaie.model.crypto.CryptoMonnaie;
import mg.working.cryptomonnaie.model.user.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PortefeuilleRepository extends JpaRepository<Portefeuille, Integer> {
    @Query("select p from Portefeuille p where p.utilisateur = :utilisateur")
    List<Portefeuille> findByUser(Utilisateur utilisateur);

    @Query("select p from Portefeuille p where p.utilisateur = :utilisateur and p.cryptoMonnaie = :cryptoMonnaie")
    Optional<Portefeuille> findByUtilisateurAndCryptoMonnaie(Utilisateur utilisateur, CryptoMonnaie cryptoMonnaie);

    @Query("select p from Portefeuille p where p.cryptoMonnaie = :cryptoMonnaie")
    Portefeuille findByCryptoMonnaie(CryptoMonnaie cryptoMonnaie);
}
