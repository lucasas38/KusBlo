package Vue;

import Controleur.Controleur;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AdaptateurClavier extends KeyAdapter {
    Controleur control;
    AdaptateurSouris adapt;
    MenuPiece m;
    Boolean activ;

    AdaptateurClavier(Controleur c, AdaptateurSouris ad, MenuPiece menu) {
        control = c;
        adapt=ad;
        m=menu;
        activ=false;
    }

    @Override
    public void keyPressed(KeyEvent event) {
        if(activ){
            switch (event.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    control.delVisu(adapt.x,adapt.y,m.piece.getMatrice(), m.piece.getDecx(),m.piece.getDecy());
                    control.toucheClavier("Left");
                    visu();
                    break;
                case KeyEvent.VK_RIGHT:
                    control.delVisu(adapt.x,adapt.y,m.piece.getMatrice(), m.piece.getDecx(),m.piece.getDecy());
                    control.toucheClavier("Right");
                    visu();
                    break;
                case KeyEvent.VK_UP:
                    control.delVisu(adapt.x,adapt.y,m.piece.getMatrice(), m.piece.getDecx(),m.piece.getDecy());
                    control.toucheClavier("Up");
                    visu();
                    break;
                case KeyEvent.VK_DOWN:
                    control.delVisu(adapt.x, adapt.y, m.piece.getMatrice(), m.piece.getDecx(),m.piece.getDecy());
                    control.toucheClavier("Down");
                    visu();
                    break;
                case KeyEvent.VK_Q:
                case KeyEvent.VK_A:
                    control.toucheClavier("Quit");
                    break;

            }
        }
    }

    public void visu(){
        if(control.estPosable(m.piece, adapt.x,adapt.y,m.piece.getDecx(),m.piece.getDecy())){
            if(control.estPosableRegle(m.piece,adapt.x,adapt.y,m.piece.getDecx(),m.piece.getDecy())){
                control.visualiser(adapt.x,adapt.y,m.piece.getMatrice(), m.piece.getDecx(),m.piece.getDecy(), false);
            }else{
                control.visualiser(adapt.x,adapt.y,m.piece.getMatrice(), m.piece.getDecx(),m.piece.getDecy(), true);
            }
        }

    }

    public void setActiv(Boolean activ) {
        this.activ = activ;
    }
}
