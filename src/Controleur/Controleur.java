package Controleur;

import Modele.*;
import Structures.Case;
import Vue.InterfaceKusBlo;
import Vue.MenuPiece;

import java.util.LinkedList;

public class Controleur {
    Jeu jeu;
    InterfaceKusBlo inter;
    IA[] ia;
    boolean animActiv = true;

    public Controleur(){
        ia = new IA[3];
        for (int i=0;i<3;i++){
            switch (i){
                case 0:
                    ia[i] = new IAAleatoire(this);
                    break;
                default:
                    ia[i] = new IAAleatoire(this);
                    break;
            }
        }
    }


    public void addIA(int type_ia,int idJoueur){
        if(type_ia>=1 && type_ia<4){
            jeu.getJoueur(idJoueur).setType_ia(type_ia);
        }else{
            jeu.getJoueur(idJoueur).setType_ia(1); //par défault
        }
    }

    public void ajouteInterfaceUtilisateur(InterfaceKusBlo v) {
        inter = v;
    }

    public void setMenu1(){
        inter.getInterJ().getGraph().stopTimer();
        if(isFinJeu()){
            inter.getInterJ().cleanTour();
            inter.getInterJ().delMouseClick();
            int maxScore=jeu.getJoueur(1).getScore();
            int bestPlayer=1;
            //affichage temporaire
            for (int i=1;i<jeu.getNbJoueurs()+1;i++){
                if(jeu.getJoueur(i).getScore()>maxScore){
                    maxScore=jeu.getJoueur(i).getScore();
                    bestPlayer=i;
                }
                System.out.println("Joueur "+i+" a obtenu un score de "+jeu.getJoueur(i).getScore());
            }
            inter.getInterJ().getM().setMenuType3(bestPlayer);

        }else {
                if (jeu.getJoueur(jeu.getIDJoueurCourant()).getCouleurCourante().isPeutJouer()) {
                    inter.getInterJ().setTour(jeu.getNumCouleurCourante());
                    int type_ia = jeu.getJoueur(jeu.getIDJoueurCourant()).getType_ia();
                    if(type_ia !=0){
                        inter.getInterJ().delMouseClick();
                        switch (type_ia){
                            case 1:
                                ia[0].joue();
                                break;
                            default:
                                ia[0].joue();
                                break;
                        }
                    }else{
                        inter.getInterJ().setMenu1(jeu.getIDJoueurCourant(), jeu.getNumCouleurCourante());
                    }

                } else {
                    passerTour();
                }
            }
    }

    public void setMenu2(int l, int c){
        int numPiece= l*7+c;
        inter.getInterJ().setMenu2(numPiece);
    }

    public void click(Piece piece,int x, int y, int decx, int decy){
        if(jeu.getJoueur(jeu.getIDJoueurCourant()).getCouleurCourante().isPeutJouer()){
            inter.getInterJ().getGraph().poserPiece(jeu.getNumCouleurCourante(), x, y, piece.getMatrice(),decx,decy);
            jeu.jouerPiece(jeu.getIDJoueurCourant(),inter.getInterJ().getM().getNumPiece(), jeu.tradMatrice(piece, x-decx,y-decy ));
            System.out.println(jeu.getNiveau());
            //jeu.getNiveau().ajouterPiece(piece,x-decx,y-decy,1);
            //inter.delMouseClick();
            inter.getInterJ().getM().resetBorder();
            for (int i=1;i<jeu.getNbJoueurs()+1;i++){
                inter.getInterJ().setScore(i,jeu.getJoueur(i).getScore());
                if(jeu.getNbJoueurs()==2){
                    inter.getInterJ().setScore(i+2,jeu.getJoueur(i).getScore());
                }
            }
            inter.getInterJ().refreshPanJoueur(jeu.getNumCouleurCourante(),piece.getId());
            setMenu1();
        }

    }

    public void joueIA2(Piece piece,LinkedList<Case> listeCases){
        jeu.jouerPiece(jeu.getIDJoueurCourant(),piece.getId(), listeCases);
        inter.getInterJ().getM().resetBorder();
        for (int i=1;i<jeu.getNbJoueurs()+1;i++){
            inter.getInterJ().setScore(i,jeu.getJoueur(i).getScore());
            if(jeu.getNbJoueurs()==2){
                inter.getInterJ().setScore(i+2,jeu.getJoueur(i).getScore());
            }
        }
        inter.getInterJ().refreshPanJoueur(jeu.getNumCouleurCourante(),piece.getId());
        setMenu1();
    }


    public void joueIA(Piece piece,LinkedList<Case> listeCases){
        if(animActiv){
            inter.getInterJ().getGraph().poserPieceIA(piece,listeCases,jeu.getNumCouleurCourante());
        }else{
            inter.getInterJ().getGraph().poserPiece(jeu.getNumCouleurCourante(), listeCases);
            joueIA2(piece,listeCases);
        }

    }

    public boolean estPosable2(Piece piece,int x, int y, int decx, int decy){
        Niveau n= jeu.getNiveau();
        return n.estPosable(piece, x-decx, y-decy);
    }

    public  boolean estPosable(Piece piece,int x, int y, int decx, int decy){
        for(int i=0;i<5;i++){
            for(int j=0; j<5; j++){
                if(x+i-decx>19 || x+i-decx<0 || y+j-decy>19 || y+j-decy<0 ){
                    if(piece.getMatrice()[i][j]!=0){
                        return  false;
                    }
                }
            }
        }
        return true;
    }

    public  boolean estPosableRegle(Piece piece,int x, int y, int decx, int decy){
        return  jeu.estPosableRegle(jeu.tradMatrice(piece,x-decx,y-decy),jeu.getIDJoueurCourant());
    }

    public  Piece getPiece(int j, int p){
        return  jeu.getJoueur(j).getCouleurCourante().getListePiecesDispo().getPiece(p);
    }

    public ListePieces getListPiece(int couleur){
        if(getNbJoueur()==2){
            if(couleur>2){
                return jeu.getJoueur(couleur-2).getListeCouleur()[1].getListePiecesDispo();
            } else {
                return jeu.getJoueur(couleur).getListeCouleur()[0].getListePiecesDispo();
            }

        } else{
            return jeu.getJoueur(couleur).getCouleurCourante().getListePiecesDispo();
        }

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
        jeu.passerTour();
        setMenu1();
    }

    public void finCouleur() {
        jeu.finCouleur();
    }

    public int getFrameH(){
        return inter.getH();
    }

    public int getFrameW(){
        return inter.getW();
    }

    public void newGame(){
       jeu= new Jeu(4);
        //addIA(1,1);
        //addIA(1,2);
       // addIA(1,3);
        //addIA(1,4);

        jeu= new Jeu(2);
        //addIA(1,1);
         addIA(1,2);

        inter.setInterJeu();

        //si joueur qui commence est une ia , elle joue son premier coup
        int type_ia = jeu.getJoueur(jeu.getIDJoueurCourant()).getType_ia();
        if(type_ia !=0) {
            switch (type_ia) {
                case 1:
                    ia[0].joue();
                    break;
                default:
                    ia[0].joue();
                    break;
            }
        }
    }

    public void menu(){
        inter.setMenu();
    }

    public boolean isFinJeu(){
        for(int i=1; i<jeu.getNbJoueurs()+1;i++){
            if(jeu.getJoueur(i).isPeutJouer()){
                return false;
            }
        }
        return true;
    }

    public  void setMenu4(){
        inter.getInterJ().getM().setMenuType4();
    }

    public void visuIA(LinkedList<Case> listeCase, int couleur){
        inter.getInterJ().getGraph().visualiser(couleur,listeCase);
    }

    public void poserPiece(LinkedList<Case> listeCase, int couleur){
        inter.getInterJ().getGraph().poserPiece(couleur,listeCase);
    }

    public void showMenuOpt(){
        inter.getInterJ().getGraph().stopTimer();
        inter.getInterJ().setMenuOpt();
    }

    public void reprendre(){
        System.out.println(ia[jeu.getIDJoueurCourant()-1]!=null);
        if(ia[jeu.getIDJoueurCourant()-1]!=null){
            inter.getInterJ().getGraph().startTimer();
        }
        inter.getInterJ().reprendre();
    }
}
