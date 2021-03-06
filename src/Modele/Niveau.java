package Modele;

import Structures.Case;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;

public class Niveau implements Serializable {
    int[][] grille;  // (0,0) en haut à gauche et (ligne,colonne)

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

    //mise à jour de la grille de niveau : ajout de la piece (test réalisé avant)
    //place la couleur correspondante dans la grille du niveau
    public void ajouterPiece(Piece p, LinkedList<Case> listeCasesPiece,int idCouleur){
        p.listeCases = listeCasesPiece;
        Iterator<Case> it = p.listeCases.iterator();
        while (it.hasNext()){
            Case ca = it.next();
            grille[ca.getX()][ca.getY()] = idCouleur;
        }
    }

    //verifie si une piece ne superpose aucune autre piece, n'ecrase rien et ne depasse pas de la grille du niveau
    public boolean estPosable(Piece p, int x, int y){
        int[][] matrice = p.getMatrice();
        for(int i=0;i<5;i++){
            for(int j=0; j<5; j++){
                if(matrice[i][j]==1){
                    if(x+i<20 && y+j<20 && x+i>=0 && y+j>=0){
                        if(grille[i+x][j+y]!=0){
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



    //retourne les voisins d'une case
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

    //return true si le point x et y est dans la grille
    public boolean estDansGrille(int x, int y){
        return (x < grille.length && x >= 0) && (y < grille.length && y >= 0);
    }

    //return true si le point x et y n'a aucun voisin de meme couleur que lui
    public boolean aucunVoisin(int x, int y, int idCouleur) {
        return testVoisin(x-1,y,idCouleur)
        && testVoisin(x+1,y,idCouleur)
        && testVoisin(x,y-1,idCouleur)
        && testVoisin(x,y+1,idCouleur);

    }

    //return true si le point x,y a au moins une case de meme couleur que lui dans un de ses coins, false sinon
    public boolean auMoinsUnCoin(int x,int y,int idCouleur){
        return !testVoisin(x+1,y-1,idCouleur)
                || !testVoisin(x+1,y+1,idCouleur)
                || !testVoisin(x-1,y-1,idCouleur)
                || !testVoisin(x-1,y+1,idCouleur);
    }

    // return true si le point x,y est dans le grille et est different de la couleur idCouleur
    private boolean testVoisin(int x, int y, int idCouleur) {
        if(estDansGrille(x,y)){
            if(grille[x][y] != idCouleur){
                return true;
            }else{
                return false;
            }
        }else{
            return true;
        }
    }
}
