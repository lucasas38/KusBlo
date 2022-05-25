package Modele;

import Controleur.IA;
import Global.Configuration;

import java.io.*;

public class Sauvegarde {

    public Sauvegarde(){}

    public void ecrire(Jeu j, IA[] ia){
        String filename = Configuration.instance().getDirUser() + File.separator + ".save";

        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename));
            out.writeObject(j);
            out.writeObject(ia);
            out.close();
            System.out.println("Sauvegardé !!!");
        } catch(IOException ex){
            ex.printStackTrace();
            System.out.println("Erreur sauvegarde !!!");
        }
    }


}
