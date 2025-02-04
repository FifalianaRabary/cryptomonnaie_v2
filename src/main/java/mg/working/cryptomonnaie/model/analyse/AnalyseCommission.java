package mg.working.cryptomonnaie.model.analyse;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import mg.working.cryptomonnaie.model.crypto.CryptoMonnaie;
import mg.working.cryptomonnaie.model.transaction.TransactionCrypto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AnalyseCommission {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_crypto_monnaie", nullable = false , referencedColumnName = "id_crypto_monnaie")
    CryptoMonnaie cryptoMonnaie;
    List<TransactionCrypto> transactions;
    Date dateHeureMin;
    Date dateHeureMax;
    double somme;
    double moyenne;

    public CryptoMonnaie getCryptoMonnaie() {
        return cryptoMonnaie;
    }

    public void setCryptoMonnaie(CryptoMonnaie cryptoMonnaie) {
        this.cryptoMonnaie = cryptoMonnaie;
    }

    public List<TransactionCrypto> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionCrypto> transactions) {
        this.transactions = transactions;
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

    public double getSomme() {
        return somme;
    }

    public void setSomme(double somme) {
        this.somme = somme;
    }

    public void setSomme () {
        List<Double> values = getValeurCommission(transactions);
        double sum = 0;
        for (Double value : values) {
            sum += value;
        }
        this.somme = sum;
    }

    public double getMoyenne() {
        return moyenne;
    }

    public void setMoyenne(double moyenne) {
        this.moyenne = moyenne;
    }

    public void setMoyenne() {
        this.moyenne = getSomme() / transactions.size();
    }

    public List<Double> getValeurCommission(List<TransactionCrypto> transactions) {
        List<Double> valeurCommission = new ArrayList<Double>();
        for (TransactionCrypto transaction : transactions) {
            valeurCommission.add(transaction.getValeur_commission().doubleValue());
        }
        return valeurCommission;
    }
}
