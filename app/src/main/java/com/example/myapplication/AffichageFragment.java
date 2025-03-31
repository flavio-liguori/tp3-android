package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AffichageFragment extends Fragment {

    private TextView tvLogin, tvNom, tvPrenom, tvDateNaissance, tvTelephone, tvEmail, tvCentresInteret;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_affichage, container, false);

        // Initialiser les vues
        tvLogin = view.findViewById(R.id.tv_login);
        tvNom = view.findViewById(R.id.tv_nom);
        tvPrenom = view.findViewById(R.id.tv_prenom);
        tvDateNaissance = view.findViewById(R.id.tv_date_naissance);
        tvTelephone = view.findViewById(R.id.tv_telephone);
        tvEmail = view.findViewById(R.id.tv_email);
        tvCentresInteret = view.findViewById(R.id.tv_centres_interet);

        // Récupérer les données de l'utilisateur
        Bundle args = getArguments();
        if (args != null && args.containsKey("utilisateur")) {
            Utilisateur utilisateur = args.getParcelable("utilisateur");
            if (utilisateur != null) {
                afficherDonneesUtilisateur(utilisateur);
            }
        }

        return view;
    }

    private void afficherDonneesUtilisateur(Utilisateur utilisateur) {
        tvLogin.setText("Login: " + utilisateur.getLogin());
        tvNom.setText("Nom: " + utilisateur.getNom());
        tvPrenom.setText("Prénom: " + utilisateur.getPrenom());
        tvDateNaissance.setText("Date de naissance: " + utilisateur.getDateNaissance());
        tvTelephone.setText("Téléphone: " + utilisateur.getTelephone());
        tvEmail.setText("Email: " + utilisateur.getEmail());

        // Afficher les centres d'intérêt
        StringBuilder centresInteret = new StringBuilder("Centres d'intérêt: ");
        if (utilisateur.getCentresInteret().isEmpty()) {
            centresInteret.append("Aucun");
        } else {
            for (int i = 0; i < utilisateur.getCentresInteret().size(); i++) {
                centresInteret.append(utilisateur.getCentresInteret().get(i));
                if (i < utilisateur.getCentresInteret().size() - 1) {
                    centresInteret.append(", ");
                }
            }
        }
        tvCentresInteret.setText(centresInteret.toString());
    }
}