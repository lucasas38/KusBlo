package Vue;

import Controleur.Controleur;

import javax.swing.*;
import java.awt.*;

public class InterfaceKusBlo implements Runnable {
    VueNiveau graph;
    MenuPiece m;
    PanneauJoueur j1;
    PanneauJoueur j2;


    public static void demarrer() {
        InterfaceKusBlo vue = new InterfaceKusBlo();
        SwingUtilities.invokeLater(vue);
    }

    public void run(){
        graph = new VueNiveau();
        m=new MenuPiece();
        j1 = new PanneauJoueur();
        j2 = new PanneauJoueur();
        JFrame frame = new JFrame("KusBlo");
        JPanel panelMain= new JPanel(new BorderLayout());
        JPanel panelGauche = new JPanel(new BorderLayout());
        JPanel panelCentral = new JPanel(new GridLayout(2,1));
        JPanel panelDroite = new JPanel(new BorderLayout());

        //Panel Gauche
        panelGauche.add(j1.pan);

        //Panel central
        panelCentral.add(graph.panelJeu);
        graph.panelJeu.addMouseListener(new AdaptateurSouris(graph));
        m.setMenuType2();
        panelCentral.add(m.menu);

        //Panel Droite
        panelDroite.add(j2.pan);


        //Pannel principal
        panelMain.add(panelGauche,BorderLayout.WEST);
        panelMain.add(panelCentral,BorderLayout.CENTER);
        panelMain.add(panelDroite,BorderLayout.EAST);

        frame.add(panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        frame.setVisible(true);

    }
}
