package mg.working.cryptomonnaie.model.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import mg.working.cryptomonnaie.model.crypto.CryptoMonnaie;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "mvt_crypto")
@SequenceGenerator(
    name = "s_mvt_crypto",
    sequenceName = "s_mvt_crypto",
    allocationSize = 1
)
public class MvtCrypto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "s_mvt_crypto")
    @Column(name = "id")
    int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_crypto_monnaie", nullable = false , referencedColumnName = "id_crypto_monnaie")
    CryptoMonnaie cryptoMonnaie;

    @Column(name = "variation", nullable = false, precision = 5, scale = 2)
    BigDecimal variation;

    @Column(name = "variation_value", nullable = false, precision = 15, scale = 2)
    BigDecimal variationValue;

    @Column(name = "date_heure", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    LocalDateTime dateHeure = LocalDateTime.now();

    public void setVariationValue(BigDecimal variation, CryptoMonnaie cryptoMonnaie) {
        if (variationValue == null) {
            variationValue = cryptoMonnaie.getPrixUnitaire();
        }
        this.variationValue = variationValue.add(cryptoMonnaie.getPrixUnitaire().multiply(variation).divide(BigDecimal.valueOf(100)));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CryptoMonnaie getCryptoMonnaie() {
        return cryptoMonnaie;
    }

    public void setCryptoMonnaie(CryptoMonnaie cryptoMonnaie) {
        this.cryptoMonnaie = cryptoMonnaie;
    }

    public BigDecimal getVariation() {
        return variation;
    }

    public void setVariation(BigDecimal variation) {
        this.variation = variation;
    }

    public BigDecimal getVariationValue() {
        return variationValue;
    }

    public void setVariationValue(BigDecimal variationValue) {
        this.variationValue = variationValue;
    }

    public LocalDateTime getDateHeure() {
        return dateHeure;
    }

    public void setDateHeure(LocalDateTime dateHeure) {
        this.dateHeure = dateHeure;
    }

    public CryptoMonnaie e () {
        return this.getCryptoMonnaie();
    }
}
