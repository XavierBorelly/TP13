package fr.formation.tp13;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import fr.formation.tp13.database.modele.User;

/**
 * Cette classe se charge d’associer un contenu (Utilisateur) au contenant (les View).
 * <p>
 * UtilisateurViewHolder : établit un lien entre la vue et ses éléments
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.UtilisateurViewHolder> {

    private final Fragment fragment;
    private List<User> utilisateurs;

    public RecyclerViewAdapter(List<User> utilisateurs, Fragment fragment) {
        this.utilisateurs = utilisateurs;
        this.fragment = fragment;
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
    public UtilisateurViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
    public void onBindViewHolder(UtilisateurViewHolder holder, final int position) {// find item by position


        String item = utilisateurs.get(position).getNom();

        holder.mContentView.setText(item);
        if ((position % 2) == 0) {
            holder.itemView.setBackgroundResource(R.color.color1);
        } else {
            holder.itemView.setBackgroundResource(R.color.color2);
        }
        holder.mView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                UtilisateurFragment articleFram = (UtilisateurFragment) fragment.getFragmentManager().findFragmentById(R.id.article_fragment);

                if (articleFram != null){
                    articleFram.updateArticleView(position);
                } else {
                    UtilisateurFragment newFragment = new UtilisateurFragment();
                    Bundle args = new Bundle();
                    args.putInt(UtilisateurFragment.getArgPosition(), position);
                    args.putString(UtilisateurFragment.getArgDescription(), utilisateurs.get(position).getDescription());
                    newFragment.setArguments(args);
                    FragmentTransaction transaction = fragment.getFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, newFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }

            }

        });

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



