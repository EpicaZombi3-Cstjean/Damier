package cstjean.mobile.damier;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainMenuFragment} factory method to
 * create an instance of this fragment.
 */
public class MainMenuFragment extends Fragment {
    private TextInputEditText joueur1;
    private TextInputEditText joueur2;
    private Button commencer;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //TODO mettre une logique pour que le texte ne soit pas vide
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_menu, container, false);

        joueur1 = view.findViewById(R.id.text_joueur1);
        joueur2 = view.findViewById(R.id.text_joueur2);
        commencer = view.findViewById(R.id.btn_jouer);

        commencer.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), DamierActivity.class);
            startActivity(intent);
        });

        return view;
    }
}