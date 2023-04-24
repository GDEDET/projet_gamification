DROP TABLE IF EXISTS personne;

CREATE TABLE personne (
    id INT AUTO_INCREMENT PRIMARY KEY,
    prenom VARCHAR(255) NOT NULL,
    nom VARCHAR(255) NOT NULL,
    mail VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

INSERT INTO personne (prenom, nom, mail, password) VALUES
    ('Laurent', 'GINA', 'laurentgina@yopmail.com', 'laurent'),
    ('Sophie', 'FONCEK', 'sophiefoncek@yopmail.com', 'sophie'),
    ('Agathe', 'FEELING', 'agathefeeling@yopmail.com', 'agathe');