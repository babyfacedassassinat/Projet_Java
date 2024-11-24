public class Reseau extends AdresseIP {
    private int masque;  
    private String adresseDebut;
    private String adresseFin;

    public Reseau(String adresse, String masque) throws InvalidIPException {
        super(adresse);  
        this.masque = convertMasqueToPrefix(masque);
        calculerPlageAdresse();
    }

    private int convertMasqueToPrefix(String masque) {
        if (masque.startsWith("/")) {
            return Integer.parseInt(masque.replace("/", ""));
        } else {
            throw new IllegalArgumentException("Format de masque invalide. Utilisez le format /X.");
        }
    }

    public void calculerPlageAdresse() {
        int[] ipOctets = getOctets(); 
        int[] masqueOctets = convertPrefixToOctets(getMasque());
        int[] debut = new int[4];
        int[] fin = new int[4];

        for (int i = 0; i < 4; i++) {
            debut[i] = ipOctets[i] & masqueOctets[i];
            fin[i] = ipOctets[i] | (~masqueOctets[i] & 0xFF);
        }

        adresseDebut = formatAdresse(debut);
        adresseFin = formatAdresse(fin);
    }

    private int[] convertPrefixToOctets(int prefix) {
        int[] octets = new int[4];
        for (int i = 0; i < 4; i++) {
            if (prefix >= 8) {
                octets[i] = 255;
                prefix -= 8;
            } else {
                octets[i] = (int) (256 - Math.pow(2, 8 - prefix));
                prefix = 0;
            }
        }
        return octets;
    }

    private String formatAdresse(int[] octets) {
        return octets[0] + "." + octets[1] + "." + octets[2] + "." + octets[3];
    }

    public String getAdresseDebut() {
        return adresseDebut;
    }

    public String getAdresseFin() {
        return adresseFin;
    }
}
