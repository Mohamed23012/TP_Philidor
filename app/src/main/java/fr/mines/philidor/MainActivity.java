package fr.mines.philidor;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;
import java.util.Random;
import fr.mines.philidor.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_CAMP = "fr.mines.philidor.extra.CAMP";
    public static final String EXTRA_CREATIVITE = "fr.mines.philidor.extra.CREATIVITE";
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.valeur.setText(String.format(Locale.getDefault(), "%.1f",
                binding.creativite.getValue()));

        binding.creativite.addOnChangeListener((slider, valeur, parUtilisateur) -> {
            binding.valeur.setText(String.format(Locale.getDefault(), "%.1f", valeur));
        });

        binding.nouvellePartie.setOnClickListener(v -> {
            int choix = binding.couleur.getCheckedButtonId();
            boolean blancs;
            if (choix == R.id.blancs) {
                blancs = true;
            } else if (choix == R.id.noirs) {
                blancs = false;
            } else {
                blancs = new Random().nextBoolean();
            }

            Intent intent = new Intent(this, PartieActivity.class);
            intent.putExtra(EXTRA_CAMP, blancs);
            intent.putExtra(EXTRA_CREATIVITE, binding.creativite.getValue());
            startActivity(intent);
        });

        binding.historique.setOnClickListener(v -> {
            Intent intent = new Intent(this, HistoriqueActivity.class);
            startActivity(intent);
        });
    }
}
