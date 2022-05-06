package Vue;

import Controleur.Controleur;

import java.awt.event.*;

public class AdaptateurSouris implements MouseListener, MouseMotionListener {
    VueNiveau n;
    MenuPiece m;
    Controleur cont;
    int x;
    int y;


    AdaptateurSouris(VueNiveau niv, MenuPiece menu, Controleur c) {
        n = niv;
        m=menu;
        cont=c;
        x=0;
        y=0;

    }


    public void mouseDragged(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {
        n.supprimerVisualisation(x,y,m.piece.getMatrice(), m.x,m.y);
    }
    public void mouseReleased(MouseEvent e) {}






    @Override
    public void mousePressed(MouseEvent e) {
        int l = e.getY() / n.hauteurCase();
        int c = e.getX() / n.largeurCase();
        if(cont.estPosable(m.piece, l,c,m.x,m.y)){
            n.poserPiece(l, c, m.piece.getMatrice(),m.x,m.y);
            cont.click(m.piece, l,c,m.x,m.y);
        }
    }
    @Override
    public void mouseMoved(MouseEvent e){

        int l = e.getY() / n.hauteurCase();
        int c = e.getX() / n.largeurCase();
        //On exécute la fonction que lorsqu'on change de case
        if(l!=x || c!=y){
            n.supprimerVisualisation(x,y,m.piece.getMatrice(), m.x,m.y);
            if(cont.estPosable(m.piece, l,c,m.x,m.y)){
                n.visualiser(l,c,m.piece.getMatrice(), m.x,m.y);
            }
            x=l;
            y=c;

        }


    }

}


