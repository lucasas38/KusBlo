package Vue;

import Controleur.Controleur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

public class InterfaceKusBlo implements Runnable {
    JFrame frameMenu;
    JFrame frameJeu;
    InterfaceJeu interJ;
    MenuPrincipal menu;
    Controleur c;
    ImageKusBlo im;
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
        frameMenu = new JFrame("KusBlo");
        frameMenu.setSize(800, 600);
        frameMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //On génère et affiche le menu principal
        menu=new MenuPrincipal(c);
        frameMenu=menu.getFrame();
        frameMenu.setVisible(true);
    }

    //Affiche le menu
    public void setMenu(){
        frameJeu.setVisible(false);
        frameMenu.setVisible(true);
    }

    //Affiche un nouveau jeu
    public void setInterJeu(){
        interJ= new InterfaceJeu(c ,frameMenu.getWidth(), frameMenu.getHeight(),im);
        test++;
        System.out.println("lancement n°"+test);
        frameJeu=interJ.getFrame();
        frameMenu.setVisible(false);
        frameJeu.setVisible(true);
    }

    public InterfaceJeu getInterJ(){
        return interJ;
    }

    public int getW(){
        return frameMenu.getWidth();
    }
    public int getH(){
        return frameMenu.getHeight();
    }

}
