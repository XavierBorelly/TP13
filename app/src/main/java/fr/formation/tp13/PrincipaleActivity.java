package fr.formation.tp13;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import fr.formation.tp13.database.datasource.DataSource;
import fr.formation.tp13.database.modele.User;

public class PrincipaleActivity extends Activity {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private List<User> utilisateurs = new ArrayList<>();
    private DataSource<User> dataSource;
    // Pour quitter l'application :
    private Toast toast;
    private long lastBackPressTime = 0;
    private int versionDB = 3; // Permet de detruire la base de données SQLite si on incrémente la version


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principale);
        View view = findViewById(R.id.utilisateur_list);
        recyclerView = (RecyclerView) view;

        adapter = new RecyclerViewAdapter(utilisateurs);
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PrincipaleActivity.this, AjouterUtilisateurActivity.class);
                startActivityForResult(i, 2);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            if (dataSource == null) {
                dataSource = new DataSource<>(this, User.class, versionDB);
                dataSource.open();
            }
        } catch (Exception e) {
            // Traiter le cas !
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            dataSource.close();
        } catch (Exception e) {
            // Traiter le cas !
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        chargerUtilisateurs();
    }

    private void chargerUtilisateurs() {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == 2) {

            String flux = data.getStringExtra("utilisateur"); // Tester si pas null ;-)
            User utilisateur = new Gson().fromJson(flux, User.class);

            try {
                dataSource.insert(utilisateur);
            } catch (Exception e) {
                // Que faire :-(
                e.printStackTrace();
            }

            // Indiquer un changement au RecycleView
            chargerUtilisateurs();
            adapter.notifyDataSetChanged();

        }

    }

    @Override
    public void onBackPressed() {
        if (this.lastBackPressTime < System.currentTimeMillis() - 4000) {
            toast = Toast.makeText(this, "Encore !!", Toast.LENGTH_LONG);
            toast.show();
            this.lastBackPressTime = System.currentTimeMillis();
        } else {
            if (toast != null) {
                toast.cancel();
            }
            toast = Toast.makeText(this, "Bye bye !", Toast.LENGTH_LONG);
            toast.show();
            super.onBackPressed();
            this.finish();
            System.exit(0);
        }
    }

}
