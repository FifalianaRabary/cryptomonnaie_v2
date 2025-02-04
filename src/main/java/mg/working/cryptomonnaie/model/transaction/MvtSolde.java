package mg.working.cryptomonnaie.model.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mg.working.cryptomonnaie.model.user.Utilisateur;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "mvt_solde")
@SequenceGenerator(
        name = "s_mvt_solde",
        sequenceName = "s_mvt_solde",
        allocationSize = 1
)
public class MvtSolde {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "s_mvt_solde")
    @Column(name = "id")
    private int id;

    @Column(name = "date_heure", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dateHeure = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utilisateur", nullable = false , referencedColumnName = "id_utilisateur")
    private Utilisateur utilisateur;

    @Column(name = "depot", nullable = false, precision = 15, scale = 2)
    private BigDecimal depot = BigDecimal.valueOf(0.00);

    @Column(name = "retrait", nullable = false, precision = 15, scale = 2)
    private BigDecimal retrait = BigDecimal.valueOf(0.00);

    @Column(name = "solde_restant", nullable = false, precision = 15, scale = 2)
    private BigDecimal soldeRestant;

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

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public BigDecimal getDepot() {
        return depot;
    }

    public void setDepot(BigDecimal depot) {
        this.depot = depot;
    }

    public BigDecimal getRetrait() {
        return retrait;
    }

    public void setRetrait(BigDecimal retrait) {
        this.retrait = retrait;
    }

    public BigDecimal getSoldeRestant() {
        return soldeRestant;
    }

    public void setSoldeRestant(BigDecimal soldeRestant) {
        this.soldeRestant = soldeRestant;
    }
}
