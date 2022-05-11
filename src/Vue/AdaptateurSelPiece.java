package Vue;

import Controleur.Controleur;

import javax.swing.*;
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

    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isMiddleMouseButton(e)) {
            int l = e.getY() / m.getHautMenu();
            int c = e.getX() / m.getLargMenu();
            if(cont.contientPiece(l*7+c+1)){
                cont.setMenu2(l,c);
                m.showMenuType2();
            }
        }else{
            int l = e.getY() / m.getHautMenu();
            int c = e.getX() / m.getLargMenu();
            if(cont.contientPiece(l*7+c+1)){
                cont.selPiece(l,c);
            }
        }
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

}