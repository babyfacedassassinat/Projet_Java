import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import output.InvalidIPException;
import output.InvalidMasqueException;

public class CalculateurReseauSwingApp extends UIComposant {
    private Reseau reseauCalcul; 

    public CalculateurReseauSwingApp() {
        super();
        initialiserUI();
    }

    @Override
    public void initialiserUI() {
        frame.setTitle("Calculateur d'Adresse Réseau");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(33, 33, 33));
        frame.setLocationRelativeTo(null);

        configurerChamp(champAdresseIP);
        configurerChamp(champMasque);
        configurerZoneResultats(zoneResultats);
        configurerBouton(boutonCalculer, new Color(76, 175, 80)); 
        boutonCalculer.setText("Calculer");

        JPanel panneauChamps = new JPanel(new GridLayout(3, 2, 10, 10));
        panneauChamps.setBackground(new Color(33, 33, 33)); 
        panneauChamps.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JLabel labelAdresse = new JLabel("Adresse IP :");
        JLabel labelMasque = new JLabel("Masque :");
        labelAdresse.setForeground(Color.WHITE); 
        labelMasque.setForeground(Color.WHITE); 

        panneauChamps.add(labelAdresse);
        panneauChamps.add(champAdresseIP);
        panneauChamps.add(labelMasque);
        panneauChamps.add(champMasque);
        panneauChamps.add(boutonCalculer);

        JScrollPane panneauResultats = new JScrollPane(zoneResultats);

        frame.add(panneauChamps, BorderLayout.NORTH);
        frame.add(panneauResultats, BorderLayout.CENTER);

        boutonCalculer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String adresseIP = champAdresseIP.getText().trim();
                    String masque = champMasque.getText().trim();

                    reseauCalcul = new Reseau(adresseIP, masque);
                    reseauCalcul.calculerPlageAdresse();

                    String resultats = String.format(
                        "Adresse IP : %s\nClasse : %s\nAdresse réseau : %s\nPremière adresse : %s\nDernière adresse : %s\nMasque (bits) : /%d\n",
                        adresseIP,
                        reseauCalcul.calculerClasse(),
                        reseauCalcul.getAdresseReseau(),
                        reseauCalcul.getAdresseDebut(),
                        reseauCalcul.getAdresseFin(),
                        reseauCalcul.getMasqueEnBits()
                    );

                    mettreAJourResultats(resultats);
                    champAdresseIP.setText("");
                    champMasque.setText("");

                    int confirmation = JOptionPane.showConfirmDialog(frame,
                            "Les résultats sont prêts !\nSouhaitez-vous les enregistrer ?",
                            "Confirmez-vous l'enregistrement ?", JOptionPane.YES_NO_OPTION);

                    if (confirmation == JOptionPane.YES_OPTION) {
                        try {
                            HistoriqueCalculs hc = new HistoriqueCalculs();
                            hc.ajouterCalcul(reseauCalcul);
                            hc.sauvegarderHistorique("Historique.txt");
                            JOptionPane.showMessageDialog(frame, "L'enregistrement des résultats a réussi.",
                                    "Sauvegarde réussie", JOptionPane.INFORMATION_MESSAGE);
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(frame, "Erreur lors de la sauvegarde.", "Erreur",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (InvalidIPException | InvalidMasqueException ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Une erreur est survenue.", "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        frame.setVisible(true);
    }

    @Override
    public void mettreAJourResultats(String resultats) {
        zoneResultats.setText(resultats);
    }

    private void configurerChamp(JTextField champ) {
        champ.setFont(new Font("Arial", Font.PLAIN, 14));
        champ.setBackground(new Color(50, 50, 50)); 
        champ.setForeground(Color.WHITE);          
        champ.setCaretColor(Color.WHITE);          
        champ.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(76, 175, 80), 2), 
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
    }

    private void configurerBouton(JButton bouton, Color couleurFond) {
        bouton.setBackground(couleurFond);           
        bouton.setForeground(Color.BLACK);           
        bouton.setFont(new Font("Arial", Font.BOLD, 16));
        bouton.setFocusPainted(false);               
        bouton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        bouton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 150, 136), 2), 
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
    }

    private void configurerZoneResultats(JTextArea zone) {
        zone.setFont(new Font("Consolas", Font.PLAIN, 14)); 
        zone.setBackground(new Color(40, 40, 40));         
        zone.setForeground(Color.GREEN);                
        zone.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 150, 136), 2), 
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
    }
}
