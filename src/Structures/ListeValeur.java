package Structures;

import java.util.LinkedList;

public class ListeValeur<E,A> {
    LinkedList<E> liste;
    A valeur;

    public ListeValeur(LinkedList<E> l , A v){
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
