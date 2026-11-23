package fr.mines.philidor;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import fr.mines.philidor.databinding.ActivityPartieBinding;

public class PartieActivity extends AppCompatActivity {
    private ActivityPartieBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPartieBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        boolean blancs = intent.getBooleanExtra(MainActivity.EXTRA_CAMP, true);
        float creativite = intent.getFloatExtra(MainActivity.EXTRA_CREATIVITE, 0.6f);
        String couleur = getString(blancs ? R.string.blancs : R.string.noirs);

        binding.campRecu.setText(getString(R.string.couleur_choisie, couleur));
        binding.creativiteRecue.setText(getString(R.string.creativite_choisie, creativite));
    }
}
