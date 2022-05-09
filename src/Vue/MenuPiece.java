package Vue;

import Controleur.Controleur;
import Modele.Piece;
import Structures.BasicBackgroundPanel;

import javax.swing.*;
import java.awt.*;

public class MenuPiece {
    JPanel menu;
    BasicBackgroundPanel[][] listePiece;
    ImageKusBlo im;
    Bouton b;
    Controleur c;
    Piece piece;
    JPanel menuType1;
    JPanel menuType2;
    JPanel menuType3;
    JPanel affichagePiece;
    //int [][] grillePiece;
    int x;
    int y;


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

    public void setMenuType1(/*int joueur*/){
       menuType1.removeAll();
        for(int k=0;k<9;k++){
            JPanel affPiece = new JPanel(new GridLayout(5,5));
            for(int i=0; i<5;i++){
                for(int j=0; j<5;j++){
                    BasicBackgroundPanel newPan = new BasicBackgroundPanel(im.gris);
                        if(piece.getMatrice()[i][j]==0){
                            newPan.changeBackground(im.gris);
                        }else{
                            newPan.changeBackground(im.rouge);
                        }
                        //newPan.setBorder(BorderFactory.createLineBorder(Color.black));
                        affPiece.add(newPan);
                    }

                }
            affPiece.setBorder(BorderFactory.createLineBorder(Color.red));
            menuType1.add(affPiece);
        }
        for(int k=0;k<12;k++){
            menuType1.add(new JPanel());
        }
        menu.removeAll();
        menu.add(menuType1);
        menu.updateUI();
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

    public void setMenuType2(/*int joueur, int piece*/){

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


    //public int[][] getGrillePiece(){
  //      return grillePiece;
   // }

    //Hauteur de la piece
    public int getHautAffPiece(){
        return  affichagePiece.getHeight()/5;
    }
    //Largeur de la piece
    public int getLargAffPiece(){
        return  affichagePiece.getWidth()/5;
    }


    //Modifie le déclage en fonction de la case sélectionné
    public void updateCaseSelec(int i, int j){
        if(piece.getMatrice()[i][j]==1){
            listePiece[x][y].changeBackground(im.rouge);
            x=i;
            y=j;
            listePiece[x][y].changeBackground(im.selRouge);
        }
    }


    public void refreshCaseSelec(){
        x=0;
        y=0;
        //On place le décalage à la première case
        while(x<4 && piece.getMatrice()[x][y]==0){
            while (y<4 && piece.getMatrice()[x][y]==0){
                y++;
            }
            if(piece.getMatrice()[x][y]==0){
                y=0;
                x++;
            }
        }
    }

    //Affiche la pièce actuelle
    public void refreshPiece(){
        refreshCaseSelec();
        for(int i=0; i<5;i++){
            for(int j=0; j<5;j++){
                if(i==x & j==y){
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
        return menuType1.getHeight();
    }

    //Largeur du menu complet
    public int getLargMenu(){
        return menuType1.getWidth();
    }

    public Piece getPiece() {
        return piece;
    }
}
