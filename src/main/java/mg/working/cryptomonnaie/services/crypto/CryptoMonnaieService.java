package mg.working.cryptomonnaie.services.crypto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import mg.working.cryptomonnaie.model.user.Utilisateur;
import mg.working.cryptomonnaie.services.utilisateur.UtilisateurService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mg.working.cryptomonnaie.model.crypto.CryptoMonnaie;
import mg.working.cryptomonnaie.model.transaction.Portefeuille;
import mg.working.cryptomonnaie.repository.crypto.CryptoMonnaieRepository;
import mg.working.cryptomonnaie.repository.transaction.PortefeuilleRepository;

import java.util.Optional;

@Service
public class CryptoMonnaieService {
    @Autowired
    CryptoMonnaieRepository cryptoMonnaieRepository;

    @Autowired
    PortefeuilleRepository portefeuilleRepository;

    @Autowired  
    UtilisateurService utilisateurService;

    public void insertCrypto(CryptoMonnaie cryptoMonnaie) {
        this.cryptoMonnaieRepository.save(cryptoMonnaie);
    }

    public List<CryptoMonnaie> getAllCrypto() {
        return this.cryptoMonnaieRepository.findAll();
    }

    public List<Integer> getAllId () {
        List<Integer> ids = new ArrayList<Integer>();
        List<CryptoMonnaie> cryptoMonnaies = this.getAllCrypto();
        for (CryptoMonnaie cryptoMonnaie: cryptoMonnaies) {
            ids.add(cryptoMonnaie.getId());
        }
        return ids;
    }

    // Récupérer une crypto-monnaie par son ID
    public CryptoMonnaie getCryptoById(int idCryptoMonnaie) {
        return this.cryptoMonnaieRepository.findById(idCryptoMonnaie).orElse(null); // Retourne null si l'ID n'est pas trouvé
    }

    // Mettre à jour le portefeuille de l'utilisateur (ajouter des quantités de cryptomonnaie)
    public void ajouterQuantiteCrypto(int userId, int cryptoId, BigDecimal quantiteAchete) {
        // Récupérer l'utilisateur
        Utilisateur utilisateur = utilisateurService.getUtilisateurById(userId);
        if (utilisateur == null) {
            throw new IllegalArgumentException("Utilisateur avec l'ID " + userId + " introuvable.");
        }

        // Récupérer la cryptomonnaie
        CryptoMonnaie crypto = getCryptoById(cryptoId);
        if (crypto == null) {
            throw new IllegalArgumentException("Crypto-monnaie avec l'ID " + cryptoId + " introuvable.");
        }

        // Récupérer ou créer le portefeuille pour cet utilisateur et cette cryptomonnaie
        Optional<Portefeuille> portefeuilleOpt = portefeuilleRepository.findByUtilisateurAndCryptoMonnaie(utilisateur, crypto);

        Portefeuille portefeuille;
        if (portefeuilleOpt.isPresent()) {
            portefeuille = portefeuilleOpt.get();
            // Ajouter la quantité achetée à la quantité existante
            portefeuille.setQuantite(portefeuille.getQuantite().add(quantiteAchete));
        } else {
            // Créer un nouveau portefeuille s'il n'existe pas encore
            portefeuille = new Portefeuille();
            portefeuille.setUtilisateur(utilisateur);
            portefeuille.setCryptoMonnaie(crypto);
            portefeuille.setQuantite(quantiteAchete);
        }

        // Sauvegarder le portefeuille mis à jour
        portefeuilleRepository.save(portefeuille);
    }

}
