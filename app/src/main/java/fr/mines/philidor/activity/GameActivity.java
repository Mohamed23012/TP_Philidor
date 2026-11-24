package fr.mines.philidor.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

import fr.mines.philidor.R;
import fr.mines.philidor.databinding.ActivityGameBinding;
import fr.mines.philidor.moteur.Partie;
import java.util.List;
public class GameActivity extends AppCompatActivity {
    private ActivityGameBinding binding;
    private void actualiserStatut() {
        Partie p = binding.echiquier.getPartie();

        if (p.enEchec())
            binding.statut.setText(R.string.echec);
        else
            binding.statut.setText(p.traitAuxBlancs()
                    ? R.string.trait_blancs
                    : R.string.trait_noirs);
        List<String> coups = p.coupsSan();
        String texte = "";

        for (int i = 0; i < coups.size(); i++) {
            if (i % 2 == 0)
                texte += (i / 2 + 1) + ". ";

            texte += coups.get(i) + " ";
        }

        binding.listeCoups.setText(texte);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowCompat.enableEdgeToEdge(getWindow());

        binding = ActivityGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(
                    WindowInsetsCompat.Type.systemBars());

            v.setPadding(systemBars.left, systemBars.top,
                    systemBars.right, systemBars.bottom);

            return insets;
        });

        binding.annuler.setOnClickListener(v -> {
            binding.echiquier.annuler();
            actualiserStatut();
        });

        binding.echiquier.setOnClickListener(v -> actualiserStatut());

        MainActivity.COULEUR couleur =
                (MainActivity.COULEUR) getIntent().getSerializableExtra(
                        "fr.mines.philidor.extra.CAMP");

        float creativite = getIntent().getFloatExtra("creativite", 0.6f);

        Toast.makeText(this, "COULEUR: " + couleur
                + ", CREA: " + creativite, Toast.LENGTH_SHORT).show();

    }

}