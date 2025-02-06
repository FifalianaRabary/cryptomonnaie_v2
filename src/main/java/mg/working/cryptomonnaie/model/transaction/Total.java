package mg.working.cryptomonnaie.model.transaction;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import mg.working.cryptomonnaie.model.user.Utilisateur;

import java.time.LocalDateTime;
import java.util.List;

public class Total {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utilisateur", nullable = false , referencedColumnName = "id_utilisateur")
    private Utilisateur utilisateur;
    private LocalDateTime date;
    private List<TransactionCrypto> transactions;
    private double totalVente;
    private double totalAchat;
    private double valeurPortefeuille;

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public List<TransactionCrypto> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionCrypto> transactions) {
        this.transactions = transactions;
    }

    public double getTotalVente() {
        return totalVente;
    }

    public void setTotalVente(double totalVente) {
        this.totalVente = totalVente;
    }
    
    public void setTotalVente() {
        double total = 0;
        for (TransactionCrypto transaction : transactions) {
            if (transaction.getTypeTransaction().equals(TransactionCrypto.TypeTransaction.VENTE)) {
                total += transaction.getPrixTotal().doubleValue();
            }
        }
        this.totalVente = total;
    }

    public double getTotalAchat() {
        return totalAchat;
    }

    public void setTotalAchat(double totalAchat) {
        this.totalAchat = totalAchat;
    }

    public void setTotalAchat() {
        double total = 0;
        for (TransactionCrypto transaction : transactions) {
            if (transaction.getTypeTransaction().equals(TransactionCrypto.TypeTransaction.ACHAT)) {
                total += transaction.getPrixTotal().doubleValue();
            }
        }
        this.totalAchat = total;
    }

    public double getValeurPortefeuille() {
        return valeurPortefeuille;
    }

    public void setValeurPortefeuille(double valeurPortefeuille) {
        this.valeurPortefeuille = valeurPortefeuille;
    }
}
