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
    Random r;

    int ouv; // type d'ouverture ( 0 : classique, 1 : Barasona)
    int type; // 1 pour IA facile, 2-6 pour IA intermédiaire, 7 pour IA difficile

    ListeValeur<Case,Piece> dernierCoup;

    int mode; // mode de l'IA intermédiaire (cf calcul_heuristique pour détail)

    IA(Jeu j){
        jeu=j;
        setR();
    }

    public int getTypeIA(){ return type;}
    public abstract void joue();
    public abstract String toString();
    public void setR(){r=new Random();};

    // effectue la copie de la liste des pièces disponibles pour un joueur
    public ListePieces copiePiecesDispo(){
        ListePieces listePieces = new ListePieces(false);

        Iterator<Piece> it = jeu.getJoueurCourant().getCouleurCourante().getListePiecesDispo().iterateur();
        Piece p;
        while (it.hasNext()){
            p = it.next();
            listePieces.getListe().addLast(p);
        }
        listePieces.setListe(listePieces.getListe());

        return listePieces;
    }

    // modifie la pièce en fonction de la rotation donnée en argument
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

    // renvoie l'heuristique pour les IA intermédiaires et difficiles
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
            case 5 : // IA difficile
                res = 3 * taille + 2 * poss_ouv + poss_bloq - case_bloq;
                break;
            default:
                break;
        }
        return res;
    }

    // détermine les positions où les pièces doivent être jouées pour les ouvertures
    Case determ_pos(int num_joueur,int nb_coup){
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
        ListeValeur<Case,Piece> res;
        Case pos;
        LinkedList<Case> l_case;
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

        // on effectue les rotations nécessaires pour la pièce trouvée
        verif_rotation(p);
        rotaPieceOuverture(p);

        // on détermine la position de la pièce et on renvoie le résultat
        pos = determ_pos(couleur, 21-taille);
        l_case = jeu.tradMatrice(p, pos.getX() - 2*p.getDebMatrice().getX(),pos.getY() - 2*p.getDebMatrice().getY());
        res = new ListeValeur<>(l_case, p);
        return res;
    }

    // tourne les pièces pour qu'elles soient dans le bon sens pour l'IA inter (pour les ouvertures)
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

    // Calcule la liste des coins d'une pièce en fonction de la grille
    private HashSet<Case> CoinsPieces(LinkedList<Case> listeCases, int idJoueur) {
        Iterator<Case> it = listeCases.iterator();
        HashSet<Case> coins = new HashSet<>();
        // on parcourt chaque case de la pièce
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

    // Vérifie qu'une case est un coin valide pour une pièce
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

    //
    public int nb_possibilite_ouverte(int idCouleur, LinkedList<Case> listeCases, int nb_coup) {
        int res = 0;
        HashSet<Case> l_coin_piece = CoinsPieces(listeCases, idCouleur); //on doit recuperer la liste des nouveau coins
        Iterator<Case> it_coin = l_coin_piece.iterator();
        while (it_coin.hasNext()) {
            Case cb = it_coin.next();
            if (nb_coup == 18 || getTypeIA() == 7){ // l'IA difficile ne tient pas compte de l'espace
                res+=1;
            } else {
                res += espaceCase(cb);
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
            LinkedList<Case> voisins = jeu.getNiveau().voisinsCase(c2);
            Iterator<Case> itVois = voisins.iterator();
            // on parcourt les voisins de la case et si ce sont des cases vide on les ajoute à la liste
            while(itVois.hasNext()){
                Case cVois = itVois.next();
                if (!lCase.contains(cVois) && jeu.getNiveau().getGrille()[cVois.getX()][cVois.getY()] == 0){
                    res++;
                    lCase.add(cVois);
                }
            }
            itCase = lCase.iterator();
            compteurBoucle ++;
            int i = 0;
            while(i<compteurBoucle && itCase.hasNext()){
                itCase.next();
                i++;
            }

        }
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

