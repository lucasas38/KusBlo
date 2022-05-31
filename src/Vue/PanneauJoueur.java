package Vue;

import Controleur.Controleur;
import Modele.ListePieces;
import Modele.Piece;

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
    JPanel lPiece;
    JLabel score;
    JLabel nomJoueur;
    JPanel[] listePiece;

    //Panneau pour la couleur couleur
    PanneauJoueur(int couleur, Controleur control, ImageKusBlo ima){
        this.couleur =couleur;
        cont=control;
        im=ima;
        affichageListe= new JPanel(new GridLayout(5,5));
        listePiece= new JPanel[25];
        pan = new JPanel(new BorderLayout());
        pan.setBackground(Color.pink);
        pan.setBorder(BorderFactory.createLineBorder(Color.black,2));
        int numJoueur;
        if(cont.getNbJoueur()==2 && couleur>2){
            numJoueur=couleur-2;
        }else{
            numJoueur=couleur;
        }
        nomJoueur=new JLabel("Joueur "+numJoueur);
        pan.add(nomJoueur, BorderLayout.PAGE_START);
        lPiece = new JPanel(new BorderLayout());
        int minDim = Math.min(cont.getFrameW()/4,cont.getFrameH()/4);
        lPiece.setPreferredSize(new Dimension(minDim,minDim));
        lPiece.add(affichageListe);
        pan.add(lPiece, BorderLayout.CENTER);
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
        for (int k = numPiece; k < 26; k++) {
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
        pan.updateUI();
    }

    public void setFinJouable(){
        votreTour.removeAll();
        votreTour.add(new JLabel("Ne peut plus jouer"));
        pan.updateUI();
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
        int minDim = Math.min(w/4,h/4);
        lPiece.setPreferredSize(new Dimension(minDim,minDim));
        infoBas.setPreferredSize(new Dimension(w/4, h/12));
    }

    public void setMenu1(){
        lPiece.removeAll();
        BasicBackgroundPanel tuto= new BasicBackgroundPanel(im.tuto);
        lPiece.add(tuto);
        lPiece.updateUI();
    }

    public void delMenu1(){
        lPiece.removeAll();
        lPiece.add(affichageListe);
        lPiece.updateUI();
    }

    public void updateNameIA(int difficulté){
        String diff = "";
        switch (difficulté){
            case 1:
                diff="facile)";
                break;
            case 2:
                diff="Intermédiaire)";
                break;
            case 3:
                diff="Intermédiaire ouvrante)";
                break;
            case 4:
                diff="Intermédiaire taille)";
                break;
            case 5:
                diff="Intermédiaire pattern)";
                break;
            case 6:
                diff="Difficile)";
                break;
        }
        int numJoueur;
        if(cont.getNbJoueur()==2 && couleur>2){
            numJoueur=couleur-2;
        }else{
            numJoueur=couleur;
        }
        nomJoueur.setText("Joueur "+numJoueur+" (IA "+diff);
    }



}
