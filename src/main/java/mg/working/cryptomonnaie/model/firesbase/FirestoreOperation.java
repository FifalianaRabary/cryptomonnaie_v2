package mg.working.cryptomonnaie.model.firesbase;


import com.fasterxml.jackson.annotation.JsonProperty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)  // Ignorer les champs non utilisés comme "name"
public class FirestoreOperation {

    @JsonProperty("montant")
    private Integer montant;

    @JsonProperty("operation")
    private String operation;

    @JsonProperty("status")
    private Boolean status;

    @JsonProperty("typeOperation")
    private String typeOperation;

    @JsonProperty("utilisateurId")
    private Integer utilisateurId;

    @JsonProperty("dateHeureOperation")
    private String dateHeureOperation;

    // Getters et Setters
    public Integer getMontant() {
        return montant;
    }

    public void setMontant(Integer montant) {
        this.montant = montant;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getTypeOperation() {
        return typeOperation;
    }

    public void setTypeOperation(String typeOperation) {
        this.typeOperation = typeOperation;
    }

    public Integer getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(Integer utilisateurId) {
        this.utilisateurId = utilisateurId;
    }

    public String getDateHeureOperation() {
        return dateHeureOperation;
    }

    public void setDateHeureOperation(String dateHeureOperation) {
        this.dateHeureOperation = dateHeureOperation;
    }
}


