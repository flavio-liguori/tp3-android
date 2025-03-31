package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "utilisateurs.db";
    private static final int DATABASE_VERSION = 2;

    // Table utilisateurs
    private static final String TABLE_UTILISATEURS = "utilisateurs";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_LOGIN = "login";
    private static final String COLUMN_MOT_DE_PASSE = "mot_de_passe";
    private static final String COLUMN_NOM = "nom";
    private static final String COLUMN_PRENOM = "prenom";
    private static final String COLUMN_DATE_NAISSANCE = "date_naissance";
    private static final String COLUMN_TELEPHONE = "telephone";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_CENTRES_INTERET = "centres_interet";

    // Table planning
    private static final String TABLE_PLANNING = "planning";
    private static final String COLUMN_PLANNING_ID = "id";
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_CRENEAU_1 = "creneau_1";
    private static final String COLUMN_CRENEAU_2 = "creneau_2";
    private static final String COLUMN_CRENEAU_3 = "creneau_3";
    private static final String COLUMN_CRENEAU_4 = "creneau_4";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Création de la table utilisateurs
        String CREATE_UTILISATEURS_TABLE = "CREATE TABLE " + TABLE_UTILISATEURS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_LOGIN + " TEXT UNIQUE, "
                + COLUMN_MOT_DE_PASSE + " TEXT, "
                + COLUMN_NOM + " TEXT, "
                + COLUMN_PRENOM + " TEXT, "
                + COLUMN_DATE_NAISSANCE + " TEXT, "
                + COLUMN_TELEPHONE + " TEXT, "
                + COLUMN_EMAIL + " TEXT, "
                + COLUMN_CENTRES_INTERET + " TEXT"
                + ")";

        // Création de la table planning
        String CREATE_PLANNING_TABLE = "CREATE TABLE " + TABLE_PLANNING + "("
                + COLUMN_PLANNING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_USER_ID + " INTEGER, "
                + COLUMN_CRENEAU_1 + " TEXT, "
                + COLUMN_CRENEAU_2 + " TEXT, "
                + COLUMN_CRENEAU_3 + " TEXT, "
                + COLUMN_CRENEAU_4 + " TEXT, "
                + "FOREIGN KEY(" + COLUMN_USER_ID + ") REFERENCES " + TABLE_UTILISATEURS + "(" + COLUMN_ID + ")"
                + ")";

        db.execSQL(CREATE_UTILISATEURS_TABLE);
        db.execSQL(CREATE_PLANNING_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Mise à jour simple : suppression et recréation des tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLANNING);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_UTILISATEURS);
        onCreate(db);
    }

    // Vérifie si un login existe déjà
    public boolean loginExiste(String login) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_UTILISATEURS,
                new String[]{COLUMN_ID},
                COLUMN_LOGIN + "=?",
                new String[]{login},
                null, null, null);
        boolean existe = cursor.getCount() > 0;
        cursor.close();
        return existe;
    }

    // Vérifie les identifiants pour la connexion
    public boolean verifierIdentifiants(String login, String motDePasse) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_UTILISATEURS,
                new String[]{COLUMN_ID},
                COLUMN_LOGIN + "=? AND " + COLUMN_MOT_DE_PASSE + "=?",
                new String[]{login, motDePasse},
                null, null, null);
        boolean valide = cursor.getCount() > 0;
        cursor.close();
        return valide;
    }

    // Récupère l'ID d'un utilisateur par son login
    @SuppressLint("Range")
    public long getUserId(String login) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_UTILISATEURS,
                new String[]{COLUMN_ID},
                COLUMN_LOGIN + "=?",
                new String[]{login},
                null, null, null);
        long id = -1;
        if (cursor.moveToFirst()) {
            id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID));
        }
        cursor.close();
        return id;
    }

    // Méthode pour sauvegarder un nouvel utilisateur avec validation
    public long ajouterUtilisateur(Utilisateur utilisateur) {
        // Validation du login
        if (!utilisateur.getLogin().matches("^[a-zA-Z].*") || utilisateur.getLogin().length() > 10) {
            return -1; // Login invalide
        }

        // Validation du mot de passe
        if (utilisateur.getMotDePasse().length() != 6) {
            return -1; // Mot de passe invalide
        }

        // Vérifier si le login existe déjà
        if (loginExiste(utilisateur.getLogin())) {
            return -1; // Login déjà utilisé
        }

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_LOGIN, utilisateur.getLogin());
        values.put(COLUMN_MOT_DE_PASSE, utilisateur.getMotDePasse());
        values.put(COLUMN_NOM, utilisateur.getNom());
        values.put(COLUMN_PRENOM, utilisateur.getPrenom());
        values.put(COLUMN_DATE_NAISSANCE, utilisateur.getDateNaissance());
        values.put(COLUMN_TELEPHONE, utilisateur.getTelephone());
        values.put(COLUMN_EMAIL, utilisateur.getEmail());

        // Convertir la liste de centres d'intérêt en chaîne séparée par des virgules
        String centresInteret = TextUtils.join(",", utilisateur.getCentresInteret());
        values.put(COLUMN_CENTRES_INTERET, centresInteret);

        // Insérer la ligne
        long id = db.insert(TABLE_UTILISATEURS, null, values);
        db.close();

        return id;
    }

    // Ajout ou mise à jour du planning
    public long ajouterOuMettreAJourPlanning(Planning planning) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result;

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, planning.getUserId());
        values.put(COLUMN_CRENEAU_1, planning.getCreneau1());
        values.put(COLUMN_CRENEAU_2, planning.getCreneau2());
        values.put(COLUMN_CRENEAU_3, planning.getCreneau3());
        values.put(COLUMN_CRENEAU_4, planning.getCreneau4());

        // Vérifier si un planning existe déjà pour cet utilisateur
        Cursor cursor = db.query(TABLE_PLANNING,
                new String[]{COLUMN_PLANNING_ID},
                COLUMN_USER_ID + "=?",
                new String[]{String.valueOf(planning.getUserId())},
                null, null, null);

        if (cursor.moveToFirst()) {
            // Mise à jour du planning existant
            @SuppressLint("Range") int planningId = cursor.getInt(cursor.getColumnIndex(COLUMN_PLANNING_ID));
            result = db.update(TABLE_PLANNING, values,
                    COLUMN_PLANNING_ID + "=?",
                    new String[]{String.valueOf(planningId)});
        } else {
            // Création d'un nouveau planning
            result = db.insert(TABLE_PLANNING, null, values);
        }

        cursor.close();
        db.close();
        return result;
    }

    // Récupérer le planning d'un utilisateur
    @SuppressLint("Range")
    public Planning getPlanning(long userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Planning planning = new Planning(); // Utilise le constructeur par défaut

        Cursor cursor = db.query(TABLE_PLANNING,
                new String[]{COLUMN_PLANNING_ID, COLUMN_USER_ID,
                        COLUMN_CRENEAU_1, COLUMN_CRENEAU_2,
                        COLUMN_CRENEAU_3, COLUMN_CRENEAU_4},
                COLUMN_USER_ID + "=?",
                new String[]{String.valueOf(userId)},
                null, null, null);

        if (cursor.moveToFirst()) {
            planning.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_PLANNING_ID)));
            planning.setUserId(cursor.getLong(cursor.getColumnIndex(COLUMN_USER_ID)));
            planning.setCreneau1(cursor.getString(cursor.getColumnIndex(COLUMN_CRENEAU_1)));
            planning.setCreneau2(cursor.getString(cursor.getColumnIndex(COLUMN_CRENEAU_2)));
            planning.setCreneau3(cursor.getString(cursor.getColumnIndex(COLUMN_CRENEAU_3)));
            planning.setCreneau4(cursor.getString(cursor.getColumnIndex(COLUMN_CRENEAU_4)));
        } else {
            // Si aucun planning n'existe, retourne un planning vide avec juste l'userId
            planning.setUserId(userId);
        }

        cursor.close();
        db.close();
        return planning;
    }}