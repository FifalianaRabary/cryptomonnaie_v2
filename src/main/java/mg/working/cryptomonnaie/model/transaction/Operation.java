package mg.working.cryptomonnaie.model.transaction;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mg.working.cryptomonnaie.model.crypto.CryptoMonnaie;
import mg.working.cryptomonnaie.model.user.Utilisateur;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "operation")
@SequenceGenerator(
        name = "s_operation",
        sequenceName = "s_operation",
        allocationSize = 1
)
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "s_operation")
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utilisateur", nullable = false , referencedColumnName = "id_utilisateur")
    private Utilisateur utilisateur;

    @Column(name = "montant", nullable = false, precision = 15, scale = 2)
    private BigDecimal montant;

    @Column(name = "date_heure_operation", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dateHeureOperation = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "type_operation", nullable = false, length = 10)
    private Operation.TypeOperation typeOperation;

    @Column(name = "date_heure_action", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dateHeureAction;

    @Column(name = "status")
    private Boolean status ;

    public enum TypeOperation {
        DEPOT,
        RETRAIT
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public LocalDateTime getDateHeureOperation() {
        return dateHeureOperation;
    }

    public void setDateHeureOperation(LocalDateTime dateHeureOperation) {
        this.dateHeureOperation = dateHeureOperation;
    }

    public TypeOperation getTypeOperation() {
        return typeOperation;
    }

    public void setTypeOperation(TypeOperation typeOperation) {
        this.typeOperation = typeOperation;
    }

    public LocalDateTime getDateHeureAction() {
        return dateHeureAction;
    }

    public void setDateHeureAction(LocalDateTime dateHeureAction) {
        this.dateHeureAction = dateHeureAction;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
