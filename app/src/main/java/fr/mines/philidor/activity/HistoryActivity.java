package fr.mines.philidor.activity;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import fr.mines.philidor.databinding.ActivityHistoryBinding;

public class HistoryActivity extends AppCompatActivity {

    static class Game {
        String result, date, ending;
        boolean white;
        int moves;

        Game(String result, boolean white, String date, int moves, String ending) {
            this.result = result;
            this.white = white;
            this.date = date;
            this.moves = moves;
            this.ending = ending;
        }
    }

    private ActivityHistoryBinding binding;
    private final List<Game> games = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        games.add(new Game("Victoire", true, "22 nov.", 41, "mat"));
        games.add(new Game("Défaite", false, "22 nov.", 28, "mat"));
        games.add(new Game("Nulle", true, "21 nov.", 63, "pat"));
        games.add(new Game("Défaite", true, "21 nov.", 19, "abandon"));
        games.add(new Game("Victoire", false, "20 nov.", 52, "mat"));
        games.add(new Game("Défaite", false, "20 nov.", 35, "mat"));

        ArrayAdapter<Game> adapter = new ArrayAdapter<Game>(this, 0, games) {
            @Override
            public View getView(int position, View view, ViewGroup parent) {
                if (view == null) {
                    view = getLayoutInflater().inflate(R.layout.item_history, parent, false);
                }
                Game g = getItem(position);

                int color = g.result.equals("Victoire") ? 0xFF1E7F4F
                        : g.result.equals("Défaite") ? 0xFFC0392B : 0xFF94A3B8;

                TextView avatar = view.findViewById(R.id.textAvatar);
                avatar.setText(g.result.substring(0, 1));
                avatar.setBackgroundTintList(ColorStateList.valueOf(color));
                ((TextView) view.findViewById(R.id.textResult)).setText(g.result);
                ((TextView) view.findViewById(R.id.textColor)).setText(g.white ? " · Blancs" : " · Noirs");
                ((TextView) view.findViewById(R.id.textDetails)).setText(g.date + " · " + g.moves + " coups · " + g.ending);
                ((TextView) view.findViewById(R.id.textKing)).setText(g.white ? "♔" : "♚");
                return view;
            }
        };

        binding.listHistory.setAdapter(adapter);
        binding.listHistory.setEmptyView(binding.emptyView);

        binding.toolbar.setNavigationOnClickListener(v -> finish());

        updateUi();
    }

    private void updateUi() {
        boolean empty = games.isEmpty();
        binding.textHint.setVisibility(empty ? View.GONE : View.VISIBLE);
        binding.textSummary.setText(games.size() + (games.size() > 1 ? " parties" : " partie"));
    }
}