-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : lun. 22 nov. 2021 à 17:36
-- Version du serveur : 10.4.21-MariaDB
-- Version de PHP : 8.0.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

DROP DATABASE `garage_fullstack`;

--
-- Base de données : `garage_fullstack`
--
CREATE DATABASE IF NOT EXISTS `garage_fullstack` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `garage_fullstack`;

-- --------------------------------------------------------

--
-- Structure de la table `client`
--

CREATE TABLE `client` (
  `id` bigint(20) NOT NULL,
  `code_postal` int(11) NOT NULL,
  `libelle` varchar(255) DEFAULT NULL,
  `ville` varchar(255) DEFAULT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `prenom` varchar(255) DEFAULT NULL,
  `tel_fixe` varchar(255) DEFAULT NULL,
  `tel_mobile` varchar(255) DEFAULT NULL,
  `type` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- --------------------------------------------------------

--
-- Structure de la table `commande`
--

CREATE TABLE `commande` (
  `id` bigint(20) NOT NULL,
  `a_livrer` bit(1) NOT NULL,
  `date_commande` date DEFAULT NULL,
  `est_livre` bit(1) NOT NULL,
  `devis_id` bigint(20) DEFAULT NULL,
  `facture_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `compo_demande_piece`
--

CREATE TABLE `compo_demande_piece` (
  `id_dem` bigint(20) NOT NULL,
  `id_piece` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `compo_piece_tache`
--

CREATE TABLE `compo_piece_tache` (
  `id_piece` bigint(20) NOT NULL,
  `id_tache` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- --------------------------------------------------------

--
-- Structure de la table `compo_user_role`
--

CREATE TABLE `compo_user_role` (
  `id_role` bigint(20) NOT NULL,
  `id_user` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- --------------------------------------------------------

--
-- Structure de la table `demande_piece`
--

CREATE TABLE `demande_piece` (
  `id` bigint(20) NOT NULL,
  `date_demande` date DEFAULT NULL,
  `etat` int(11) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `devis_vehicule`
--

CREATE TABLE `devis_vehicule` (
  `id` bigint(20) NOT NULL,
  `date_creation` date DEFAULT NULL,
  `date_validation` date DEFAULT NULL,
  `is_valide` bit(1) NOT NULL,
  `libelle` varchar(255) DEFAULT NULL,
  `prixttc` double NOT NULL,
  `quantite` int(11) NOT NULL,
  `client_id` bigint(20) DEFAULT NULL,
  `commercial_id` bigint(20) DEFAULT NULL,
  `vehicule_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `facture`
--

CREATE TABLE `facture` (
  `id` bigint(20) NOT NULL,
  `date_creation` date DEFAULT NULL,
  `prix` float NOT NULL,
  `prixttc` float NOT NULL,
  `type` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `fiche_entretien`
--

CREATE TABLE `fiche_entretien` (
  `id` bigint(20) NOT NULL,
  `date_cloture` date DEFAULT NULL,
  `date_creation` date DEFAULT NULL,
  `is_cloture` bit(1) NOT NULL,
  `is_valid` bit(1) NOT NULL,
  `client_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `hibernate_sequence`
--

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `piece`
--

CREATE TABLE `piece` (
  `id` bigint(20) NOT NULL,
  `date_creation` date DEFAULT NULL,
  `is_in_stock` bit(1) NOT NULL,
  `prix` float NOT NULL,
  `prix_facture` float NOT NULL,
  `quantite_stock` int(11) NOT NULL,
  `nom_piece` varchar(255) DEFAULT NULL,
  `type` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- --------------------------------------------------------

--
-- Structure de la table `role`
--

CREATE TABLE `role` (
  `id` bigint(20) NOT NULL,
  `nom_role` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- --------------------------------------------------------

--
-- Structure de la table `tache`
--

CREATE TABLE `tache` (
  `id` bigint(20) NOT NULL,
  `intitule` varchar(255) DEFAULT NULL,
  `is_terminee` bit(1) NOT NULL,
  `priorite` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `fiche_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- --------------------------------------------------------

--
-- Structure de la table `user`
--

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `is_spa_active` bit(1) NOT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `prenom` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `vehicule`
--

CREATE TABLE `vehicule` (
  `id` bigint(20) NOT NULL,
  `date_creation` date DEFAULT NULL,
  `is_in_stock` bit(1) NOT NULL,
  `prix` float NOT NULL,
  `prix_facture` float NOT NULL,
  `quantite_stock` int(11) NOT NULL,
  `couleur` varchar(255) DEFAULT NULL,
  `etat_vehicule` int(11) DEFAULT NULL,
  `marque` varchar(255) DEFAULT NULL,
  `modele` varchar(255) DEFAULT NULL,
  `devis_v_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `client`
--
ALTER TABLE `client`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `commande`
--
ALTER TABLE `commande`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKsbr5ln2qitk26fq7bnrtkkmr4` (`devis_id`),
  ADD KEY `FKo35571d53e6npqs1nn0rb5x42` (`facture_id`);

--
-- Index pour la table `compo_demande_piece`
--
ALTER TABLE `compo_demande_piece`
  ADD PRIMARY KEY (`id_dem`,`id_piece`),
  ADD KEY `FKfr04x8wjxe74r5g9se758impk` (`id_piece`);

--
-- Index pour la table `compo_piece_tache`
--
ALTER TABLE `compo_piece_tache`
  ADD PRIMARY KEY (`id_piece`,`id_tache`),
  ADD KEY `FKb5iqpe93ynfe28c9nb24ivwa3` (`id_tache`);

--
-- Index pour la table `compo_user_role`
--
ALTER TABLE `compo_user_role`
  ADD PRIMARY KEY (`id_role`,`id_user`),
  ADD KEY `FK7kmh4bm91eht51v8jw9sqq6al` (`id_user`);

--
-- Index pour la table `demande_piece`
--
ALTER TABLE `demande_piece`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK393vd1xjf3kygtgtxa0ywr2a4` (`user_id`);

--
-- Index pour la table `devis_vehicule`
--
ALTER TABLE `devis_vehicule`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKawgab23hk6103c2jsu1336omy` (`client_id`),
  ADD KEY `FKj1pesr8l9jixwf1kvpars10nr` (`commercial_id`),
  ADD KEY `FK2ffhogt53p9o12d9s36xkhrdl` (`vehicule_id`);

--
-- Index pour la table `facture`
--
ALTER TABLE `facture`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `fiche_entretien`
--
ALTER TABLE `fiche_entretien`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK7aer3e61e3jmekarb16d1hgj1` (`client_id`);

--
-- Index pour la table `piece`
--
ALTER TABLE `piece`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `tache`
--
ALTER TABLE `tache`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK8nqlr258a1hxv7cu2br48gqqd` (`fiche_id`),
  ADD KEY `FKiwf2rjd64no70ggrx8qkxkiip` (`user_id`);

--
-- Index pour la table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `vehicule`
--
ALTER TABLE `vehicule`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKioq20mg3abnxnuckfqvsev7ie` (`devis_v_id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `client`
--
ALTER TABLE `client`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT pour la table `commande`
--
ALTER TABLE `commande`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `devis_vehicule`
--
ALTER TABLE `devis_vehicule`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `facture`
--
ALTER TABLE `facture`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `fiche_entretien`
--
ALTER TABLE `fiche_entretien`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `role`
--
ALTER TABLE `role`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT pour la table `tache`
--
ALTER TABLE `tache`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `user`
--
ALTER TABLE `user`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `commande`
--
ALTER TABLE `commande`
  ADD CONSTRAINT `FKo35571d53e6npqs1nn0rb5x42` FOREIGN KEY (`facture_id`) REFERENCES `facture` (`id`),
  ADD CONSTRAINT `FKsbr5ln2qitk26fq7bnrtkkmr4` FOREIGN KEY (`devis_id`) REFERENCES `devis_vehicule` (`id`);

--
-- Contraintes pour la table `compo_demande_piece`
--
ALTER TABLE `compo_demande_piece`
  ADD CONSTRAINT `FKfr04x8wjxe74r5g9se758impk` FOREIGN KEY (`id_piece`) REFERENCES `piece` (`id`),
  ADD CONSTRAINT `FKrhwm38w5anbtrar8ai9ss96x3` FOREIGN KEY (`id_dem`) REFERENCES `demande_piece` (`id`);

--
-- Contraintes pour la table `compo_piece_tache`
--
ALTER TABLE `compo_piece_tache`
  ADD CONSTRAINT `FKb5iqpe93ynfe28c9nb24ivwa3` FOREIGN KEY (`id_tache`) REFERENCES `tache` (`id`),
  ADD CONSTRAINT `FKhxn0ial7yapu211a789qjxgx9` FOREIGN KEY (`id_piece`) REFERENCES `piece` (`id`);

--
-- Contraintes pour la table `compo_user_role`
--
ALTER TABLE `compo_user_role`
  ADD CONSTRAINT `FK7kmh4bm91eht51v8jw9sqq6al` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `FKgd0qqg30d503rukxk8uwrat7d` FOREIGN KEY (`id_role`) REFERENCES `role` (`id`);

--
-- Contraintes pour la table `demande_piece`
--
ALTER TABLE `demande_piece`
  ADD CONSTRAINT `FK393vd1xjf3kygtgtxa0ywr2a4` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Contraintes pour la table `devis_vehicule`
--
ALTER TABLE `devis_vehicule`
  ADD CONSTRAINT `FK2ffhogt53p9o12d9s36xkhrdl` FOREIGN KEY (`vehicule_id`) REFERENCES `vehicule` (`id`),
  ADD CONSTRAINT `FKawgab23hk6103c2jsu1336omy` FOREIGN KEY (`client_id`) REFERENCES `client` (`id`),
  ADD CONSTRAINT `FKj1pesr8l9jixwf1kvpars10nr` FOREIGN KEY (`commercial_id`) REFERENCES `user` (`id`);

--
-- Contraintes pour la table `fiche_entretien`
--
ALTER TABLE `fiche_entretien`
  ADD CONSTRAINT `FK7aer3e61e3jmekarb16d1hgj1` FOREIGN KEY (`client_id`) REFERENCES `client` (`id`);

--
-- Contraintes pour la table `tache`
--
ALTER TABLE `tache`
  ADD CONSTRAINT `FK8nqlr258a1hxv7cu2br48gqqd` FOREIGN KEY (`fiche_id`) REFERENCES `fiche_entretien` (`id`),
  ADD CONSTRAINT `FKiwf2rjd64no70ggrx8qkxkiip` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Contraintes pour la table `vehicule`
--
ALTER TABLE `vehicule`
  ADD CONSTRAINT `FKioq20mg3abnxnuckfqvsev7ie` FOREIGN KEY (`devis_v_id`) REFERENCES `devis_vehicule` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
