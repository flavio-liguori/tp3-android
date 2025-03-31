package com.example.myapplication;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class InscriptionFragment extends Fragment {

    private EditText etLogin, etMotDePasse, etNom, etPrenom, etDateNaissance, etTelephone, etEmail;
    private LinearLayout centresInteretLayout;
    private Button btnSoumettre;
    private DatabaseHelper dbHelper;
    private OnInscriptionCompleteListener listener;

    // Interface pour communiquer avec l'activité
    public interface OnInscriptionCompleteListener {
        void onInscriptionComplete(Utilisateur utilisateur);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnInscriptionCompleteListener) {
            listener = (OnInscriptionCompleteListener) context;
        } else {
            throw new RuntimeException(context + " doit implémenter OnInscriptionCompleteListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inscription, container, false);

        // Initialiser la base de données
        dbHelper = new DatabaseHelper(getContext());

        // Initialiser les vues
        etLogin = view.findViewById(R.id.et_login);
        etMotDePasse = view.findViewById(R.id.et_mot_de_passe);
        etNom = view.findViewById(R.id.et_nom);
        etPrenom = view.findViewById(R.id.et_prenom);
        etDateNaissance = view.findViewById(R.id.et_date_naissance);
        etTelephone = view.findViewById(R.id.et_telephone);
        etEmail = view.findViewById(R.id.et_email);
        centresInteretLayout = view.findViewById(R.id.centres_interet_layout);
        btnSoumettre = view.findViewById(R.id.btn_soumettre);

        // Configuration du DatePicker pour la date de naissance
        etDateNaissance.setOnClickListener(v -> showDatePickerDialog());

        // Configuration du bouton Soumettre
        btnSoumettre.setOnClickListener(v -> {
            if (validerFormulaire()) {
                sauvegarderUtilisateur();
            }
        });

        return view;
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                (DatePicker view, int yearSelected, int monthOfYear, int dayOfMonth) -> {
                    String dateSelected = dayOfMonth + "/" + (monthOfYear + 1) + "/" + yearSelected;
                    etDateNaissance.setText(dateSelected);
                },
                year, month, day
        );
        datePickerDialog.show();
    }

    private boolean validerFormulaire() {
        // Vérifier que tous les champs obligatoires sont remplis
        if (etLogin.getText().toString().isEmpty() ||
                etMotDePasse.getText().toString().isEmpty() ||
                etNom.getText().toString().isEmpty() ||
                etPrenom.getText().toString().isEmpty() ||
                etDateNaissance.getText().toString().isEmpty() ||
                etTelephone.getText().toString().isEmpty() ||
                etEmail.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void sauvegarderUtilisateur() {
        // Créer un nouvel utilisateur avec les données du formulaire
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setLogin(etLogin.getText().toString());
        utilisateur.setMotDePasse(etMotDePasse.getText().toString());
        utilisateur.setNom(etNom.getText().toString());
        utilisateur.setPrenom(etPrenom.getText().toString());
        utilisateur.setDateNaissance(etDateNaissance.getText().toString());
        utilisateur.setTelephone(etTelephone.getText().toString());
        utilisateur.setEmail(etEmail.getText().toString());

        // Récupérer les centres d'intérêt sélectionnés
        List<String> centresInteret = new ArrayList<>();
        for (int i = 0; i < centresInteretLayout.getChildCount(); i++) {
            View view = centresInteretLayout.getChildAt(i);
            if (view instanceof CheckBox) {
                CheckBox checkBox = (CheckBox) view;
                if (checkBox.isChecked()) {
                    centresInteret.add(checkBox.getText().toString());
                }
            }
        }
        utilisateur.setCentresInteret(centresInteret);

        // Sauvegarder l'utilisateur dans la base de données
        long id = dbHelper.ajouterUtilisateur(utilisateur);

        if (id != -1) {
            Toast.makeText(getContext(), "Inscription réussie !", Toast.LENGTH_SHORT).show();
            // Notifier l'activité que l'inscription est terminée
            listener.onInscriptionComplete(utilisateur);
        } else {
            Toast.makeText(getContext(), "Erreur lors de l'inscription", Toast.LENGTH_SHORT).show();
        }
    }
}