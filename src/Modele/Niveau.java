package Modele;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.LinkedList;

public class Niveau {
    int[][] grille;  // (0,0) en haut à gauche

    public Niveau() {
        this.grille = new int[20][20];
    }

    public int[][] getGrille() {
        return grille;
    }

    @Override
    public String toString() {
        String res = "";
        for (int i = 0;i< grille.length;i++){
            for (int j = 0;j< grille.length;j++){
                res += grille[i][j];
            }
            res +="\n";
        }

        return res;
    }

//    //mise à jour de la grille de niveau : ajout de la piece (test réalisé avant)
public void ajouterPiece(Piece p,int x, int y,int idJoueur){
    int [][] matrice = p.getMatrice();

    int debX = p.getDebMatrice().getX();
    int debY = p.getDebMatrice().getY();
    int finX = p.getFinMatrice().getX();
    int finY = p.getFinMatrice().getY();

    p.listeCases = new LinkedList<>();

    for (int i = debX;i< finX+1;i++){
        for (int j = debY;j< finY+1;j++){
            if(matrice[i][j] == 1){
                grille[x+i-debX][y+j-debY] = idJoueur;
                p.listeCases.add(new Case(x+i-debX,y+j-debY));
            }
        }
    }

}

    //mise à jour de la grille de niveau : ajout de la piece (test réalisé avant)
    public void ajouterPiece(Piece p, LinkedList<Case> listeCasesPiece,int idJoueur){
        p.listeCases = listeCasesPiece;  // voir si clone() utile ?
        Iterator<Case> it = p.listeCases.iterator();
        while (it.hasNext()){
            Case ca = it.next();
            grille[ca.getX()][ca.getY()] = idJoueur;
        }
    }

    public boolean estPosable(Piece p, int x, int y){
        int[][] matrice = p.getMatrice();
        for(int i=0;i<5;i++){
            for(int j=0; j<5; j++){
                if(matrice[i][j]==1){
                    if(x+i<20 && y+j<20 && x+i>=0 && y+j>=0){
                        if(grille[i+x][j+y]==1){
                            return false;
                        }
                    } else{
                        return false;
                    }
                }
            }
        }
        return true;
    }



    public LinkedList<Case> voisinsCase(Case ca) {
        LinkedList<Case> liste = new LinkedList<>();
        int x = ca.getX();
        int y = ca.getY();
        if(estDansGrille(x+1,y)){
            liste.add(new Case(x+1,y));
        }
        if(estDansGrille(x-1,y)){
            liste.add(new Case(x-1,y));
        }
        if(estDansGrille(x,y+1)){
            liste.add(new Case(x,y+1));
        }
        if(estDansGrille(x,y-1)){
            liste.add(new Case(x,y-1));
        }

        return liste;
    }

    boolean estDansGrille(int x, int y){
        return (x < grille.length && x >= 0) && (y < grille.length && y >= 0);
    }

    public boolean aucunVoisin(int x, int y, int idJoueur) {
        return testVoisin(x-1,y,idJoueur)
        && testVoisin(x+1,y,idJoueur)
        && testVoisin(x,y-1,idJoueur)
        && testVoisin(x,y+1,idJoueur);

    }

    private boolean testVoisin(int x, int y, int idJoueur) {
        if(estDansGrille(x,y)){
            if(grille[x][y] != idJoueur){
                return true;
            }else{
                return false;
            }
        }else{
            return true;
        }
    }
}
