package Vue;

import Controleur.Controleur;

import javax.swing.*;
import java.awt.*;

public class InterfaceKusBlo implements Runnable {
    VueNiveau graph;
    MenuPiece m;
    PanneauJoueur j1;
    PanneauJoueur j2;
    AdaptateurSouris adapt;
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
        adapt =new AdaptateurSouris(graph,m,c);
        j1 = new PanneauJoueur();
        j2 = new PanneauJoueur();
        JFrame frame = new JFrame("KusBlo");

        //Panel Gauche
        JPanel panelGauche = new JPanel(new BorderLayout());
        panelGauche.add(j1.pan);

        //Panel central
        JPanel panelCentral = new JPanel(new BorderLayout());
        JPanel repartiteur =new JPanel(); //réparti le jeu et le menu du bas
        repartiteur.setLayout((new BoxLayout(repartiteur,BoxLayout.PAGE_AXIS)));
        repartiteur.add(graph.panelJeu);
        setMenu1();
        repartiteur.add(m.menu);
        panelCentral.add(repartiteur,BorderLayout.CENTER);

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
        graph.panelJeu.removeMouseListener(adapt);
        graph.panelJeu.removeMouseMotionListener(adapt);
    }

    //Active le menu bas 1
    public void setMenu1(){
        delMouseClick();
        m.setMenuType1();
        m.menuType1.addMouseListener(new AdaptateurSelPiece(graph,m,c,true));
    }

    //Active le menu base 2
    public  void setMenu2(){
        graph.panelJeu.addMouseMotionListener(adapt);
        graph.panelJeu.addMouseListener(adapt);
        m.setMenuType2();
        m.affichagePiece.addMouseListener(new AdaptateurSelPiece(graph, m,c,false));
    }

    public MenuPiece getM() {
        return m;
    }
}
