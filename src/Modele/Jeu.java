package Modele;

import Structures.Case;
import Structures.CoupleListeValeur;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

public class Jeu {
    Niveau n;
    int nbJoueurs; // nombre de joueurs 2 , 3 ou 4
    Joueur[] listeJoueurs;  //tableau de nbJoueurs joueurs
    int joueurCourant;


    public Jeu(int nombreJoueurs){
        this.n = new Niveau();

        if(nombreJoueurs>1 && nombreJoueurs<=4){
            this.nbJoueurs = nombreJoueurs;
        }else{
            this.nbJoueurs = 4;
        }

        listeJoueurs = new Joueur[this.nbJoueurs];

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

        //Random r = new Random();
        //joueurCourant = r.nextInt(nbJoueurs)+1 ; //choisi joueur aleatoire
        joueurCourant = 1;
    }

    public Niveau getNiveau() {
        return n;
    }

    public Joueur getJoueur(int idJoueur){
        return listeJoueurs[idJoueur-1];
    }

    //dans cette méthode on considère que la pièce est dans la grille et ne superpose aucune autre piece (grace à estPosable appellé avant)
    public void jouerPiece(int idJoueur,int idPiece, LinkedList<Case> listeCasesPiece){
            Piece piece = listeJoueurs[idJoueur-1].getCouleurCourante().getPieceDispo(idPiece);
            if(piece != null) {
                if (estPosableRegle(listeCasesPiece, idJoueur)) {
                    n.ajouterPiece(piece,listeCasesPiece,getNumCouleurCourante());
                    calculeCoinPiece(piece,idJoueur);
                    listeJoueurs[idJoueur-1].jouePiece(piece);
                    if(!restePieceJouable()){
                        finCouleur();
                    }
                    setJoueurCourant(); //mise à jour joueurCourant
                    listeJoueurs[idJoueur-1].setCouleurCourant();  //mise à jour couleurCourante pour le joueur
                }else{
                    System.out.println("Piece "+idPiece+" n'est pas posable selon les règles du jeu");
                }
            }else{
                System.out.println("Piece "+idPiece+" n'est plus disponible pour le joueur "+idJoueur);
            }

    }


    public void setJoueurCourant() {
        joueurCourant = (joueurCourant%nbJoueurs)+1;
    }

    void finJoueur(int idJoueur){
        listeJoueurs[idJoueur-1].setScoreFinal();
        listeJoueurs[idJoueur-1].peutJouer = false;
    }


    private void calculeCoinPiece(Piece piece, int idJoueur) {
        Iterator<Case> it = piece.listeCases.iterator();

        //ajoute les nouveaux coins possibles
        while (it.hasNext()){
            Case ca = it.next();

            int x = ca.getX();
            int y = ca.getY();

            estCoinValide(x+1,y-1,idJoueur);
            estCoinValide(x+1,y+1,idJoueur);
            estCoinValide(x-1,y-1,idJoueur);
            estCoinValide(x-1,y+1,idJoueur);


        }

        //reparcourt les coins pour le joueur courant et enleve les coins qui ne sont plus valides (meme couleurs à coté)
        it = getJoueur(idJoueur).getCouleurCourante().listeCoins.iterator();
        while (it.hasNext()){
            Case ca = it.next();
            if(!estCoinValide(ca.getX(),ca.getY(),idJoueur)){
                it.remove();
            }
        }

        it = piece.listeCases.iterator();
        while (it.hasNext()){
            Case ca = it.next();
            for (int i=0;i<nbJoueurs;i++){
                for (int j=0;j<listeJoueurs[i].nbCouleurs;j++){
                    HashSet listeCoinsCouleur = listeJoueurs[i].listeCouleur[j].getListeCoins();
                    if(listeCoinsCouleur.contains(ca)){
                        listeCoinsCouleur.remove(ca);
                    }
                }
            }
        }

    }

    boolean estCoinValide(int x, int y, int idJoueur){
        if(n.estDansGrille(x,y) && n.grille[x][y] == 0){
            if(n.aucunVoisin(x,y,getJoueur(idJoueur).getCouleurCourante().getId())){
                listeJoueurs[idJoueur-1].ajouteCoin(new Case(x,y));
                return true;
            }
        }
        return false;
    }

    //dans cette méthode on considère que la pièce est dans la grille et ne superpose aucune autre piece (grace à estPosable appellé avant)
    public boolean estPosableRegle(LinkedList<Case> listeCasesPiece, int idJoueur){
        Couleur couleurCourante = getJoueur(idJoueur).getCouleurCourante();
        boolean estSurUnCoinPossible = false;
        Iterator<Case> it = listeCasesPiece.iterator();
        while (it.hasNext()){
            Case caCourante = it.next();
            LinkedList<Case> voisinsCaseCourante = n.voisinsCase(caCourante);
            Iterator<Case> it2 = voisinsCaseCourante.iterator();
            while(it2.hasNext()){
                Case voisin = it2.next();
                if(!(n.grille[voisin.getX()][voisin.getY()] != couleurCourante.getId() ||
                        listeCasesPiece.contains(voisin))){ // && grille[voisin.getX()][voisin.getY()] == idJoueur)
                    return false;
                }
            }
            if(!estSurUnCoinPossible){  //dès qu'on trouve UNE CASE qui est sur un coin possible du joueur, on ne teste plus les autres
                estSurUnCoinPossible = couleurCourante.listeCoins.contains(caCourante);  //on test si au moins une des est dans un coin possible pour le joueur
            }

        }
        //si on arrive ici : la piece n'est pas collée à une pièce de même couleur sur ses cotés "+"
        //il suffit de retourner ok pour savoir si au moins une des cases de la piece est sur un coin possible pour le joueur

        return estSurUnCoinPossible;
    }

    public int getIDJoueurCourant() {
        return joueurCourant;
    }

    public int getNumCouleurCourante(){
        return listeJoueurs[joueurCourant-1].getCouleurCourante().id;
    }

    public LinkedList<Case> tradMatrice(Piece p, int x, int y){
        int[][] matrice = p.getMatrice();
        LinkedList<Case> liste= new LinkedList<>();
        for(int i=0;i<5;i++){
            for(int j=0; j<5; j++){
                if(matrice[i][j]!=0){
                    int coordx=x+i;
                    int coordy=y+j;
                    Case c= new Case(coordx, coordy);
                    liste.add(c);
                }
            }
        }
        return liste;
    }

    //si liste en retour à une taille de 0 alors aucune possiblités
    public LinkedList<CoupleListeValeur<Case,Integer>> positionPossibleConfig(Piece p){

        LinkedList<CoupleListeValeur<Case,Integer>> listeEmplacementPossible = new LinkedList<>();

        HashSet<LinkedList<Case>> configPiecePossible = new HashSet<>();
        int decx,decy;

        LinkedList<Case> config;

        for (int i=0;i<8;i++){ //chaque config

            decx = p.getDecx();
            decy = p.getDecy();

            config = tradMatrice(p,0,0);

            if(!configPiecePossible.contains(config)){

                for (int x = 0;x<20;x++){
                    for (int y=0;y<20;y++){
                        if(n.estPosable(p,x-decx,y-decy)){
                            LinkedList<Case> configSelonEmplacement = tradMatrice(p,x-decx,y-decy);
                            if(estPosableRegle(configSelonEmplacement,this.joueurCourant)){
                                listeEmplacementPossible.add(new CoupleListeValeur(configSelonEmplacement,i));
                            }
                        }
                    }
                }
                configPiecePossible.add(config);
            }

            if(i == 4){
                p.rotationSymetrique();
            }else{
                p.rotationHoraire();
            }
        }

        p.rotationSymetrique();
        p.rotationAntiHoraire();

       return listeEmplacementPossible;
    }

    //return true si au moins une piece peut encore être joué , false sinon
    public boolean restePieceJouable(){
        Couleur couleur = getJoueur(getIDJoueurCourant()).getCouleurCourante();
        System.out.println("restePieceJouable "+couleur.id);
        ListePieces listePiecesDispoClone = couleur.getListePiecesDispo();
        Iterator<Piece> it = listePiecesDispoClone.iterateur();

        while (it.hasNext()){ //chaque piece
            Piece p = it.next();
            LinkedList<CoupleListeValeur<Case,Integer>> liste = positionPossibleConfig(p);
            if(liste.size()>0){
                System.out.println(" "+true);
                return true;
            }
        }
        System.out.println(" "+false);
        return false;

    }

    public int getNbJoueurs() {
        return nbJoueurs;
    }

    public void finCouleur() {
        System.out.println("Fin jeu pour couleur "+getJoueur(getIDJoueurCourant()).getCouleurCourante().id);
        Joueur joueur = listeJoueurs[joueurCourant-1];
        joueur.peutJouer = joueur.finCouleur();
        if(!joueur.peutJouer){
            joueur.setScoreFinal();
        }
    }

    public void passerTour() {
        System.out.println("Couleur "+getJoueur(getIDJoueurCourant()).getCouleurCourante().id +" passe son tour");
        int idJoueur = getIDJoueurCourant();
        setJoueurCourant();
        getJoueur(idJoueur).setCouleurCourant();
    }
}
