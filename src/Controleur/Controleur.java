package Controleur;

import Modele.Jeu;
import Modele.Niveau;
import Modele.Piece;
import Vue.InterfaceKusBlo;
import Vue.MenuPiece;

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
        inter.setMenu1();
    }

    public void setMenu2(){
        inter.setMenu2();
    }

    public void click(Piece piece,int x, int y, int decx, int decy){
        jeu.getNiveau().ajouterPiece(piece,x-decx,y-decy,1);
        inter.delMouseClick();
    }

    public  boolean estPosable(Piece piece,int x, int y, int decx, int decy){
        Niveau n=jeu.getNiveau();
        return  n.estPosable(piece,x-decx,y-decy);
    }

    public  Piece getPiece(int j, int p){
        return  jeu.getJoueur(j).getCouleurCourante().getListePiecesDispo().getPiece(p);
    }

    public void rotaHorraire(){
        MenuPiece m= inter.getM();
        m.getPiece().rotationHoraire();
        m.refreshPiece();
    }
}
