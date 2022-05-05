package Vue;

import Structures.BasicBackgroundPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class MenuPiece {
    JPanel menu;
    BasicBackgroundPanel[][] listePiece;
    ImageKusBlo im;
    JPanel menuType2;
    int [][] grillePiece;


    public MenuPiece(){
        menu= new JPanel(new BorderLayout());
        im=new ImageKusBlo();
        menuType2= new JPanel(new GridLayout(1,3));
        JPanel affichagePiece = new JPanel(new GridLayout(5,5));
        listePiece=new BasicBackgroundPanel[5][5];
        for(int i=0; i<5;i++){
            for(int j=0; j<5;j++){
                BasicBackgroundPanel newPan= new BasicBackgroundPanel(im.gris);
                newPan.setBorder(BorderFactory.createLineBorder(Color.black));
                affichagePiece.add(newPan);
                listePiece[i][j]=newPan;
            }
        }
        menuType2.add(new JPanel());
        menuType2.add(affichagePiece);
        menuType2.add(new JPanel());
    }

    public void setMenuType1(/*int joueur*/){

    }

    public void setMenuType2(/*int joueur, int piece*/){
        grillePiece= new int[5][5];
        for(int i=0; i<5;i++){
            for(int j=0; j<5;j++){
                Random rand = new Random();
                grillePiece[i][j]=rand.nextInt(2);
            }
        }
        for(int i=0; i<5;i++){
            for(int j=0; j<5;j++){
                if(grillePiece[i][j]==0){
                    listePiece[i][j].changeBackground(im.gris);
                }else{
                    listePiece[i][j].changeBackground(im.choco);
                }
            }
        }
        menu.removeAll();
        menu.add(menuType2);
        menu.updateUI();

    }

    public void setMenuType3(){
        menu.removeAll();
        JPanel info =new JPanel();
        info.add(new JLabel("Ce n'est pas a votre tour de jouer"));
        menu.add(info);

    }
    public int[][] getGrillePiece(){
        return grillePiece;
    }
}
