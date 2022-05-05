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
        JPanel panelCentral = new JPanel(new BorderLayout());
        JPanel panelDroite = new JPanel(new BorderLayout());

        //Panel Gauche
        panelGauche.add(j1.pan);

        //Panel central
        JPanel repartiteur =new JPanel();
        repartiteur.setLayout((new BoxLayout(repartiteur,BoxLayout.PAGE_AXIS)));
        AdaptateurSouris adapt =new AdaptateurSouris(graph,m);
        graph.panelJeu.addMouseMotionListener(adapt);
        graph.panelJeu.addMouseListener(adapt);
        //mouvPanel.addMouseMotionListener(new AdaptateurMouvementSouris(graph,m));
        repartiteur.add(graph.panelJeu);
        m.setMenuType2();
        repartiteur.add(m.menu);
        panelCentral.add(repartiteur,BorderLayout.CENTER);
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

    GridBagConstraints constraints(int x, int y,int w, int h,double wx,double wy,int type){
        GridBagConstraints c = new GridBagConstraints();
        c.gridx=x;
        c.gridy=y;
        c.gridwidth=w;
        c.gridheight=h;
        c.weightx=wx;
        c.weighty=wy;
        c.fill =GridBagConstraints.NONE;
        switch (type){
            case 0:

                c.anchor= GridBagConstraints.NORTH;
                break;
            case 1:
                c.anchor= GridBagConstraints.SOUTH;
                break;
        }
        return  c;
    }
}
