package fr.formation.tp13;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import fr.formation.tp13.database.datasource.DataSource;
import fr.formation.tp13.database.modele.User;

/**
 * Created by admin on 16/06/2017.
 */

public class ListeUtilisateurFragment extends Fragment {

    private DataSource<User> dataSource;
    private int versionDB = 4; // Permet de detruire la base de données SQLite si on incrémente la version
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private List<User> utilisateurs = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_utilisateur, container, false);
        recyclerView = (RecyclerView) view;

        chargerUtilisateurs();


        adapter = new RecyclerViewAdapter(utilisateurs, this);
        recyclerView.setAdapter(adapter);
        return view;
    }



    @Override
    public void onStart() {
        super.onStart();
        try {
            if (dataSource == null) {
                dataSource = new DataSource<>(this.getContext(), User.class, versionDB);
                dataSource.open();
            }
        } catch (Exception e) {
            // Traiter le cas !
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            dataSource.close();
        } catch (Exception e) {
            // Traiter le cas !
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        chargerUtilisateurs();
    }

    public void chargerUtilisateurs() {
        // On charge les données depuis la base.
        try {
            List<User> users = dataSource.readAll();
            utilisateurs.clear();
            utilisateurs.addAll(users);

        } catch (Exception e) {
            // Que faire ?
            e.printStackTrace();
        }
    }
}
