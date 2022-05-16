package Controleur;

import Modele.Jeu;
import Modele.ListePieces;
import Modele.Piece;
import Structures.Case;
import Structures.ListeValeur;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;

public abstract class IA implements Serializable {
    Jeu jeu;

    IA(Jeu j){
        jeu=j;
    }

    public abstract ListeValeur<Case,Piece> joue();
    public abstract String toString();
    public abstract void setR();

    public ListePieces copiePiecesDispo(){
        ListePieces listePieces = new ListePieces();

        LinkedList<Piece> liste = new LinkedList<>();
        Iterator<Piece> it = jeu.getJoueur(jeu.getIDJoueurCourant()).getCouleurCourante().getListePiecesDispo().iterateur();
        Piece p;
        while (it.hasNext()){
            p = it.next();
            liste.addLast(p);
        }
        listePieces.setListe(liste);

        return listePieces;
    }

}

