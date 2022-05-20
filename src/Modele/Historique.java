package Modele;

import Structures.Case;
import Structures.Trio;

import java.io.Serializable;
import java.util.*;

public class Historique implements Serializable {
    LinkedList<Trio<Piece,Integer,Integer>> passe;
    LinkedList<Trio<Piece,Integer,Integer>> futur;

    Historique(){
        passe = new LinkedList<>();
        futur = new LinkedList<>();
    }

    public LinkedList<Trio<Piece,Integer,Integer>> getPasse() {
        return passe;
    }

    public LinkedList<Trio<Piece,Integer,Integer>> getFutur() {
        return futur;
    }

    public void nouveau(Trio<Piece,Integer,Integer> element){
        passe.addFirst(element);
        while (!futur.isEmpty()){
            futur.removeFirst();
        }
    }

    public boolean peutAnnuler() {
        return !passe.isEmpty();
    }

    public Trio<Piece,Integer,Integer> annuler() {
        if (peutAnnuler()) {
            Trio<Piece,Integer,Integer> dernier = passe.removeFirst();
            futur.addFirst(dernier);
            return dernier;
        } else {
            System.out.println("Pas de coup antérieur");
        }
        return null;
    }

    public boolean peutRefaire() {
        return !futur.isEmpty();
    }

    public Trio<Piece,Integer,Integer> refaire() {
        if (peutRefaire()) {
            Trio<Piece,Integer,Integer> prochain = futur.removeFirst();
            passe.addFirst(prochain);
            return prochain;
        }else{
            System.out.println("Pas de coup futur");
        }
        return null;
    }

    @Override
    public String toString() {
        String res="passe=[";
        Iterator<Trio<Piece,Integer,Integer>> it = passe.iterator();
        while (it.hasNext()){
            Trio<Piece,Integer,Integer> element = it.next();
            res += element.toString();
            res += " ";
        }
        res+="]";

        res+="\nfutur=[";
        it = futur.iterator();
        while (it.hasNext()){
            Trio<Piece,Integer,Integer> element = it.next();
            res += element.toString();
            res += " ";
        }
        res+="]";
        return res;
    }
}
