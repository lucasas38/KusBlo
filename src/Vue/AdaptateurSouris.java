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
        cont.delVisu(x,y,m.piece.getMatrice(), m.piece.getDecx(),m.piece.getDecy());
    }
    public void mouseReleased(MouseEvent e) {}






    @Override
    public void mousePressed(MouseEvent e) {
        int l = e.getY() / n.hauteurCase();
        int c = e.getX() / n.largeurCase();
        System.out.println("clic : ["+l+","+c+"]");
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


