package Vue;

import Controleur.Controleur;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class AdaptateurSelPiece implements MouseListener {
    VueNiveau n;
    MenuJeu m;
    Controleur cont;
    boolean activ;


    AdaptateurSelPiece(VueNiveau niv, MenuJeu menu, Controleur c) {
        n = niv;
        m = menu;
        cont=c;
        activ=true;
    }

    //Sélectionne la pièce ou affiche la pièce toute seule avec un click molette;
    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        int l = e.getY() / m.getHautMenu();
        int c = e.getX() / m.getLargMenu();
        if(cont.contientPiece(l*7+c+1)){
            cont.selPiece(l,c);
        }
    }

    public void setActiv(boolean activ) {
        this.activ = activ;
    }
}