INSERT INTO crypto_monnaie (id_crypto_monnaie, designation, symbol, prix_unitaire) VALUES
(nextval('s_crypto_monnaie'), 'Bitcoin', 'BTC', 35000.00),
(nextval('s_crypto_monnaie'), 'Ethereum', 'ETH', 1800.00),
(nextval('s_crypto_monnaie'), 'Ripple', 'XRP', 50.50),
(nextval('s_crypto_monnaie'), 'Cardano', 'ADA', 8000.30),
(nextval('s_crypto_monnaie'), 'Polkadot', 'DOT', 50000.00),
(nextval('s_crypto_monnaie'), 'Litecoin', 'LTC', 100.00),
(nextval('s_crypto_monnaie'), 'Chainlink', 'LINK', 600.00),
(nextval('s_crypto_monnaie'), 'Binance Coin', 'BNB', 250.00),
(nextval('s_crypto_monnaie'), 'Dogecoin', 'DOGE', 1000.07),
(nextval('s_crypto_monnaie'), 'Solana', 'SOL', 20.00);

insert into admin values (nextval('s_admin'), 'admin', 'admin'),
                         (nextval('s_admin'), 'admin2', 'admin2');

truncate table mvt_crypto cascade;
ALTER SEQUENCE s_mvt_crypto RESTART WITH 1;

truncate table crypto_monnaie cascade;
ALTER SEQUENCE s_crypto_monnaie RESTART WITH 1;


INSERT INTO crypto_monnaie (id_crypto_monnaie, designation, symbol, prix_unitaire) VALUES
(nextval('s_crypto_monnaie'), 'Bitcoin', 'BTC', 35000.00),
(nextval('s_crypto_monnaie'), 'Ethereum', 'ETH', 1800.00),
(nextval('s_crypto_monnaie'), 'Ripple', 'XRP', 50.50),
(nextval('s_crypto_monnaie'), 'Cardano', 'ADA', 8000.30),
(nextval('s_crypto_monnaie'), 'Polkadot', 'DOT', 50000.00),
(nextval('s_crypto_monnaie'), 'Litecoin', 'LTC', 100.00),
(nextval('s_crypto_monnaie'), 'Chainlink', 'LINK', 600.00),
(nextval('s_crypto_monnaie'), 'Binance Coin', 'BNB', 250.00),
(nextval('s_crypto_monnaie'), 'Dogecoin', 'DOGE', 1000.07),
(nextval('s_crypto_monnaie'), 'Solana', 'SOL', 20.00);


truncate table transaction_crypto cascade ;
ALTER SEQUENCE s_transaction_crypto RESTART WITH 1;

insert into mvt_commission (id, date_heure, pourcentage_commission, type_transaction)  values (nextval('s_mvt_commission'), '2025-02-03 23:22:50.00', 10.00, 'ACHAT'),
                                                                                              (nextval('s_mvt_commission'), '2025-02-03 23:22:50.00', 10.00, 'VENTE');


