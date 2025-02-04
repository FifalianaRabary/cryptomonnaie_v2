# inscription

# connexion

# gestion profil

# page evolution graphique 

# achat de cryptomonnaie
page achat : formulaire
- select crypto_monnaie
- quantite_achete 
- bouton acheter => envoie email de validation 

traitement:
- lastVariation : getLastVariationCrypto(id_crypto_monnaie)

- valeur_actuel_unitaire : calcul valeur crypto actuel : prix_unitaire * lastVariation / 100

- valeur_total_achat : calcul valeur total achat : valeur_actuel_unitaire * quantite_achete

- solde : estSuffisantSolde(id_utilisateur) 
=> solde!=null: 
- solde_restant : solde_restant = user.getSolde - valeur_total_achat

- insert new mvt_solde avec retrait = valeur_total_achat, solde_restant = solde_restant

- insert new transaction_crypto :
    - quantite = quantite_acheter
    - prix_total = valeur_total_achat
    - date_heure = now()
    - type_transaction = 0 (achat)

- modif solde utilisateur : setSolde(solde_restant)
- modif portefeuille pour le crypto:
    - quantite + quantite_achete

# vente de cryptomonnaie
page : liste tableau crypto-monnaie de l'utilisateur avec les colonnes:
- libellé crypto_monnaie
- qtt_crypto
- prix unitaire crypto_monnaie
- bouton mettre en vente =>
    - page : vente :
        - input (qtt_a_vendre) avec valeur par défaut qtt_crypto et max: qtt_crypto
        - bouton valider vente => envoie mail de validation
    
traitement: 
- lastVariation : getLastVariationCrypto(id_crypto_monnaie)

- valeur_actuel_unitaire : calcul valeur crypto actuel : prix_unitaire * lastVariation / 100

- valeur_total_vente : calcul valeur total achat : valeur_actuel_unitaire * quantite_à_vendre

- new_solde : new_solde = user.getSolde + valeur_total_vente

- insert new mvt_solde avec depot = valeur_total_vente, new_solde = new_solde

- insert new transaction_crypto :
    - quantite = quantite_a_vendre
    - prix_total = valeur_total_vente
    - date_heure = now()
    - type_transaction = 1 (vente)

- modif solde utilisateur : setSolde(new_solde)
- modif portefeuille pour le crypto:
    - quantite - quantite_a_vendre