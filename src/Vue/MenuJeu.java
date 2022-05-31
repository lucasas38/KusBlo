package Vue;

import Controleur.Controleur;
import Global.Configuration;
import Modele.ListePieces;
import Modele.Piece;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;

public class MenuJeu {
    JPanel menu;
    ImageKusBlo im;
    Bouton b;
    Controleur c;
    JPanel menuListePiece;
    JPanel[] listePiece;
    Piece piece;
    int numPiece;
    int joueur;
    int couleur;
    boolean pieceSelected;

    JPanel menuFinPartie;
    JPanel menuTourIA;
    JPanel menuHistorique;




    public MenuJeu(Controleur cont, Bouton bout, ImageKusBlo ima){
        menu= new JPanel(new BorderLayout());
        im=ima;
        c=cont;
        b=bout;
        creerMenuListePiece();
        creerMenuFinPartie();
        creerMenuTourIA();
        creerMenuHistorique();
        resize(c.getFrameW(),c.getFrameH());
    }


    //Création du menu de sélection de pièce
    public void creerMenuListePiece(){
        menuListePiece = new JPanel(new GridLayout(3,7,2,2));
        listePiece=new JPanel[21];
    }

    //Création du menu d'affichage de fin de partie
    public void creerMenuFinPartie(){
        menuFinPartie =new JPanel(new GridLayout(6,1));
        JPanel finPartie = new JPanel();
        finPartie.add(new JLabel("Fin de la partie"));
        menuFinPartie.add(finPartie);
    }

    //Création du menu IA
    public void creerMenuTourIA(){
        menuTourIA =new JPanel(new BorderLayout());
        JPanel tourIA= new JPanel();
        tourIA.add(new JLabel("Ce n'est pas a votre tour de jouer"));
        menuTourIA.add(tourIA);
    }

    //Création du menu de parcours d'historique
    public void creerMenuHistorique(){
        menuHistorique =new JPanel(new GridLayout(2,1));
        menuHistorique.add(new JLabel("Vous êtes en train de parcourir l'historique"));
        menuHistorique.add(new JLabel("Vous êtes au tour du joueur : "+c.getActCouleur()));
    }


    //Hauteur du menu complet
    public int getHautMenu(){
        return menuListePiece.getHeight()/3;
    }

    //Largeur du menu complet
    public int getLargMenu(){
        return menuListePiece.getWidth()/7;
    }

    public Piece getPiece() {
        return piece;
    }

    public int getNumPiece(){
        return numPiece;
    }


    //Affiche la liste des pièces d'un joueur
    public void setMenuListePiece(int joue, int c){
        joueur=joue;
        couleur= c;
        refreshAffichageListePiece();
        pieceSelected=false;
        menu.removeAll();
        menu.add(menuListePiece);
        menu.updateUI();
    }

    //Sélectionne la pièce
    public void setMenuPieceUnique(int p){
        numPiece=p+1;
        piece = c.getListPiece(couleur).getPiece(p+1);
    }



    //Menu de fin de partie
    public void setMenuFinPartie(int[] joueur, int nbVainqueur){
        resetMenuFinPartie();
        if(nbVainqueur==1){
            //On affiche le gagnant
            JPanel finPartie1=new JPanel();
            finPartie1.add(new JLabel("Victoire du joueur "+joueur[0]));
            menuFinPartie.add(finPartie1);
        }else{
            //Si on a une égalité, on affiche tous les vainqueurs
            JPanel finPartie2=new JPanel();
            finPartie2.add(new JLabel("Égalité !"));
            menuFinPartie.add(finPartie2);
            for(int i=0; i<nbVainqueur;i++){
                JPanel finPartie3= new JPanel();
                finPartie3.add(new JLabel("Victoire du joueur "+joueur[i]));
                menuFinPartie.add(finPartie3);
            }
        }
        menu.removeAll();
        menu.add(menuFinPartie);
        menu.updateUI();
    }

    //Menu pour indiquer que le joueur ne peut plus jouer
    public void setMenuTourIA(){
        menu.removeAll();
        menu.add(menuTourIA);
        menu.updateUI();
    }

    //Menu de pause et de parcours d'historique
    public void setMenuHistorique(){
        menuHistorique.removeAll();
        JPanel histo1= new JPanel();
        JPanel histo2 = new JPanel();
        histo1.add(new JLabel("Vous êtes en train de parcourir l'historique"));
        histo2.add(new JLabel("Vous êtes au tour du joueur : "+c.getActCouleur()));
        menuHistorique.add(histo1);
        menuHistorique.add(histo2);
        menu.removeAll();
        menu.add(menuHistorique);
        menu.updateUI();
    }



    //Utilisé par le menu liste piece, affiche la liste des pièces pour
    public void refreshAffichageListePiece() {
        menuListePiece.removeAll();
        int numPiece = 1;
        ListePieces liste = c.getListPiece(couleur); //On récupère la liste de la couleur actuelle
        Iterator<Piece> ite = liste.iterateur();
        Piece p;
        int indiceListe=0;
        boolean grise=false;
        //On parcourt la liste pour afficher les différentes pièces
        while (ite.hasNext()) {
            grise=false;
            p = ite.next();

            if(Boolean.parseBoolean(Configuration.instance().lis("AidePiecePosable"))){
                if(!c.estPiecePosable(p)){
                    grise=true;
                }
            }

            //Si des pièces ont déjà été joués on affiche une case vide à la place
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
                menuListePiece.add(affPiece);
                listePiece[indiceListe]=affPiece;
                numPiece++;
                indiceListe++;
            }

            //On affiche la pièce
            JPanel affPiece = new JPanel(new GridLayout(5, 5));
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    BasicBackgroundPanel newPan = new BasicBackgroundPanel(im.gris);
                    if (p.getMatrice()[i][j] == 0) {
                        newPan.changeBackground(im.gris);
                    } else {
                        if(grise){
                            newPan.changeBackground(im.animJoueur(5,0));
                        }else{
                            newPan.changeBackground(im.coulJoueur(couleur));
                        }

                    }
                    affPiece.add(newPan);
                }
            }
            affPiece.setBorder(BorderFactory.createLineBorder(Color.black));
            menuListePiece.add(affPiece);
            listePiece[indiceListe]=affPiece;
            numPiece++;
            indiceListe++;
        }

        //On finit de remplir avec des cases vides
        for (int k = numPiece; k < 22; k++) {
            JPanel affPiece = new JPanel(new GridLayout(5, 5));
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    BasicBackgroundPanel newPan = new BasicBackgroundPanel(im.gris);
                    newPan.changeBackground(im.gris);
                    affPiece.add(newPan);
                }
                }affPiece.setBorder(BorderFactory.createLineBorder(Color.black));
            menuListePiece.add(affPiece);
            listePiece[k-1]=affPiece;
        }

    }


    //Sélectionne une pièce du menu liste piece
    public void selPiece(int num){
        resetBorder();
        pieceSelected=true;
        listePiece[num-1].setBorder(BorderFactory.createLineBorder(Color.red,2));
    }

    //Enlève l'affichage de sélection pour la pièce précedente
    public void resetBorder(){
        for(int i=0;i<21;i++){
            listePiece[i].setBorder(BorderFactory.createLineBorder(Color.black));
        }
    }

    //Change la taille des différents menus
    public void resize(int w, int h){
        menuListePiece.setPreferredSize(new Dimension(w/2,h/4));
        menuHistorique.setPreferredSize(new Dimension(w/2,h/4));
        menuTourIA.setPreferredSize(new Dimension(w/2,h/4));
        menuFinPartie.setPreferredSize(new Dimension(w/2,h/4));
    }
    

    public void resetMenuFinPartie(){
        menuFinPartie.removeAll();
        menuFinPartie.add(new JLabel("Fin de la partie"));
    }
}
