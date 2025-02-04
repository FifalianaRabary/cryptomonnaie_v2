-- Insertion des cryptomonnaies avec utilisation de nextval pour l'ID
INSERT INTO crypto_monnaie (id_crypto_monnaie, designation, symbol, prix_unitaire) 
VALUES (nextval('s_crypto_monnaie'), 'Bitcoin', 'BTC', 25000.00),
       (nextval('s_crypto_monnaie'), 'Ethereum', 'ETH', 1800.00),
       (nextval('s_crypto_monnaie'), 'Binance Coin', 'BNB', 230.00),
       (nextval('s_crypto_monnaie'), 'Cardano', 'ADA', 0.30),
       (nextval('s_crypto_monnaie'), 'Solana', 'SOL', 20.00),
       (nextval('s_crypto_monnaie'), 'XRP', 'XRP', 0.50),
       (nextval('s_crypto_monnaie'), 'Dogecoin', 'DOGE', 0.06),
       (nextval('s_crypto_monnaie'), 'Polygon', 'MATIC', 0.70),
       (nextval('s_crypto_monnaie'), 'Polkadot', 'DOT', 4.50),
       (nextval('s_crypto_monnaie'), 'Litecoin', 'LTC', 65.00);


INSERT INTO utilisateur (id_utilisateur, nom, mail, mdp, dtn, solde)
VALUES (nextval('s_utilisateur'), 'Fifaliana', 'rabaryfifaliana@gmail.com', 'aaa', '2004-07-16', 100000000);