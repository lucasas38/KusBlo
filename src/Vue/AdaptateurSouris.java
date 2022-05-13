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
    boolean activ;


    AdaptateurSouris(VueNiveau niv, MenuPiece menu, Controleur c) {
        n = niv;
        m=menu;
        cont=c;
        x=0;
        y=0;
        activ=false;
    }


    public void mouseClicked(MouseEvent e) {
        if(activ){
            //Fonction de flip de la pièce
            if(SwingUtilities.isRightMouseButton(e)){
                cont.delVisu(x,y,m.piece.getMatrice(), m.piece.getDecx(),m.piece.getDecy());
                cont.flip();
                if(cont.estPosable(m.piece, x,y,m.piece.getDecx(),m.piece.getDecy())){
                    if(estPosableSelonRegle(x,y)){
                        cont.visualiser(x,y,m.piece.getMatrice(), m.piece.getDecx(),m.piece.getDecy(), false);
                    }else{
                        cont.visualiser(x,y,m.piece.getMatrice(), m.piece.getDecx(),m.piece.getDecy(), true);
                    }
                }
            } else{
                //Fonction pour poser la pièce
                int l = e.getY() / n.hauteurCase();
                int c = e.getX() / n.largeurCase();
                if(cont.estPosable(m.piece, l,c,m.piece.getDecx(),m.piece.getDecy())){
                    if(estPosableSelonRegle(l,c)){
                        cont.click(m.piece, l,c,m.piece.getDecx(),m.piece.getDecy());
                    }
                }
            }
        }
    }

    public void mouseExited(MouseEvent e) {
        if(activ){
            cont.delVisu(x,y,m.piece.getMatrice(), m.piece.getDecx(),m.piece.getDecy());
        }
    }

    public void mouseWheelMoved(MouseWheelEvent e){
        //Fonction de rotation de la pièce
        if(activ){
            cont.delVisu(x,y,m.piece.getMatrice(), m.piece.getDecx(),m.piece.getDecy());
            if(e.getWheelRotation()>0){
                cont.rotaHoraire();
            }else{
                cont.antiHoraire();
            }
            if(cont.estPosable(m.piece, x,y,m.piece.getDecx(),m.piece.getDecy())){
                if(estPosableSelonRegle(x,y)){
                    cont.visualiser(x,y,m.piece.getMatrice(), m.piece.getDecx(),m.piece.getDecy(), false);
                }else{
                    cont.visualiser(x,y,m.piece.getMatrice(), m.piece.getDecx(),m.piece.getDecy(), true);
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e){
        //Fonction de visualisation de la pièce
        if(activ){
            int l = e.getY() / n.hauteurCase();
            int c = e.getX() / n.largeurCase();
            //On exécute la fonction que lorsqu'on change de case
            if(l!=x || c!=y){
                cont.delVisu(x,y,m.piece.getMatrice(), m.piece.getDecx(),m.piece.getDecy());
                if(cont.estPosable(m.piece, l,c,m.piece.getDecx(),m.piece.getDecy())){
                    if(estPosableSelonRegle(l,c)){
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

    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseDragged(MouseEvent e) {}

    public void setActiv(boolean activ) {
        this.activ = activ;
    }
    public boolean estPosableSelonRegle(int l, int c){
        return cont.estPosableRegle(m.piece,l,c,m.piece.getDecx(),m.piece.getDecy()) && cont.estPosable2(m.piece,l,c,m.piece.getDecx(),m.piece.getDecy());
    }
}


