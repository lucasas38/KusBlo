import Modele.*;

public class Test {

    public static void main(String args[]){
        System.out.println("Hello World!");


        Jeu j = new Jeu(2);

        ListePieces l = new ListePieces();
        //Utilise equals
        //l.supprimer(l.liste.getFirst());
        l.supprimer(l.getListe().getLast().getId());



    }
}
