package com.example.myapplication;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PlanningActivity extends AppCompatActivity {

    private EditText etCreneau1, etCreneau2, etCreneau3, etCreneau4;
    private Button btnValiderPlanning;
    private DatabaseHelper dbHelper;
    private long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planning);

        // Récupérer l'ID de l'utilisateur
        userId = getIntent().getLongExtra("USER_ID", -1);
        if (userId == -1) {
            Toast.makeText(this, "Erreur: utilisateur non identifié", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Initialisation des composants
        etCreneau1 = findViewById(R.id.et_creneau_1);
        etCreneau2 = findViewById(R.id.et_creneau_2);
        etCreneau3 = findViewById(R.id.et_creneau_3);
        etCreneau4 = findViewById(R.id.et_creneau_4);
        btnValiderPlanning = findViewById(R.id.btn_valider_planning);

        dbHelper = new DatabaseHelper(this);

        // Charger le planning existant s'il y en a un
        Planning planning = dbHelper.getPlanning(userId);
        if (planning != null) {
            etCreneau1.setText(planning.getCreneau1());
            etCreneau2.setText(planning.getCreneau2());
            etCreneau3.setText(planning.getCreneau3());
            etCreneau4.setText(planning.getCreneau4());
        }

        btnValiderPlanning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sauvegarderPlanning();
            }
        });
    }

    private void sauvegarderPlanning() {
        // Création d'un objet Planning
        Planning planning = new Planning();
        planning.setUserId(userId);
        planning.setCreneau1(etCreneau1.getText().toString().trim());
        planning.setCreneau2(etCreneau2.getText().toString().trim());
        planning.setCreneau3(etCreneau3.getText().toString().trim());
        planning.setCreneau4(etCreneau4.getText().toString().trim());

        // Sauvegarde dans la base de données
        long result = dbHelper.ajouterOuMettreAJourPlanning(planning);

        if (result != -1) {
            Toast.makeText(this, "Planning sauvegardé avec succès", Toast.LENGTH_SHORT).show();

            // Passage à l'activité de synthèse
            Intent intent = new Intent(PlanningActivity.this, PlanningSummaryActivity.class);
            intent.putExtra("PLANNING", planning);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Erreur lors de la sauvegarde du planning", Toast.LENGTH_SHORT).show();
        }
    }
}