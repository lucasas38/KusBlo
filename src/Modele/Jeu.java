package Modele;

import java.util.Iterator;
import java.util.LinkedList;

public class Jeu {
    Niveau n;
//    ListePieces listePiecesBase;
    Joueur[] listeJoueurs;


    public Jeu(int nbJoueurs){
        this.n = new Niveau();
//        this.listePiecesBase = new ListePieces();
        listeJoueurs = new Joueur[nbJoueurs];
        for (int i=0;i<nbJoueurs;i++){
            listeJoueurs[i] = new Joueur(i+1,new ListePieces());
        }
    }

    public Niveau getNiveau() {
        return n;
    }

//    public ListePieces getListePiecesBase() {
//        return listePiecesBase;
//    }

    public Joueur[] getListeJoueurs() {
        return listeJoueurs;
    }

    public Joueur getJoueur(int idJoueur){
        return listeJoueurs[idJoueur-1];
    }

    public void jouerPiece(int idJoueur,int idPiece, int x, int y){
        Piece piece = listeJoueurs[idJoueur-1].getPieceDispo(idPiece);
        if(piece != null){
            if(n.estPosable(piece,x,y)){
                n.ajouterPiece(piece,x,y,idJoueur);
                calculeCoinPiece(piece,idJoueur);
                listeJoueurs[idJoueur-1].jouePiece(piece);
            }else{
                System.out.println("Piece n'est pas posable (superpose autre piece ou en dehors grille)");
            }
        }else{
            System.out.println("Piece plus disponible pour le joueur");
        }

    }

    private void calculeCoinPiece(Piece piece, int idJoueur) {
        Iterator<Case> it = piece.listeCases.iterator();

        while (it.hasNext()){
            Case ca = it.next();

            int x = ca.getX();
            int y = ca.getY();

            if(getJoueur(idJoueur).listeCoins.contains(ca)){
                getJoueur(idJoueur).listeCoins.remove(ca);
            }

            estCoinValide(x+1,y-1,idJoueur);
            estCoinValide(x+1,y+1,idJoueur);
            estCoinValide(x-1,y-1,idJoueur);
            estCoinValide(x-1,y+1,idJoueur);

        }
    }

    void estCoinValide(int x, int y, int idJoueur){
        if(n.estDansGrille(x,y) && n.grille[x][y] == 0){
            if(n.aucunVoisin(x,y,idJoueur)){
                listeJoueurs[idJoueur-1].ajouteCoin(new Case(x,y));
            }
        }
    }

}
