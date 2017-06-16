package fr.formation.tp13;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import fr.formation.tp13.database.datasource.DataSource;
import fr.formation.tp13.database.modele.User;

public class PrincipaleActivity extends FragmentActivity {

    private DataSource<User> dataSource;
    private int versionDB = 4; // Permet de detruire la base de données SQLite si on incrémente la version
    // Pour quitter l'application :
    private Toast toast;
    private long lastBackPressTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(PrincipaleActivity.this, AjouterUtilisateurActivity.class);
                startActivityForResult(intent, 2);
            }
        });


        if(findViewById(R.id.fragment_container) != null) {
            if(savedInstanceState != null) {
                return;
            }
            ListeUtilisateurFragment firstFragment = new ListeUtilisateurFragment();
            firstFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, firstFragment).commit();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == 2) {

            String flux = data.getStringExtra("utilisateur"); // Tester si pas null ;-)
            if(flux != null) {

                User utilisateur = new Gson().fromJson(flux, User.class);

                try {
                    dataSource.insert(utilisateur);
                } catch (Exception e) {
                    // Que faire :-(
                    e.printStackTrace();
                }


                Toast.makeText(this, utilisateur.getNom(),
                        Toast.LENGTH_LONG).show();

            }

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


    @Override
    public void onStart() {
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
    public void onDestroy() {
        super.onDestroy();
        try {
            dataSource.close();
        } catch (Exception e) {
            // Traiter le cas !
            e.printStackTrace();
        }
    }
}
