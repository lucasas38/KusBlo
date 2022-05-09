package Modele;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.LinkedList;

public class ListePieces {
    LinkedList<Piece> liste;

    ListePieces(){
        this.lirePieces();
    }

    public void lirePieces(){
        try {
            File f = new File("res/listePieces");

            FileInputStream in = new FileInputStream(f);

            LecteurPieces lecteurPieces = new LecteurPieces(in);
            this.liste = lecteurPieces.lirePieces();

        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void supprimer(Piece p) {
        if(!estVide()){
            this.liste.remove(p);
        }
    }

    public void ajoute(Piece p){
        this.liste.add(p);
    }

    public boolean estVide(){
        return this.liste.isEmpty();
    }

    public int getTaille(){
        return this.liste.size();
    }

    public Piece getPiece(int idPiece) {
        Iterator<Piece> it = liste.iterator();
        while (it.hasNext()){
            Piece p = it.next();
            if(p.getId() == idPiece){
                return p;
            }
        }
        return null;
    }

    public Iterator<Piece> iterateur(){
        return liste.iterator();
    }

    @Override
    public String toString() {
        String res = "";
        Iterator<Piece> it = iterateur();
        while (it.hasNext()){
            Piece p = it.next();
            res += p.toString() + " ";
        }
        return res;
    }

}
