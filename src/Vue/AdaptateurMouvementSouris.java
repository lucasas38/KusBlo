package Vue;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class AdaptateurMouvementSouris extends MouseMotionAdapter {
    VueNiveau n;
    MenuPiece m;
    int x;
    int y;


    AdaptateurMouvementSouris(VueNiveau niv, MenuPiece menu) {
        n = niv;
        m=menu;
        x=0;
        y=0;

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