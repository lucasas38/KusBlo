import Controleur.IA;
import Controleur.IAAleatoire;
import Modele.*;

public class Test {

    public static void main(String args[]){

        Jeu j = new Jeu(2);

        IA ia = new IAAleatoire(j);

        ia.joue();



    }
}
