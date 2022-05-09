package Vue;

import Controleur.Controleur;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class AdaptateurSelPiece implements MouseListener {
    VueNiveau n;
    MenuPiece m;
    Controleur cont;
    boolean pieceMultiple;
    int x;
    int y;


    AdaptateurSelPiece(VueNiveau niv, MenuPiece menu,Controleur c, boolean multPiece) {
        n = niv;
        m = menu;
        pieceMultiple=multPiece;
        x = 0;
        y = 0;
        cont=c;
    }

    public void mouseDragged(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }



    @Override
    public void mousePressed(MouseEvent e) {
        if(pieceMultiple){
            int l = e.getY() / m.getHautMenu();
            int c = e.getX() / m.getLargMenu();
            cont.setMenu2(l,c);
        }



    }

}