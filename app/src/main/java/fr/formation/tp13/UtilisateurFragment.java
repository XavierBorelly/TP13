package fr.formation.tp13;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by admin on 16/06/2017.
 */

public class UtilisateurFragment extends Fragment {
    private final static String ARG_POSITION = "position";
    private final static String ARG_DESCRIPTION = "description";
    private int mCurrentPosition = -1;
    private TextView article;
    private String description;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState != null){
            mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
        }
        View view = inflater.inflate(R.layout.utilisateur, container, false);
        article = (TextView) view.findViewById(R.id.article);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle args = getArguments();
        if(args != null) {
            description = args.getString(ARG_DESCRIPTION);
            updateArticleView(args.getInt(ARG_POSITION));
        } else if (mCurrentPosition != -1){
            updateArticleView(mCurrentPosition);
        }
    }

    public void updateArticleView(int position) {
        if(article != null){
            article.setText(description);
            article.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    ListeUtilisateurFragment utilisateurFram = (ListeUtilisateurFragment) getFragmentManager().findFragmentById(R.id.liste_fragment);

                    if (utilisateurFram == null) {
                        ListeUtilisateurFragment newFragment = new ListeUtilisateurFragment();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, newFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }

                }
            });
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ARG_POSITION, mCurrentPosition);
    }

    public static String getArgDescription(){
        return ARG_DESCRIPTION;
    }

    public static String getArgPosition(){
        return ARG_POSITION;
    }
}
