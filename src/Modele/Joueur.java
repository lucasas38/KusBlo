package Modele;

import Structures.Case;

import java.io.Serializable;
import java.util.Iterator;

public class Joueur implements Serializable {
    int id;  //identifiant unique d'un joueur entre (1 et 2) ou (1 et 3) ou (1 et 4)
    int score;  //score d'un joueur
    boolean peutJouer; //true si joueur à encore une couleur qu'il peut jouer
    Couleur[] listeCouleur;  // Liste des couleurs du joueur entre (1 et 2)
    int nbCouleurs;  //nombre de couleurs que joue un joueur
    int couleurCourant;  //identifiant de la couleur courante que le joueur joue/va jouer

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

    //definit le score final d'un joueur : ajoute les bonus et les malus concernant les pieces qu'il a posé
    public void setScoreFinal(){
        for (int i = 0;i<nbCouleurs;i++){
            if(!this.listeCouleur[i].getListePiecesDispo().estVide()){
                //enlever pour chaque piece restante sa taille
                Iterator<Piece> it = this.listeCouleur[i].getListePiecesDispo().iterateur();
                Piece p;
                while(it.hasNext()){
                    p = it.next();
                    this.score -= p.taille;
                }

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

    //met à jour le score d'un joueur et joue une piece pour sa couleur courante
    void jouePiece(Piece p){
        this.score += p.taille;
        this.listeCouleur[couleurCourant-1].jouePiece(p);
    }

    //ajoute un coin à la couleur courante du joueur
    void ajouteCoin(Case ca){
        this.listeCouleur[couleurCourant-1].ajouteCoin(ca);
    }

    public Couleur getCouleurCourante(){
        return listeCouleur[couleurCourant-1];
    }

    //change la couleur courante d'un joueur, passe à la suivante
    public void setCouleurCourant(){
        couleurCourant = (couleurCourant%nbCouleurs)+1;
    }

    //met l'attribut peutJouer de sa couleur courante à faux, signifie que cette couleur ne peut plus jouer
    //parcourt ses couleur et si plus aucune ne peut jouer, passe son attribut peutJouer à faux, le joueur ne peut plus jouer
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

    public boolean isPeutJouer() {
        return peutJouer;
    }

}
