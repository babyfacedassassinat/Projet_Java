import java.util.List;

public class TestHistoriqueCalculs {
    public static void main(String[] args) {
        try {
            AdresseIP ip1 = new AdresseIP("192.168.1.1/24");
            AdresseIP ip2 = new AdresseIP("10.0.0.1/8");
            AdresseIP ip3 = new AdresseIP("172.16.0.1/16");

            HistoriqueCalculs historique = new HistoriqueCalculs();

            historique.ajouterCalcul(ip1);
            historique.ajouterCalcul(ip2);
            historique.ajouterCalcul(ip3);

            historique.sauvegarderHistorique("output/historique.txt");

            for (AdresseIP ip : historique.getHistorique()) {
                System.out.println("Adresse IP : " + ip);
                System.out.println("Adresse réseau : " + ip.getAdresseReseau());
                System.out.println("Plage d'adresses : " + String.join(" - ", ip.getPlageAdresses()));
                System.out.println();
            }

            System.out.println("Historique sauvegardé avec succès !");
        } catch (InvalidIPException e) {
            System.err.println("Erreur lors de la saisie : " + e.getMessage());
        }
    }
}
