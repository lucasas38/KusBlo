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
    BasicBackgroundPanel[][] listePiece;
    ImageKusBlo im;
    Bouton b;
    Controleur c;
    Piece piece;
    int numPiece;
    JPanel menuType1;
    JPanel menuType2;
    JPanel menuType3;
    JPanel affichagePiece;
    int joueur;
    int couleur;
    //int [][] grillePiece;



    public MenuPiece(Controleur cont){
        menu= new JPanel(new BorderLayout());
        im=new ImageKusBlo();
        b=new Bouton(cont);
        c=cont;
        creerMenuType1();
        creerMenuType2();
        creerMenuType3();
        //Création de la grille, à importer depuis la piece
        piece = c.getPiece(1,3);
    }



    public void creerMenuType1(){
        menuType1 = new JPanel(new GridLayout(3,7,2,2));
    }

    public void creerMenuType2(){
        menuType2= new JPanel(new GridLayout(1,3));
        JPanel centre =new JPanel(new BorderLayout());
        affichagePiece = new JPanel(new GridLayout(5,5));

        //On créer le carré central
        listePiece=new BasicBackgroundPanel[5][5];
        for(int i=0; i<5;i++){
            for(int j=0; j<5;j++){
                BasicBackgroundPanel newPan= new BasicBackgroundPanel(im.blanc);
                affichagePiece.add(newPan);
                listePiece[i][j]=newPan;
            }
        }

        //On ajoute les boutons latéraux
        //Retour a la liste des pièces
        JPanel boutGauche=new JPanel(new GridLayout(2,1,15,5));
        boutGauche.add(b.retourListePiece());
        boutGauche.add(new JPanel());
        menuType2.add(boutGauche);

        //Affichage de la pièce et parcours gauche/droite
        centre.add(new JButton("<"),BorderLayout.WEST);
        centre.add(affichagePiece,BorderLayout.CENTER);
        centre.add(new JButton(">"),BorderLayout.EAST);
        menuType2.add(centre);

        //Rotation + Flip
        JPanel boutDroit=new JPanel(new GridLayout(2,2,15,5));
        boutDroit.add(b.rotaHorraire());
        menuType2.add(boutDroit);


    }

    public void creerMenuType3(){
        menuType3 =new JPanel(new BorderLayout());
        menuType3.add(new JLabel("Ce n'est pas a votre tour de jouer"));
    }

    public void setMenuType1(int joue, int c){
        joueur=joue;
        couleur= c;
        refreshAffichageListePiece();

        menu.removeAll();
        menu.add(menuType1);
        menu.updateUI();
    }
    public void setMenuType2(int p){
        numPiece=p+1;
        piece = c.getListPiece(joueur).getPiece(p+1);
        //Affichage de la piece
        refreshPiece();

        //Affichage du menu
        menu.removeAll();
        menu.add(menuType2);
        menu.updateUI();

    }


    //Utilisé contre l'ia
    public void setMenuType3(){
        menu.removeAll();
        menu.add(menuType3);
        menu.updateUI();
    }




    //Hauteur de la piece
    public int getHautAffPiece(){
        return  affichagePiece.getHeight()/5;
    }
    //Largeur de la piece
    public int getLargAffPiece(){
        return  affichagePiece.getWidth()/5;
    }


    public void refreshAffichageListePiece() {
        menuType1.removeAll();
        int numPiece = 1;
        ListePieces liste = c.getListPiece(joueur);
        Iterator<Piece> ite = liste.iterateur();
        Piece p = null;

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
                affPiece.setBorder(BorderFactory.createLineBorder(Color.red));
                menuType1.add(affPiece);
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
            affPiece.setBorder(BorderFactory.createLineBorder(Color.red));
            menuType1.add(affPiece);

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
                }affPiece.setBorder(BorderFactory.createLineBorder(Color.red));
            menuType1.add(affPiece);
        }

    }

    //Affiche la pièce actuelle
    public void refreshPiece(){
        //refreshCaseSelec();
        for(int i=0; i<5;i++){
            for(int j=0; j<5;j++){
                if(i== piece.getDecx() & j==piece.getDecy()){
                    listePiece[i][j].changeBackground(im.selRouge);
                }else {
                    if(piece.getMatrice()[i][j]==0){
                        listePiece[i][j].changeBackground(im.gris);
                    }else{
                        listePiece[i][j].changeBackground(im.rouge);
                    }
                }

            }
        }

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
}
