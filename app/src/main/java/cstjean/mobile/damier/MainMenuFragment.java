package cstjean.mobile.damier;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
     * Le clé utilisé repris dans le fragment du joueur Blanc.
     */
    private final String keynomblanc = "nom_blanc";
    /**
     * Le clé utilisé repris dans le fragment du joueur Blanc.
     */
    private final String keynomnoir = "nom_noir";

    /**
     * Le texte pour écrire le nom du joueur blanc.
     */
    private TextInputEditText texteJoueur1;
    /**
     * Le texte pour écrire le nom du joueur noir.
     */
    private TextInputEditText texteJoueur2;

    /**
     * Le bouton pour commencer la partie.
     */
    private Button boutonCommencer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_menu, container, false);

        texteJoueur1 = view.findViewById(R.id.text_joueur1);
        texteJoueur2 = view.findViewById(R.id.text_joueur2);
        boutonCommencer = view.findViewById(R.id.btn_jouer);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                update();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };

        texteJoueur1.addTextChangedListener(
            textWatcher
        );

        texteJoueur2.addTextChangedListener(
            textWatcher
        );

        boutonCommencer.setOnClickListener(v -> {
            String nom1 = preparerNom((Objects.requireNonNull(texteJoueur1.getText())).toString());
            String nom2 = preparerNom((Objects.requireNonNull(texteJoueur2.getText())).toString());

            if (!TextUtils.isEmpty(nom1) && !TextUtils.isEmpty(nom2)) {
                Intent intent = new Intent(getActivity(), DamierActivity.class);
                intent.putExtra(keynomblanc, nom1);
                intent.putExtra(keynomnoir, nom2);
                startActivity(intent);
            }
        });

        update();

        return view;
    }

    private void update() {
        boutonCommencer.setEnabled(
            preparerNom(Objects.requireNonNull(texteJoueur1.getText()).toString()).length() > 0 &&
            preparerNom(Objects.requireNonNull(texteJoueur2.getText()).toString()).length() > 0
        );

    }

    private String preparerNom(String str) {

        int beginningWords = 0;
        int endWords = str.length() - 1;

        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) != ' ' && str.charAt(i) != '\n') {
                beginningWords = i;
                break;
            } else {
                if (i == str.length() - 1) {
                    return "";
                }
            }
        }
        for (int i = str.length() - 1; i >= 0; i--) {
            if (str.charAt(i) != ' ' && str.charAt(i) != '\n') {
                endWords = i;
                break;
            }
        }

        StringBuilder futureStringBuilder = new StringBuilder();
        for (int i = beginningWords; i <= endWords; i++) {
            if (str.charAt(i) != '\n') {
                futureStringBuilder.append(str.charAt(i));
            }
        }
        return futureStringBuilder.toString();
    }
}