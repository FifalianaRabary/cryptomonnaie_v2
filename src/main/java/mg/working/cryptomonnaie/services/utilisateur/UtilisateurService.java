package mg.working.cryptomonnaie.services.utilisateur;

import jdk.jshell.execution.Util;
import mg.working.cryptomonnaie.model.user.Utilisateur;
import mg.working.cryptomonnaie.repository.utilisateur.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilisateurService {
    @Autowired
    UtilisateurRepository utilisateurRepository;

    public Utilisateur getUtilisateurByEmail(String email) {
        return this.utilisateurRepository.findUserByEmail(email);
    }

    public void insertUtilisateur(Utilisateur utilisateur) {
        this.utilisateurRepository.save(utilisateur);
    }

    public List<Utilisateur> getAllUtilisateur() {
        return this.utilisateurRepository.findAll();
    }

    public Utilisateur getUtilisateurById(int id) {
        return this.utilisateurRepository.findById(id).orElse(null);
    }

    public Utilisateur getUtilisateurByEmailMdp(String email , String mdp) {
        return this.utilisateurRepository.findUserByEmailMdp(email, mdp);
    }

    public void updateSoldeUtilisateur(Utilisateur utilisateur , String solde) {
        utilisateur.setSolde(solde);
        this.insertUtilisateur(utilisateur);
    }

    public boolean checkSoldeBeforeRetrait (Utilisateur utilisateur, double montant) {
        if (utilisateur.getSolde() >= montant) {
            return true;
        }
        return false;
    }
}
