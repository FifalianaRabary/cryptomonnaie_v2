package mg.working.cryptomonnaie.services.transaction;

import mg.working.cryptomonnaie.model.transaction.Operation;
import mg.working.cryptomonnaie.repository.transaction.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OperationService {

    @Autowired
    OperationRepository operationRepository;

    public void save(Operation operation) { operationRepository.save(operation); }

    public Operation findById (int id ) { return operationRepository.findById(id).orElse(null); }

    public List<Operation> findAll() { return operationRepository.findAll(); }

    public List<Operation> findEnAttente () { return operationRepository.findEnAttente(); }

    public List<Operation> findByDateHeureOperation (String dateHeureOperationString) {
        LocalDateTime dateHeureOperation = LocalDateTime.parse(dateHeureOperationString);
        return operationRepository.findByDateHeureOperation(dateHeureOperation);
    }

    public List<Operation> findByIdUtilisateur (int idUtilisateur) { return operationRepository.findByIdUtilisateur(idUtilisateur); }

}
