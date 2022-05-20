package Controleur;

import Modele.Couleur;
import Modele.Jeu;
import Modele.ListePieces;
import Modele.Piece;
import Structures.Case;
import Structures.ListeValeur;

import java.sql.SQLOutput;
import java.util.*;

public class IAIntermediaire extends IA {

    int mode; // mode de l'IA inetermédiaire (cf calcul_heuristique pour détail)
    int ouv; // type d'ouverture ( 0 : classique, 1 : Barasona)

    IAIntermediaire(Jeu j, int m) {
        super(j);
        mode = m;
        ouv = r.nextInt(2);
        type = 2+mode;
    }

    // joue une pièce pour l'IA intermédiaire
    @Override
    public ListeValeur<Case, Piece> joue() {
        ListePieces listePiecesDispo = copiePiecesDispo();
        Piece p_max = null;
        Piece p;
        int heur_max = 0;
        int rotation_max = 0;
        ListeValeur<Case, Piece> res =null;


        int ouverture = determ_ouv();

        LinkedList<Case> listeCasesMax = null;

        if (listePiecesDispo.getTaille() > 18){     // pour les 3 premiers coups (ouvertures)
            switch(ouverture){
                case 0:
                    res = ouvertures(listePiecesDispo); // on ne met pas le type d'ouverture en paramètres car il est déjà en attribut de la classe
                    break;
                case 1:
                    res = ouvertures(listePiecesDispo);
                    break;
                default : break;
            }
        } else {    // pour tous les coups suivants
            while (listePiecesDispo.getTaille() > 0) {

                p = listePiecesDispo.getListe().get(0);
                LinkedList<Case> listeCases = null;

                LinkedList<ListeValeur<Case, Integer>> listeEmplacementPossible = jeu.positionPossibleConfig(p,jeu.getIDJoueurCourant(),jeu.getJoueurCourant().getIndiceTabCouleurCourant());

                for (int i = 0; i < listeEmplacementPossible.size(); i++) { //tous les emplacment et les rotations pour une piece

                    listeCases = listeEmplacementPossible.get(i).getListe();
                    int rotation = listeEmplacementPossible.get(i).getValeur();
                    int poss_ouv = nb_possibilite_ouverte(jeu.getIDJoueurCourant(), listeCases);
                    int taille = p.getTaille();
                    int poss_bloq = nb_possibilite_bloquees(jeu.getIDJoueurCourant(), listeCases);
                    int case_bloq = nb_case_bloquees(listeCases);

                    int heur = calcul_heuristique(taille, poss_ouv,poss_bloq,case_bloq); // calcul heuristique

                    if (heur < 0) {
                        heur = 0;
                    }
                    //randomise les coups pour ne pas choisir toujours le même à chaque partie
                    int random_coup = 0;
                    if (heur == heur_max) {
                        random_coup = r.nextInt(2);
                    }

                    if (heur > heur_max || random_coup == 1) {
                        heur_max = heur;
                        p_max = p;
                        listeCasesMax = listeCases;
                        rotation_max = rotation;
                    }

                }
                int i = 0;
                while (i < rotation_max) {
                    if (i == 4) {
                        p_max.rotationSymetrique();
                    } else {
                        p_max.rotationHoraire();
                    }
                    i++;
                }
                res = new ListeValeur<>(listeCasesMax, p_max);

                listePiecesDispo.supprimer(p.getId());
            }
        }

        // res peut ne pas être null mais il peut contenir des choses null
        // a réfléchir pour savoir si c'est la bonne solution
        if(res!=null && res.getListe()!=null && res.getValeur()!=null){
            return res;
        }

        System.out.println("Ia ne peut plus jouer");
        return null;
    }

    // renvoie l'heuristique pour les IA intermédiaires
    // peut probablement être amélioré en faisant évoluer les coeficients au fur et à mesure de la partie
    int calcul_heuristique(int taille, int poss_ouv, int poss_bloq, int case_bloq){
        int res = 0;
        switch (mode) {
            case 0: // IA test
                res = 4*taille + 2*poss_ouv - case_bloq;
                break;
            case 1: // IA ouvrante
                res = 2 * taille + 2 * poss_ouv + poss_bloq - case_bloq;
                break;
            case 2: // IA privilégiant les grandes pièces
                res = 3 * taille + poss_ouv + poss_bloq - case_bloq;
                break;
            case 3: // IA pattern
                res = 2 * taille + poss_ouv + poss_bloq - 2 * case_bloq;
                break;
            default:
                break;
        }
        return res;
    }

    // renvoie le type d'ouverture pour chaque IA
    // on garde cette méthode pour pouvoir facilement changer les ouvertures de chaque IA pour les tests (sinon elle est inutile)
    int determ_ouv(){
        int ouverture = 0;
        switch (mode){
            case 0: // ouverture pour l'IA test
                ouverture = ouv;
                break;
            case 1:// ouverture pour l'IA ouvrante
                ouverture = ouv;
                break;
            case 2:// ouverture pour l'IA taille
                ouverture = ouv;
                break;
            case 3:// ouverture pour l'IA pattern
                ouverture = ouv;
                break;
            default : break;
        }
        return ouverture;
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
        tourne_piece(p); // oriente les pièces correctement pour leur prochain coup

        pos = determ_pos(couleur, 21-taille);

        l_case = jeu.tradMatrice(p, pos.getX() - 2*p.getDebMatrice().getX(),pos.getY() - 2*p.getDebMatrice().getY());
        res = new ListeValeur<>(l_case, p);
        return res;
    }

    // tourne les pièces pour qu'elles soient dans le bon sens pour l'IA inter
    public void tourne_piece(Piece p){
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

    @Override
    public String toString() {
        return "IAIntermediaire";
    }

    @Override
    public int getType(){
        return 2;
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


    public int nb_possibilite_ouverte(int idCouleur, LinkedList<Case> listeCases) {
        int res = 0;
        HashSet<Case> l_coin_piece = CoinsPieces(listeCases, idCouleur); //on doit recuperer la liste des nouveau coins
        Iterator<Case> it_coin = l_coin_piece.iterator();
        while (it_coin.hasNext()) {
            Case cb = it_coin.next();
            if (jeu.getNiveau().getGrille()[cb.getX()][cb.getY()] == 0) {
                res++;
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