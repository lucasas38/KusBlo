package Modele;

import Structures.Case;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;

public class Piece implements Serializable {
    int id;  //identifiant unique d'une piece
    int taille;  //nombre de petits carré qui composent la piece
    int decx;  //correspondent à l'ordonée du premier carré rempli dans la matrice
    int decy;  //correspond à l'abscisse
    int[][] matrice; // /!\ doit être une matrice carrée : représente la piece sur une matrice carré (5*5) : 1 = contient un carré et 0 = vide
    Case debMatrice, finMatrice;  //indice de début et fin de la sous-matrice : permet de connaitre la sous matrice dans la matrice (5*5)
    LinkedList<Case> listeCases; //mis à jour quand la piece est posée (on sait qu'elle ne bougera plus) : liste de cases de la piece avec les "vraies" coordonnées sur le plateau

    public Piece(int id,int taille){
        this.id = id;
        this.taille = taille;
        this.matrice = null;
        this.debMatrice = null;
        this.finMatrice = null;
        this.listeCases = null; //liste des cases d'une piece avec les vraies coordonnées dans la grille du niveau
    }

    //met à jour decx et decy
    public void refreshCaseSelec(){
        decx=0;
        decy=0;
        //On place le décalage à la première case
        while(decx<5 && getMatrice()[decx][decy]==0){
            while (decy<5 && getMatrice()[decx][decy]==0){
                decy++;
            }
            if(decy>4 || getMatrice()[decx][decy]==0){
                decy=0;
                decx++;
            }
        }
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

    //met à jour la matrice d'une piece ainsi que debMatrice et finMatrice
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

    //tourne une piece dans le sens horaire
    public void rotationHoraire(){
        int[][] newMatrice = new int[5][5];
        for (int i = 0;i< matrice.length;i++){
            for (int j = 0;j<matrice[i].length;j++){
                if(matrice[i][j] == 1){
                    newMatrice[j][matrice.length-1-i] = 1;
                }
            }
        }

        setMatrice(newMatrice);
        refreshCaseSelec();
    }

    //tourne une piece dans le sens anti-horaire
    public void rotationAntiHoraire(){
        int[][] newMatrice = new int[5][5];
        for (int i = 0;i< matrice.length;i++){
            for (int j = 0;j<matrice.length;j++){
                if(matrice[i][j] == 1){
                    newMatrice[matrice.length-1-j][i] = 1;
                }
            }
        }

        setMatrice(newMatrice);
        refreshCaseSelec();
    }

    ////tourne une piece de façon symetrique
    public void rotationSymetrique(){
        int[][] newMatrice = new int[5][5];
        for (int i = 0;i< matrice.length;i++){
            for (int j = 0;j<matrice.length;j++){
                if(matrice[i][j] == 1){
                    newMatrice[i][matrice.length-1-j] = 1;
                }
            }
        }

        setMatrice(newMatrice);
        refreshCaseSelec();
    }

    @Override
    public String toString() {
        return ""+id;
        /*
        String res = "id=" + id + ", taille=" + taille + "\n";
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

         */

    }

    public String toStringMatrice() {
        String res = "";
        for (int i = 0;i< matrice.length;i++){
            for (int j = 0;j< matrice.length;j++){
                res += matrice[i][j];
            }
            res +="\n";
        }

        return res;
    }

    public int getDecx() {
        return decx;
    }

    public int getDecy() {
        return decy;
    }

    @Override
    public boolean equals(Object obj) {
        Piece p = (Piece) obj;
        return id == p.id;
    }
}
