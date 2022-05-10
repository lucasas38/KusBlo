package Modele;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class Joueur {
    int id;
    int score;
    boolean peutJouer;
    Couleur[] listeCouleur;  //à explorer pour 2, 3 joueurs : ? créer class couleur + deplaceent de méthodes de Jeu vers Couleur + changement Jeu (constructeur et attribut, et méthodes)
    int nbCouleurs;
    int couleurCourant;

    Joueur(int id){
        this.id = id;
        this.peutJouer=true;
        this.score = 0;
        listeCouleur = new Couleur[2];
        this.couleurCourant = 1;
        nbCouleurs=0;
    }

    public int getId() {
        return id;
    }

    public int getScore() {
        return score;
    }

    public Couleur[] getListeCouleur() {
        return listeCouleur;
    }

    public void addCouleur(Couleur c){
        listeCouleur[nbCouleurs] = c;
        nbCouleurs++;
    }

    public void setScoreFinal(){
        for (int i = 0;i<nbCouleurs;i++){
            if(!this.listeCouleur[i].getListePiecesDispo().estVide()){
                this.score -= this.listeCouleur[i].getListePiecesDispo().getTaille();
                if(this.score < 0){
                    this.score = 0;
                }
            }else{
                this.score += 15;
                if(this.listeCouleur[i].getListesPiecesPosees().getLast().getId() == 1){
                    this.score += 5;
                }
            }
        }
    }

    void jouePiece(Piece p){
        this.score += p.taille;
        this.listeCouleur[couleurCourant-1].jouePiece(p);
    }

    void ajouteCoin(Case ca){
        this.listeCouleur[couleurCourant-1].ajouteCoin(ca);
    }

//    @Override
//    public String toString() {
//        String res = "Joueur{" +
//                "id=" + id +
//                "\n, listePiecesDispo=\n" + listePiecesDispo.toString() +
//                "\n, listesPiecesPosees=\n" + listesPiecesPosees.toString() +
//                "\n, score=" + score +
//                "\n, listeCoins=" + listeCoins.toString() +
//                '}';
//
//        return res;
//    }

    public Couleur getCouleurCourante(){
        return listeCouleur[couleurCourant-1];
    }

    void setCouleurCourant(){
        couleurCourant = (couleurCourant%nbCouleurs)+1;
    }

    public boolean finCouleur() {
        Couleur couleur = getCouleurCourante();
        couleur.peutJouer=false;
        for (int i=0;i<nbCouleurs;i++){
            if(listeCouleur[i].peutJouer){
                return true;
            }
        }
        return false;
    }
}
