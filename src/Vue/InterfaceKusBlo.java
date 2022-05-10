package Vue;

import Controleur.Controleur;

import javax.swing.*;
import java.awt.*;

public class InterfaceKusBlo implements Runnable {
    JFrame frame;
    VueNiveau graph;
    MenuPiece m;
    PanneauJoueur j1;
    PanneauJoueur j2;
    AdaptateurSouris mouseAdapt;
    AdaptateurClavier keyAdapt;
    Controleur c;


    public InterfaceKusBlo(Controleur cont){
        c=cont;
    }

    public static void demarrer(Controleur cont) {
        InterfaceKusBlo vue = new InterfaceKusBlo(cont);
        cont.ajouteInterfaceUtilisateur(vue);
        SwingUtilities.invokeLater(vue);
    }

    public void run(){
        m=new MenuPiece(c);
        graph = new VueNiveau();
        mouseAdapt =new AdaptateurSouris(graph,m,c);
        keyAdapt= new AdaptateurClavier(c, mouseAdapt,m);
        j1 = new PanneauJoueur();
        j2 = new PanneauJoueur();
        frame = new JFrame("KusBlo");

        //Panel Gauche
        JPanel panelGauche = new JPanel(new BorderLayout());
        panelGauche.add(j1.pan);

        //Panel central
        JPanel panelCentral = new JPanel(new BorderLayout());
        JPanel repartiteur =new JPanel(); //réparti le jeu et le menu du bas
        repartiteur.setLayout((new BoxLayout(repartiteur,BoxLayout.PAGE_AXIS)));
        repartiteur.add(graph.panelJeu);
        setMenu1(c.getActJoueur(),c.getActCouleur());
        repartiteur.add(m.menu);
        panelCentral.add(repartiteur,BorderLayout.CENTER);
        m.menuType1.addMouseListener(new AdaptateurSelPiece(graph,m,c,true));
        m.affichagePiece.addMouseListener(new AdaptateurSelPiece(graph, m,c,false));

        //Panel Droite
        JPanel panelDroite = new JPanel(new BorderLayout());
        panelDroite.add(j2.pan);


        //Pannel principal
        JPanel panelMain= new JPanel(new BorderLayout());
        panelMain.add(panelGauche,BorderLayout.WEST);
        panelMain.add(panelCentral,BorderLayout.CENTER);
        panelMain.add(panelDroite,BorderLayout.EAST);


        //Ajout à la fenêtre + affichage
        frame.add(panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        frame.setVisible(true);

    }
    //Pas utilisé
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


    //Supprime les listener du terrain de jeu
    public void delMouseClick(){
        graph.panelJeu.removeMouseMotionListener(mouseAdapt);
        graph.panelJeu.removeMouseListener(mouseAdapt);
        graph.panelJeu.removeMouseWheelListener(mouseAdapt);
        frame.removeKeyListener(keyAdapt);
    }

    //Active le menu bas 1
    public void setMenu1(int j, int coul){
        delMouseClick();
        m.setMenuType1(j,coul);

    }

    //Active le menu base 2
    public  void setMenu2(int numPiece){
        graph.panelJeu.addMouseMotionListener(mouseAdapt);
        graph.panelJeu.addMouseListener(mouseAdapt);
        graph.panelJeu.addMouseWheelListener(mouseAdapt);
        frame.addKeyListener(keyAdapt);
        m.setMenuType2(numPiece);

    }

    public MenuPiece getM() {
        return m;
    }

    public VueNiveau getGraph() {
        return graph;
    }
}
