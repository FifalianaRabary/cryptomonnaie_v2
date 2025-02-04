package mg.working.cryptomonnaie.model.user;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "utilisateur")
@SequenceGenerator(
    name = "s_utilisateur",
    sequenceName = "s_utilisateur",
    allocationSize = 1
)
public class Utilisateur {
    @Id 
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "s_utilisateur") 
    @Column(name = "id_utilisateur")
    private int id;

    private String nom;
    private String mail;
    private String mdp;
    private Date dtn;
    private double solde = 0.0;

    // Getter et Setter pour id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter et Setter pour nom
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    // Getter et Setter pour mail
    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    // Getter et Setter pour mdp
    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    // Getter et Setter pour dtn
    public Date getDtn() {
        return dtn;
    }

    public void setDtn(Date dtn) {
        this.dtn = dtn;
    }

    // Setter pour dtn avec String
    public void setDtn(String dtn) {
        this.dtn = Date.valueOf(dtn);
    }

    // Getter et Setter pour solde
    public double getSolde() {
        return this.solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    public void setSolde(String solde) {
        this.solde = Double.parseDouble(solde);
    }

    public double calculNewSolde(double valeurTotalVente) {
        return this.solde + valeurTotalVente;
    }
}
