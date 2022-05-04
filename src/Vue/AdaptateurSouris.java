package Vue;

import Controleur.Controleur;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdaptateurSouris extends MouseAdapter {
    VueNiveau n;


    AdaptateurSouris(VueNiveau niv) {
        n = niv;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int l = e.getY() / n.hauteurCase();
        int c = e.getX() / n.largeurCase();
            n.updatePan(l,c);



    }
}
