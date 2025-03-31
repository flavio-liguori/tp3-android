package com.example.myapplication;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

public class Utilisateur implements Parcelable {
    private String login;
    private String motDePasse;
    private String nom;
    private String prenom;
    private String dateNaissance;
    private String telephone;
    private String email;
    private List<String> centresInteret;

    public Utilisateur() {
        centresInteret = new ArrayList<>();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getCentresInteret() {
        return centresInteret;
    }

    public void setCentresInteret(List<String> centresInteret) {
        this.centresInteret = centresInteret;
    }

    // Implémentation Parcelable pour passer l'objet entre fragments
    protected Utilisateur(Parcel in) {
        login = in.readString();
        motDePasse = in.readString();
        nom = in.readString();
        prenom = in.readString();
        dateNaissance = in.readString();
        telephone = in.readString();
        email = in.readString();
        centresInteret = new ArrayList<>();
        in.readStringList(centresInteret);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(login);
        dest.writeString(motDePasse);
        dest.writeString(nom);
        dest.writeString(prenom);
        dest.writeString(dateNaissance);
        dest.writeString(telephone);
        dest.writeString(email);
        dest.writeStringList(centresInteret);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Utilisateur> CREATOR = new Creator<Utilisateur>() {
        @Override
        public Utilisateur createFromParcel(Parcel in) {
            return new Utilisateur(in);
        }

        @Override
        public Utilisateur[] newArray(int size) {
            return new Utilisateur[size];
        }
    };
}