package Modele;

import java.util.Iterator;
import java.util.LinkedList;

public class Piece {
    int id;
    int taille;
    int[][] matrice; // /!\ doit être une matrice carrée
    Case debMatrice, finMatrice;  //indice de début et fin de la sous-matrice
    LinkedList<Case> listeCases;

    public Piece(){
    }

    public Piece(int id,int taille){
        this.id = id;
        this.taille = taille;
        this.matrice = null;
        this.debMatrice = null;
        this.finMatrice = null;
        this.listeCases = null; //liste des cases d'une piece avec les vraies coordonnées dans la grille du niveau
    }

    public int getId() {
        return id;
    }

    public int[][] getMatrice() {
        return matrice;
    }

    public int getTaille() {
        return taille;
    }

    public Case getDebMatrice() {
        return debMatrice;
    }

    public Case getFinMatrice() {
        return finMatrice;
    }

    public LinkedList<Case> getListeCases() {
        return listeCases;
    }

    public void setMatrice(int[][] matrice) {
        this.matrice = matrice;

        debMatrice = new Case(4,4);
        finMatrice = new Case(0,0);

        for (int i=0;i< matrice.length;i++){
            for (int j=0;j< matrice.length;j++){
                if(matrice[i][j] == 1){
                    debMatrice.setXY(Math.min(i, debMatrice.getX()),Math.min(j,debMatrice.getY()));
                    finMatrice.setXY(Math.max(i, finMatrice.getX()),Math.max(j,finMatrice.getY()));
                }
            }
        }
    }

    public void rotationHoraire(){
        int[][] newMatrice = new int[5][5];
        for (int i = 0;i< matrice.length;i++){
            for (int j = 0;j<matrice[i].length;j++){
                if(matrice[i][j] == 1){
                    newMatrice[j][matrice.length-1-i] = 1;
                }
            }
        }

        this.setMatrice(newMatrice);
    }

    public void rotationAntiHoraire(){
        int[][] newMatrice = new int[5][5];
        for (int i = 0;i< matrice.length;i++){
            for (int j = 0;j<matrice.length;j++){
                if(matrice[i][j] == 1){
                    newMatrice[matrice.length-1-j][i] = 1;
                }
            }
        }

        this.setMatrice(newMatrice);
    }

    public void rotationSymetrique(){
        int[][] newMatrice = new int[5][5];
        for (int i = 0;i< matrice.length;i++){
            for (int j = 0;j<matrice.length;j++){
                if(matrice[i][j] == 1){
                    newMatrice[i][matrice.length-1-j] = 1;
                }
            }
        }

        this.setMatrice(newMatrice);
    }

    @Override
    public String toString() {
        String res = "id=" + id +", taille=" + taille + "\n";
        for (int i = 0;i< matrice.length;i++){
            for (int j = 0;j< matrice.length;j++){
                res += matrice[i][j];
            }
            res +="\n";
        }
        res += "debMatrice=" + debMatrice.toString();
        res += ", finMatrice=" + finMatrice.toString() + "\n";

        if(listeCases != null){
            Iterator<Case> it = listeCases.iterator();
            while (it.hasNext()){
                Case c = it.next();
                res += c.toString() + " ";
            }
        }else{
            res += ", listeCases=null";
        }
        res +="\n";

        return res;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Piece piece = (Piece) o;
        return id == piece.id;
    }

}
