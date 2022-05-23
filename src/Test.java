import Controleur.IA;
import Controleur.IAAleatoire;
import Global.Configuration;
import Modele.*;

public class Test {

    public static void main(String args[]){

        Configuration instance = Configuration.instance();

        instance.ecris("AnimActive","true");
        instance.ecris("VitesseAnim","20");

        System.out.println("AnimActive = " +instance.lis("AnimActive"));
        System.out.println("VitesseAnim = " +instance.lis("VitesseAnim"));

        instance.ecris("AnimActive","false");
        instance.ecris("VitesseAnim","30");

        System.out.println("AnimActive = " +instance.lis("AnimActive"));
        System.out.println("VitesseAnim = " +instance.lis("VitesseAnim"));

    }
}
