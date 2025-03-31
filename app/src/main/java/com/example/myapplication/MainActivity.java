package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements InscriptionFragment.OnInscriptionCompleteListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Charger le fragment d'inscription au démarrage de l'application
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new InscriptionFragment())
                    .commit();
        }
    }

    @Override
    public void onInscriptionComplete(Utilisateur utilisateur) {
        // Créer une instance du fragment d'affichage et lui passer les données
        AffichageFragment affichageFragment = new AffichageFragment();

        // Préparer les données à passer au fragment
        Bundle args = new Bundle();
        args.putParcelable("utilisateur", utilisateur);
        affichageFragment.setArguments(args);

        // Remplacer le fragment d'inscription par le fragment d'affichage
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, affichageFragment)
                .addToBackStack(null)
                .commit();
    }
}