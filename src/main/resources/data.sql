-- Lägg till 5 kunder
INSERT INTO customers (username, name, address, email, phone) VALUES
                                                                  ('Freddan', 'Fredrik', 'Prästgårdsängen 8', 'flundell89@gmail.com', '+46708388404'),
                                                                  ('Anders55', 'Anders', 'Andersgatan 55', 'anders@hotmail.com', '+46701112233'),
                                                                  ('Pia132', 'Pia', 'Sommarvädersgatan 12', 'piapia@gmail.com', '+46702223344'),
                                                                  ('tokstolle', 'Johannes', 'Avenyen 33', 'tokstollenjohannes@gmail.com', '+46703334455'),
                                                                  ('crazycatlady', 'Gert', 'Kattvägen 93', 'mjau-mjau@gmail.com', '+46704445566');



-- Lägg till 5 bilar
INSERT INTO cars (manifacturer, model, reg_Nr, price_Per_Day, available) VALUES
                                                                         ('Tesla', 'Model 3', 'ABC123', 150, true),
                                                                         ('Volvo', 'S90', 'JHT534', 150, true),
                                                                         ('Toyota', 'Yaris', 'TAM967', 150, false),
                                                                         ('Audi', 'A7', 'VRR332', 150, false),
                                                                         ('Audi', 'TT', 'FEL312', 150, false);

-- Lägg till 3 orders
INSERT INTO orders (customer_id, car_id, hiring_Date) VALUES
      (1, 4, NOW()),
      (3, 3, NOW()),
      (5, 5, NOW());