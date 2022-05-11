package Structures;

import java.util.LinkedList;

public class CoupleListeValeur<E,A> {
    LinkedList<E> liste;
    A valeur;

    public CoupleListeValeur(LinkedList<E> l ,A v){
        liste = l;
        valeur = v;
    }

    public LinkedList<E> getListe() {
        return liste;
    }

    public A getValeur() {
        return valeur;
    }
}
