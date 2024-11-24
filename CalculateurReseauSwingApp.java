import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculateurReseauSwingApp extends UIComposant {
    private JTextField champAdresseIP;
    private JTextField champMasque;

    @Override
    public void initialiserUI() {
        frame = new JFrame("Calculateur d'Adresse Réseau");
        champAdresseIP = new JTextField(15);
        champMasque = new JTextField(5);
        JButton boutonCalculer = new JButton("Calculer");
        zoneResultats = new JTextArea(10, 30);
        zoneResultats.setEditable(false);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Adresse IP :"));
        panel.add(champAdresseIP);
        panel.add(new JLabel("Masque (/X) :"));
        panel.add(champMasque);
        panel.add(boutonCalculer);
        panel.add(new JScrollPane(zoneResultats));

        frame.add(panel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        boutonCalculer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gererCalcul();
            }
        });
    }

    private void gererCalcul() {
        try {
            String adresse = champAdresseIP.getText();
            String masque = champMasque.getText();
            Reseau reseau = new Reseau(adresse, masque);
            String resultats = "Classe : " + reseau.getClasse() +
                               "\nAdresse de début : " + reseau.getAdresseDebut() +
                               "\nAdresse de fin : " + reseau.getAdresseFin();
            mettreAJourResultats(resultats);
        } catch (InvalidIPException ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void mettreAJourResultats(String resultats) {
        zoneResultats.setText(resultats);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CalculateurReseauSwingApp app = new CalculateurReseauSwingApp();
            app.initialiserUI();
        });
    }
}
