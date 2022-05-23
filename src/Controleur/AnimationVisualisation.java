package Controleur;

import Modele.Piece;
import Structures.BasicBackgroundPanel;
import Structures.Case;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class AnimationVisualisation {
    Timer timerAnimation;
    Timer timeAnimeIa;
    Timer timeAnimeAide;
    int numImage;
    boolean asc; //indique l'ordre de sélection des images de clignotement
    Controleur c;

    public AnimationVisualisation(Controleur cont){
        asc= false;
        c=cont;
    }

    //Créer et lance de le timer d'animation
    public void visualisationClignotement(BasicBackgroundPanel[][] listPanel, int x, int y, int[][] grille, Image[] imgs){
        numImage=4;
        timerAnimation =new Timer(150, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                visuCligonetement(listPanel,x,y,grille,imgs);
            }
        });
        timerAnimation.start();
    }

    //On change l'image pour faire un effet de clignotement
    public void visuCligonetement(BasicBackgroundPanel[][] listPanel, int x, int y, int[][] grille, Image[] imgs){
        for(int i=0;i<5;i++){
            for(int j=0; j<5; j++){
                if(grille[i][j]==1){
                    listPanel[i+x][j+y].changeBackground(imgs[numImage]);
                }
            }
        }
        if(asc){
            numImage++;
            if(numImage==4){
                asc=false;
            }
        }else {
            numImage--;
            if(numImage==0){
                asc=true;
            }
        }
    }

    //Stop de le timer et le reset à son état d'origine
    public  void resetTimerAnimation(){
        timerAnimation.stop();
        numImage=4;
        asc=false;
    }

    //Stop le timer IA
    public  void resetTimerIa(){
        timeAnimeIa.stop();
    }
    public  void startTimeIA(){
        timeAnimeIa.start();
    }

    public  boolean hasTimer(){
        return timerAnimation !=null;
    }
    public  boolean hasTimerIA(){
        return timeAnimeIa !=null;
    }


    //Fonction de visualisation du time IA
    public void visualisationIa(LinkedList<Case> listeCase, int couleur){
        //Timer qui pose la pièce après un delai
        timeAnimeIa = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.poserPiece(listeCase,couleur);
                c.joueIA2();
            }
        });
        //On affiche la visualisation
        c.visuIA(listeCase,couleur);
        timeAnimeIa.start();
        c.setMenu4();
    }

    public void visualisationAide(LinkedList<Case> listeCase){
        //Timer qui pose la pièce après un delai
        timeAnimeAide = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.supprVisAide(listeCase);
            }
        });
        //On affiche la visualisation
        timeAnimeAide.start();
    }

    public  boolean hasTimerAide(){
        return timeAnimeAide !=null;
    }

    public  void resetTimerAide(){
        if(hasTimerAide())
        timeAnimeAide.stop();
    }
    public  void startTimeAide(){
        if(hasTimerAide())
        timeAnimeAide.start();
    }
}
