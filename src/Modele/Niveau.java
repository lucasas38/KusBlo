package Modele;

import java.util.Arrays;

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

    public void ajouterPiece(Piece p,int x, int y,int couleur){
        int [][] matrice = p.getMatrice();

        int debX = p.getDebMatrice().getX();
        int debY = p.getDebMatrice().getY();
        int finX = p.getFinMatrice().getX();
        int finY = p.getFinMatrice().getY();

        for (int i = debX;i< finX+1;i++){
            for (int j = debY;j< finY+1;j++){
                if(matrice[i][j] == 1){
                    grille[x+i-debX][y+j-debY] = couleur;
                }
            }
        }
    }

    public boolean estPosable(Piece p, int x, int y, int couleur){
        int[][] matrice = p.getMatrice();

        int debX = p.getDebMatrice().getX();
        int debY = p.getDebMatrice().getY();
        int finX = p.getFinMatrice().getX();
        int finY = p.getFinMatrice().getY();

        //test piece ne depasse pas grille du niveau
        if(x+finX-debX < grille.length && y+finY-debY < grille.length){
            for (int i = debX;i< finX+1;i++){
                for (int j = debY;j< finY+1;j++){
                    if(grille[i+x][j+y] != 0 && matrice[i][j] == 1){
                        System.out.println(false);
                        return false;
                    }
                }
            }
            System.out.println(true);
            return true;
        }else{
            System.out.println(false);
            return false;
        }

    }

}
