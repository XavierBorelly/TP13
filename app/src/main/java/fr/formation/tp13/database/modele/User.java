package fr.formation.tp13.database.modele;

import java.util.List;

import fr.formation.tp13.database.datasource.Modele;
import fr.formation.tp13.database.datasource.e.Type;

import static fr.formation.tp13.database.datasource.Modele.DataBase;
import static fr.formation.tp13.database.datasource.Modele.Table;

@Table("USER")
@DataBase("sante.db")
public class User extends Modele<User> {

    private String nom;

    private String description;

    @Columne(value = "NOM", type = Type.String)
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Columne(value = "DESCRIPTION", type = Type.String)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreateDataBase() {
        return "CREATE TABLE " + getTable() + "("
                + "ID" + " Integer PRIMARY KEY AUTOINCREMENT, "
                + "NOM Text NOT NULL, "
                + "description Text NOT NULL "
                + ");";
    }

    @Override
    public User build(int id, List<String> valeurs) {
        User user = new User();
        user.setId(id);
        user.setNom(valeurs.get(1));
        user.setDescription(valeurs.get(0));
        return user;
    }

}
