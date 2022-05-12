package Controleur;

import Structures.BasicBackgroundPanel;
import Structures.Case;
import Vue.ImageKusBlo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.LinkedList;

public class AnimationVisualisation {
    Timer timerAnimation;
    Timer timeAnimeIa;
    int numImage;
    boolean asc;
    Controleur c;

    public AnimationVisualisation(Controleur cont){
        asc= false;
        c=cont;
    }

    public void visualisation(BasicBackgroundPanel[][] listPanel,int x, int y, int[][] grille, Image[] imgs){
        numImage=4;
        timerAnimation =new Timer(150, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                visu(listPanel,x,y,grille,imgs);
            }
        });
        timerAnimation.start();
    }

    public void visu(BasicBackgroundPanel[][] listPanel,int x, int y, int[][] grille, Image[] imgs){
        for(int i=0;i<5;i++){
            for(int j=0; j<5; j++){
                if(grille[i][j]==1){
                    if(listPanel[i+x][j+y].estVide()){
                        listPanel[i+x][j+y].changeBackground(imgs[numImage]);
                        //listPanel[i+x][j+y].setBorder(BorderFactory.createLineBorder(Color.red));
                    }

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

    public  void resetTimerAnimation(){
        timerAnimation.stop();
        numImage=4;
        asc=false;
    }

    public  void resetTimerIa(){
        timeAnimeIa.stop();
    }

    public  boolean hasTimer(){
        return timerAnimation !=null;
    }
    public  boolean hasTimerIA(){
        return timeAnimeIa !=null;
    }

    public void visualisationIa(LinkedList<Case> listeCase,BasicBackgroundPanel[][] listPanel, int couleur){
        timeAnimeIa = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Iterator<Case>ite = listeCase.iterator();
                while(ite.hasNext()){
                    Case ca= ite.next();
                    listPanel[ca.getX()][ca.getY()].changeBackground(new ImageKusBlo().coulJoueur(couleur));
                    c.setMenu1();
                }
            }
        });
        Iterator<Case>ite = listeCase.iterator();
        while(ite.hasNext()){
            Case c= ite.next();
            listPanel[c.getX()][c.getY()].changeBackground(new ImageKusBlo().animJoueur(couleur, 0));
        }
        timeAnimeIa.start();
        c.setMenu4();
    }
}
