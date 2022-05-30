package Modele;

import Controleur.IA;
import Global.Configuration;

import java.io.*;

public class Chargement {

    Jeu jeu;
    IA[] ia;


    public Chargement(){
        jeu=null;
        ia=null;
    }

    public void lire() {
        String filename = Configuration.instance().getDirUser() + File.separator + ".save";

        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename));
            jeu = (Jeu) in.readObject();
            ia = (IA[]) in.readObject();

            //changement seed random IAAleatoire
            for (int i=0;i<jeu.getNbJoueurs();i++){
                if(ia[i]!=null){
                    ia[i].setR();
                }
            }

            in.close();
            System.out.println("Jeu chargé !!!");
        } catch (FileNotFoundException fnf){
//            fnf.printStackTrace();
            System.out.println("Erreur chargement !!! Fichier "+filename+" n'existe pas");
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
