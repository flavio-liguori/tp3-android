package com.example.myapplication;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText etLogin, etMotDePasse;
    private Button btnConnexion;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialisation des composants
        etLogin = findViewById(R.id.et_login);
        etMotDePasse = findViewById(R.id.et_mot_de_passe);
        btnConnexion = findViewById(R.id.btn_connexion);

        dbHelper = new DatabaseHelper(this);

        btnConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = etLogin.getText().toString().trim();
                String motDePasse = etMotDePasse.getText().toString().trim();

                if (login.isEmpty() || motDePasse.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Vérification des identifiants
                if (dbHelper.verifierIdentifiants(login, motDePasse)) {
                    // Connexion réussie, charger l'ID de l'utilisateur
                    long userId = dbHelper.getUserId(login);

                    // Redirection vers l'activité Planning
                    Intent intent = new Intent(LoginActivity.this, PlanningActivity.class);
                    intent.putExtra("USER_ID", userId);
                    startActivity(intent);
                    finish();
                } else {
                    // Identifiants incorrects
                    Toast.makeText(LoginActivity.this, "Login ou mot de passe incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}