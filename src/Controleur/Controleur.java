package Controleur;

import Vue.InterfaceKusBlo;

public class Controleur {
    InterfaceKusBlo inter;

    public void ajouteInterfaceUtilisateur(InterfaceKusBlo v) {
        inter = v;
    }

    public void setMenu1(){
        inter.setMenu1();
    }

    public void setMenu2(){
        inter.setMenu2();
    }

    public void click(){
        inter.delMouseClick();
    }
}
