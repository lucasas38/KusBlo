import Controleur.Controleur;
import Controleur.IA;
import Controleur.IAAleatoire;
import Global.Configuration;
import Modele.*;
import Structures.Case;
import Structures.ListeValeur;
import Vue.InterfaceKusBlo;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Random;

public class Test {

    public static void main(String args[]){

//        Jeu jeu = new Jeu(4);
        ListePieces listePieces = new ListePieces(true);
        Iterator<Piece> it = listePieces.iterateur();
        Piece p;
        int[][] avant = new int[5][5];
        int[][] apres = new int[5][5];

        Chargement chargement = new Chargement();
        chargement.lire();
        Jeu jeu=chargement.getJeu();

        Random r = new Random();
        int nb_rota;

        while (it.hasNext()){
            p = it.next();

            nb_rota = r.nextInt(8);
            System.out.println("Piece "+p+ " nb_rota = "+nb_rota);

            for (int rota = 0;rota<nb_rota;rota++){
                if(rota == 4){
                    p.rotationSymetrique();
                }else{
                    p.rotationHoraire();
                }
            }

            System.out.println("avant = ");
            for (int i = 0;i<p.getMatrice().length;i++){
                for (int j = 0;j<p.getMatrice().length;j++){
                    avant[i][j] = p.getMatrice()[i][j];
                    System.out.print(avant[i][j]);
                }
                System.out.println();
            }

            jeu.positionPossibleConfig(p,1,1);

            System.out.println("apres = ");
            for (int i = 0;i<p.getMatrice().length;i++){
                for (int j = 0;j<p.getMatrice().length;j++){
                    apres[i][j] = p.getMatrice()[i][j];
                    System.out.print(apres[i][j]);
                }
                System.out.println();
            }


            for (int i = 0;i<p.getMatrice().length;i++){
                for (int j = 0;j<p.getMatrice().length;j++){
                    if(apres[i][j] != avant[i][j]){
                        System.out.println("Piece "+p +" differente ");
                        break;
                    }
                }
            }

        }

    }
}
