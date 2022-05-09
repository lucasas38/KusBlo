package Vue;

import Controleur.AnimationVisualisation;
import Structures.BasicBackgroundPanel;

import javax.swing.*;
import java.awt.*;

public class VueNiveau {
    JPanel panelJeu;
    BasicBackgroundPanel [][] listPanel;
    ImageKusBlo im;
    AnimationVisualisation anim;

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
        anim= new AnimationVisualisation();
        //Création de la grille
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

    //Pas utilisé
    public void updatePan(int i, int j){
        listPanel[i][j].changeBackground(im.rouge);
    }

    //Pose la pièce (dans la mesure du possible)
    public void poserPiece(int joueur,int x, int y, int[][] grille, int decx, int decy){
        for(int i=0;i<5;i++){
            for(int j=0; j<5; j++){
                if(x+i-decx<20 && y+j-decy<20 && x+i-decx>=0 && y+j-decy>=0){
                    if(grille[i][j]!=0){
                        listPanel[i+x-decx][j+y-decy].changeBackground(im.coulJoueur(joueur));
                        listPanel[i+x-decx][j+y-decy].setBorder(BorderFactory.createLineBorder(Color.black));
                        listPanel[i+x-decx][j+y-decy].setVide(false);
                    }
                }
            }
        }
    }

    //Visualisation de la pièce(à rajouter la condition du estPosable)
    public void visualiser(int joue,int x, int y, int[][] grille, int decx, int decy ){
        if(joue==2){
            anim.visualisation(listPanel,x-decx,y-decy,grille, im.selAnimRouge);
        } else {
            for(int i=0;i<5;i++){
                for(int j=0; j<5; j++){
                    if(grille[i][j]==1){
                        if(listPanel[i+x-decx][j+y-decy].estVide()){
                            listPanel[i+x-decx][j+y-decy].changeBackground(im.animJoueur(joue));
                            //listPanel[i+x-decx][j+y-decy].setBorder(BorderFactory.createLineBorder(Color.red));
                        }

                    }
                }
            }
        }

    }

    //Supprime la visualisation
    public void supprimerVisualisation(int x, int y, int[][] grille, int decx, int decy){
        for(int i=0;i<5;i++){
            for(int j=0; j<5; j++){
                if(x+i-decx<20 && y+j-decy<20 && x+i-decx>=0 && y+j-decy>=0){
                    if(grille[i][j]!=0){
                        if(listPanel[i+x-decx][j+y-decy].estVide()){
                            listPanel[i+x-decx][j+y-decy].changeBackground(im.gris);
                        }

                        listPanel[i+x-decx][j+y-decy].setBorder(BorderFactory.createLineBorder(Color.black));
                    }
                }
            }
        }
        if(anim.hasTimer()){
            anim.resetTimer();
        }

    }


}
