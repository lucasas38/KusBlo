package Controleur;

import Modele.Jeu;
import Modele.ListePieces;
import Modele.Piece;
import Structures.Case;
import Structures.ListeValeur;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

public abstract class IA implements Serializable {
    Jeu jeu;
    //mettre attribut Joueur et Couleur et definir a la creation puis utiliser ces attributs a la place de getCourant
    Random r;

    int type;

    IA(Jeu j){
        jeu=j;
        r=new Random();
    }

    public int getTypeIA(){ return type;} //
    public abstract ListeValeur<Case,Piece> joue();
    public abstract String toString();
    public void setR(){r=new Random();};
    public abstract int getType();

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

