package mg.working.cryptomonnaie.model.transaction;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime; // Import pour la gestion de la date et heure

@Entity
@Table(name = "pending_transaction")
public class PendingTransaction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer userId;

    @Column(nullable = false)
    private Integer cryptoId;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal quantite;

    @Column(nullable = false)
    private String validationToken;

    @Column(nullable = false)
    private boolean isValidated = false;

    @Column(nullable = false) // Date d'expiration obligatoire
    private LocalDateTime expirationDate;

    private static double tempsExpiration = 60; //minutes

    // Constructeur sans argument
    public PendingTransaction() {}

    // Constructeur avec paramètres
    public PendingTransaction(Integer userId, Integer cryptoId, BigDecimal quantite, String validationToken, LocalDateTime expirationDate) {
        this.userId = userId;
        this.cryptoId = cryptoId;
        this.quantite = quantite;
        this.validationToken = validationToken;
        this.expirationDate = expirationDate;
    }

    // Getters et setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCryptoId() {
        return cryptoId;
    }

    public void setCryptoId(Integer cryptoId) {
        this.cryptoId = cryptoId;
    }

    public BigDecimal getQuantite() {
        return quantite;
    }

    public void setQuantite(BigDecimal quantite) {
        this.quantite = quantite;
    }

    public String getValidationToken() {
        return validationToken;
    }

    public void setValidationToken(String validationToken) {
        this.validationToken = validationToken;
    }

    public boolean isValidated() {
        return isValidated;
    }

    public void setValidated(boolean isValidated) {
        this.isValidated = isValidated;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    // Méthode pour vérifier si le token est toujours valide
    public boolean isTokenValid() {
        return LocalDateTime.now().isBefore(this.expirationDate); // Retourne true si le token n'est pas expiré
    }

    // Méthode pour définir une date d'expiration à 60 minutes après la création
    public void setExpiration(double minute) {
        if (minute == -1) {
            minute = PendingTransaction.tempsExpiration;
        }
        this.expirationDate = LocalDateTime.now().plusMinutes((long) minute); // Conversion du double en long
    }
    
}
