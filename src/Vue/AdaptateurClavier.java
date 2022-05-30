package Vue;

import Controleur.Controleur;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

//NON FONCTIONNEL
public class AdaptateurClavier extends KeyAdapter {
    Controleur control;
    Boolean activ;

    AdaptateurClavier(Controleur c) {
        control = c;
        activ=false;
    }

    @Override
    public void keyPressed(KeyEvent event) {
        System.out.println(event.getKeyCode());
        if(activ){
            switch (event.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    control.delVisu(control.getOldX(),control.getOldY());
                    control.toucheClavier("Left");
                    visu();
                    break;
                case KeyEvent.VK_RIGHT:
                    control.delVisu(control.getOldX(),control.getOldY());
                    control.toucheClavier("Right");
                    visu();
                    break;
                case KeyEvent.VK_UP:
                    control.delVisu(control.getOldX(),control.getOldY());
                    control.toucheClavier("Up");
                    visu();
                    break;
                case KeyEvent.VK_DOWN:
                    control.delVisu(control.getOldX(), control.getOldY());
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
        if(control.estPosable(control.getOldX(),control.getOldY())){
            control.visualiser(control.getOldX(),control.getOldY(), !estPosableSelonRegle(control.getOldX(),control.getOldY()));
        }

    }

    public void setActiv(Boolean activ) {
        this.activ = activ;
    }

    public boolean estPosableSelonRegle(int l, int c){
        return control.estPosableRegle(l,c) && control.estPosable2(l,c);
    }
}
