package Controleur;

import Modele.Couleur;
import Modele.Jeu;
import Modele.ListePieces;
import Modele.Piece;
import Structures.Case;
import Structures.ListeValeur;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

public abstract class IA implements Serializable {
    Jeu jeu;
    //mettre attribut Joueur et Couleur et definir a la creation puis utiliser ces attributs a la place de getCourant
    Random r;

    int ouv; // type d'ouverture ( 0 : classique, 1 : Barasona)
    int type;

    int mode; // mode de l'IA inetermédiaire (cf calcul_heuristique pour détail)

    IA(Jeu j){
        jeu=j;
        setR();
        ouv = r.nextInt(2);
    }

    public int getTypeIA(){ return type;} //
    public abstract ListeValeur<Case,Piece> joue();
    public abstract String toString();
    public void setR(){r=new Random();};
    public abstract int getType();

    public ListePieces copiePiecesDispo(){
        ListePieces listePieces = new ListePieces(false);

        Iterator<Piece> it = jeu.getJoueur(jeu.getIDJoueurCourant()).getCouleurCourante().getListePiecesDispo().iterateur();
        Piece p;
        while (it.hasNext()){
            p = it.next();
            listePieces.getListe().addLast(p);
        }
        listePieces.setListe(listePieces.getListe());

        return listePieces;
    }

    public void tournePiece(int rota, Piece p){
        int i = 0;
        while (i < rota) {
            if (i == 4) {
                p.rotationSymetrique();
            } else {
                p.rotationHoraire();
            }
            i++;
        }
    }

    // renvoie l'heuristique pour les IA intermédiaires
    // peut probablement être amélioré en faisant évoluer les coeficients au fur et à mesure de la partie
    int calcul_heuristique(int taille, int poss_ouv, int poss_bloq, int case_bloq){
        int res = 0;
        switch (mode) {
            case 0: // IA test
                res = 200 * taille + poss_ouv - 100 * case_bloq;
                break;
            case 1: // IA ouvrante
                res = 100 * taille + poss_ouv + 50 * poss_bloq - 50 * case_bloq;
                break;
            case 2: // IA privilégiant les grandes pièces
                res = 150 * taille + poss_ouv + 50 * poss_bloq - 50 * case_bloq;
                break;
            case 3: // IA pattern
                res = 100 * taille + poss_ouv + 50 * poss_bloq - 50 * case_bloq;
                break;
            case 4: // IA bloquante
                res = 100 * taille + poss_ouv + 200 * poss_bloq - 50 * case_bloq;
                break;
            default:
                break;
        }
        /*if (mode == 0 && jeu.getJoueurCourant().getCouleurCourante().getListePiecesDispo().getTaille() == 12){
            mode = 4;
            System.out.println("Passage en mode bloquant");
        }*/
        return res;
    }

    // peut être amélioré en étant lu dans un fichier
    Case determ_pos(int num_joueur,int nb_coup){ // permet de déterminer la position où on va jouer les ouvertures
        Case c = null;
        switch(ouv) {
            case 0 : //ouverture classique
                switch (num_joueur) {
                    case 1: // joueur 1
                        switch (nb_coup) {
                            case 0: // coup 1
                                c = new Case(0, 0);
                                break;
                            case 1: // coup 2
                                c = new Case(3, 3);
                                break;
                            case 2: // coup 3
                                c = new Case(6, 6);
                                break;
                            default:
                                break;
                        }
                        break;
                    case 2: // joueur 2
                        switch (nb_coup) {
                            case 0:
                                c = new Case(0, 19);
                                break;
                            case 1:
                                c = new Case(3, 16);
                                break;
                            case 2:
                                c = new Case(6, 13);
                                break;
                            default:
                                break;
                        }
                        break;
                    case 3: // joueur 3
                        switch (nb_coup) {
                            case 0:
                                c = new Case(19, 19);
                                break;
                            case 1:
                                c = new Case(16, 16);
                                break;
                            case 2:
                                c = new Case(13, 13);
                                break;
                            default:
                                break;
                        }
                        break;
                    case 4: // joueur 4
                        switch (nb_coup) {
                            case 0:
                                c = new Case(19, 0);
                                break;
                            case 1:
                                c = new Case(16, 3);
                                break;
                            case 2:
                                c = new Case(13, 6);
                                break;
                            default:
                                break;
                        }
                        break;
                    default: // default switch joueur
                        break;
                }
                break;
            case 1: // ouverture barasona
                switch (num_joueur) {
                    case 1: // joueur 1
                        switch (nb_coup) {
                            case 0:
                                c = new Case(0, 0);
                                break;
                            case 1:
                                c = new Case(2, 2);
                                break;
                            case 2:
                                c = new Case(6, 4);
                                break;
                            default:
                                break;
                        }
                        break;
                    case 2: // joueur 2
                        switch (nb_coup) {
                            case 0:
                                c = new Case(0, 19);
                                break;
                            case 1:
                                c = new Case(2, 17);
                                break;
                            case 2:
                                c = new Case(4, 13);
                                break;
                            default:
                                break;
                        }
                        break;
                    case 3: //joueur 3
                        switch (nb_coup) {
                            case 0:
                                c = new Case(19, 19);
                                break;
                            case 1:
                                c = new Case(17, 17); // pas sur
                                break;
                            case 2:
                                c = new Case(13, 15);
                                break;
                            default:
                                break;
                        }
                        break;
                    case 4: // joueur 4
                        switch (nb_coup) {
                            case 0:
                                c = new Case(19, 0);
                                break;
                            case 1:
                                c = new Case(17, 2); // pas sur
                                break;
                            case 2:
                                c = new Case(15, 6);
                                break;
                            default:
                                break;
                        }
                        break;
                    default: // default switch joueur
                        break;
                }
                break;
            default : break; // default switch ouverture
        }
        return c;
    }

    // détermine les coups pour chacune des ouvertures
    public ListeValeur<Case, Piece> ouvertures(ListePieces l_p){
        int taille = l_p.getTaille();
        Piece p = null;
        ListeValeur<Case,Piece> res = null;
        Case pos = null;
        LinkedList<Case> l_case = null;
        int couleur = jeu.getJoueurCourant().getCouleurCourante().getId();
        switch(taille){
            case 21 :   // première pièce
                if (ouv == 0) { // ouverture classique
                    p = l_p.getPiece(17);
                }
                else { // ouverture de barasona
                    p = l_p.getPiece(20);
                }
                break;
            case 20 :   // seconde pièce
                if (ouv == 0) {
                    p = l_p.getPiece(18);
                } else {
                    p = l_p.getPiece(21);
                }
                break;
            case 19 :   // troisième pièce
                if (ouv == 0) {
                    p = l_p.getPiece(19);
                } else {
                    p = l_p.getPiece(18);
                }
                break;
            default : break;
        }

        verif_rotation(p); // remet les pièces à leur rotation d'origine
        rotaPieceOuverture(p); // oriente les pièces correctement pour leur prochain coup

        pos = determ_pos(couleur, 21-taille);

        l_case = jeu.tradMatrice(p, pos.getX() - 2*p.getDebMatrice().getX(),pos.getY() - 2*p.getDebMatrice().getY());
        res = new ListeValeur<>(l_case, p);
        return res;
    }

    // tourne les pièces pour qu'elles soient dans le bon sens pour l'IA inter (pour les ouvertures
    public void rotaPieceOuverture(Piece p){
        if (p.getId() == 18 && ouv == 1){ // cas spécial où la pièce a besoin d'avoir une rotation initiale différente
            p.rotationAntiHoraire();
        }
        for (int i = 0; i < jeu.getJoueurCourant().getCouleurCourante().getId() - 1; i++) {
            p.rotationHoraire();
        }
    }

    // remet les pièces dans leur bonne orientation pour les ouvertures de l'IA inter
    public void verif_rotation(Piece p){
        while (p.getDebMatrice().getX() != 0 || p.getDebMatrice().getY() != 0) {
            p.rotationAntiHoraire();
        }
    }

    private HashSet<Case> CoinsPieces(LinkedList<Case> listeCases, int idJoueur) {
        Iterator<Case> it = listeCases.iterator();
        HashSet<Case> coins = new HashSet<>();
        //parcourt chaque case de la piece
        while (it.hasNext()) {
            Case ca = it.next();

            int x = ca.getX();
            int y = ca.getY();

            //pour les 4 coins potentiel, si il est valide, on l'ajoute à la liste des coins de la couleur
            if (simuleCoinValide(x + 1, y - 1, idJoueur, listeCases)) {
                coins.add(new Case(x + 1, y - 1));
            }
            if (simuleCoinValide(x + 1, y + 1, idJoueur, listeCases)) {
                coins.add(new Case(x + 1, y + 1));
            }
            if (simuleCoinValide(x - 1, y - 1, idJoueur, listeCases)) {
                coins.add(new Case(x - 1, y - 1));
            }
            if (simuleCoinValide(x - 1, y + 1, idJoueur, listeCases)) {
                coins.add(new Case(x - 1, y + 1));
            }
        }
        return coins;
    }


    public boolean simuleCoinValide(int x, int y, int idJoueur, LinkedList<Case> listeCases) {
        boolean voisinEstPasDansListeCase = true;
        LinkedList<Case> voisins = jeu.getNiveau().voisinsCase(new Case(x, y));

        // on vérifie que le coin n'est pas une des cases de la pièce
        for (int i = 0; i < voisins.size(); i++) {
            if (listeCases.contains(voisins.get(i))) {
                voisinEstPasDansListeCase = false;
            }
        }

        // on vérifie que le coin est dans la grille, qu'il est bien vide et qu'il ne touche pas une autre case de sa couleur
        if (jeu.getNiveau().estDansGrille(x, y) && jeu.getNiveau().getGrille()[x][y] == 0) {
            if (jeu.getNiveau().aucunVoisin(x, y, jeu.getJoueur(idJoueur).getCouleurCourante().getId())) {
                if (voisinEstPasDansListeCase) {
                    return true;
                }
            }
        }

        return false;
    }


    public int nb_possibilite_ouverte(int idCouleur, LinkedList<Case> listeCases, int nb_coup) {
        int res = 0;
        HashSet<Case> l_coin_piece = CoinsPieces(listeCases, idCouleur); //on doit recuperer la liste des nouveau coins
        Iterator<Case> it_coin = l_coin_piece.iterator();
        while (it_coin.hasNext()) {
            Case cb = it_coin.next();
            if (jeu.getNiveau().getGrille()[cb.getX()][cb.getY()] == 0) {
                if (nb_coup == 18 || getTypeIA() == 7){ // on réduit les calculs pour l'IA difficile et le premier coup après l'ouverture
                    res+=1;
                } else {
                    res += espaceCase(cb);
                }
            }
        }
        return res;
    }

    // calcule le nombre de cases accessibles à partir d'une case
    // permet d'avoir une notion d'espace dans l'heuristique
    public int espaceCase(Case c){
        int res = 0;
        int compteurBoucle = 0;
        LinkedList<Case> lCase = new LinkedList<>();
        lCase.add(c);
        Iterator<Case> itCase = lCase.iterator();
        while (itCase.hasNext()){
            Case c2 = itCase.next();
            //System.out.println("Case choisie : " + c2.toString());
            LinkedList<Case> voisins = jeu.getNiveau().voisinsCase(c2);
            Iterator<Case> itVois = voisins.iterator();
            while(itVois.hasNext()){
                Case cVois = itVois.next();
                if (!lCase.contains(cVois) && jeu.getNiveau().getGrille()[cVois.getX()][cVois.getY()] == 0){
                    res++;
                    lCase.add(cVois);
                }
            }
            //System.out.println("Liste case : " + lCase.toString());
            itCase = lCase.iterator();
            compteurBoucle ++;
            int i = 0;
            while(i<compteurBoucle && itCase.hasNext()){
                itCase.next();
                i++;
            }

        }
        //System.out.println("Sortie fonction");
        return res;
    }

    // nombre de cases autour d'une pièce qui ne seront plus utilisables
    public int nb_case_bloquees( LinkedList<Case> listeCases) {
        int res = 0;
        LinkedList<Case> l_voisins;
        LinkedList<Case> voisins_visites= new LinkedList<>();
        Iterator<Case> it = listeCases.iterator();
        while (it.hasNext()) {
            Case ca = it.next();
            l_voisins = jeu.getNiveau().voisinsCase(ca);
            Iterator<Case> it_vois = l_voisins.iterator();
            while (it_vois.hasNext()) {
                Case cb = it_vois.next();
                // on vérifie que le voisin n'a pas déjà été visité, qu'il est bien dans la grille et qu'il n'est pas dans la pièce
                if (jeu.getNiveau().getGrille()[cb.getX()][cb.getY()] == 0 && !voisins_visites.contains(cb) && !listeCases.contains(cb)) {
                    res++;
                    voisins_visites.add(cb);
                }
            }
            l_voisins.clear();
        }
        return res;
    }

    // nombre de coins que l'on bloque pour les adversaires lorsque l'on pose une pièce
    public int nb_possibilite_bloquees(int idCouleur, LinkedList<Case> listeCase) {
        int res = 0;
        for (int j = 0; j < jeu.getNbJoueurs(); j++) {
            Couleur[] C = new Couleur[2];
            C =jeu.getJoueur(j+1).getListeCouleur();

            for (int i = 0; i < jeu.getJoueur(j+1).getNbCouleurs(); i++) {
                if(C[i].getId() != idCouleur) {
                    HashSet<Case> l_coin = C[i].getListeCoins();
                    Iterator<Case> it_coin = l_coin.iterator();
                    while (it_coin.hasNext()) {
                        Case cb = it_coin.next();
                        if (listeCase.contains(cb)) {
                            res++;
                        }
                    }
                }
            }
        }

        return res;
    }

}

