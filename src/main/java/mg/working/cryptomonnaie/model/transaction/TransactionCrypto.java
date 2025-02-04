package mg.working.cryptomonnaie.model.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mg.working.cryptomonnaie.model.crypto.CryptoMonnaie;
import mg.working.cryptomonnaie.model.user.Utilisateur;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transaction_crypto")
@SequenceGenerator(
        name = "s_transaction_crypto",
        sequenceName = "s_transaction_crypto",
        allocationSize = 1
)
public class TransactionCrypto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "s_transaction_crypto")
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utilisateur", nullable = false , referencedColumnName = "id_utilisateur")
    private Utilisateur utilisateur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_crypto_monnaie", nullable = false , referencedColumnName = "id_crypto_monnaie")
    private CryptoMonnaie cryptoMonnaie;

    @Column(name = "quantite", nullable = false, precision = 15, scale = 6)
    private BigDecimal quantite;

    @Column(name = "prix_total", nullable = false, precision = 15, scale = 2)
    private BigDecimal prixTotal;

    @Column(name = "date_heure", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dateHeure = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "type_transaction", nullable = false, length = 10)
    private TypeTransaction typeTransaction;

    public enum TypeTransaction {
        ACHAT,
        VENTE
    }

    @Column(name = "pourcentage_commission", nullable = false, precision = 15, scale = 2)
    private BigDecimal pourcentage_commission;

    @Column(name = "valeur_commission", nullable = false, precision = 15, scale = 2)
    private BigDecimal valeur_commission;

    public BigDecimal getPourcentage_commission() {
        return pourcentage_commission;
    }

    public void setPourcentage_commission(BigDecimal pourcentage_commission) {
        this.pourcentage_commission = pourcentage_commission;
    }

    public BigDecimal getValeur_commission() {
        return valeur_commission;
    }

    public void setValeur_commission(BigDecimal valeur_commission) {
        this.valeur_commission = valeur_commission;
    }

    public void setValeur_commission() {
        this.valeur_commission = pourcentage_commission.multiply(prixTotal).divide(BigDecimal.valueOf(100));
    }

    // Getter et Setter pour id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter et Setter pour utilisateur
    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    // Getter et Setter pour cryptoMonnaie
    public CryptoMonnaie getCryptoMonnaie() {
        return cryptoMonnaie;
    }

    public void setCryptoMonnaie(CryptoMonnaie cryptoMonnaie) {
        this.cryptoMonnaie = cryptoMonnaie;
    }

    // Getter et Setter pour quantite
    public BigDecimal getQuantite() {
        return quantite;
    }

    public void setQuantite(BigDecimal quantite) {
        this.quantite = quantite;
    }

    // Getter et Setter pour prixTotal
    public BigDecimal getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(BigDecimal prixTotal) {
        this.prixTotal = prixTotal;
    }

    // Getter et Setter pour dateHeure
    public LocalDateTime getDateHeure() {
        return dateHeure;
    }

    public void setDateHeure(LocalDateTime dateHeure) {
        this.dateHeure = dateHeure;
    }

    // Getter et Setter pour typeTransaction
    public TypeTransaction getTypeTransaction() {
        return typeTransaction;
    }

    public void setTypeTransaction(TypeTransaction typeTransaction) {
        this.typeTransaction = typeTransaction;
    }
}
