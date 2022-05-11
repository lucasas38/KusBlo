package Controleur;

import Modele.*;
import Structures.Case;
import Vue.InterfaceKusBlo;
import Vue.MenuPiece;

import java.util.LinkedList;

public class Controleur {
    Jeu jeu;
    InterfaceKusBlo inter;
    IA ia;
    boolean ia_active;

    public Controleur(Jeu j){
        jeu=j;
        ia_active= false;
    }

    public void addIA(int type_ia){
        ia_active=true;
        if(type_ia==1){
            ia = new IAAleatoire(this);
        }

    }

    public void ajouteInterfaceUtilisateur(InterfaceKusBlo v) {
        inter = v;
    }

    public void setMenu1(){
        inter.getInterJ().setMenu1(jeu.getIDJoueurCourant(), jeu.getNumCouleurCourante());
    }

    public void setMenu2(int l, int c){
        int numPiece= l*7+c;
        inter.getInterJ().setMenu2(numPiece);
    }

    public void click(Piece piece,int x, int y, int decx, int decy){
        inter.getInterJ().getGraph().poserPiece(jeu.getNumCouleurCourante(), x, y, piece.getMatrice(),decx,decy);
        jeu.jouerPiece(jeu.getIDJoueurCourant(),inter.getInterJ().getM().getNumPiece(), jeu.tradMatrice(piece, x-decx,y-decy ));
        //jeu.getNiveau().ajouterPiece(piece,x-decx,y-decy,1);
        //inter.delMouseClick();
        inter.getInterJ().getM().resetBorder();
        setMenu1();
        inter.getInterJ().refreshPanJoueur(jeu.getNumCouleurCourante(),piece.getId());
        if(ia_active && jeu.getIDJoueurCourant() == 2){
            ia.joue();
        }
    }

    public void joueIA(Piece piece,LinkedList<Case> listeCases){
        inter.getInterJ().getGraph().poserPiece(jeu.getNumCouleurCourante(), listeCases);
        jeu.jouerPiece(jeu.getIDJoueurCourant(),piece.getId(), listeCases);
        inter.getInterJ().getM().resetBorder();
        setMenu1();
        inter.getInterJ().refreshPanJoueur(jeu.getNumCouleurCourante(),piece.getId());
    }

    public  boolean estPosable(Piece piece,int x, int y, int decx, int decy){
        Niveau n=jeu.getNiveau();
        return  n.estPosable(piece,x-decx,y-decy);
    }

    public  boolean estPosableRegle(Piece piece,int x, int y, int decx, int decy){
        return  jeu.estPosableRegle(jeu.tradMatrice(piece,x-decx,y-decy),jeu.getIDJoueurCourant());
    }

    public  Piece getPiece(int j, int p){
        return  jeu.getJoueur(j).getCouleurCourante().getListePiecesDispo().getPiece(p);
    }

    public ListePieces getListPiece(int joueur){
        return jeu.getJoueur(joueur).getCouleurCourante().getListePiecesDispo();
    }

    public void visualiser(int x, int y,int[][] grille, int decx,int decy,boolean error){
        if(error){
            inter.getInterJ().getGraph().visualiser(5,x,y,grille,decx,decy);
        } else{
            inter.getInterJ().getGraph().visualiser(jeu.getNumCouleurCourante(),x,y,grille,decx,decy);
        }
    }

    public void delVisu(int x, int y,int[][] grille, int decx,int decy){
        inter.getInterJ().getGraph().supprimerVisualisation(x,y,grille,decx,decy);
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
        MenuPiece m= inter.getInterJ().getM();
        m.getPiece().rotationAntiHoraire();
        m.refreshPiece(jeu.getNumCouleurCourante());
    }
    public void rotaHoraire(){
        MenuPiece m= inter.getInterJ().getM();
        m.getPiece().rotationHoraire();
        m.refreshPiece(jeu.getNumCouleurCourante());

    }

    public void flip(){
        MenuPiece m= inter.getInterJ().getM();
        m.getPiece().rotationSymetrique();
        m.refreshPiece(jeu.getNumCouleurCourante());
    }

    public void toucheClavier(String touche) {
        switch (touche) {
            case "Left":
                rotaHoraire();
                break;
            case "Right":
                antiHoraire();
                break;
            case "Up":
            case "Down":
                flip();
                break;
            case "Quit":
                System.exit(0);
                break;
            default:
                System.out.println("Touche inconnue : " + touche);
        }
    }
    public int getNbJoueur(){
        return  jeu.getNbJoueurs();
    }

    public void selPiece(int l, int c){
        setMenu2(l,c);
        inter.getInterJ().getM().selPiece(l*7+c+1);
    }

    public void passerTour(){
        System.out.println("Passe tour");
        jeu.passerTour();
        setMenu1();
    }

    public int getFrameH(){
        return inter.getH();
    }

    public int getFrameW(){
        return inter.getW();
    }

    public void newGame(){
        jeu= new Jeu(4);
        inter.setInterJeu();
    }

    public void menu(){
        inter.setMenu();
    }
}
