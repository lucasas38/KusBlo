package Vue;

import Controleur.Controleur;

import java.awt.event.*;

public class AdaptateurSouris implements MouseListener, MouseMotionListener {
    VueNiveau n;
    MenuPiece m;
    int x;
    int y;



    public void mouseDragged(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}




    AdaptateurSouris(VueNiveau niv, MenuPiece menu) {
        n = niv;
        m=menu;
        x=0;
        y=0;

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int l = e.getY() / n.hauteurCase();
        int c = e.getX() / n.largeurCase();
        n.poserPiece(l, c, m.getGrillePiece());


    }
    @Override
    public void mouseMoved(MouseEvent e){
        int l = e.getY() / n.hauteurCase();
        int c = e.getX() / n.largeurCase();
        n.supprimerVisualisation(x,y,m.getGrillePiece());
        n.visualiser(l,c,m.getGrillePiece());
        x=l;
        y=c;

    }

}


