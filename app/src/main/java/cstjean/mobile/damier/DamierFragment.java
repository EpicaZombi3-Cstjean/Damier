package cstjean.mobile.damier;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.Stack;

import cstjean.mobile.damier.logique.Damier;
import cstjean.mobile.damier.logique.ElementHistorique;
import cstjean.mobile.damier.logique.Pion;

public class DamierFragment extends Fragment {

    /**
     * Le damier utilisé // TODO UTILIER UN SINGLETON?
     */
    public Damier damier = new Damier();

    TextView textTourJoueur;
    TextView textLastMove;
    Button btn_back_reset;
    final Stack<Integer> historiqueSelectedSlots = new Stack<>();


    /**
     * Le ID des boutons, nous permettant de continuer à les utiliser.
     */
    int[] buttonIDs = new int[50];

    private InterfaceState interfaceState = InterfaceState.ShowOnly;
    private int selectedSlot = 0;

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

        textTourJoueur = view.findViewById(R.id.txt_tour_joueur);

        textLastMove = view.findViewById(R.id.text_lastMove);

        btn_back_reset = view.findViewById(R.id.btn_back_reset);
        btn_back_reset.setOnClickListener(v -> {

            buttonRetourArrierePress(view);

        }); // Note : je ne connais pas les lambdas.

        updateInterface(view);

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
            bouton.setOnClickListener(v -> buttonPress(finalI, view)); // Note : je ne connais pas les lambdas.

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
                bouton.setContentDescription("Position " + finalI + ", Case Blanche");
            } else {

                gridLayout.addView(caseBlanche);
                gridLayout.addView(bouton);
                bouton.setContentDescription("Position " + finalI + ", Case Noire");
            }


        }
    }

    private void buttonPress(int position, View view) {
        if (interfaceState == InterfaceState.ShowOnly) {

            modeShowOnly(position);

        } else if (interfaceState == InterfaceState.Selected) {

           modeSelected(position);

        }

        updateInterface(view);

    }

    private void buttonRetourArrierePress(View view) {
        if (!historiqueSelectedSlots.isEmpty()) {

            selectedSlot = historiqueSelectedSlots.pop();
            interfaceState = InterfaceState.Selected;
        }

        damier.retourArriere();
        updateInterface(view);

    }
    private void modeShowOnly(int position) {
        Pion pion = damier.findPion(position);

        if (pion != null && pion.getCouleur() == damier.getTourJoueur()) {

            Integer[] deplacementsPossibles;

            if (damier.getPrisesFromHistorique(pion.getCouleur()).length == 0
                    || damier.getPrisesFromHistorique(
                    pion.getCouleur() == Pion.Couleur.Blanc
                            ? Pion.Couleur.Noir
                            : Pion.Couleur.Blanc).length != 0) {

                deplacementsPossibles = damier.getDeplacementsPossibles(position, false);

            } else {
                // prise forcée
                deplacementsPossibles = damier.getDeplacementsPossibles(position, true);

            }

            if (deplacementsPossibles.length > 0) {

                selectedSlot = position;
                interfaceState = InterfaceState.Selected;

            }
        }

    }

    private void modeSelected(int position) {
        Pion pion = damier.findPion(selectedSlot);

        if (pion != null && pion.getCouleur() == damier.getTourJoueur()) {

            Integer[] deplacementsPossibles;

            if (damier.getPrisesFromHistorique(pion.getCouleur()).length == 0
                    || damier.getPrisesFromHistorique(
                    pion.getCouleur() == Pion.Couleur.Blanc
                            ? Pion.Couleur.Noir
                            : Pion.Couleur.Blanc).length != 0) {

                deplacementsPossibles = damier.getDeplacementsPossibles(selectedSlot, false);

            } else {
                // prise forcée
                deplacementsPossibles = damier.getDeplacementsPossibles(selectedSlot, true);

            }

            boolean isInPossibilities = Damier.estPositionDansArray(position, deplacementsPossibles);

            if (isInPossibilities) {
                // regarde si peut faire une autre 'PRISE'.

                damier.deplacerPion(selectedSlot, position);
                historiqueSelectedSlots.push(selectedSlot);

                if (damier.getDeplacementsPossibles(position, true).length > 0
                    && damier.findPion(position).getCouleur() == damier.getTourJoueur()
                ) {

                    selectedSlot = position;

                } else {

                    selectedSlot = 0;
                    interfaceState = InterfaceState.ShowOnly;

                }

            } else {

                selectedSlot = 0;
                interfaceState = InterfaceState.ShowOnly;

            }
        } else {

            selectedSlot = 0;
            interfaceState = InterfaceState.ShowOnly;

        }
    }

    public void updateInterface(View view) {

        for(int i = 1; i <= buttonIDs.length; i++) {

            ImageButton btn = view.findViewById(buttonIDs[i - 1]);

            btn.setBackgroundResource(R.mipmap.ic_case_dark_grey);

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

        if (selectedSlot != 0 && interfaceState == InterfaceState.Selected) {
            modifierCouleurCases(selectedSlot, view);
        }

        textLastMove.setText(ElementHistorique.getHistoriqueTour(damier));

        if (damier.getEtatPartie() == Damier.EtatPartie.EnCours) {

            textTourJoueur.setText("Joueur actuel: " + damier.getTourJoueur().toString());

        } else {

            textTourJoueur.setText(damier.getEtatPartie().toString());
        }
    }

    /**
     * Modifie la couleur des cases si elles font partie des options possibles
     * et modifie la couleur de la case focusée.
     */
    private void modifierCouleurCases(int position, View view) {

        Pion pion = damier.findPion(position);

        if (pion != null && pion.getCouleur() == damier.getTourJoueur()) {

            Integer[] deplacementsPossibles;

            if (damier.getPrisesFromHistorique(pion.getCouleur()).length == 0
                    || damier.getPrisesFromHistorique(
                            pion.getCouleur() == Pion.Couleur.Blanc
                                    ? Pion.Couleur.Noir
                                    : Pion.Couleur.Blanc).length != 0) {
                // déplacement (avec ou sans prise)
                deplacementsPossibles = damier.getDeplacementsPossibles(position, false);

            } else {
                // prise forcée
                deplacementsPossibles = damier.getDeplacementsPossibles(position, true);

            }

            for (int i = 0; i < deplacementsPossibles.length; i++) {
                if (deplacementsPossibles[i] - 1 > 0 && deplacementsPossibles[i] - 1 <= 50) {

                    ImageButton button = view.findViewById(buttonIDs[deplacementsPossibles[i] - 1]);

                    button.setBackgroundResource(R.mipmap.ic_case_deplacement_possible);

                }

            }
        }

    }

    /**
     *
     */
    private enum InterfaceState {
        ShowOnly, Selected, GameOver
    }
}