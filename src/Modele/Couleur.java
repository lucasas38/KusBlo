package Modele;

import Structures.Case;

import java.util.HashSet;
import java.util.LinkedList;

public class Couleur {
    int id;  //identifiant unique d'une piece (entre 1 et 4)
    boolean peutJouer; //true si une couleur peut encore jouer
    ListePieces listePiecesDispo; //liste des pieces disponibles pour une couleur
    LinkedList<Piece> listesPiecesPosees;  //liste des pieces posées par une couleur
    HashSet<Case> listeCoins;  //liste des coins possible pour une couleur

    Couleur(int idCouleur){
        this.id = idCouleur;
        this.peutJouer=true;
        this.listePiecesDispo = new ListePieces();
        this.listesPiecesPosees = new LinkedList<>();
        this.listeCoins = new HashSet<>();
        //determine les coins de départ pour une couleur
        //exemple : couleur 1 commence en  haut à gauche
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

    public boolean isPeutJouer() {
        return peutJouer;
    }

    public ListePieces getListePiecesDispo() {
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

    //met à jour les listes de piece d'une couleur
    public void jouePiece(Piece p) {
        this.listePiecesDispo.supprimer(p.id);
        this.listesPiecesPosees.addLast(p);
    }

    public void ajouteCoin(Case ca) {
        listeCoins.add(ca);
    }

}
