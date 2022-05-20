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
    JPanel infoBas;
    JPanel votreTour;
    JLabel score;
    JPanel[] listePiece;

    //Panneau pour la couleur couleur
    PanneauJoueur(int couleur, Controleur control, ImageKusBlo ima){
        this.couleur =couleur;
        cont=control;
        im=ima;
        affichageListe= new JPanel(new GridLayout(3,7));
        listePiece= new JPanel[21];
        pan = new JPanel(new BorderLayout());
        pan.setBackground(Color.pink);
        pan.setBorder(BorderFactory.createLineBorder(Color.black,2));
        int numJoueur;
        if(cont.getNbJoueur()==2 && couleur>2){
            numJoueur=couleur-2;
        }else{
            numJoueur=couleur;
        }
        pan.add(new JLabel("Joueur "+numJoueur), BorderLayout.PAGE_START);

        pan.add(affichageListe, BorderLayout.CENTER);
        infoBas= new JPanel(new GridLayout(1,2));
        votreTour= new JPanel();
        votreTour.add(new JLabel(""));
        score=new JLabel("Score : 0");
        JPanel tabScore=new JPanel();
        tabScore.add(score);
        tabScore.setBorder(BorderFactory.createLineBorder(Color.black));
        infoBas.add(votreTour);
        infoBas.add(tabScore);

        pan.add(infoBas,BorderLayout.SOUTH);
        refreshListPiece();
        resize(cont.getFrameW(),cont.getFrameH());
    }

    //Créer la liste des pièces pour la couleur, en fonction des pièces disponible
    public void refreshListPiece(){
        affichageListe.removeAll();
        int numPiece = 1;
        ListePieces liste = cont.getListPiece(couleur);
        Iterator<Piece> ite = liste.iterateur();
        Piece p;
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
    public void refreshAffichage(int piece, boolean undo, Piece p){
        if(undo){
            new PanelPiece().redoPanel(listePiece[piece-1],im,p,couleur);
        }else{
            new PanelPiece().videPanel(listePiece[piece-1],im);
        }
    }

    public void setTour(){
        votreTour.add(new JLabel("A votre tour"));
        switch (couleur){
            case 1:
                votreTour.setBackground(new Color(73,216,230));
                pan.setBorder(BorderFactory.createLineBorder(Color.blue,2));
                break;
            case 2:
                votreTour.setBackground(new Color(233,150,122));
                pan.setBorder(BorderFactory.createLineBorder(Color.red,2));
                break;
            case 3:
                votreTour.setBackground(new Color(255,215,0));
                pan.setBorder(BorderFactory.createLineBorder(Color.yellow,2));
                break;
            case 4:
                votreTour.setBackground(new Color(154,205,50));
                pan.setBorder(BorderFactory.createLineBorder(Color.green,2));
                break;
        }
    }

    public void delTour(){
        votreTour.removeAll();
        votreTour.setBackground(Color.white);
        pan.setBorder(BorderFactory.createLineBorder(Color.black,2));
    }

    public  void setScore(int val){
        score.setText("Score : "+val);
    }

    //Proportionne la fenêtre
    public void resize(int w, int h){
        affichageListe.setPreferredSize(new Dimension(w/4, h/4));
        infoBas.setPreferredSize(new Dimension(w/4, h/12));
    }



}
