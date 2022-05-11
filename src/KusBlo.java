import Controleur.Controleur;
import Modele.Jeu;
import Vue.InterfaceKusBlo;


public class KusBlo {
    public static void main(String args[]){
        Jeu jeu = new Jeu(4);
        Controleur cont = new Controleur(jeu);
        cont.addIA(1);
        InterfaceKusBlo.demarrer(cont);

    }
}
