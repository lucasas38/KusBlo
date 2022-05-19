package Vue;

import Controleur.Controleur;
import Modele.ListePieces;
import Modele.Piece;
import Structures.BasicBackgroundPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;

public class MenuPiece {
    JPanel menu;
    ImageKusBlo im;
    Bouton b;
    Controleur c;
    JPanel menuType1;
    JPanel[] listePiece;
    JPanel menuType2;
    Piece piece;
    BasicBackgroundPanel[][] pieceUnique;
    JPanel affichagePiece;
    int numPiece;
    int joueur;
    int couleur;
    boolean pieceSelected;

    JPanel menuType3;
    JPanel menuType4;
    JPanel menuType5;





    public MenuPiece(Controleur cont){
        menu= new JPanel(new BorderLayout());
        im=new ImageKusBlo();
        b=new Bouton(cont);
        c=cont;
        creerMenuType1();
        creerMenuType2();
        creerMenuType3();
        creerMenuType4();
        creerMenuType5();
        resize(c.getFrameW(),c.getFrameH());
    }



    public void creerMenuType1(){
        menuType1 = new JPanel(new GridLayout(3,7,2,2));
        listePiece=new JPanel[21];

    }

    public void creerMenuType2(){
        menuType2= new JPanel(new GridLayout(1,3));
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
        menuType2.add(boutGauche);

        //Affichage de la pièce et parcours gauche/droite
        centre.add(new JButton("<"),BorderLayout.WEST);
        centre.add(affichagePiece,BorderLayout.CENTER);
        centre.add(new JButton(">"),BorderLayout.EAST);
        menuType2.add(centre);

        //Rotation + Flip
        JPanel boutDroit=new JPanel(new GridLayout(2,2,15,5));
        boutDroit.add(b.rotaHoraire());
        boutDroit.add(b.rotaAntiHoraire());
        boutDroit.add(b.flip());
        menuType2.add(boutDroit);


    }

    public void creerMenuType3(){
        menuType3 =new JPanel(new GridLayout(6,1));
        menuType3.add(new JLabel("Fin de la partie"));
    }
    public void creerMenuType4(){
        menuType4 =new JPanel(new BorderLayout());
        menuType4.add(new JLabel("Ce n'est pas a votre tour de jouer"));
    }
    public void creerMenuType5(){
        menuType5 =new JPanel(new GridLayout(2,1));
        menuType5.add(new JLabel("Vous êtes en train de parcourir l'historique"));
        menuType5.add(new JLabel("Vous êtes au tour du joueur : "+c.getActCouleur()));
    }

    //Hauteur du menu complet
    public int getHautMenu(){
        return menuType1.getHeight()/3;
    }

    //Largeur du menu complet
    public int getLargMenu(){
        return menuType1.getWidth()/7;
    }

    public Piece getPiece() {
        return piece;
    }

    public int getNumPiece(){
        return numPiece;
    }


    public void setMenuType1(int joue, int c){
        joueur=joue;
        couleur= c;
        refreshAffichageListePiece();
        pieceSelected=false;
        menu.removeAll();
        menu.add(menuType1);
        menu.updateUI();
    }
    public void setMenuType2(int p){
        numPiece=p+1;
        piece = c.getListPiece(couleur).getPiece(p+1);
    }



    //Menu de fin de partie, Ver.1
    public void setMenuType3(int[] joueur, int nbVainqueur){
        resetMenu3();
        if(nbVainqueur==1){
            menuType3.add(new JLabel("Victoire du joueur "+joueur[0]));
        }else{
            menuType3.add(new JLabel("Égalité !"));
            for(int i=0; i<nbVainqueur;i++){
                menuType3.add(new JLabel("Victoire du joueur "+joueur[i]));
            }
        }

        menu.removeAll();
        menu.add(menuType3);
        menu.updateUI();
    }

    //Menu pour indiquer que le joueur ne peut plus jouer
    public void setMenuType4(){
        menu.removeAll();
        menu.add(menuType4);
        menu.updateUI();
    }
    public void setMenuType5(){
        menuType5.removeAll();
        menuType5.add(new JLabel("Vous êtes en train de parcourir l'historique"));
        menuType5.add(new JLabel("Vous êtes au tour du joueur : "+c.getActCouleur()));
        menu.removeAll();
        menu.add(menuType5);
        menu.updateUI();
    }


    //Affiche le menu avec pièce unique
    public void showMenuType2(){
        refreshPiece(c.getActCouleur());
        menu.removeAll();
        menu.add(menuType2);
        menu.updateUI();
    }


    //Utilisé par le menu 1, affiche la liste des pièces pour
    public void refreshAffichageListePiece() {
        menuType1.removeAll();
        int numPiece = 1;
        ListePieces liste = c.getListPiece(couleur); //On récupère la liste de la couleur actuelle
        Iterator<Piece> ite = liste.iterateur();
        Piece p;
        int indiceListe=0;
        //On parcourt la liste pour afficher les différentes pièces
        while (ite.hasNext()) {
            p = ite.next();
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
                menuType1.add(affPiece);
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
                        newPan.changeBackground(im.coulJoueur(couleur));
                    }
                    affPiece.add(newPan);
                }
            }
            affPiece.setBorder(BorderFactory.createLineBorder(Color.black));
            menuType1.add(affPiece);
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
            menuType1.add(affPiece);
            listePiece[k-1]=affPiece;
        }

    }

    //Affiche la pièce actuelle pour le menu 2
    public void refreshPiece(int couleur){
        for(int i=0; i<5;i++){
            for(int j=0; j<5;j++){
                if(i== piece.getDecx() & j==piece.getDecy()){
                    pieceUnique[i][j].changeBackground(im.selCouleur(couleur));
                }else {
                    if(piece.getMatrice()[i][j]==0){
                        pieceUnique[i][j].changeBackground(im.gris);
                    }else{
                        pieceUnique[i][j].changeBackground(im.coulJoueur(couleur));
                    }
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
        menuType1.setPreferredSize(new Dimension(w/2,h/4));
        menuType2.setPreferredSize(new Dimension(w/2,h/4));
        menuType3.setPreferredSize(new Dimension(w/2,h/4));
    }

    public boolean isPieceSelected(){
        return  pieceSelected;
    }

    public void resetMenu3(){
        menuType3.removeAll();
        menuType3.add(new JLabel("Fin de la partie"));
    }
}
