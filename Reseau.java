import output.InvalidIPException;
import output.InvalidMasqueException;

public class Reseau extends AdresseIP{
    private String masque;
    private String adresseDebut;
    private String adresseFin;
    private String adresseReseau;

    public Reseau(String adresseIP, String masque) throws InvalidIPException, InvalidMasqueException {
        super(adresseIP);
        validerMasque(masque);
        this.masque = masque;
        calculerPlageAdresse();
    }

    public int getMasqueEnBits() {
        String[] parts = masque.split("\\.");
        int bits = 0;
        for (String part : parts) {
            bits += Integer.bitCount(Integer.parseInt(part));
        }
        return bits;
    }

    @Override
    public void calculerPlageAdresse() {
        int ip = 0, mask = 0;
        for (int i = 0; i < 4; i++) {
            ip = (ip << 8) | octets[i];
            mask = (mask << 8) | Integer.parseInt(masque.split("\\.")[i]);
        }

        int networkAddress = ip & mask;
        int broadcastAddress = networkAddress | ~mask & 0xFFFFFFFF;

        adresseReseau = convertirEnIP(networkAddress); 
        adresseDebut = convertirEnIP(networkAddress + 1); 
        adresseFin = convertirEnIP(broadcastAddress - 1);

        if (getMasqueEnBits() >= 31) {
            adresseReseau = convertirEnIP(networkAddress);
            adresseDebut = convertirEnIP(networkAddress); 
            adresseFin = convertirEnIP(broadcastAddress);
        } else if (getMasqueEnBits() >= 32) {
            // Cas particulier pour un masque /32
            adresseReseau = convertirEnIP(networkAddress);
            adresseDebut = adresseReseau; // Une seule adresse disponible
            adresseFin = adresseReseau;
        } else {
            // Cas général
            adresseReseau = convertirEnIP(networkAddress);
            adresseDebut = convertirEnIP(networkAddress + 1); // Première adresse utilisable
            adresseFin = convertirEnIP(broadcastAddress - 1); // Dernière adresse utilisable
        }
    }


    private String convertirEnIP(int adresse) {
        return String.format("%d.%d.%d.%d", 
        (adresse >> 24) & 0xFF, 
        (adresse >> 16) & 0xFF, 
        (adresse >> 8) & 0xFF, 
        adresse & 0xFF);
    }

    public String getAdresseDebut(){
        return adresseDebut;
    }

    public String getAdresseFin(){
        return adresseFin;
    }

    public String getAdresseReseau(){
        return adresseReseau;
    }

    @Override
    public String toString(){
        return super.toString() + " | adresse Resau : " + adresseReseau + 
        " | Masque : " + masque + 
        " | Début : " + adresseDebut + 
        " | Fin : " + adresseFin;
    }

    public void validerMasque(String masque) throws InvalidMasqueException {
        String[] octets = masque.split("\\.");
        if (octets.length != 4) {
            throw new InvalidMasqueException ("Masque invalide : " + masque);
        }

        int masqueBits = 0;
        for (String octet : octets) {
            int valeur;
            try {
                valeur = Integer.parseInt(octet);
            } catch (NumberFormatException e) {
                throw new InvalidMasqueException("Masque invalide : " + masque);
            }
            if (valeur < 0 || valeur > 255) {
                throw new InvalidMasqueException("Masque invalide : " + masque);
            }
            masqueBits = (masqueBits << 8) | valeur;
        }

        boolean bitsContigus = ((~masqueBits + 1) & ~masqueBits) == 0;
        if (!bitsContigus) {
            throw new InvalidMasqueException("Masque invalide : " + masque);
        }
    }

}
