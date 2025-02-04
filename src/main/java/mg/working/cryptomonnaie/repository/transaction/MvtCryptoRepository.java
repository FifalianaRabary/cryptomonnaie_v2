package mg.working.cryptomonnaie.repository.transaction;

import mg.working.cryptomonnaie.model.transaction.MvtCrypto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MvtCryptoRepository extends JpaRepository<MvtCrypto, Integer> {

    @Query("select m from MvtCrypto m order by m.id desc limit 10")
    List<MvtCrypto> getAllLastMvtCrypto();

    @Query("select m from MvtCrypto m where m.cryptoMonnaie.id = :id order by m.id desc limit 7")
    List<MvtCrypto> get7LastMvtCryptoByCrypto(@Param("id") int id);

    @Query("select m from MvtCrypto m where m.cryptoMonnaie.id = :id")
    List<MvtCrypto> getByCryptoMonnaie(@Param("id") int id);
}
