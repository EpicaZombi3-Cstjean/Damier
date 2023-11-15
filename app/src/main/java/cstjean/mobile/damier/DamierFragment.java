package cstjean.mobile.damier;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;

import cstjean.mobile.damier.logique.Damier;
import cstjean.mobile.damier.logique.Pion;

public class DamierFragment extends Fragment {

    /**
     * Le damier utilisé // TODO UTILIER UN SINGLETON?
     */
    public Damier damier = new Damier();

    /**
     * Le ID des boutons, nous permettant de continuer à les utiliser.
     */
    int[] buttonIDs = new int[50];

    private InterfaceState interfaceState = InterfaceState.ShowOnly;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        placerCasesDamier(view);

        updateInterface(view,0);

        return view;
    }


    private void placerCasesDamier(View view) {

        GridLayout gridLayout = view.findViewById(R.id.grille);

        for (int i = 1; i <= 50; i++) {

            final int finalI = i;
            final ImageButton bouton = new ImageButton(getActivity());

            int buttonSize;

            if (getResources().getConfiguration().orientation ==
                    Configuration.ORIENTATION_LANDSCAPE) {
                buttonSize = 75;
            } else {
                buttonSize = 100;
            }

            // LayoutParams pour le positionnement par rapport au layout
            TableLayout.LayoutParams params = new TableLayout.LayoutParams();
            params.height = buttonSize;
            params.width = buttonSize;
            params.setMargins(0, 0, 0, 0);

            // Setup du bouton.
            bouton.setLayoutParams(params);
            bouton.setOnClickListener(v -> buttonPress(finalI)); // Note : je ne connais pas les lambdas.

            bouton.setScaleType(ImageView.ScaleType.FIT_XY);
            bouton.setPadding(5, 5, 5, 5);

            // Ajout de l'ID pour le bouton.
            int btn_id = View.generateViewId();
            buttonIDs[i - 1] = btn_id;
            bouton.setId(btn_id);
            bouton.setBackgroundResource(R.mipmap.ic_case_dark_grey);

            // Image servant à placer la case blanche située dans la paire (car une position = 2 cases).

            final ImageView caseBlanche = new ImageView(getActivity());

            caseBlanche.setLayoutParams(params);
            caseBlanche.setImageResource(R.mipmap.ic_case_light_grey);

            if (Damier.getRow(i) % 2 == 0) {

                gridLayout.addView(bouton);
                gridLayout.addView(caseBlanche);

            } else {

                gridLayout.addView(caseBlanche);
                gridLayout.addView(bouton);

            }


        }
    }

    private void buttonPress(int index) {
        Log.d("oui", "oui");
    }

    public void updateInterface(View view, int focusPos) {

        for(int i = 1; i <= buttonIDs.length; i++) {

            ImageButton btn = view.findViewById(buttonIDs[i - 1]);

            if (damier.findPion(i) != null) {

                if (damier.findPion(i).getCouleur() == Pion.Couleur.Blanc) {

                    if (damier.findPion(i).estDame()) {

                        btn.setImageResource(R.drawable.ic_baseline_add_circle_outline_light_grey);

                    } else {

                        btn.setImageResource(R.drawable.ic_baseline_radio_button_unchecked_light_grey);

                    }

                } else {

                    if (damier.findPion(i).estDame()) {

                        btn.setImageResource(R.drawable.ic_baseline_add_circle_outline_grey);

                    } else {

                        btn.setImageResource(R.drawable.ic_baseline_radio_button_unchecked_grey);

                    }

                }
            } else {

                btn.setImageResource(R.drawable.ic_invisible_icon);

            }
        }

        if (focusPos != 0) {
            modifierCouleurCases(focusPos);
        }
    }

    /**
     * Modifie la couleur des cases si elles font partie des options possibles
     * et modifie la couleur de la case focusée.
     */
    private void modifierCouleurCases(int position) {

        damier.getDeplacementsPossibles(position);



    }

    /**
     *
     */
    private enum InterfaceState {
        ShowOnly, Selected, GameOver
    }
}