package Structures;

import Modele.Piece;
import Vue.ImageKusBlo;

import javax.swing.*;
import java.awt.*;

public class PanelPiece {


    public JPanel newPanelPiece(Piece p, int couleur,ImageKusBlo im){
        JPanel affPiece = new JPanel(new GridLayout(5, 5));
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                BasicBackgroundPanel newPan = new BasicBackgroundPanel(im.coulJoueur(5));
                if (p.getMatrice()[i][j] == 0) {
                    newPan.changeBackground(im.coulJoueur(5));
                } else {
                    newPan.changeBackground(im.coulJoueur(couleur));
                }
                affPiece.add(newPan);
            }
        }
        return affPiece;
    }

    public JPanel newPanelPieceVide(ImageKusBlo im){
        JPanel affPiece = new JPanel(new GridLayout(5, 5));
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                BasicBackgroundPanel newPan = new BasicBackgroundPanel(im.coulJoueur(5));
                affPiece.add(newPan);
            }
        }
        return affPiece;
    }

    public void refreshPanel(JPanel pan, Piece p){

    }

    public void videPanel(JPanel pan, ImageKusBlo im){
        pan.removeAll();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                BasicBackgroundPanel newPan = new BasicBackgroundPanel(im.coulJoueur(5));
                pan.add(newPan);
            }
        }
        pan.updateUI();
    }

    public void redoPanel(JPanel pan, ImageKusBlo im, Piece p, int couleur){
        pan.removeAll();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                BasicBackgroundPanel newPan = new BasicBackgroundPanel(im.coulJoueur(5));
                if (p.getMatrice()[i][j] == 0) {
                    newPan.changeBackground(im.coulJoueur(5));
                } else {
                    newPan.changeBackground(im.coulJoueur(couleur));
                }
                pan.add(newPan);
            }
        }
    }
}
