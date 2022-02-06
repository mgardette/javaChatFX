DROP TABLE Utilisateur;
DROP TABLE Jeu;
DROP TABLE ScoreJeu;


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
    nb_victoires int,
    nb_parties int,
    joueur int,
    adversaire int,
    num_jeu int
);

ALTER TABLE ScoreJeu ADD CONSTRAINT fk_num_jeu FOREIGN KEY (num_jeu) REFERENCES Jeu(num_jeu);
ALTER TABLE ScoreJeu ADD CONSTRAINT fk_joueur FOREIGN KEY (joueur) REFERENCES Utilisateur(num_uti);
ALTER TABLE ScoreJeu ADD CONSTRAINT fk_adversaire FOREIGN KEY (adversaire) REFERENCES Utilisateur(num_uti);
