import javax.swing.*;

public abstract class UIComposant {
    protected JFrame frame;
    protected JTextArea zoneResultats;

    public abstract void initialiserUI();

    public abstract void mettreAJourResultats(String resultats);
}
