package Vue;

import Controleur.AnimationVisualisation;
import Modele.Piece;
import Structures.Case;
import Controleur.Controleur;
import Structures.BasicBackgroundPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.LinkedList;

public class VueNiveau {
    JPanel panelJeu;
    BasicBackgroundPanel [][] listPanel;
    ImageKusBlo im;
    AnimationVisualisation anim;
    Controleur c;

    public JPanel getPanelJeu() {
        return panelJeu;
    }

    public JPanel[][] getListPanel() {
        return listPanel;
    }

    VueNiveau(Controleur cont, ImageKusBlo ima) {
        c= cont;
        panelJeu = new JPanel();
        im = ima;
        panelJeu.setLayout(new GridLayout(20, 20));

        anim= new AnimationVisualisation(c);
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
        resize(c.getFrameW(),c.getFrameH()); //Formate l'affichage de la grille

    }
    //Renvoi la hauteur d'une case de façon dynamique
    public int hauteurCase(){
        return panelJeu.getHeight()/20;
    }
    //Renvoi la largeur d'une case de façon dynamique
    public int largeurCase(){
        return  panelJeu.getWidth()/20;
    }


    //Pose la pièce dans la grille avec la couleur correspondante
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

    //Pose la pièce dans la grille à partir du liste de case
    public void poserPiece(int joueur,LinkedList<Case> listeCases){
        Iterator<Case> it = listeCases.iterator();
        Case ca;
        int x,y;
        while(it.hasNext()){
            ca = it.next();
            x = ca.getX();
            y=ca.getY();
            listPanel[x][y].changeBackground(im.coulJoueur(joueur));
            listPanel[x][y].setBorder(BorderFactory.createLineBorder(Color.black));
            listPanel[x][y].setVide(false);
        }
    }


    //Visualisation de la pièce en fonction de la couleur du fond
    public void visualiser(int joue,int x, int y, int[][] grille, int decx, int decy ){
        if(joue==2){
            anim.visualisationClignotement(listPanel,x-decx,y-decy,grille, im.selAnimRouge);
        } else {
            for(int i=0;i<5;i++){
                for(int j=0; j<5; j++){
                    if(grille[i][j]!=0){
                        //On récupère la couleur du fond pour ne pas le supprimer
                        int cFond = im.imToInt(listPanel[i+x-decx][j+y-decy].getBackgroundImage());
                        listPanel[i+x-decx][j+y-decy].changeBackground(im.animJoueur(joue, cFond));
                    }
                }
            }
        }
    }

    //Visualisation à partir d'une liste de case
    public void visualiser(int couleur,LinkedList<Case> listeCase){
        Iterator<Case>ite = listeCase.iterator();
        while(ite.hasNext()){
            Case ca= ite.next();
            listPanel[ca.getX()][ca.getY()].changeBackground(new ImageKusBlo().animJoueur(couleur, 0));
        }
    }

    //Supprime la visualisation
    public void supprimerVisualisation(int x, int y, int[][] grille, int decx, int decy){
        for(int i=0;i<5;i++){
            for(int j=0; j<5; j++){
                if(x+i-decx<20 && y+j-decy<20 && x+i-decx>=0 && y+j-decy>=0){
                    if(grille[i][j]!=0){
                        //On récupère le fond de visualisation pour remettre la bonne couleur
                        int cFond = im.imToInt(listPanel[i+x-decx][j+y-decy].getBackgroundImage());
                        listPanel[i+x-decx][j+y-decy].changeBackground(im.coulJoueur(cFond));
                        listPanel[i+x-decx][j+y-decy].setBorder(BorderFactory.createLineBorder(Color.black));
                    }
                }
            }
        }

        //On supprime le time d'animation pour la visualisation rouge
        if(anim.hasTimer()){
            anim.resetTimerAnimation();
        }
    }

    //redimensionne la taille de la grille
    public void resize(int w, int h){
        int minDim = Math.min(w/2,3*h/4);
        panelJeu.setPreferredSize(new Dimension(minDim,minDim));
    }


    //Fonction de visualisation pour l'animation de l'IA
    public void poserPieceIA(Piece piece, LinkedList<Case> listeCase, int couleur){
        anim.visualisationIa(piece,listeCase,couleur);
    }


    //Arrête de le timer pour la visualisation de l'IA
    public void stopTimer(){
        if(anim.hasTimerIA()){
            anim.resetTimerIa();
        }
    }


}
