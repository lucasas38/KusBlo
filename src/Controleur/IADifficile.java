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
    int horizon = 1;
    int cpt = 0; // compte les appels récursifs

    IADifficile(Jeu j){
        super(j);
        type = 7;
        mode = r.nextInt(5); // randomise les heuristiques d'IA
        System.out.println("Je suis de type : " + mode);
    }

    @Override
    public void joue() {

        ListeValeur<Case, Piece> res = null;
        cpt = 0;
        ListePieces listePiecesDispo = jeu.getJoueurCourant().getCouleurCourante().getListePiecesDispo();
        if ( listePiecesDispo.getTaille() > 18){
            res = ouvertures(listePiecesDispo);
        } else {
            miniMax(jeu, horizon, 0,jeu.getIDJoueurCourant());
            Piece p = jeu.getJoueurCourant().getCouleurCourante().getListePiecesDispo().getPiece(indicePieceaJouer);
            tournePiece(rotation,p);
            res = new ListeValeur<>(listeCaseaJouer, p);
        }
        if(res!=null && res.getListe()!=null && res.getValeur()!=null){
            dernierCoup = res;
            return;
        }
        System.out.println("Ia ne peut plus jouer");
        dernierCoup=null;
    }

    //fonction minMax
    // initialiser compteur_joueur à 0
    public int miniMax(Jeu jeu, int hor, int compteur_joueur, int joueuraRegarder){

        int n;
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

        //on initialise avec des valeurs inatteignable pour l'heuristique
        int valeur ;
        if (compteur_joueur == 0) { // cas max
            valeur = -1000000000;
        } else { // cas min
            valeur = 1000000000;
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

                n = Heuristique(ind_piece,piece,joueuraRegarder);
                jeu.jouerPiece(jeu.getIDJoueurCourant(),jeu.getJoueurCourant().getIndiceTabCouleurCourant(), ind_piece, piece.getListe(), false);
                if (hor != 0) { // cas récursif
                    if (compteur_joueur == 0) {
                        valeur = max(n, miniMax(jeu, hor - 1, (compteur_joueur + 1) % jeu.getNbJoueurs(), joueuraRegarder));
                    } else {
                        valeur = min(valeur, miniMax(jeu, hor - 1, (compteur_joueur + 1) % jeu.getNbJoueurs(),joueuraRegarder));
                    }
                } else { // cas de base
                    valeur = n;
                }
                h.put(valeur,piece);
                hNumPiece.put(valeur,ind_piece);
                jeu.annuler();
            }

        }

        int indiceHashTable;

        if (compteur_joueur == 0) { // cas max : on prend la plus grande heuristique parmi celles calculées
            Iterator<Integer> it_hash_table = h.keySet().iterator();
            indiceHashTable = 0; //it_hash_table.next();
            while (it_hash_table.hasNext()) {
                int valeur_clef = it_hash_table.next();
                if (valeur_clef > indiceHashTable) {
                    indiceHashTable = valeur_clef;
                    //System.out.println("indiceHashTable : " + indiceHashTable);
                }
            }
        } else { // cas min : on prend la plus petite heuristique parmi celles calculées
            indiceHashTable = 1000000000;
            Iterator<Integer> it_hash_table = h.keySet().iterator();
            while (it_hash_table.hasNext()) {
                int valeur_clef = it_hash_table.next();
                if (valeur_clef < indiceHashTable) {
                    indiceHashTable = valeur_clef;
                }
            }
        }

        if (hor == horizon){ // si on est dans l'appel initial, on modifie les variables
            System.out.println("Nombre d'appels récursifs : " + cpt);
            indicePieceaJouer = hNumPiece.get(indiceHashTable);
            listeCaseaJouer = h.get(indiceHashTable).getListe();
            rotation = h.get(indiceHashTable).getValeur();
        }
        return indiceHashTable;
    }

    // heuristique d'évaluation d'une configuration
    // Pour l'instant même heuristique que pour l'IA intermédiaire
    public int Heuristique(int ip,ListeValeur<Case,Integer> l, int idJ){
        cpt ++ ;
        Piece p = jeu.getJoueurCourant().getCouleurCourante().getListePiecesDispo().getPiece(ip);
        int t = p.getTaille();
        int poss_ouv = nb_possibilite_ouverte(idJ,l.getListe(),jeu.getJoueurCourant().getCouleurCourante().getListePiecesDispo().getTaille());
        int poss_bloq = nb_possibilite_bloquees(idJ,l.getListe());
        int case_bloq = 0;
        if (idJ == jeu.getIDJoueurCourant()){
            case_bloq = nb_case_bloquees(l.getListe());
        }
        return calcul_heuristique(t,poss_ouv,poss_bloq,case_bloq);
    }

    @Override
    public String toString(){
        return "IADifficile";
    }
}
