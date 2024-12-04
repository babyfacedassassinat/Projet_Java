import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import output.InvalidIPException;

public class HistoriqueCalculs
{
    private ArrayList<AdresseIP> historique;
    public HistoriqueCalculs(){
        historique = new ArrayList<>();
    }

    public void ajouterCalcul(AdresseIP adresse){
        historique.add(adresse);
    }

    public void sauvegarderHistorique(String nomFichier) throws InvalidIPException{
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomFichier, true))) { 
            for (AdresseIP adresse : historique) {
                writer.write(adresse.toString());
                writer.newLine(); 
            }
            historique.clear(); 
        } catch (IOException e) {
            throw new InvalidIPException("Erreur lors de la sauvegarde de l'historique : " + e.getMessage());
        }
    }
}
