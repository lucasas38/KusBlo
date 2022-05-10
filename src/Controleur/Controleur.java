package Controleur;

import Modele.*;
import Vue.InterfaceKusBlo;
import Vue.MenuPiece;

public class Controleur {
    Jeu jeu;
    InterfaceKusBlo inter;

    //suppr
    int i=0;

    public Controleur(Jeu j){
        jeu=j;
    }

    public void ajouteInterfaceUtilisateur(InterfaceKusBlo v) {
        inter = v;
    }

    public void setMenu1(){
        inter.setMenu1(jeu.getIDJoueurCourant(), jeu.getNumCouleurCourante());
    }

    public void setMenu2(int l, int c){
        int numPiece= l*7+c;
        inter.setMenu2(numPiece);
    }

    public void click(Piece piece,int x, int y){
        int decx = piece.getDebMatrice().getX();
        int decy = piece.getDebMatrice().getY();
        inter.getGraph().poserPiece(jeu.getNumCouleurCourante(), x, y, piece.getMatrice(),decx,decy);
        jeu.jouerPiece(jeu.getIDJoueurCourant(),inter.getM().getNumPiece(), jeu.tradMatrice(piece, x-decx,y-decy));
        //jeu.getNiveau().ajouterPiece(piece,x-decx,y-decy,1);
        //inter.delMouseClick();
        setMenu1();
    }

    public  boolean estPosable(Piece piece,int x, int y){
        Niveau n=jeu.getNiveau();
        return  n.estPosable(piece,x-piece.getDebMatrice().getX(),y-piece.getDebMatrice().getY());
    }

    public  boolean estPosableRegle(Piece piece,int x, int y){
        return  jeu.estPosableRegle(jeu.tradMatrice(piece,x-piece.getDebMatrice().getX(),y-piece.getDebMatrice().getY()),jeu.getIDJoueurCourant());
    }

    public  Piece getPiece(int j, int p){
        return  jeu.getJoueur(j).getCouleurCourante().getListePiecesDispo().getPiece(p);
    }


    public ListePieces getListPiece(int joueur){
        return jeu.getJoueur(joueur).getCouleurCourante().getListePiecesDispo();
    }

    public void visualiser(int x, int y,int[][] grille, int decx,int decy,boolean error){
        if(error){
            inter.getGraph().visualiser(5,x,y,grille,decx,decy);
        } else{
            inter.getGraph().visualiser(jeu.getNumCouleurCourante(),x,y,grille,decx,decy);
        }

    }

    public void delVisu(int x, int y,int[][] grille, int decx,int decy){
        inter.getGraph().supprimerVisualisation(x,y,grille,decx,decy);
    }

    public int getActCouleur(){
        return jeu.getNumCouleurCourante();
    }
    public int getActJoueur(){
        return jeu.getIDJoueurCourant();
    }

    public boolean contientPiece(int id){
        return jeu.getJoueur(jeu.getIDJoueurCourant()).getCouleurCourante().getListePiecesDispo().contient(id);
    }

    public void antiHoraire(){
        MenuPiece m= inter.getM();
        m.getPiece().rotationAntiHoraire();
//        m.getPiece().refreshCaseSelec();
        m.refreshPiece();
    }
    public void rotaHoraire(){
        MenuPiece m= inter.getM();
        m.getPiece().rotationHoraire();
//        m.getPiece().refreshCaseSelec();
        m.refreshPiece();
        if(i==0){
            jeu.positionPossible(1,6);
            i++;
        }

    }

    public void flip(){
        MenuPiece m= inter.getM();
        m.getPiece().rotationSymetrique();
//        m.getPiece().refreshCaseSelec();
        m.refreshPiece();
    }
}
