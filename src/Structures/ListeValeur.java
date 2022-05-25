package Structures;

import java.io.Serializable;
import java.util.LinkedList;

public class ListeValeur<E,A> implements Serializable {
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

    @Override
    public String toString() {
        return "{" +
                "liste=" + liste +
                ", valeur=" + valeur +
                "}\n";
    }
}
