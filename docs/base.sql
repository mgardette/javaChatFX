DROP TABLE ScoreJeu;
DROP TABLE Utilisateur;
DROP TABLE Jeu;

CREATE TABLE Utilisateur (
    num_uti int CONSTRAINT pk_num_uti PRIMARY KEY,
    pseudo varchar2(30) UNIQUE NOT NULL,
    mdp varchar2(30) NOT NULL,
    photo_profil varchar2(255) DEFAULT 'no_profile',
    statut varchar2(200)
);

CREATE TABLE Jeu (
    num_jeu int CONSTRAINT pk_num_jeu PRIMARY KEY,
    nom_jeu varchar2(20) NOT NULL,
    description varchar2(100)
);

CREATE TABLE ScoreJeu (
    num_jeu int NOT NULL,
    joueur varchar2(30) NOT NULL,
    adversaire varchar2(30) NOT NULL,
    resultat varchar2(1) NOT NULL CHECK (resultat IN ('V', 'D', 'N'))
);

ALTER TABLE ScoreJeu ADD CONSTRAINT fk_num_jeu FOREIGN KEY (num_jeu) REFERENCES Jeu(num_jeu);
ALTER TABLE ScoreJeu ADD CONSTRAINT fk_joueur FOREIGN KEY (joueur) REFERENCES Utilisateur(pseudo);
ALTER TABLE ScoreJeu ADD CONSTRAINT fk_adversaire FOREIGN KEY (adversaire) REFERENCES Utilisateur(pseudo);

INSERT INTO utilisateur VALUES (1, 'testmat', 'testmat', default, null);
INSERT INTO utilisateur VALUES (2, 'testmat2', 'testmat2', default, null);
INSERT INTO utilisateur VALUES (3, '123123', '123123', default, null);
INSERT INTO utilisateur VALUES (4, '456456', '456456', default, null);

INSERT INTO jeu VALUES(1, 'Puissance 4', 'Aligner 4 pièces de même couleur');

INSERT INTO scorejeu VALUES (1, 'testmat', 'testmat2', 'V');
INSERT INTO scorejeu VALUES (1, 'testmat', 'testmat2', 'D');
INSERT INTO scorejeu VALUES (1, 'testmat', 'testmat2', 'N');
INSERT INTO scorejeu VALUES (1, 'testmat2', 'testmat', 'D');
INSERT INTO scorejeu VALUES (1, 'testmat2', 'testmat', 'V');