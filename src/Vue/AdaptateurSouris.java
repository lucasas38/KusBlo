package Vue;

import Controleur.Controleur;

import javax.swing.*;
import java.awt.event.*;

public class AdaptateurSouris implements MouseListener, MouseMotionListener, MouseWheelListener {
    Controleur cont;
    boolean activ;


    AdaptateurSouris(Controleur c) {
        cont=c;
        activ=false;
        c.setOldX(10);
        c.setOldY(10);
    }


    public void mouseClicked(MouseEvent e) {
        if(activ){
            //Fonction de flip de la pièce
            if(SwingUtilities.isRightMouseButton(e)){
                cont.delVisu(cont.getOldX(),cont.getOldY());
                cont.flip();
                if(cont.estPosable(cont.getOldX(),cont.getOldY())){
                    cont.visualiser(cont.getOldX(),cont.getOldY(), !estPosableSelonRegle(cont.getOldX(), cont.getOldY()));
                }
            } else{
                //Fonction pour poser la pièce
                int l = e.getY() / cont.getHautCaseGrille();
                int c = e.getX() / cont.getLargeCaseGrille();
                if(cont.estPosable(l,c)){
                    if(estPosableSelonRegle(l,c)){
                        cont.click(l,c);
                    }
                }
            }
        }
    }

    public void mouseExited(MouseEvent e) {
        if(activ){
            cont.delVisu(cont.getOldX(),cont.getOldY());
        }
    }

    public void mouseWheelMoved(MouseWheelEvent e){
        //Fonction de rotation de la pièce
        if(activ){
            cont.delVisu(cont.getOldX(),cont.getOldY());
            if(e.getWheelRotation()>0){
                cont.rotaHoraire();
            }else{
                cont.antiHoraire();
            }
            if(cont.estPosable(cont.getOldX(),cont.getOldY())){
                cont.visualiser(cont.getOldX(),cont.getOldY(), !estPosableSelonRegle(cont.getOldX(), cont.getOldY()));
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
            int l = e.getY() / cont.getHautCaseGrille();
            int c = e.getX() / cont.getLargeCaseGrille();
            //On exécute la fonction que lorsqu'on change de case
            if(l!=cont.getOldX() || c!=cont.getOldY()){
                cont.delVisu(cont.getOldX(),cont.getOldY());
                if(cont.estPosable(l,c )){
                    cont.visualiser(l,c, !estPosableSelonRegle(l, c));
                }
                cont.setOldX(l);
                cont.setOldY(c);
            }
        }

    }

    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {
        if(activ){
            cont.stopTimerAide();
            if(cont.dernierCoupAide()!=null)
                cont.supprVisAide(cont.dernierCoupAide().getListe());
        }

    }
    public void mouseDragged(MouseEvent e) {}

    public void setActiv(boolean activ) {
        this.activ = activ;
    }
    public boolean estPosableSelonRegle(int l, int c){
        return cont.estPosableRegle(l,c) && cont.estPosable2(l,c);
    }
}


