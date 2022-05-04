package Vue;

import Structures.BasicBackgroundPanel;

import javax.swing.*;
import java.awt.*;

public class VueNiveau {
    JPanel panelJeu;
    BasicBackgroundPanel [][] listPanel;
    ImageKusBlo im;

    public JPanel getPanelJeu() {
        return panelJeu;
    }

    public JPanel[][] getListPanel() {
        return listPanel;
    }

    VueNiveau() {
        panelJeu = new JPanel();
        im = new ImageKusBlo();
        panelJeu.setLayout(new GridLayout(20, 20));
        listPanel = new BasicBackgroundPanel[20][20];
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                BasicBackgroundPanel newPan = new BasicBackgroundPanel(im.gris);

                newPan.setBorder(BorderFactory.createLineBorder(Color.black));
                newPan.setBackground(Color.lightGray);
                listPanel[i][j] = newPan;
                panelJeu.add(newPan);
            }
        }panelJeu.setBorder(BorderFactory.createLineBorder(Color.BLUE));
    }
    //Renvoi la hauteur d'une case de façon dynamique
    public int hauteurCase(){
        return panelJeu.getHeight()/20;
    }
    //Renvoi la largeur d'une case de façon dynamique
    public int largeurCase(){
        return  panelJeu.getWidth()/20;
    }


    public void updatePan(int i, int j){
        listPanel[i][j].changeBackground(im.choco);
    }
}
