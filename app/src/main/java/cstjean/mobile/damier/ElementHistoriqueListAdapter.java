package cstjean.mobile.damier;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import cstjean.mobile.damier.logique.ElementHistorique;
import java.util.List;

/**
 * Adaptateur d'éléments d'historique pour le recyclerView.
 */
public class ElementHistoriqueListAdapter extends RecyclerView.Adapter<ElementHistoriqueViewHolder> {

    /**
     * Les éléments d'historique de l'adapter.
     */
    private final List<ElementHistorique> elementHistoriques;

    /**
     * Le constructeur.
     *
     * @param elementHistoriques les elements hisoriques.
     */
    public ElementHistoriqueListAdapter(List<ElementHistorique> elementHistoriques) {
        this.elementHistoriques = elementHistoriques;
    }

    @NonNull
    @Override
    public ElementHistoriqueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_element_historique, parent, false);
        return new ElementHistoriqueViewHolder(view);
    }

    /**
     *  Le binding de view holder.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     *                 NOTE : LE 2e ARGUMENT N'EST PAS DANS LES NOTES DE COURS, J'AI TOUT SIMPLEMENT
     *                 DÉCIDÉ DE L'AJOUTER AFIN DE FACILITER UNE CERTAINE MANOEUVRE. (l'affichage
     *                 du numéro d'élément historique)
     */
    @Override
    public void onBindViewHolder(@NonNull ElementHistoriqueViewHolder holder, int position) {
        holder.bindElementHistorique(elementHistoriques.get(position),
                getItemCount() - position);
    }

    /**
     * Le nb d'items d'historique.
     *
     * @return le nb d'items.
     *
     */
    @Override
    public int getItemCount() {
        return elementHistoriques.size();
    }

}

/**
 * Le viewHolder.
 * Le seul checkstyle qui devrait nous chialer après haha.
 */
class ElementHistoriqueViewHolder extends RecyclerView.ViewHolder {

    /**
     * Le TextView utilisé pour afficher le mouvement unique en manoury.
     */
    private final TextView manouryTextView;

    /**
     * Le TextView utilisé pour afficher le mouvement visé est le combientième.
     */
    private final TextView positionTextView;

    /**
     * Éléments d'historique.
     *
     * @param itemView le view visé.
     */
    public ElementHistoriqueViewHolder(@NonNull View itemView) {
        super(itemView);

        manouryTextView = itemView.findViewById(R.id.move_info_manoury);

        positionTextView = itemView.findViewById(R.id.numero_move);
    }

    /**
     * Permet de lier le contenu du view avec l'objet.

     * @param elementHistorique l'élément d'historique visé.
     * @param numeroMove utilisé pour le "combientième".
     */
    void bindElementHistorique(ElementHistorique elementHistorique, Integer numeroMove) {

        String numMove = numeroMove.toString();
        manouryTextView.setText(ElementHistorique.getMouvementString(elementHistorique));
        positionTextView.setText(numMove);
    }
}