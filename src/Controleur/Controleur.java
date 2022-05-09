package Controleur;

import Modele.*;
import Vue.InterfaceKusBlo;
import Vue.MenuPiece;

import java.util.LinkedList;

public class Controleur {
    Jeu jeu;
    InterfaceKusBlo inter;

    public Controleur(Jeu j){
        jeu=j;
    }

    public void ajouteInterfaceUtilisateur(InterfaceKusBlo v) {
        inter = v;
    }

    public void setMenu1(){
        inter.setMenu1(jeu.getJoueurCourant());
    }

    public void setMenu2(int l, int c){
        int numPiece= l*7+c;
        inter.setMenu2(numPiece);
    }

    public void click(Piece piece,int x, int y, int decx, int decy){
        inter.getGraph().poserPiece(jeu.getJoueurCourant(),x, y, piece.getMatrice(),decx,decy);
        jeu.jouerPiece(jeu.getJoueurCourant(),inter.getM().getNumPiece(), tradMatrice(piece, x-decx,y-decy ));
        //jeu.getNiveau().ajouterPiece(piece,x-decx,y-decy,1);
        inter.delMouseClick();
        inter.setMenu1(jeu.getJoueurCourant());
    }

    public  boolean estPosable(Piece piece,int x, int y, int decx, int decy){
        Niveau n=jeu.getNiveau();
        return  n.estPosable(piece,x-decx,y-decy);
    }

    public  boolean estPosableRegle(Piece piece,int x, int y, int decx, int decy){
        return  jeu.estPosableRegle(tradMatrice(piece,x-decx,y-decy),jeu.getJoueurCourant());
    }

    public  Piece getPiece(int j, int p){
        return  jeu.getJoueur(j).getListePiecesDispo().getPiece(p);
    }

    public void rotaHorraire(){
        MenuPiece m= inter.getM();
        m.getPiece().rotationHoraire();
        m.getPiece().refreshCaseSelec();
        m.refreshPiece();

    }
    public ListePieces getListPiece(int joueur){
        return jeu.getJoueur(joueur).getListePiecesDispo();
    }

    public LinkedList<Case> tradMatrice(Piece p, int x, int y){
        int[][] matrice = p.getMatrice();
        LinkedList<Case> liste= new LinkedList<>();
        for(int i=0;i<5;i++){
            for(int j=0; j<5; j++){
                if(matrice[i][j]!=0){
                    int coordx=x+i;
                    int coordy=y+j;
                    Case c= new Case(coordx, coordy);
                    liste.add(c);
                }
            }
        }
        return liste;
    }

    public void visualiser(int x, int y,int[][] grille, int decx,int decy,boolean error){
        if(error){
            inter.getGraph().visualiser(5,x,y,grille,decx,decy);
        } else{
            inter.getGraph().visualiser(jeu.getJoueurCourant(),x,y,grille,decx,decy);
        }

    }

    public void delVisu(int x, int y,int[][] grille, int decx,int decy){
        inter.getGraph().supprimerVisualisation(x,y,grille,decx,decy);
    }

    public int getActJoueur(){
        return jeu.getJoueurCourant();
    }
}
