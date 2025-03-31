package com.example.myapplication;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class PlanningSummaryActivity extends AppCompatActivity {

    private TextView tvCreneau1, tvCreneau2, tvCreneau3, tvCreneau4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planning_summary);

        // Initialisation des composants
        tvCreneau1 = findViewById(R.id.tv_creneau_1);
        tvCreneau2 = findViewById(R.id.tv_creneau_2);
        tvCreneau3 = findViewById(R.id.tv_creneau_3);
        tvCreneau4 = findViewById(R.id.tv_creneau_4);

        // Récupération du planning
        if (getIntent().hasExtra("PLANNING")) {
            Planning planning = getIntent().getParcelableExtra("PLANNING");
            if (planning != null) {
                // Affichage des créneaux
                tvCreneau1.setText("08h-10h : " + planning.getCreneau1());
                tvCreneau2.setText("10h-12h : " + planning.getCreneau2());
                tvCreneau3.setText("14h-16h : " + planning.getCreneau3());
                tvCreneau4.setText("16h-18h : " + planning.getCreneau4());
            }
        }
    }
}
