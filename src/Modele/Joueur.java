package Modele;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class Joueur {
    int id;
    ListePieces listePiecesDispo;
    LinkedList<Piece> listesPiecesPosees;
    int score;
    HashSet<Case> listeCoins;
    boolean peutJouer=true;
//    Couleur[] listeCouleur;  //à explorer pour 2, 3 joueurs : ? créer class couleur + deplaceent de méthodes de Jeu vers Couleur + changement Jeu (constructeur et attribut, et méthodes)

    Joueur(int id,ListePieces listePiecesDepart){
        this.id = id;
        this.listePiecesDispo = listePiecesDepart;
        this.listesPiecesPosees = new LinkedList<>();
        this.score = 0;
        this.listeCoins = new HashSet<>();
        switch (id){
            case 1:
                listeCoins.add(new Case(0,0));
                break;
            case 2:
                listeCoins.add(new Case(19,19));
                break;
            case 3:
                listeCoins.add(new Case(0,19));
                break;
            case 4:
                listeCoins.add(new Case(19,0));
                break;
        }
    }

    public int getId() {
        return id;
    }

    public ListePieces getListePiecesDispo() {
        return listePiecesDispo;
    }

    public int getScore() {
        return score;
    }

    public void setScoreFinal(){
        if(!this.listePiecesDispo.estVide()){
            this.score -= listePiecesDispo.getTaille();
            if(this.score < 0){
                this.score = 0;
            }
        }else{
            this.score += 15;
            if(this.listesPiecesPosees.getLast().getId() == 1){
                this.score += 5;
            }
        }
    }

    void jouePiece(Piece p){
        this.score += p.taille;
        this.listePiecesDispo.supprimer(p);
        this.listesPiecesPosees.addLast(p);
    }

    Piece getPieceDispo(int idPiece){
        return listePiecesDispo.getPiece(idPiece);
    }

    void ajouteCoin(Case ca){
        this.listeCoins.add(ca);
    }

    @Override
    public String toString() {
        String res = "Joueur{" +
                "id=" + id +
                "\n, listePiecesDispo=\n" + listePiecesDispo.toString() +
                "\n, listesPiecesPosees=\n" + listesPiecesPosees.toString() +
                "\n, score=" + score +
                "\n, listeCoins=" + listeCoins.toString() +
                '}';

        return res;
    }
}
