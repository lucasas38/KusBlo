package Modele;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

public class Jeu {
    Niveau n;
    int nbJoueurs; // nombre de joueurs 2 , 3 ou 4
    Joueur[] listeJoueurs;  //tableau de nbJoueurs joueurs
    int joueurCourant;


    public Jeu(int nombreJoueurs){
        this.n = new Niveau();

        if(!(nombreJoueurs>1 && nombreJoueurs<=4)){
            this.nbJoueurs = 4;
        }else{
            this.nbJoueurs = nombreJoueurs;
        }

        listeJoueurs = new Joueur[nombreJoueurs];

        for (int i=0;i<this.nbJoueurs;i++){
            listeJoueurs[i] = new Joueur(i+1);
            if(this.nbJoueurs == 4){
                listeJoueurs[i].addCouleur(new Couleur(i+1));
            }else if(this.nbJoueurs == 2){
                listeJoueurs[i].addCouleur(new Couleur(i+1));
                listeJoueurs[i].addCouleur(new Couleur(i+3));
            }else{
                listeJoueurs[i].addCouleur(new Couleur(i+1));
                //manque couleur 4 (meme reference) à ajouter à chacun
            }
        }

        Random r = new Random();
        joueurCourant = r.nextInt(4)+1 ; //choisi joueur aleatoire
    }

    public Niveau getNiveau() {
        return n;
    }

    public Joueur getJoueur(int idJoueur){
        return listeJoueurs[idJoueur-1];
    }

    //dans cette méthode on considère que la pièce est dans la grille et ne superpose aucune autre piece (grace à estPosable appellé avant)
    public void jouerPiece(int idJoueur,int idPiece, LinkedList<Case> listeCasesPiece){
        if(listeJoueurs[idJoueur-1].peutJouer){
            Piece piece = listeJoueurs[idJoueur-1].getCouleurCourante().getPieceDispo(idPiece);
            if(piece != null) {
                if (estPosableRegle(listeCasesPiece, idJoueur)) {
                    n.ajouterPiece(piece,listeCasesPiece,idJoueur);
                    calculeCoinPiece(piece,idJoueur);
                    listeJoueurs[idJoueur-1].jouePiece(piece);
                    joueurCourant = (joueurCourant%4)+1;  //mis à jour du joueur courant
                    listeJoueurs[idJoueur-1].setCouleurCourant();  //mise à jour couleurCourante pour le joueur
                }else{
                    System.out.println("Piece "+idPiece+" n'est pas posable selon les règles du jeu");
                }
            }else{
                System.out.println("Piece "+idPiece+" n'est plus disponible pour le joeuur "+idJoueur);
            }
        }

    }

    void finJoueur(int idJoueur){
        listeJoueurs[idJoueur-1].setScoreFinal();
        listeJoueurs[idJoueur-1].peutJouer = false;
    }


    private void calculeCoinPiece(Piece piece, int idJoueur) {
        Iterator<Case> it = piece.listeCases.iterator();

        while (it.hasNext()){
            Case ca = it.next();

            int x = ca.getX();
            int y = ca.getY();

            if(getJoueur(idJoueur).getCouleurCourante().listeCoins.contains(ca)){
                getJoueur(idJoueur).getCouleurCourante().listeCoins.remove(ca);
            }

            estCoinValide(x+1,y-1,idJoueur);
            estCoinValide(x+1,y+1,idJoueur);
            estCoinValide(x-1,y-1,idJoueur);
            estCoinValide(x-1,y+1,idJoueur);

        }
    }

    void estCoinValide(int x, int y, int idJoueur){
        if(n.estDansGrille(x,y) && n.grille[x][y] == 0){
            if(n.aucunVoisin(x,y,idJoueur)){
                listeJoueurs[idJoueur-1].ajouteCoin(new Case(x,y));
            }
        }
    }

    //dans cette méthode on considère que la pièce est dans la grille et ne superpose aucune autre piece (grace à estPosable appellé avant)
    public boolean estPosableRegle(LinkedList<Case> listeCasesPiece, int idJoueur){
        boolean estSurUnCoinPossible = false;
        Iterator<Case> it = listeCasesPiece.iterator();
        while (it.hasNext()){
            Case caCourante = it.next();
            LinkedList<Case> voisinsCaseCourante = n.voisinsCase(caCourante);
            Iterator<Case> it2 = voisinsCaseCourante.iterator();
            while(it2.hasNext()){
                Case voisin = it2.next();
                if(!(n.grille[voisin.getX()][voisin.getY()] != idJoueur ||
                        listeCasesPiece.contains(voisin))){ // && grille[voisin.getX()][voisin.getY()] == idJoueur)
                    return false;
                }
            }
            if(!estSurUnCoinPossible){  //dès qu'on trouve UNE CASE qui est sur un coin possible du joueur, on ne teste plus les autres
                estSurUnCoinPossible = listeJoueurs[idJoueur-1].getCouleurCourante().listeCoins.contains(caCourante);  //on test si au moins une des est dans un coin possible pour le joueur
            }

        }
        //si on arrive ici : la piece n'est pas collée à une pièce de même couleur sur ses cotés "+"
        //il suffit de retourner ok pour savoir si au moins une des cases de la piece est sur un coin possible pour le joueur

        return estSurUnCoinPossible;
    }

    public int getJoueurCourant() {
        return joueurCourant;
    }
}
