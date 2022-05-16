package Modele;

import Controleur.IA;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Sauvegarde {

    public Sauvegarde(){}

    public void ecrire(Jeu j, IA[] ia){
        String filename = "res/sauvegarde";
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
