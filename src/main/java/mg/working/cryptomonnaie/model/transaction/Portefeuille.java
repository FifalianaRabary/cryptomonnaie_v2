package mg.working.cryptomonnaie.model.transaction;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mg.working.cryptomonnaie.model.crypto.CryptoMonnaie;
import mg.working.cryptomonnaie.model.user.Utilisateur;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "portefeuille")
@SequenceGenerator(
        name = "s_portefeuille",
        sequenceName = "s_portefeuille",
        allocationSize = 1
)
public class Portefeuille {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "s_portefeuille")
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

    public BigDecimal getQuantite() {
        return quantite;
    }

    // Setter pour la quantité
    public void setQuantite(BigDecimal quantite) {
        this.quantite = quantite;
    }

    // Ajoutez les autres getters et setters nécessaires
    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public CryptoMonnaie getCryptoMonnaie() {
        return cryptoMonnaie;
    }

    public void setCryptoMonnaie(CryptoMonnaie cryptoMonnaie) {
        this.cryptoMonnaie = cryptoMonnaie;
    }


}
