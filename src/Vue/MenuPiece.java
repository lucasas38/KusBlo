package Vue;

import Controleur.Controleur;
import Global.Configuration;
import Modele.ListePieces;
import Modele.Piece;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;

public class MenuPiece {
    JPanel menu;
    ImageKusBlo im;
    Bouton b;
    Controleur c;
    JPanel menuListePiece;
    JPanel[] listePiece;
    JPanel menuPieceUnique;
    Piece piece;
    BasicBackgroundPanel[][] pieceUnique;
    JPanel affichagePiece;
    int numPiece;
    int joueur;
    int couleur;
    boolean pieceSelected;

    JPanel menuFinPartie;
    JPanel menuTourIA;
    JPanel menuHistorique;
    JPanel menuPasserTour;





    public MenuPiece(Controleur cont, Bouton bout, ImageKusBlo ima){
        menu= new JPanel(new BorderLayout());
        im=ima;
        c=cont;
        b=bout;
        creerMenuListePiece();
        creerMenuPieceUnique();
        creerMenuFinPartie();
        creerMenuTourIA();
        creerMenuHistorique();
        resize(c.getFrameW(),c.getFrameH());
    }



    public void creerMenuListePiece(){
        menuListePiece = new JPanel(new GridLayout(3,7,2,2));
        listePiece=new JPanel[21];

    }

    public void creerMenuPieceUnique(){
        menuPieceUnique = new JPanel(new GridLayout(1,3));
        JPanel centre =new JPanel(new BorderLayout());
        pieceUnique = new BasicBackgroundPanel[5][5];

        //On créer le carré central
        affichagePiece=new JPanel(new GridLayout(5, 5));
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                BasicBackgroundPanel newPan = new BasicBackgroundPanel(im.coulJoueur(5));
                affichagePiece.add(newPan);
                pieceUnique[i][j]=newPan;
            }
        }
        //On ajoute les boutons latéraux
        //Retour a la liste des pièces
        JPanel boutGauche=new JPanel(new GridLayout(2,1,15,5));
        boutGauche.add(b.retourListePiece());
        boutGauche.add(b.skipTour());
        boutGauche.add(new JPanel());
        menuPieceUnique.add(boutGauche);

        //Affichage de la pièce et parcours gauche/droite
        centre.add(new JButton("<"),BorderLayout.WEST);
        centre.add(affichagePiece,BorderLayout.CENTER);
        centre.add(new JButton(">"),BorderLayout.EAST);
        menuPieceUnique.add(centre);

        //Rotation + Flip
        JPanel boutDroit=new JPanel(new GridLayout(2,2,15,5));
        boutDroit.add(b.rotaHoraire());
        boutDroit.add(b.rotaAntiHoraire());
        boutDroit.add(b.flip());
        menuPieceUnique.add(boutDroit);


    }

    public void creerMenuFinPartie(){
        menuFinPartie =new JPanel(new GridLayout(6,1));
        JPanel finPartie = new JPanel();
        finPartie.add(new JLabel("Fin de la partie"));
        menuFinPartie.add(finPartie);
    }
    public void creerMenuTourIA(){
        menuTourIA =new JPanel(new BorderLayout());
        JPanel tourIA= new JPanel();
        tourIA.add(new JLabel("Ce n'est pas a votre tour de jouer"));
        menuTourIA.add(tourIA);
    }
    public void creerMenuHistorique(){
        menuHistorique =new JPanel(new GridLayout(2,1));
        menuHistorique.add(new JLabel("Vous êtes en train de parcourir l'historique"));
        menuHistorique.add(new JLabel("Vous êtes au tour du joueur : "+c.getActCouleur()));
    }

    public void creerPasserTour(){
        menuTourIA =new JPanel(new BorderLayout());
        menuTourIA.add(new JLabel("Vous n'avez plus de pièce disponible"));
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


    public void setMenuListePiece(int joue, int c){
        joueur=joue;
        couleur= c;
        refreshAffichageListePiece();
        pieceSelected=false;
        menu.removeAll();
        menu.add(menuListePiece);
        menu.updateUI();
    }
    public void setMenuPieceUnique(int p){
        numPiece=p+1;
        piece = c.getListPiece(couleur).getPiece(p+1);
    }



    //Menu de fin de partie, Ver.1
    public void setMenuFinPartie(int[] joueur, int nbVainqueur){
        resetMenuFinPartie();
        if(nbVainqueur==1){
            JPanel finPartie1=new JPanel();
            finPartie1.add(new JLabel("Victoire du joueur "+joueur[0]));
            menuFinPartie.add(finPartie1);
        }else{
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

    public void setMenuPasserTour(){
        menu.removeAll();
        menu.add(menuPasserTour);
        menu.updateUI();
    }


    //Affiche le menu avec pièce unique
    public void showMenuPieceUnique(){
        refreshPiece(c.getActCouleur());
        menu.removeAll();
        menu.add(menuPieceUnique);
        menu.updateUI();
    }


    //Utilisé par le menu 1, affiche la liste des pièces pour
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

    //Affiche la pièce actuelle pour le menu 2
    public void refreshPiece(int couleur){
        for(int i=0; i<5;i++){
            for(int j=0; j<5;j++){
                if(piece.getMatrice()[i][j]==0){
                    pieceUnique[i][j].changeBackground(im.gris);
                }else{
                    pieceUnique[i][j].changeBackground(im.coulJoueur(couleur));
                }
            }
        }

    }


    //Sélectionne une pièce du menu 1
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

    //Change la taille des différents menu
    public void resize(int w, int h){
        menuListePiece.setPreferredSize(new Dimension(w/2,h/4));
        menuPieceUnique.setPreferredSize(new Dimension(w/2,h/4));
        menuFinPartie.setPreferredSize(new Dimension(w/2,h/4));
    }

    public boolean isPieceSelected(){
        return  pieceSelected;
    }

    public void resetMenuFinPartie(){
        menuFinPartie.removeAll();
        menuFinPartie.add(new JLabel("Fin de la partie"));
    }
}
