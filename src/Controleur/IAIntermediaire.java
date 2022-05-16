package Controleur;

import Modele.Jeu;
import Modele.Piece;
import Structures.Case;
import Structures.ListeValeur;

public class IAIntermediaire extends IA{

    IAIntermediaire(Jeu j){
        super(j);
    }

    @Override
    public ListeValeur<Case, Piece> joue() {
        return null;
    }

    @Override
    public String toString(){
        return "IAIntermediaire";
    }
}
