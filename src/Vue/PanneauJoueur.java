package Vue;

import Controleur.Controleur;
import Modele.ListePieces;
import Modele.Piece;
import Structures.BasicBackgroundPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;

public class PanneauJoueur {
    JPanel pan ;
    int couleur;
    ImageKusBlo im;
    Controleur cont;
    JPanel affichageListe;
    JPanel[] listePiece;

    PanneauJoueur(int c, Controleur control){
        couleur=c;
        cont=control;
        listePiece= new JPanel[21];
        im=new ImageKusBlo();
        pan = new JPanel(new BorderLayout());
        pan.setBackground(Color.pink);
        pan.add(new JLabel("Joueur "+c%cont.getNbJoueur()), BorderLayout.PAGE_START);
        refreshListPiece();
        affichageListe.setPreferredSize(new Dimension(200, 10));
        pan.add(affichageListe, BorderLayout.CENTER);
    }

    public void refreshListPiece(){
        affichageListe= new JPanel(new GridLayout(3,7));
        int numPiece = 1;
        ListePieces liste = cont.getListPiece(couleur);
        Iterator<Piece> ite = liste.iterateur();
        Piece p = null;
        int indiceListe=0;
        while (ite.hasNext()) {
            p = ite.next();
            while(numPiece!=p.getId()){
                JPanel affPiece = new JPanel(new GridLayout(5, 5));
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 5; j++) {
                        BasicBackgroundPanel newPan = new BasicBackgroundPanel(im.gris);
                        newPan.changeBackground(im.gris);
                        affPiece.add(newPan);
                    }
                }
                affPiece.setBorder(BorderFactory.createLineBorder(Color.black));
                affichageListe.add(affPiece);
                listePiece[indiceListe]=affPiece;
                indiceListe++;
                numPiece++;
            }

            JPanel affPiece = new JPanel(new GridLayout(5, 5));
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    BasicBackgroundPanel newPan = new BasicBackgroundPanel(im.gris);
                    if (p.getMatrice()[i][j] == 0) {
                        newPan.changeBackground(im.gris);
                    } else {
                        newPan.changeBackground(im.coulJoueur(couleur));
                    }
                    //newPan.setBorder(BorderFactory.createLineBorder(Color.black));
                    affPiece.add(newPan);
                }
            }
            affPiece.setBorder(BorderFactory.createLineBorder(Color.black));
            affichageListe.add(affPiece);
            listePiece[indiceListe]=affPiece;
            indiceListe++;
            numPiece++;
        }

        for (int k = numPiece; k < 22; k++) {
            JPanel affPiece = new JPanel(new GridLayout(5, 5));
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    BasicBackgroundPanel newPan = new BasicBackgroundPanel(im.gris);
                    newPan.changeBackground(im.gris);
                    affPiece.add(newPan);
                }
            }affPiece.setBorder(BorderFactory.createLineBorder(Color.black));
            affichageListe.add(affPiece);
            listePiece[k-1]=affPiece;
        }
    }

    public void refreshAffichage(int piece){
        listePiece[piece-1].removeAll();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                BasicBackgroundPanel newPan = new BasicBackgroundPanel(im.gris);
                newPan.changeBackground(im.gris);
                //newPan.setBorder(BorderFactory.createLineBorder(Color.black));
                listePiece[piece-1].add(newPan);
            }
        }
        listePiece[piece-1].updateUI();
    }




}
