public class AdresseIP {
    private int[] octets;  
    private String classe; 
    private int masque;    

    public AdresseIP(String adresse) throws InvalidIPException {
        if (validerAdresse(adresse)) {
            String[] parts = adresse.split("/");
            String adresseIP = parts[0];
            this.masque = (parts.length > 1) ? Integer.parseInt(parts[1]) : 24;
            String[] adresseParts = adresseIP.split("\\.");
            
            octets = new int[4];
            for (int i = 0; i < 4; i++) {
                octets[i] = Integer.parseInt(adresseParts[i]);
            }
            
            this.classe = getClasse();
        } else {
            throw new InvalidIPException("Adresse IP invalide ! Vérifiez le format (ex: 192.168.1.1 ou 192.168.1.1/24)");
        }
    }

    public boolean validerAdresse(String adresse) {
        String regex = "^((25[0-5]|2[0-4][0-9]|[0-1]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[0-1]?[0-9][0-9]?)(/([0-9]|[1-2][0-9]|3[0-2]))?$";
        return adresse.matches(regex);
    }

    public String getClasse() {
        if (octets[0] >= 1 && octets[0] <= 126) return "A";
        else if (octets[0] >= 128 && octets[0] <= 191) return "B";
        else if (octets[0] >= 192 && octets[0] <= 223) return "C";
        else if (octets[0] >= 224 && octets[0] <= 239) return "D";
        else return "E";
    }

    public String getAdresseReseau() {
        int[] adresseReseau = new int[4];
        int[] masqueOctets = convertPrefixToOctets(masque);

        for (int i = 0; i < 4; i++) {
            adresseReseau[i] = octets[i] & masqueOctets[i];
        }

        return formatAdresse(adresseReseau);
    }

    public String[] getPlageAdresses() {
        int[] adresseReseau = new int[4];
        int[] adresseBroadcast = new int[4];
        int[] masqueOctets = convertPrefixToOctets(masque);

        for (int i = 0; i < 4; i++) {
            adresseReseau[i] = octets[i] & masqueOctets[i];
            adresseBroadcast[i] = adresseReseau[i] | (~masqueOctets[i] & 0xFF);
        }

        String adresseReseauStr = formatAdresse(adresseReseau);
        String adresseBroadcastStr = formatAdresse(adresseBroadcast);
        return new String[]{adresseReseauStr, adresseBroadcastStr};
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

    public int[] getOctets() {
        return octets;
    }

    public int getMasque() {
        return masque;
    }

    @Override
    public String toString() {
        return octets[0] + "." + octets[1] + "." + octets[2] + "." + octets[3] + "/" + masque + " (Classe " + classe + ")";
    }
}
