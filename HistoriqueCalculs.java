import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

public class HistoriqueCalculs {
    private List<AdresseIP> historique;

    public HistoriqueCalculs() {
        historique = new ArrayList<>();
    }

    public void ajouterCalcul(AdresseIP adresse) {
        historique.add(adresse);
        sauvegarderNouvelleAdresseDansFichier("output/historique.txt", adresse);  
    }

    private void sauvegarderNouvelleAdresseDansFichier(String nomFichier, AdresseIP adresse) {
        try (FileWriter writer = new FileWriter(nomFichier, true)) { 
            writer.write("Adresse IP : " + adresse.toString() + "\n");
            writer.write("Adresse réseau : " + adresse.getAdresseReseau() + "\n");
            writer.write("Plage d'adresses : " + String.join(" - ", adresse.getPlageAdresses()) + "\n");
            writer.write("======================================\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sauvegarderHistorique(String nomFichier) {
        System.out.println("Enregistrement de l'historique complet dans : " + nomFichier);
        File file = new File(nomFichier);

        if (!file.exists()) {
            try {
                file.createNewFile();
                System.out.println("Fichier créé avec succès : " + nomFichier);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    
        try (FileWriter writer = new FileWriter(file)) {
            for (AdresseIP adresse : historique) {
                writer.write("Adresse IP : " + adresse.toString() + "\n");
                writer.write("Adresse réseau : " + adresse.getAdresseReseau() + "\n");
                writer.write("Plage d'adresses : " + String.join(" - ", adresse.getPlageAdresses()) + "\n");
                writer.write("======================================\n");
            }
            System.out.println("Historique complet sauvegardé.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<AdresseIP> getHistorique() {
        return historique;
    }
}
