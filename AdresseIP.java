import java.util.regex.Pattern;
import output.InvalidIPException;

public class AdresseIP implements Calculable {
    protected int[] octets = new int[4]; 
    protected String classe; 

    public AdresseIP(String adresse) throws InvalidIPException{
        validerAdresse(adresse); 
    }

    public int[] getAdresseIp(){
        return octets;
    }

    public void validerAdresse(String adresse) throws InvalidIPException{
        String regex = "^((25[0-5]|2[0-4][0-9]|[0-1]?[0-9]{1,2})\\.){3}(25[0-5]|2[0-4][0-9]|[0-1]?[0-9]{1,2})$";
        if (!Pattern.matches(regex, adresse))
        {
            throw new InvalidIPException("Adresse IP invalide : " + adresse);
        }

        String[] parts = adresse.split("\\.");
        for (int i = 0; i < 4; i++) {
            octets[i] = Integer.parseInt(parts[i]);
        }
    }

    @Override
    public String calculerClasse(){
        int premierOctet = octets[0];
        if (premierOctet >= 1 && premierOctet <= 126) {classe = "A";} 
        else if (premierOctet >= 128 && premierOctet <= 191) {classe = "B";} 
        else if (premierOctet >= 192 && premierOctet <= 223) {classe = "C";} 
        else if (premierOctet >= 224 && premierOctet <= 239) {classe = "D";} 
        else {classe = "E";}
        return classe;
    }

    @Override
    public void calculerPlageAdresse(){}

    @Override
    public String toString(){
        return String.format("%d.%d.%d.%d (%s)", octets[0], octets[1], octets[2], octets[3], calculerClasse());
    }
}
