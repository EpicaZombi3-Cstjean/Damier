package cstjean.mobile.damier;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;

public class DamierFragment extends Fragment {

    int[] rowIDs = new int[10];
    int[] buttonIDs = new int[50];

    public DamierFragment() {
        // Required empty public constructor
    }

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
                buttonSize = 50;
            } else {
                buttonSize = 75;
            }

            // LayoutParams pour le positionnement par rapport au layout
            TableRow.LayoutParams params = new TableRow.LayoutParams();
            params.height = buttonSize;
            params.width = buttonSize;

            params.setMargins(0, 0, 0, 0);

            bouton.setLayoutParams(params);

            //TableRow row = getRowWithButtonIndex(i, view);

            bouton.setOnClickListener(v -> buttonPress(finalI)); // Note : je ne connais pas les lambdas.

            int btn_id = View.generateViewId();
            buttonIDs[i - 1] = btn_id;
            bouton.setId(btn_id);

            bouton.setImageResource(R.drawable.ic_placeholder_android_icon);

            gridLayout.addView(bouton);
        }

    }



    private void buttonPress(int index) {
        Log.d("oui", "oui");
    }
}