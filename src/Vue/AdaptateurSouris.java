package Vue;

import Controleur.Controleur;

import javax.swing.*;
import java.awt.event.*;

public class AdaptateurSouris implements MouseListener, MouseMotionListener, MouseWheelListener {
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
    public void mouseClicked(MouseEvent e) {
        if(SwingUtilities.isRightMouseButton(e)){
            cont.delVisu(x,y,m.piece.getMatrice(), m.piece.getDecx(),m.piece.getDecy());
            cont.flip();
            if(cont.estPosable(m.piece, x,y,m.piece.getDecx(),m.piece.getDecy())){
                if(cont.estPosableRegle(m.piece,x,y,m.piece.getDecx(),m.piece.getDecy())){
                    cont.visualiser(x,y,m.piece.getMatrice(), m.piece.getDecx(),m.piece.getDecy(), false);
                }else{
                    cont.visualiser(x,y,m.piece.getMatrice(), m.piece.getDecx(),m.piece.getDecy(), true);
                }
            }
        }


    }
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {
        cont.delVisu(x,y,m.piece.getMatrice(), m.piece.getDecx(),m.piece.getDecy());
    }

    public void mouseWheelMoved(MouseWheelEvent e){
        cont.delVisu(x,y,m.piece.getMatrice(), m.piece.getDecx(),m.piece.getDecy());
        if(e.getWheelRotation()>0){
            cont.rotaHorraire();
        }else{
            cont.antiHorraire();
        }
        if(cont.estPosable(m.piece, x,y,m.piece.getDecx(),m.piece.getDecy())){
            if(cont.estPosableRegle(m.piece,x,y,m.piece.getDecx(),m.piece.getDecy())){
                cont.visualiser(x,y,m.piece.getMatrice(), m.piece.getDecx(),m.piece.getDecy(), false);
            }else{
                cont.visualiser(x,y,m.piece.getMatrice(), m.piece.getDecx(),m.piece.getDecy(), true);
            }
        }
    }
    public void mouseReleased(MouseEvent e) {}






    @Override
    public void mousePressed(MouseEvent e) {
        int l = e.getY() / n.hauteurCase();
        int c = e.getX() / n.largeurCase();
        if(cont.estPosable(m.piece, l,c,m.piece.getDecx(),m.piece.getDecy())){
            if(cont.estPosableRegle(m.piece,l,c,m.piece.getDecx(),m.piece.getDecy())){
                cont.click(m.piece, l,c,m.piece.getDecx(),m.piece.getDecy());
            }
        }
    }
    @Override
    public void mouseMoved(MouseEvent e){

        int l = e.getY() / n.hauteurCase();
        int c = e.getX() / n.largeurCase();
        //On exécute la fonction que lorsqu'on change de case
        if(l!=x || c!=y){
            cont.delVisu(x,y,m.piece.getMatrice(), m.piece.getDecx(),m.piece.getDecy());
            if(cont.estPosable(m.piece, l,c,m.piece.getDecx(),m.piece.getDecy())){
                if(cont.estPosableRegle(m.piece,l,c,m.piece.getDecx(),m.piece.getDecy())){
                    cont.visualiser(l,c,m.piece.getMatrice(), m.piece.getDecx(),m.piece.getDecy(), false);
                }else{
                    cont.visualiser(l,c,m.piece.getMatrice(), m.piece.getDecx(),m.piece.getDecy(), true);
                }
            }
            x=l;
            y=c;

        }


    }

}


