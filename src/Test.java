import Modele.Jeu;
import Modele.Joueur;
import Modele.Niveau;
import Modele.Piece;

public class Test {

    public static void main(String args[]){
        System.out.println("Hello World!");


        Jeu j = new Jeu(2);


        j.jouerPiece(2,6,18,17);
        j.jouerPiece(2,1,17,17);
        j.getJoueur(2).getListePiecesDispo().getPiece(2).rotationHoraire();
        j.jouerPiece(2,2,16,18);

        j.getJoueur(2).getListePiecesDispo().getPiece(5).rotationHoraire();
        j.getJoueur(2).getListePiecesDispo().getPiece(5).rotationHoraire();
        j.jouerPiece(2,5,13,16);

        System.out.println(j.getNiveau().toString());
//        System.out.println(j.getJoueur(2).toString());


    }
}
