package mg.working.cryptomonnaie.repository.transaction;

import mg.working.cryptomonnaie.model.transaction.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Integer> {

    @Query("select o from Operation o where o.dateHeureAction is null ")
    List<Operation> findEnAttente() ;
}
