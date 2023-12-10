package cstjean.mobile.damier;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import com.google.android.material.textfield.TextInputEditText;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainMenuFragment} factory method to
 * create an instance of this fragment.
 */
public class MainMenuFragment extends Fragment {
    /**
     * Le texte pour écrire le nom du joueur blanc.
     */
    private TextInputEditText joueur1;
    /**
     * Le texte pour écrire le nom du joueur noir.
     */
    private TextInputEditText joueur2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // TODO mettre une logique pour que le texte ne soit pas vide
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_menu, container, false);

        joueur1 = view.findViewById(R.id.text_joueur1);
        joueur2 = view.findViewById(R.id.text_joueur2);
        Button commencer = view.findViewById(R.id.btn_jouer);

        commencer.setOnClickListener(v -> {
            String nom1 = (Objects.requireNonNull(joueur1.getText())).toString();
            String nom2 = (Objects.requireNonNull(joueur2.getText())).toString();

            nom1 = nom1.replace(" ", "");
            nom2 = nom2.replace(" ", "");
            if (!TextUtils.isEmpty(nom1) && !TextUtils.isEmpty(nom2)) {
                Intent intent = new Intent(getActivity(), DamierActivity.class);
                intent.putExtra("nomJoueur1", nom1);
                intent.putExtra("nomJoueur2", nom2);
                startActivity(intent);
            }
        });

        return view;
    }
}