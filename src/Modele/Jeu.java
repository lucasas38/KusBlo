package Modele;

import Structures.Case;
import Structures.Trio;
import Structures.ListeValeur;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

public class Jeu implements Serializable {
    Niveau n;  //le plateau de jeu
    int nbJoueurs; // nombre de joueurs 2 , 3 ou 4
    Joueur[] listeJoueurs;  //tableau de nbJoueurs joueurs chacun contenant une/des couleurs
    int joueurCourant; //indice du joueur courant dasn le tableau qui joue/doit jouer
    Historique historique;

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

        joueurCourant = 1;
        historique=new Historique();
    }

    public Niveau getNiveau() {
        return n;
    }

    public Joueur getJoueur(int idJoueur){
        return listeJoueurs[idJoueur-1];
    }

    public Historique getHistorique() {
        return historique;
    }

    public int getIDJoueurCourant() {
        return joueurCourant;
    }

    public Joueur getJoueurCourant(){
        return listeJoueurs[joueurCourant-1];
    }

    public int getNumCouleurCourante(){
        return listeJoueurs[joueurCourant-1].getCouleurCourante().id;
    }

    public int getNbJoueurs() {
        return nbJoueurs;
    }

    //met à jour le joueur courant, passe au joueur suivant
    public void setJoueurCourant() {
        joueurCourant = (joueurCourant%nbJoueurs)+1;
    }

    public void setJoueur(int idJoueur) {
        joueurCourant = idJoueur;
    }

    //dans cette méthode on considère que la pièce est dans la grille et ne superpose aucune autre piece (grace à estPosable appellé avant)
    public void jouerPiece(int idJoueur,int indTabCouleur, int idPiece, LinkedList<Case> listeCasesPiece,boolean refaire){
            //recupere liste des pieces disponibles pour la couleur courante
            Piece piece = listeJoueurs[idJoueur-1].getCouleur(indTabCouleur).getPieceDispo(idPiece);
            //si la piece que l'on veut jouer est disponible
            if(piece != null) {
                //si la piece est posable selon les regles du jeu
                if (estPosableRegle(listeCasesPiece, idJoueur,indTabCouleur)) {
                    //on ajoute la piece au niveau (plateau du jeu)
                    n.ajouterPiece(piece,listeCasesPiece,getJoueur(idJoueur).getCouleur(indTabCouleur).getId());
                    //on calcule les coins possibles pour la couleur courante du joueur
                    calculeCoinPiece(piece,idJoueur,indTabCouleur);
                    //on joue la piece pour le joueur (et sa couleur courante)
                    listeJoueurs[idJoueur-1].jouePiece(piece,indTabCouleur);
                    //met à jour historique si l'on ne refait pas un coup du futur
                    if(!refaire){
                        historique.nouveau(new Trio(piece,idJoueur,indTabCouleur));
                        int idJoueurFutur = (idJoueur%nbJoueurs)+1;
                        int indTabCouleurJoueurFutur = (indTabCouleur%(getJoueur(idJoueur).getNbCouleurs()))+1;
                        setJoueur(idJoueurFutur);
                        listeJoueurs[idJoueur-1].setCouleur(indTabCouleurJoueurFutur);
                    }
                }else{
                    System.out.println("Piece "+idPiece+" n'est pas posable selon les règles du jeu");
                }
            }else{
                System.out.println("Piece "+idPiece+" n'est plus disponible pour la couleur "+indTabCouleur);
            }

    }


    //calcule les coins possible pour une piece pour la couleur courante d'un joueur
    private void calculeCoinPiece(Piece piece, int idJoueur, int indTabCouleur) {

        ajouteCoinsValides(piece,idJoueur,indTabCouleur);

        //reparcourt les coins de la couleur courante et enleve ceux qui ne sont plus valides
        supprimerCoinsInvalides(idJoueur,indTabCouleur);

        //parcourt la liste des coins des autres couleurs et si notre piece ecrase un des coins de l'autre couleur on l'enleve
        miseAJourCoinsAutreJoueurs(piece);

    }

    private void ajouteCoinsValides(Piece piece, int idJoueur, int indTabCouleur) {
        Iterator<Case> it = piece.listeCases.iterator();

        //parcourt chaque case de la piece
        while (it.hasNext()){
            Case ca = it.next();

            int x = ca.getX();
            int y = ca.getY();

            //pour les 4 coins potentiel, si il est valide, on l'ajoute à la liste des coins de la couleur
            estCoinValide(x+1,y-1,idJoueur,indTabCouleur);
            estCoinValide(x+1,y+1,idJoueur,indTabCouleur);
            estCoinValide(x-1,y-1,idJoueur,indTabCouleur);
            estCoinValide(x-1,y+1,idJoueur,indTabCouleur);
        }
    }

    private void supprimerCoinsInvalides(int idJoueur, int indTabCouleur) {
        Iterator<Case> it = getJoueur(idJoueur).getCouleur(indTabCouleur).listeCoins.iterator();
        while (it.hasNext()){
            Case ca = it.next();
            if(!estCoinValide(ca.getX(),ca.getY(),idJoueur,indTabCouleur)){
                it.remove();
            }
        }
    }

    private void miseAJourCoinsAutreJoueurs(Piece piece) {
        Iterator<Case> it = piece.listeCases.iterator();
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
    boolean estCoinValide(int x, int y, int idJoueur, int indTabCouleur){
        if(n.estDansGrille(x,y) && n.grille[x][y] == 0){
            if(n.aucunVoisin(x,y,getJoueur(idJoueur).getCouleur(indTabCouleur).getId()) && n.auMoinsUnCoin(x,y,getJoueur(idJoueur).getCouleur(indTabCouleur).getId())){
                listeJoueurs[idJoueur-1].ajouteCoin(new Case(x,y),indTabCouleur);
                    return true;
            }
        }
        return false;
    }

    //dans cette méthode on considère que la pièce est dans la grille et ne superpose aucune autre piece (grace à estPosable appellé avant)
    //verifie si une piece est posable selon les regles du jeu
    public boolean estPosableRegle(LinkedList<Case> listeCasesPiece, int idJoueur, int indTabCouleur){
        Couleur couleur = getJoueur(idJoueur).getCouleur(indTabCouleur);
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
                if(!(n.grille[voisin.getX()][voisin.getY()] != couleur.getId() ||
                        listeCasesPiece.contains(voisin))){
                    return false;
                }
            }
            if(!estSurUnCoinPossible){  //dès qu'on trouve UNE CASE qui est sur un coin possible du joueur, on ne teste plus les autres
                estSurUnCoinPossible = couleur.listeCoins.contains(caCourante);  //on test si au moins une des est dans un coin possible pour le joueur
            }

        }
        //si on arrive ici : la piece n'est pas collée à une pièce de même couleur sur ses cotés "+"
        //il suffit de retourner "estSurUnCoinPossible" pour savoir si au moins une des cases de la piece est sur un coin possible pour le joueur

        return estSurUnCoinPossible;
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
    public LinkedList<ListeValeur<Case,Integer>> positionPossibleConfig(Piece p, int idJoueur, int indTabCouleur){

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

            //si cette configuration a déjà été testé/parcouru, on ne la refait pas
            if(!configPiecePossible.contains(config)){

                //parcourt toute la grille du niveau
                for (int x = 0;x<20;x++){
                    for (int y=0;y<20;y++){
                        //si une piece est posable alors on stocke cette piece
                        if(n.estPosable(p,x-decx,y-decy)){
                            LinkedList<Case> configSelonEmplacement = tradMatrice(p,x-decx,y-decy);
                            if(estPosableRegle(configSelonEmplacement,idJoueur,indTabCouleur)){
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
    public boolean restePieceJouable(int idJoueur,int indTabCouleur){
        Couleur couleur = getJoueur(idJoueur).getCouleur(indTabCouleur);
        ListePieces listePiecesDispoClone = couleur.getListePiecesDispo();
        Iterator<Piece> it = listePiecesDispoClone.iterateur();

        while (it.hasNext()){ //chaque piece
            Piece p = it.next();
            LinkedList<ListeValeur<Case,Integer>> liste = positionPossibleConfig(p,idJoueur,indTabCouleur);
            if(liste.size()>0){
                return true;
            }
        }
        return false;

    }

    public void passerTour(int idJoueur,int indTabCouleur) {
        System.out.println("Couleur "+getJoueur(idJoueur).getCouleur(indTabCouleur).id +" passe son tour");
        setJoueur((idJoueur%nbJoueurs)+1);
        getJoueur(idJoueur).setCouleur((indTabCouleur%(getJoueur(idJoueur).nbCouleurs))+1);
    }

    //HISTORIQUE

    public void annuler(){
        //recup dernier coup joué et change futur et passe
        Trio<Piece,Integer,Integer> dernier = historique.annuler();
//        s'il y a un dernierCoup joué
        if(dernier != null){

            Piece piecePrec = dernier.getE1();
            Integer idJoueurPrec = dernier.getE2();
            Integer indTabCouleurJoueurPrec = dernier.getE3();

            //parcourt la liste des cases de la piece (coordonées réelles sur la grille)
            //et met à zero (vide) les cases de la grille
            n.ajouterPiece(piecePrec,piecePrec.listeCases,0);

            //change joueur courant et couleur courante pour le joueur
            setJoueur(idJoueurPrec);
            getJoueur(idJoueurPrec).setCouleur(indTabCouleurJoueurPrec);

            if(!getJoueur(idJoueurPrec).peutJouer){
                getJoueur(idJoueurPrec).annulerScoreFinal();
            }

            //s'il ne pouvait plus jouer , maintenant il peut de nouveau jouer
            if(!getJoueur(idJoueurPrec).getCouleur(indTabCouleurJoueurPrec).isRestePieceJouable()){
                getJoueur(idJoueurPrec).setRestePieceJouableCouleur(indTabCouleurJoueurPrec,true);
            }

            getJoueur(idJoueurPrec).score -= piecePrec.taille;

            //met à jour pieces disponibles et pieces posées
            getJoueur(idJoueurPrec).getCouleur(indTabCouleurJoueurPrec).getListePiecesDispo().ajoutePieceOrdre(piecePrec);
            getJoueur(idJoueurPrec).getCouleur(indTabCouleurJoueurPrec).getListesPiecesPosees().remove(piecePrec);

            //on recalcule les coins pour joueur courant et autre joueur
            annulerCoins(idJoueurPrec,indTabCouleurJoueurPrec);

        }
    }

    void annulerCoins(int idJoueur,int indTabCouleur){

        supprimerCoinsInvalides(idJoueur,indTabCouleur);

        Iterator<Piece> it;
        for (int i=0;i<getNbJoueurs();i++){
            Joueur joueur = getJoueur(i+1);
            for (int j=0;j<joueur.nbCouleurs;j++){
                Couleur couleur = joueur.listeCouleur[j];
                it = couleur.listesPiecesPosees.iterator();
                while (it.hasNext()){
                    Piece p = it.next();
                    ajouteCoinsValides(p,i+1,j+1);
                }
            }
        }

        if(getJoueur(idJoueur).getCouleur(indTabCouleur).getListesPiecesPosees().isEmpty()){
            getJoueur(idJoueur).getCouleur(indTabCouleur).ajoutePremierCoinCouleur();
        }
    }

}
