import Controleur.Controleur;
import Modele.Jeu;
import Vue.InterfaceKusBlo;


public class KusBlo {
    public static void main(String args[]){
        Jeu jeu = new Jeu(1);
        Controleur cont = new Controleur(jeu);
        InterfaceKusBlo.demarrer(cont);

    }
}
