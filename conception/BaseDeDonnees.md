# utilisateur:
- id
- nom
- mail
- mdp
- dtn
- solde

# crypto_monnaie
- id
- disignation
- symbol
- prix_unitaire

# mvt_crypto
- id
- id_crypto_monnaire
- variation (%)
- date_heure

# protefeuille
- id
- id_utilisateur
- id_crypto_monnaie
- quantite

# mvt_solde
- id
- date_heure
- id_utilisateur
- depot
- retrait
- solde restant

# transaction_crypto
- id
- id_crypto_monnaie
- quantite
- prix_total
- date_heure
- type_transaction (achat,vente)