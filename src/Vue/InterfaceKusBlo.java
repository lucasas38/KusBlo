package Vue;

import Controleur.Controleur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

public class InterfaceKusBlo implements Runnable {
    JFrame frame;
    InterfaceJeu interJ;
    MenuPrincipal menu;
    MenuSolo menuSol;
    MenuMulti menuMult;
    MenuPartiePerso menuPerso;
    Controleur c;
    ImageKusBlo im;
    Bouton b;
    AdaptateurClavier keyAdapt;
    int test=0;


    public InterfaceKusBlo(Controleur cont){
        c=cont;
    }

    public static void demarrer(Controleur cont) {
        InterfaceKusBlo vue = new InterfaceKusBlo(cont);
        cont.ajouteInterfaceUtilisateur(vue);
        SwingUtilities.invokeLater(vue);
    }

    public void run(){
        im= new ImageKusBlo();
        b=new Bouton(c);
        frame = new JFrame("KusBlo");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //On génère et affiche le menu principal
        menu=new MenuPrincipal(c,b,im, frame.getWidth(),frame.getHeight());
        menuSol=new MenuSolo(c,b, frame.getWidth(),frame.getHeight());
        menuMult=new MenuMulti(c,b, frame.getWidth(),frame.getHeight());
        menuPerso=new MenuPartiePerso(c,b, frame.getWidth(),frame.getHeight());
        frame.setContentPane(menu.getFrame());
        frame.setVisible(true);
    }

    //Affiche le menu
    public void setMenu(){
        frame.setContentPane(menu.getFrame());
        frame.revalidate();
    }

    public void setMenuSolo(){
        frame.setContentPane(menuSol.getFrame());
        frame.revalidate();
    }

    public void setMenuMulti(){
        frame.setContentPane(menuMult.getFrame());
        frame.revalidate();
    }

    public void setMenuPerso(){
        frame.setContentPane(menuPerso.getFrame());
        frame.revalidate();
    }

    //Affiche un nouveau jeu
    public void setInterJeu(){
        interJ= new InterfaceJeu(c ,b,frame.getWidth(), frame.getHeight(),im);
        test++;
        System.out.println("lancement n°"+test);
        frame.setContentPane(interJ.getFrame());
        interJ.resizeAllPanel();
        frame.revalidate();
       // frame.repaint();
        //frameMenu.setVisible(false);
        //frameJeu.setVisible(true);
    }

    public InterfaceJeu getInterJ(){
        return interJ;
    }

    public int getW(){
        return frame.getWidth();
    }
    public int getH(){
        return frame.getHeight();
    }

    public void charger(){
        interJ.charger();
    }

    public int[] getListDiff(){
       return menuPerso.getListeDiff();
    }
}
