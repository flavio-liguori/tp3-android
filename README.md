# TP3 - Gestion des utilisateurs et de leur planning

## Description
Ce projet est une application Android qui permet aux utilisateurs de s'inscrire, de se connecter et de gérer leur planning journalier. L'application utilise des fragments et une base de données pour stocker les informations des utilisateurs ainsi que leurs plannings.

## Fonctionnalités

### Exercice 1 : Création de la base de données
1. **Fragment 1 - Inscription**
   - Permet à l'utilisateur de saisir les informations suivantes :
     - Login et mot de passe
     - Nom, prénom, date de naissance, numéro de téléphone, adresse e-mail
     - Sélectionner des centres d'intérêt : sport, musique, lecture, etc.
   - Bouton "Soumettre" permettant :
     - Sauvegarde des données en base de données.
     - Passage au fragment suivant.

2. **Fragment 2 - Affichage**
   - Affiche une synthèse des données saisies par l'utilisateur.

### Exercice 2 : Utilisation de la base de données
1. **Ajout d'une activité de choix**
   - Permet à l'utilisateur de choisir entre :
     - "Nouvelle Inscription"
     - "Connexion"

2. **Nouvelle Inscription**
   - Vérification des règles de validation des champs :
     - Le login doit commencer par une lettre et ne pas dépasser 10 caractères.
     - Le mot de passe doit contenir exactement 6 caractères.
     - Vérification que le login n'existe pas déjà en base de données.

3. **Connexion**
   - Interface permettant de saisir login et mot de passe.
   - Vérification de l'existence de l'utilisateur en base de données.
   - En cas de succès : passage à l'activité "Planning".
   - En cas d'échec : affichage d'une erreur et possibilité de réessayer.

4. **Activité "Planning"**
   - Permet à l'utilisateur de renseigner son emploi du temps journalier sur 4 créneaux horaires :
     - 08h-10h
     - 10h-12h
     - 14h-16h
     - 16h-18h
   - Exemple :
     - 08h-10h : Cours HAI813I
     - 10h-12h : Réunion TER
     - 14h-16h : TP HAI811I
     - 16h-18h : Sport
   - Validation et sauvegarde du planning en base de données.
   - Affichage d'une synthèse du planning après validation.

## Technologies utilisées
- **Langage** : Java 
- **Base de données** : SQLite
- **Framework UI** : Android Fragments & Activities
- **IDE** : Android Studio

## Installation et exécution
1. Cloner le repository :
   ```sh
   git clone https://github.com/flavio-liguori/android-tp3.git
   ```
2. Ouvrir le projet avec **Android Studio**.
3. Lancer l'application sur un émulateur ou un appareil physique.

## Auteurs
- **Flavio LIGUORI** - [Profil GitHub](https://github.com/flavio-kiguori)

