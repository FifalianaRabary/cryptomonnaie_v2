package mg.working.cryptomonnaie.repository.transaction;

import mg.working.cryptomonnaie.model.transaction.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Integer> {

    @Query("select o from Operation o where o.dateHeureAction is null ")
    List<Operation> findEnAttente() ;

    @Query("select o from Operation o where o.dateHeureOperation <= :dateHeureOperation")
    List<Operation> findByDateHeureOperation(@Param("dateHeureOperation") LocalDateTime dateHeureOperation);

    @Query("select o from Operation o where o.utilisateur.id = :idUtilisateur")
    List<Operation> findByIdUtilisateur (int idUtilisateur);



}
