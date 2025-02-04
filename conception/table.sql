-- Table utilisateur
CREATE TABLE utilisateur (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    mail VARCHAR(150) UNIQUE NOT NULL,
    mdp VARCHAR(255) NOT NULL,
    dtn DATE NOT NULL,
    solde NUMERIC(15, 2) DEFAULT 0.00
);

-- Table crypto_monnaie
CREATE TABLE crypto_monnaie (
    id SERIAL PRIMARY KEY,
    designation VARCHAR(100) NOT NULL,
    symbol VARCHAR(10) UNIQUE NOT NULL,
    prix_unitaire NUMERIC(15, 2) NOT NULL
);

-- Table mvt_crypto
CREATE TABLE mvt_crypto (
    id SERIAL PRIMARY KEY,
    id_crypto_monnaie INT NOT NULL,
    variation NUMERIC(5, 2) NOT NULL,
    date_heure TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_crypto_monnaie) REFERENCES crypto_monnaie (id) ON DELETE CASCADE
);

-- Table portefeuille
CREATE TABLE portefeuille (
    id SERIAL PRIMARY KEY,
    id_utilisateur INT NOT NULL,
    id_crypto_monnaie INT NOT NULL,
    quantite NUMERIC(15, 6) NOT NULL,
    FOREIGN KEY (id_utilisateur) REFERENCES utilisateur (id) ON DELETE CASCADE,
    FOREIGN KEY (id_crypto_monnaie) REFERENCES crypto_monnaie (id) ON DELETE CASCADE
);

-- Table mvt_solde
CREATE TABLE mvt_solde (
    id SERIAL PRIMARY KEY,
    date_heure TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    id_utilisateur INT NOT NULL,
    depot NUMERIC(15, 2) DEFAULT 0.00,
    retrait NUMERIC(15, 2) DEFAULT 0.00,
    solde_restant NUMERIC(15, 2) NOT NULL,
    FOREIGN KEY (id_utilisateur) REFERENCES utilisateur (id) ON DELETE CASCADE
);

-- Table transaction_crypto
CREATE TABLE transaction_crypto (
    id SERIAL PRIMARY KEY,
    id_crypto_monnaie INT NOT NULL,
    quantite NUMERIC(15, 6) NOT NULL,
    prix_total NUMERIC(15, 2) NOT NULL,
    date_heure TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    type_transaction VARCHAR(10) CHECK (type_transaction IN ('achat', 'vente')) NOT NULL,
    FOREIGN KEY (id_crypto_monnaie) REFERENCES crypto_monnaie (id) ON DELETE CASCADE
);