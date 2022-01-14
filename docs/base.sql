CREATE TABLE Utilisateur (
    num_uti int CONSTRAINT pk_num_uti PRIMARY KEY,
    pseudo varchar2(30) NOT NULL,
    mdp varchar2(30) NOT NULL,
    mail varchar2(100) NOT NULL,
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
    adversaire int
);

ALTER TABLE ScoreJeu ADD CONSTRAINT fk_joueur FOREIGN KEY (joueur) REFERENCES Utilisateur(num_uti);
ALTER TABLE ScoreJeu ADD CONSTRAINT fk_adversaire FOREIGN KEY (adversaire) REFERENCES Utilisateur(num_uti);
