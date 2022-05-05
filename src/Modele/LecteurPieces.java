package Modele;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.Scanner;

public class LecteurPieces {
    Scanner s;

    LecteurPieces(InputStream in) {
        s = new Scanner(in);
    }

    LinkedList<Piece> lirePieces(){
        String ligne;
        int idPiece=1;
        int taillePiece = 0;

        if(!s.hasNext()){
            return null;
        }

        LinkedList<Piece> listePieces = new LinkedList<>();

        Piece p;
        int[][] matrice = new int[5][5];
        int i = 0;

        while (s.hasNextLine()) {
            if (s.hasNext(";")){
                s.nextLine();
                p = new Piece(idPiece,taillePiece);
                p.setMatrice(matrice);
                listePieces.add(p);
                idPiece++;
                taillePiece=0;
                matrice = new int[5][5];
                i=0;
                continue;
            }else{

                int j=0;
                ligne = s.nextLine();


                while(j < ligne.length()){
                    int val = Character.getNumericValue(ligne.charAt(j));
                    if(val == 1){
                        taillePiece++;
                    }
                    matrice[i][j] = val;
                    j++;
                }
                i++;
            }


        }

        return listePieces;
    }
}
