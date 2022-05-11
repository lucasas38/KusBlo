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


    public InterfaceKusBlo(Controleur cont){
        c=cont;
    }

    public static void demarrer(Controleur cont) {
        InterfaceKusBlo vue = new InterfaceKusBlo(cont);
        cont.ajouteInterfaceUtilisateur(vue);
        SwingUtilities.invokeLater(vue);
    }

    public void run(){
        frameMenu = new JFrame("KusBlo");
        frameMenu.setSize(800, 600);
        frameMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menu=new MenuPrincipal(c);
        frameMenu=menu.getFrame();
        frameMenu.setVisible(true);

    }

    public void setMenu(){
        frameJeu.setVisible(false);
        frameMenu.setVisible(true);
    }

    public void setInterJeu(){
        interJ= new InterfaceJeu(c ,frameMenu.getWidth(), frameMenu.getHeight());
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
