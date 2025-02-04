package mg.working.cryptomonnaie.model.analyse;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import mg.working.cryptomonnaie.model.crypto.CryptoMonnaie;
import mg.working.cryptomonnaie.model.transaction.MvtCrypto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class AnalyseCrypto {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_crypto_monnaie", nullable = false , referencedColumnName = "id_crypto_monnaie")
    CryptoMonnaie cryptoMonnaie;
    List<MvtCrypto> mvtCryptos;
    Date dateHeureMin;
    Date dateHeureMax;
    double quartile;
    double max;
    double min;
    double moyenne;
    double ecartType;

    public CryptoMonnaie getCryptoMonnaie() {
        return cryptoMonnaie;

    }

    public void setCryptoMonnaie(CryptoMonnaie cryptoMonnaie) {
        this.cryptoMonnaie = cryptoMonnaie;
    }

    public List<MvtCrypto> getMvtCryptos() {
        return mvtCryptos;
    }

    public void setMvtCryptos(List<MvtCrypto> mvtCryptos) {
        this.mvtCryptos = mvtCryptos;
    }

    public Date getDateHeureMin() {
        return dateHeureMin;
    }

    public void setDateHeureMin(Date dateHeureMin) {
        this.dateHeureMin = dateHeureMin;
    }

    public Date getDateHeureMax() {
        return dateHeureMax;
    }

    public void setDateHeureMax(Date dateHeureMax) {
        this.dateHeureMax = dateHeureMax;
    }

    public double getQuartile() {
        return quartile;
    }

    public void setQuartile(double quartile) {
        this.quartile = quartile;
    }

    public void setQuartile() {
        List<Double> valeur = getValueVariationMvtCrypto(mvtCryptos);
        int quartilePosition = (int) Math.ceil(valeur.size() * 0.25) - 1; // Index basé sur 0
        this.quartile = valeur.get(Math.max(0, quartilePosition));
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public void setMax() {
        List<Double> valeur = getValueVariationMvtCrypto(mvtCryptos);
        this.max = valeur.get(valeur.size() - 1);

    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public void setMin() {
        List<Double> valeur = getValueVariationMvtCrypto(mvtCryptos);
        this.min = valeur.get(0);
    }

    public double getMoyenne() {
        return moyenne;
    }

    public void setMoyenne(double moyenne) {
        this.moyenne = moyenne;
    }

    public void setMoyenne() {
        List<Double> valeur = getValueVariationMvtCrypto(mvtCryptos);
        this.moyenne = valeur.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }

    public double getEcartType() {
        return ecartType;
    }

    public void setEcartType(double ecartType) {
        this.ecartType = ecartType;
    }

    public void setEcartType() {
        List<Double> valeur = getValueVariationMvtCrypto(mvtCryptos);
        double variance = valeur.stream()
                .mapToDouble(v -> Math.pow(v - moyenne, 2))
                .average()
                .orElse(0.0);
        this.ecartType = Math.sqrt(variance);
    }

    public List<Double> getValueVariationMvtCrypto(List<MvtCrypto> mvtCryptos) {
        List<Double> valueVariationMvtCrypto = new ArrayList<>();
        for (MvtCrypto mvtCrypto : mvtCryptos) {
            valueVariationMvtCrypto.add(mvtCrypto.getVariationValue().doubleValue());
        }
        return valueVariationMvtCrypto.stream().sorted().collect(Collectors.toList());
    }
}
