package mg.working.cryptomonnaie.model.analyse;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mg.working.cryptomonnaie.model.crypto.CryptoMonnaie;
import mg.working.cryptomonnaie.model.transaction.TransactionCrypto;
import mg.working.cryptomonnaie.model.user.Utilisateur;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "mvt_commission")
@SequenceGenerator(
        name = "s_mvt_commission",
        sequenceName = "s_mvt_commission",
        allocationSize = 1
)
public class MvtCommission {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "s_mvt_commission")
    @Column(name = "id")
    private int id;

    @Column(name = "date_heure", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dateHeure = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "type_transaction", nullable = false, length = 10)
    private MvtCommission.TypeTransaction typeTransaction;

    public enum TypeTransaction {
        ACHAT,
        VENTE
    }

    @Column(name = "pourcentage_commission", nullable = false, precision = 15, scale = 2)
    private BigDecimal pourcentage_commission;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDateHeure() {
        return dateHeure;
    }

    public void setDateHeure(LocalDateTime dateHeure) {
        this.dateHeure = dateHeure;
    }

    public void setDateHeure(String dateHeure) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.dateHeure = LocalDateTime.parse(dateHeure, formatter);
    }

    public MvtCommission.TypeTransaction getTypeTransaction() {
        return typeTransaction;
    }

    public void setTypeTransaction(MvtCommission.TypeTransaction typeTransaction) {
        this.typeTransaction = typeTransaction;
    }

    public BigDecimal getPourcentage_commission() {
        return pourcentage_commission;
    }

    public void setPourcentage_commission(BigDecimal pourcentage_commission) {
        this.pourcentage_commission = pourcentage_commission;
    }
}
