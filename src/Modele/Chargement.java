package Modele;

import Controleur.IA;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Chargement {

    Jeu jeu;
    IA[] ia;


    public Chargement(){
        jeu=null;
        ia=null;
    }

    public void lire() {
        String filename = "res/sauvegarde";

        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename));
            jeu = (Jeu) in.readObject();
            ia = (IA[]) in.readObject();
            in.close();
            System.out.println("Jeu chargé !!!");
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Erreur chargement !!!");
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
            System.out.println("Erreur chargement !!!");
        }

    }

    public Jeu getJeu() {
        return jeu;
    }

    public IA[] getIa() {
        return ia;
    }
}
