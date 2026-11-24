
package fr.mines.philidor.View;
import fr.mines.philidor.R;
import fr.mines.philidor.moteur.Partie;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.MotionEvent;
import androidx.annotation.NonNull;

public class EchiquierView extends View {

    private final Paint pinceau = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Partie partie = new Partie();
    private float taille;
    private int colonneSelectionnee = -1, ligneSelectionnee = -1;
    public EchiquierView(Context c) {
        super(c);
    }

    public EchiquierView(Context c, AttributeSet a) {
        super(c, a);
    }
    @Override
    protected void onSizeChanged(int w, int h, int ancienW, int ancienH) {
        super.onSizeChanged(w, h, ancienW, ancienH);
        taille = Math.min(w, h) / 8f;
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        String dernier = partie.dernierCoup();
        for (int ligne = 0; ligne < 8; ligne++) {
            for (int colonne = 0; colonne < 8; colonne++) {

                float x = colonne * taille;
                float y = ligne * taille;

                pinceau.setColor(getContext().getColor(
                        (ligne + colonne) % 2 == 0
                                ? R.color.case_claire : R.color.case_foncee));
                if (dernier != null) {
                    if ((colonne == dernier.charAt(0) - 'a'
                            && 7 - ligne == dernier.charAt(1) - '1')
                            || (colonne == dernier.charAt(2) - 'a'
                            && 7 - ligne == dernier.charAt(3) - '1')) {

                        pinceau.setColor(getContext().getColor(R.color.dernier_coup));
                    }
                }
                if (colonne == colonneSelectionnee && ligne == ligneSelectionnee)
                    pinceau.setColor(getContext().getColor(R.color.case_selectionnee));

                canvas.drawRect(x, y, x + taille, y + taille, pinceau);

                char piece = partie.piece(colonne, 7 - ligne);
                int indice = "KQRBNPkqrbnp".indexOf(piece);

                if (indice >= 0) {
                    pinceau.setColor(getContext().getColor(R.color.couleur_pieces));
                    pinceau.setTextSize(taille * 0.8f);

                    canvas.drawText(String.valueOf((char) ('\u2654' + indice)),
                            x + taille * 0.1f, y + taille * 0.8f, pinceau);
                }
            }
        }

        // Afficher les coups possibles
        if (colonneSelectionnee >= 0 && colonneSelectionnee < 8
                && ligneSelectionnee >= 0 && ligneSelectionnee < 8) {

            pinceau.setColor(getContext().getColor(R.color.coup_possible));

            for (String coup : partie.coupsDepuis(
                    colonneSelectionnee, 7 - ligneSelectionnee)) {

                int c = coup.charAt(2) - 'a';
                int l = 7 - (coup.charAt(3) - '1');

                if (partie.piece(c, 7 - l) == ' ') {
                    pinceau.setStyle(Paint.Style.FILL);

                    canvas.drawCircle(c * taille + taille / 2,
                            l * taille + taille / 2,
                            taille / 8, pinceau);
                } else {
                    pinceau.setStyle(Paint.Style.STROKE);
                    pinceau.setStrokeWidth(taille / 15);

                    canvas.drawCircle(c * taille + taille / 2,
                            l * taille + taille / 2,
                            taille / 2.2f, pinceau);

                    pinceau.setStyle(Paint.Style.FILL);
                }
            }
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                return true;

            case MotionEvent.ACTION_UP:
                int c = (int) (e.getX() / taille);
                int l = (int) (e.getY() / taille);

                if (c < 0 || c >= 8 || l < 0 || l >= 8)
                    return true;

                boolean joue = false;

                if (colonneSelectionnee >= 0) {
                    for (String coup : partie.coupsDepuis(
                            colonneSelectionnee, 7 - ligneSelectionnee)) {

                        if (coup.charAt(2) - 'a' == c
                                && coup.charAt(3) - '1' == 7 - l
                                && (coup.length() == 4 || coup.endsWith("q"))) {
                            joue = partie.jouer(coup);
                            break;
                        }
                    }
                }

                colonneSelectionnee = -1;
                ligneSelectionnee = -1;

                if (!joue && !partie.coupsDepuis(c, 7 - l).isEmpty()) {
                    colonneSelectionnee = c;
                    ligneSelectionnee = l;
                }

                invalidate();
                performClick();
                return true;
        }

        return super.onTouchEvent(e);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }
    public void annuler() {
        partie.annuler();
        colonneSelectionnee = -1;
        ligneSelectionnee = -1;
        invalidate();
    }
    public Partie getPartie() {
        return partie;
    }


}
