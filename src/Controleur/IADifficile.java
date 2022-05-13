package Controleur;

import Modele.Jeu;
import Modele.Piece;
import Structures.Case;
import Structures.ListeValeur;

public class IADifficile extends IA{

    IADifficile(Jeu j){
        super(j);
    }

    @Override
    public ListeValeur<Case, Piece> joue() {
        return null;
    }
}
