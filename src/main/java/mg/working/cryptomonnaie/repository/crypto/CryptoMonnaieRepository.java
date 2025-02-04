package mg.working.cryptomonnaie.repository.crypto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mg.working.cryptomonnaie.model.crypto.CryptoMonnaie;

@Repository
public interface CryptoMonnaieRepository extends JpaRepository<CryptoMonnaie , Integer>{

}
