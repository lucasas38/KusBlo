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

    int horizon = 2;

    int cpt = 0;

    IADifficile(Jeu j){
        super(j);
        type = 6;
        mode = 0;
    }

    @Override
    public ListeValeur<Case, Piece> joue() {
        System.out.println("IA difficile joue :");

        ListeValeur<Case, Piece> res = null;
        cpt = 0;
        ListePieces listePiecesDispo = jeu.getJoueurCourant().getCouleurCourante().getListePiecesDispo();
        if ( listePiecesDispo.getTaille() > 18){
            res = ouvertures(listePiecesDispo);
        } else {
            miniMax(jeu, horizon, 0);
            Piece p = jeu.getJoueurCourant().getCouleurCourante().getListePiecesDispo().getPiece(indicePieceaJouer);
            int i = 0;
            while (i < rotation) {
                if (i == 4) {
                    p.rotationSymetrique();
                } else {
                    p.rotationHoraire();
                }
                i++;
            }
            System.out.println("Liste case à jouer : " + listeCaseaJouer.toString());
            res = new ListeValeur<>(listeCaseaJouer, p);
        }
        if(res!=null && res.getListe()!=null && res.getValeur()!=null){
            return res;
        }
        System.out.println("Ia ne peut plus jouer");
        return null;
    }

    //fonction minMax
    // initialiser compteur_joueur à 0
    public int miniMax(Jeu jeu, int hor, int compteur_joueur){

        int n = 0;

        // cas de base, quand on arrive à l'horizon voulu où qu'il n'y a plus de coups possible pour le joueur en cours
        if(hor == 0){ // pour l'instant on laisse seulement horizon, à voir...
            return 0;
        }

        // cas récursif
        else{
            // pour l'instant on initialise les valeur avec les bornes max et min théorique de l'heuristique (0 et 109)
            int valeur;
            if (compteur_joueur == 0){
                valeur = 0;
            } else {
                valeur =1000000000; // on suppose que l'heuristique ne dépassera pas cette valeur
            }

            // en premier on récupère TOUS les coups possibles dans la configuration actuelle
            ListePieces l_piece_init = jeu.getJoueurCourant().getCouleurCourante().getListePiecesDispo();
            Iterator<Piece> it_piece = l_piece_init.iterateur();
            LinkedList<LinkedList<ListeValeur<Case, Integer>>> listeCoupPossible = new LinkedList<>();
            LinkedList<Integer> tab_indice_piece = new LinkedList<>();
            while(it_piece.hasNext()) {
                Piece p = it_piece.next();
                tab_indice_piece.add(p.getId());
                listeCoupPossible.add(jeu.positionPossibleConfig(p, jeu.getIDJoueurCourant(), jeu.getJoueurCourant().getIndiceTabCouleurCourant()));
            }

            // On parcourt les coups pour chaque pièce et on fait les appels récursifs correspondant
            HashMap<Integer,ListeValeur<Case,Integer>> h = new HashMap<Integer,ListeValeur<Case,Integer>>();
            HashMap<Integer,Integer> hNumPiece = new HashMap<Integer, Integer>();
            Iterator<LinkedList<ListeValeur<Case, Integer>>> it_liste_pieces = listeCoupPossible.iterator();
            Iterator<Integer> it_ind_tab = tab_indice_piece.iterator();
            while(it_liste_pieces.hasNext()){ // on parcourt la liste des coups par pièce
                int ind_piece = it_ind_tab.next();
                LinkedList<ListeValeur<Case, Integer>> listePiece = it_liste_pieces.next();
                Iterator<ListeValeur<Case, Integer>> it_piece2 = listePiece.iterator();
                while(it_piece2.hasNext()){ // on parcourt la liste des coups pour une seule pièce
                    ListeValeur<Case,Integer> piece = it_piece2.next();
                    // à voir
                    n = Heuristique(ind_piece,piece);
                    jeu.jouerPiece(jeu.getIDJoueurCourant(),jeu.getJoueurCourant().getIndiceTabCouleurCourant(), ind_piece, piece.getListe(), false);
                    if(compteur_joueur == 0) {
                        valeur = max(n, miniMax(jeu, hor - 1, (compteur_joueur + 1) % jeu.getNbJoueurs()));
                    } else {
                        valeur = min(valeur, miniMax(jeu, hor - 1, (compteur_joueur + 1) % jeu.getNbJoueurs()));
                    }
                    h.put(valeur,piece);
                    hNumPiece.put(valeur,ind_piece);
                    jeu.annuler();
                }

            }
            //parcourir hashmap et retourner la valeur de la plus grande clef (fonction max des clefs n'existe pas...)
            int max_hash_table = 0;
            Iterator<Integer> it_hash_table = h.keySet().iterator();
            while(it_hash_table.hasNext()){
                int valeur_clef = it_hash_table.next();
                if(valeur_clef > max_hash_table){
                    max_hash_table = valeur_clef;
                }
            }
            System.out.println("Nombre d'appels récursifs : " + cpt);

            // peut-être qu'on attribue ces variables que quand on est au sommet de l'arbre ?
            if (hor == horizon) {
                indicePieceaJouer = hNumPiece.get(max_hash_table);
                listeCaseaJouer = h.get(max_hash_table).getListe();
                rotation = h.get(max_hash_table).getValeur();
            }
            return max_hash_table;
        }
    }

    // heuristique d'évaluation d'une configuration
    // pour l'instant on fait une heuristique très simple, on améliorera plus tard
    // Faut-il utiliser la même heuristique que pour l'IA intermédiaire
    public int Heuristique(int ip,ListeValeur<Case,Integer> l){
        //return jeu.getJoueurCourant().getScore();
        cpt ++ ;
        /*System.out.println("Liste Pièce dispo : " + jeu.getJoueurCourant().getCouleurCourante().getListePiecesDispo().toString());
        System.out.println("IP : " + ip);*/
        //System.out.println("liste pièces préc: " + jeu.getJoueur(jeu.getIDJoueurCourant()-1).getCouleurCourante().getListePiecesDispo().toString());
        Piece p = jeu.getJoueurCourant().getCouleurCourante().getListePiecesDispo().getPiece(ip);
        /*System.out.println("liste pièce joueur courant: " +jeu.getJoueurCourant().getCouleurCourante().getListePiecesDispo().toString() );
        System.out.println("ID joueur courant: "+ jeu.getIDJoueurCourant());*/
        if (p == null) {
            System.out.println("pièce null, ip : " + ip);
        }
        int t = p.getTaille();
        int poss_ouv = nb_possibilite_ouverte(jeu.getIDJoueurCourant(),l.getListe());
        int poss_bloq = nb_possibilite_bloquees(jeu.getIDJoueurCourant(),l.getListe());
        int case_bloq = nb_case_bloquees(l.getListe());
        return calcul_heuristique(t,poss_ouv,poss_bloq,case_bloq);
    }

    @Override
    public String toString(){
        return "IADifficile";
    }

    @Override
    public int getType(){
        return type;
    }
}
