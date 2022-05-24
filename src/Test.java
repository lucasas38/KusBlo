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

public class Test {

    public static void main(String args[]){

        Jeu jeu = new Jeu(2);

        ListePieces listePieces = new ListePieces(true);
        for (int i = 0;i< listePieces.getTaille();i++){
            Piece p = listePieces.getPiece(i+1);

            LinkedList<ListeValeur<Case,Integer>> configPossible = jeu.positionPossibleConfig(p,1,1);
            System.out.println("piece "+p.getId()+" : "+ configPossible);
        }



    }
}
