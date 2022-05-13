package Modele;

import Structures.Case;
import Structures.ListeValeur;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

public class Jeu {
    Niveau n;  //le plateau de jeu
    int nbJoueurs; // nombre de joueurs 2 , 3 ou 4
    Joueur[] listeJoueurs;  //tableau de nbJoueurs joueurs chacun contenant une/des couleurs
    int joueurCourant; //identifiant unique du joueur courant qui joue/doit jouer


    public Jeu(int nombreJoueurs){
        this.n = new Niveau();

        //nombre de joueurs doit etre compris entre 1 et 4
        if(nombreJoueurs>1 && nombreJoueurs<=4){
            this.nbJoueurs = nombreJoueurs;
        }else{
            this.nbJoueurs = 4;
        }

        listeJoueurs = new Joueur[this.nbJoueurs];

        //si 4 joueurs, chaque joueur a une couleur
        //si 2 joeurs, chque joueur a 2 couleurs différentes
        //si 3 joueurs, chque joueur a une couleur et se partagent la derniere
        for (int i=0;i<this.nbJoueurs;i++){
            listeJoueurs[i] = new Joueur(i+1);
            if(this.nbJoueurs == 4){
                listeJoueurs[i].addCouleur(new Couleur(i+1));
            }else if(this.nbJoueurs == 2){
                listeJoueurs[i].addCouleur(new Couleur(i+1));
                listeJoueurs[i].addCouleur(new Couleur(i+3));
            }else{
                listeJoueurs[i].addCouleur(new Couleur(i+1));
                //manque couleur 4 (meme reference) à ajouter à chacun (sans compter le score pour celle ci)
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
            //recupere liste des pieces disponibles pour la couleur courante
            Piece piece = listeJoueurs[idJoueur-1].getCouleurCourante().getPieceDispo(idPiece);
            //si la piece que l'on veut jouer est disponible
            if(piece != null) {
                //si la piece est posable selon les regles du jeu
                if (estPosableRegle(listeCasesPiece, idJoueur)) {
                    //on ajoute la piece au niveau (plateau du jeu)
                    n.ajouterPiece(piece,listeCasesPiece,getNumCouleurCourante());
                    //on calcule les coins possibles pour la couleur courante du joueur
                    calculeCoinPiece(piece,idJoueur);
                    //on joue la piece pour le joueur (et sa couleur courante)
                    listeJoueurs[idJoueur-1].jouePiece(piece);
                    //si la couleur courante du joueur n'a plus de pieces a jouer
                    //alors on passe la couleur courante en fin de jeu (peut plus jouer et score final mis à jour)
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

    //met à jour le joueur courant, passe au joueur suivant
    public void setJoueurCourant() {
        joueurCourant = (joueurCourant%nbJoueurs)+1;
    }

    //calcule les coins possible pour une piece pour la couleur courante d'un joueur
    private void calculeCoinPiece(Piece piece, int idJoueur) {
        Iterator<Case> it = piece.listeCases.iterator();

        //parcourt chaque case de la piece
        while (it.hasNext()){
            Case ca = it.next();

            int x = ca.getX();
            int y = ca.getY();

            //pour les 4 coins potentiel, si il est valide, on l'ajoute à la liste des coins de la couleur
            estCoinValide(x+1,y-1,idJoueur);
            estCoinValide(x+1,y+1,idJoueur);
            estCoinValide(x-1,y-1,idJoueur);
            estCoinValide(x-1,y+1,idJoueur);


        }

        //reparcourt les coins de la couleur courante et enleve ceux qui nesont plus valides
        it = getJoueur(idJoueur).getCouleurCourante().listeCoins.iterator();
        while (it.hasNext()){
            Case ca = it.next();
            if(!estCoinValide(ca.getX(),ca.getY(),idJoueur)){
                it.remove();
            }
        }

        //parcourt la liste des coins des autres couleurs et si notre piece ecrase un des coins de l'autre couleur on l'enleve
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

    //true si un coin est valide (point dans la grille du niveau et n'as pas une meme couleur ajacente)
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
    //verifie si une piece est posable selon les regles du jeu
    public boolean estPosableRegle(LinkedList<Case> listeCasesPiece, int idJoueur){
        Couleur couleurCourante = getJoueur(idJoueur).getCouleurCourante();
        boolean estSurUnCoinPossible = false;
        Iterator<Case> it = listeCasesPiece.iterator();
        //pour chaque case de la piece
        while (it.hasNext()){
            Case caCourante = it.next();
            //on recupere la liste de voisins (case ajdjacente en "+")
            LinkedList<Case> voisinsCaseCourante = n.voisinsCase(caCourante);
            Iterator<Case> it2 = voisinsCaseCourante.iterator();
            //pour chaque voisins
            while(it2.hasNext()){
                Case voisin = it2.next();
                //si le voisin est d'une meme couleur que notre couleur courante
                // ou si notre piece contient déjà ce voisins(donc voisisn par réellement un voisin car est une case de la piece)
                if(!(n.grille[voisin.getX()][voisin.getY()] != couleurCourante.getId() ||
                        listeCasesPiece.contains(voisin))){
                    return false;
                }
            }
            if(!estSurUnCoinPossible){  //dès qu'on trouve UNE CASE qui est sur un coin possible du joueur, on ne teste plus les autres
                estSurUnCoinPossible = couleurCourante.listeCoins.contains(caCourante);  //on test si au moins une des est dans un coin possible pour le joueur
            }

        }
        //si on arrive ici : la piece n'est pas collée à une pièce de même couleur sur ses cotés "+"
        //il suffit de retourner "estSurUnCoinPossible" pour savoir si au moins une des cases de la piece est sur un coin possible pour le joueur

        return estSurUnCoinPossible;
    }

    public int getIDJoueurCourant() {
        return joueurCourant;
    }

    public int getNumCouleurCourante(){
        return listeJoueurs[joueurCourant-1].getCouleurCourante().id;
    }

    //traduit la matrice d'une piece en une liste de case par rapport à un point (x,y)
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

    //retourne une liste de CoupleListeValeur
    //CoupleListeValeur contient une LinkedList<E> et une valeur
    //retourne la liste de toute les position possible pour une piece selon toutes ses configurations (rotation, miroir)
    //si la taille de la liste retourné est == 0 alors il n'y a aucune position possible pour cette piece (elle ne peut pas etre placé
    public LinkedList<ListeValeur<Case,Integer>> positionPossibleConfig(Piece p){

        LinkedList<ListeValeur<Case,Integer>> listeEmplacementPossible = new LinkedList<>();

        //hashset pour stocker les configurations possibles pour une piece (differentes rotation/miroir peuvent obtenir la meme piece)
        HashSet<LinkedList<Case>> configPiecePossible = new HashSet<>();
        int decx,decy;

        LinkedList<Case> config;

        //pour chaque config (rotation,miroir)
        for (int i=0;i<8;i++){

            decx = p.getDecx();
            decy = p.getDecy();

            config = tradMatrice(p,0,0);

            //si cette configuration a déjà était testé/parcourut, on ne le refait pas
            if(!configPiecePossible.contains(config)){

                //parcourt toute la grille du niveau
                for (int x = 0;x<20;x++){
                    for (int y=0;y<20;y++){
                        //si une piece est posable alors on stocke cette piece
                        if(n.estPosable(p,x-decx,y-decy)){
                            LinkedList<Case> configSelonEmplacement = tradMatrice(p,x-decx,y-decy);
                            if(estPosableRegle(configSelonEmplacement,this.joueurCourant)){
                                listeEmplacementPossible.add(new ListeValeur(configSelonEmplacement,i));
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
        ListePieces listePiecesDispoClone = couleur.getListePiecesDispo();
        Iterator<Piece> it = listePiecesDispoClone.iterateur();

        while (it.hasNext()){ //chaque piece
            Piece p = it.next();
            LinkedList<ListeValeur<Case,Integer>> liste = positionPossibleConfig(p);
            if(liste.size()>0){
                return true;
            }
        }
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
