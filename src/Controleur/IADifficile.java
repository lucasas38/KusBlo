package Controleur;

import Modele.Jeu;
import Modele.ListePieces;
import Modele.Piece;
import Modele.Joueur;
import Structures.Case;
import Structures.ListeValeur;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class IADifficile extends IA{

    LinkedList<Case> listeCaseaJouer;
    int indicePieceaJouer;
    int rotation;
    int horizon = 2; // nombre de coups que l'algorithme Min/Max va regarder à l'avance
    int cpt; // compte les appels récursifs

    boolean aide;

    IADifficile(Jeu j,boolean aide){
        super(j);
        type = 7;
        mode = 5; // détermine l'heuristique de l'IA difficile
        ouv = 0; // On force l'IA difficile à utiliser l'ouverture classique
        this.aide = aide;
    }

    // Joue une pièce pour l'IA difficile
    @Override
    public void joue() {

        ListeValeur<Case, Piece> res;
        cpt = 0;
        ListePieces listePiecesDispo = jeu.getJoueurCourant().getCouleurCourante().getListePiecesDispo();
        if ( listePiecesDispo.getTaille() > 18 && !aide){
            res = ouvertures(listePiecesDispo); // calcul des coups pour les ouvertures
        } else {
            alphaBeta(jeu, horizon, 0,-1000000000, 1000000000, jeu.getIDJoueurCourant());
            Piece p = jeu.getJoueurCourant().getCouleurCourante().getListePiecesDispo().getPiece(indicePieceaJouer);
            tournePiece(rotation,p);
            res = new ListeValeur<>(listeCaseaJouer, p);
        }
        if(res!=null && res.getListe()!=null && res.getValeur()!=null){
            dernierCoup = res;
            return;
        }
        dernierCoup=null;
    }


    public int alphaBeta(Jeu jeu, int hor, int compteur_joueur, int a, int b, int joueur) {

        int n;
        int joueuraregarder = joueur;
        int alpha = a;
        int beta = b;
        int valeur;

        // en premier on récupère TOUS les coups possibles dans la configuration actuelle
        ListePieces l_piece_init = jeu.getJoueurCourant().getCouleurCourante().getListePiecesDispo();
        Iterator<Piece> it_piece = l_piece_init.iterateur();
        LinkedList<LinkedList<ListeValeur<Case, Integer>>> listeCoupPossible = new LinkedList<>();
        LinkedList<Integer> tab_indice_piece = new LinkedList<>();
        while (it_piece.hasNext()) {
            Piece p = it_piece.next();
            tab_indice_piece.add(p.getId());
            listeCoupPossible.add(jeu.positionPossibleConfig(p, jeu.getIDJoueurCourant(), jeu.getJoueurCourant().getIndiceTabCouleurCourant()));
        }

        // On parcourt les coups pour chaque pièce et on fait les appels récursifs correspondant
        HashMap<Integer, ListeValeur<Case, Integer>> h = new HashMap<Integer, ListeValeur<Case, Integer>>();
        HashMap<Integer, Integer> hNumPiece = new HashMap<Integer, Integer>();
        Iterator<LinkedList<ListeValeur<Case, Integer>>> it_liste_pieces = listeCoupPossible.iterator();
        Iterator<Integer> it_ind_tab = tab_indice_piece.iterator();
        while (it_liste_pieces.hasNext()) { // pour chaque pièce
            int ind_piece = it_ind_tab.next();
            LinkedList<ListeValeur<Case, Integer>> listePiece = it_liste_pieces.next();
            Iterator<ListeValeur<Case, Integer>> it_piece2 = listePiece.iterator();
            while (it_piece2.hasNext()) { // pour chaque coup possible avec cette pièce
                ListeValeur<Case, Integer> piece = it_piece2.next();
                // on calcule l'heuristique
                n = Heuristique(ind_piece, piece, joueuraregarder);
                // on joue la pièce sur la grille puis on fait les appels récursifs correspondant
                jeu.jouerPiece(jeu.getIDJoueurCourant(), jeu.getJoueurCourant().getIndiceTabCouleurCourant(), ind_piece, piece.getListe(), false);
                if (hor != 0) {
                    if (compteur_joueur == 0) {
                        alpha = max(n, alphaBeta(jeu, hor - 1, (compteur_joueur + 1) % jeu.getNbJoueurs(), alpha, beta, joueuraregarder));
                        if (alpha >= beta) {
                            valeur = beta;
                            h.put(valeur, piece);
                            hNumPiece.put(valeur, ind_piece);
                            jeu.annuler();
                            break;
                        } else {
                            valeur = alpha;
                            h.put(valeur, piece);
                            hNumPiece.put(valeur, ind_piece);
                            jeu.annuler();
                        }
                    } else {
                        beta = min(beta, alphaBeta(jeu, hor - 1, (compteur_joueur + 1) % jeu.getNbJoueurs(), alpha, beta, joueuraregarder));

                        if (alpha >= beta) {
                            valeur = alpha;
                            h.put(valeur, piece);
                            hNumPiece.put(valeur, ind_piece);
                            jeu.annuler();
                            break;
                        } else {
                            valeur = beta;
                            h.put(valeur, piece);
                            hNumPiece.put(valeur, ind_piece);
                            jeu.annuler();
                        }
                    }
                } else {
                    jeu.annuler();
                }
            }
            if (alpha >= beta) {
                break;
            }

        }

        int max_hash_table = 0;
        Iterator<Integer> it_hash_table = h.keySet().iterator();
        while (it_hash_table.hasNext()) {
            int valeur_clef = it_hash_table.next();
            if (valeur_clef > max_hash_table) {
                max_hash_table = valeur_clef;
            }
        }

        // Quand on est au sommet de l'arbre, on stocke la rotation et la pièce à jouer
        if (hor == horizon) {
            //System.out.println("Nombre d'appels récursifs : " + cpt);
            indicePieceaJouer = hNumPiece.get(max_hash_table);
            listeCaseaJouer = h.get(max_hash_table).getListe();
            rotation = h.get(max_hash_table).getValeur();
        }
        return max_hash_table;

    }

    // Calcul l'heuristique en fonction du cas min et max
    public int Heuristique(int ip,ListeValeur<Case,Integer> l, int idJ){
        cpt ++ ;
        Piece p = jeu.getJoueurCourant().getCouleurCourante().getListePiecesDispo().getPiece(ip);
        int t = p.getTaille();
        int poss_ouv = nb_possibilite_ouverte(idJ,l.getListe(),jeu.getJoueurCourant().getCouleurCourante().getListePiecesDispo().getTaille());
        int poss_bloq = nb_possibilite_bloquees(idJ,l.getListe());
        int case_bloq = 0;
        if (idJ == jeu.getIDJoueurCourant()){ // on ne regarde le nombre de cases perdues que si on est dans le cas max
            case_bloq = nb_case_bloquees(l.getListe());
        }
        return calcul_heuristique(t,poss_ouv,poss_bloq,case_bloq);
    }

    @Override
    public String toString(){
        return "IADifficile";
    }
}
