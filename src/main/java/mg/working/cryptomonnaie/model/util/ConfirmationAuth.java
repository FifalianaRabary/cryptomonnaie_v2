package mg.working.cryptomonnaie.model.util;

public class ConfirmationAuth {
    String jeton;
    boolean confirmed;

    public ConfirmationAuth(String jeton, boolean confirmed) {
        this.jeton = jeton;
        this.confirmed = confirmed;
    }

    public String getJeton() {
        return jeton;
    }

    public void setJeton(String jeton) {
        this.jeton = jeton;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public ConfirmationAuth () {}
}
