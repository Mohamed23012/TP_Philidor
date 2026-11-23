package fr.mines.philidor;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import fr.mines.philidor.databinding.ActivityHistoriqueBinding;

public class HistoriqueActivity extends AppCompatActivity {
    private ActivityHistoriqueBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHistoriqueBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
