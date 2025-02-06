package mg.working.cryptomonnaie.model.user;

import jakarta.persistence.*;

@Entity
@Table(name = "image_utilisateur")
@SequenceGenerator(
    name = "s_image_utilisateur",
    sequenceName = "s_image_utilisateur",
    allocationSize = 1
)
public class ImageUtilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "s_image_utilisateur")
    @Column(name = "id_image")
    private int id;

    @OneToOne
    @JoinColumn(name = "id_utilisateur", referencedColumnName = "id_utilisateur", nullable = false)
    private Utilisateur utilisateur;

    @Column(name = "url", nullable = false)
    private String url;

    // Constructeur par défaut
    public ImageUtilisateur() {}

    // Constructeur avec paramètres
    public ImageUtilisateur(Utilisateur utilisateur, String url) {
        this.utilisateur = utilisateur;
        this.url = url;
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

    // Getter et Setter pour url
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
