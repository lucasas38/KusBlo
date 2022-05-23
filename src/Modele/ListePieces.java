package Modele;

import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ListePieces implements Serializable {
    LinkedList<Piece> liste;

    public ListePieces(boolean init){
        if(init){
            this.lirePieces();
        }else{
            liste = new LinkedList<>();
        }
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

    public void supprimer(int idPiece) {
        if(!estVide()){
            Iterator<Piece> it = liste.iterator();
            while (it.hasNext()){
                Piece p = it.next();
                if(p.getId() == idPiece){
                    it.remove();
                }
            }
        }
    }

    public void ajoute(Piece p){
        this.liste.add(p);
    }

    public void ajoutePieceOrdre(Piece p){
        Iterator<Piece> it = iterateur();
        int i = 0;
        if(it.hasNext()){
            Piece pCourante = it.next();
            while (it.hasNext() && pCourante.id < p.id){
                pCourante = it.next();
                i++;
            }
            if(liste.getLast().id < p.id){
                liste.addLast(p);
            }else{
                liste.add(i,p);
            }
        }else{
            liste.addLast(p);
        }
    }

    public boolean estVide(){
        return this.liste.isEmpty();
    }

    public int getTaille(){
        return this.liste.size();
    }

    public LinkedList<Piece> getListe() {
        return liste;
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
            res += p.id + " ";
        }
        return res;
    }

    public boolean contient(int id){
        Iterator<Piece> ite = iterateur();
        while(ite.hasNext()){
            Piece p = ite.next();
            if(p.getId()==id){
                return  true;
            }
        }
        return false;
    }

    public void setListe(LinkedList<Piece> liste) {
        this.liste = liste;
    }
}