package Controleur;

import Modele.Jeu;
import Modele.Piece;
import Structures.Case;
import Structures.ListeValeur;

import java.util.Random;

public class IADifficile extends IA{

    IADifficile(Jeu j){
        super(j);
    }

    @Override
    public void joue() {
        dernierCoup=null;
    }

    @Override
    public String toString(){
        return "IADifficile";
    }
}
