package Vue;

import Controleur.Controleur;
import Modele.ListePieces;
import Modele.Piece;
import Structures.PanelPiece;

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

    //Panneau pour la couleur c
    PanneauJoueur(int c, Controleur control){
        couleur=c;
        cont=control;
        im=new ImageKusBlo();
        affichageListe= new JPanel(new GridLayout(3,7));
        listePiece= new JPanel[21];
        pan = new JPanel(new BorderLayout());
        pan.setBackground(Color.pink);
        int numJoueur=0;
        if(cont.getNbJoueur()==2 && c>2){
            numJoueur=c-2;
        }else{
            numJoueur=c;
        }
        pan.add(new JLabel("Joueur "+numJoueur), BorderLayout.PAGE_START);
        refreshListPiece();
        resize(cont.getFrameW(),cont.getFrameH());
        pan.add(affichageListe, BorderLayout.CENTER);
    }

    //Créer la liste des pièces pour la couleur, en fonction des pièces disponible
    public void refreshListPiece(){
        affichageListe.removeAll();
        int numPiece = 1;
        ListePieces liste = cont.getListPiece(couleur);
        Iterator<Piece> ite = liste.iterateur();
        Piece p = null;
        int indiceListe=0;
        //Si des pièces on déjà été joués on affiche une case vide à la place
        while (ite.hasNext()) {
            p = ite.next();
            while(numPiece!=p.getId()){
                JPanel affPiece = new PanelPiece().newPanelPieceVide(im);
                affPiece.setBorder(BorderFactory.createLineBorder(Color.black));
                affichageListe.add(affPiece);
                listePiece[indiceListe]=affPiece;
                indiceListe++;
                numPiece++;
            }

            JPanel affPiece = new PanelPiece().newPanelPiece(p,couleur,im);
            affPiece.setBorder(BorderFactory.createLineBorder(Color.black));
            affichageListe.add(affPiece);
            listePiece[indiceListe]=affPiece;
            indiceListe++;
            numPiece++;
        }
        //On finit de remplir avec des cases
        // vides
        for (int k = numPiece; k < 22; k++) {
            JPanel affPiece = new PanelPiece().newPanelPieceVide(im);
            affPiece.setBorder(BorderFactory.createLineBorder(Color.black));
            affichageListe.add(affPiece);
            listePiece[k-1]=affPiece;
        }
    }


    //Vide la case de la pièce jouée
    public void refreshAffichage(int piece){
        new PanelPiece().videPanel(listePiece[piece-1],im);
    }

    //Proportionne la fenêtre
    public void resize(int w, int h){
        affichageListe.setPreferredSize(new Dimension(w/4, h/4));
    }



}
