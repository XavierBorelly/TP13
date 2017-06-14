package fr.formation.tp13;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import fr.formation.tp13.database.modele.User;

/**
 * Cette classe se charge d’associer un contenu (Utilisateur) au contenant (les View).
 * <p>
 * UtilisateurViewHolder : établit un lien entre la vue et ses éléments
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<User> utilisateurs;

    public RecyclerViewAdapter(List<User> utilisateurs) {
        this.utilisateurs = utilisateurs;
    }

    /**
     * Méthode appellée lors du premier affichage
     * onCreateViewHolder sera appelée lorsque le RecyclerView aura besoin d’un nouveau UtilisateurViewHolder
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.utilisateur_list_content, parent, false);
        return new UtilisateurViewHolder(view);

    }

    /**
     * onBindViewHolder est appelée par le RecyclerView lorsqu’il doit afficher un élément à une position donnée
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((UtilisateurViewHolder) holder).mContentView.setText(utilisateurs.get(position).getNom());
    }

    @Override
    public int getItemCount() {
        return utilisateurs.size();
    }

    public class UtilisateurViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public final TextView mContentView;

        public UtilisateurViewHolder(View view) {
            super(view);
            mView = view;
            mContentView = (TextView) view.findViewById(R.id.content);
        }

    }

}



