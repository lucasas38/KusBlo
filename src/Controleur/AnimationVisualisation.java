package Controleur;

import Structures.BasicBackgroundPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AnimationVisualisation {
    Timer timerAnimation;
    int numImage;
    boolean asc;

    public AnimationVisualisation(){
        asc= false;
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

    public  void resetTimer(){
        timerAnimation.stop();
        numImage=4;
        asc=false;
    }

    public  boolean hasTimer(){
        return timerAnimation !=null;
    }
}
