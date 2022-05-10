package Modele;

import java.util.Iterator;
import java.util.LinkedList;

public class Piece {
    int id;
    int taille;
    int decx;
    int decy;
    int[][] matrice; // /!\ doit être une matrice carrée
    Case debMatrice, finMatrice;  //indice de début et fin de la sous-matrice
    LinkedList<Case> listeCases; //mis à jour quand la piece est posée (on sait qu'elle ne bougera plus)

    public Piece(int id,int taille){
        this.id = id;
        this.taille = taille;
        this.matrice = null;
        this.debMatrice = null;
        this.finMatrice = null;
        this.listeCases = null; //liste des cases d'une piece avec les vraies coordonnées dans la grille du niveau
    }

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

        setMatrice(newMatrice);
        refreshCaseSelec();
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

        setMatrice(newMatrice);
        refreshCaseSelec();
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

        setMatrice(newMatrice);
        refreshCaseSelec();
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

    @Override
    public boolean equals(Object o) {
        System.out.println("equals piece appellé");
        return true;
    }

    public int getDecx() {
        return decx;
    }

    public int getDecy() {
        return decy;
    }

    @Override
    public Piece clone() {
        Piece clone = null;
        try {
            clone = (Piece) super.clone();
            clone.matrice = matrice.clone();
            clone.debMatrice = debMatrice.clone();
            clone.finMatrice = finMatrice.clone();
            clone.listeCases = (LinkedList<Case>) listeCases.clone();
        } catch (CloneNotSupportedException e) {
            System.err.println("Bug interne serieux avec le clone : Piece");
            System.exit(1);
        }
        return clone;
    }

}
