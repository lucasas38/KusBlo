package Modele;

import Structures.Case;

import java.util.HashSet;
import java.util.LinkedList;

public class Couleur {
    int id;
    boolean peutJouer;
    ListeChaine listePiecesDispo;
    LinkedList<Piece> listesPiecesPosees;
    HashSet<Case> listeCoins;

    Couleur(int idCouleur){
        this.id = idCouleur;
        this.peutJouer=true;
        this.listePiecesDispo = new ListeChaine();
        this.listesPiecesPosees = new LinkedList<>();
        this.listeCoins = new HashSet<>();
        switch (idCouleur){
            case 1:
                listeCoins.add(new Case(0,0));
                break;
            case 2:
                listeCoins.add(new Case(0,19));
                break;
            case 3:
                listeCoins.add(new Case(19,19));
                break;
            case 4:
                listeCoins.add(new Case(19,0));
                break;
        }
    }

    public int getId() {
        return id;
    }

    public ListeChaine getListePiecesDispo() {
        return listePiecesDispo;
    }

    public LinkedList<Piece> getListesPiecesPosees() {
        return listesPiecesPosees;
    }

    public HashSet<Case> getListeCoins() {
        return listeCoins;
    }

    Piece getPieceDispo(int idPiece){
        return listePiecesDispo.getPiece(idPiece);
    }

    public void jouePiece(Piece p) {
        this.listePiecesDispo.supprimer(p.id);
        this.listesPiecesPosees.addLast(p);
    }

    public void ajouteCoin(Case ca) {
        listeCoins.add(ca);
    }

}
