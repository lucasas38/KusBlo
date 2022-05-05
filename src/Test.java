import Modele.Niveau;
import Modele.Piece;

public class Test {

    public static void main(String args[]){
        System.out.println("Hello World!");


        Niveau n = new Niveau();

        int[][] p1Matrice = new int[5][5];
        p1Matrice[0][0] = 1;
        p1Matrice[0][1] = 1;
        p1Matrice[1][1] = 1;
        p1Matrice[1][2] = 1;
        p1Matrice[2][1] = 1;

        Piece p1 = new Piece(1,5);
            p1.setMatrice(p1Matrice);

        int[][] p2Matrice = new int[5][5];
        p2Matrice[0][1] = 1;
        p2Matrice[1][0] = 1;
        p2Matrice[1][1] = 1;
        p2Matrice[1][2] = 1;
        p2Matrice[2][1] = 1;

        Piece p2 = new Piece(2,5);
            p2.setMatrice(p2Matrice);

    //        n.estPosable(p1,0,0,1);
            n.ajouterPiece(p1,0,0,1);



            p2.rotationHoraire();
            p2.rotationHoraire();
    //        n.estPosable(p2,17,17,2);
            n.ajouterPiece(p2,17,17,2);

    //        n.estPosable(p2,18,18,1);

            n.estPosable(p1,0,0,1);
            n.estPosable(p1,0,1,1);
            n.estPosable(p1,0,2,1);

            System.out.println(n.toString());
    }
}
