package Controleur;

import Modele.Couleur;
import Modele.Jeu;
import Modele.ListePieces;
import Modele.Piece;
import Structures.Case;
import Structures.ListeValeur;
import java.util.*;

public class IAIntermediaire extends IA {

    IAIntermediaire(Jeu j, int m, boolean aide) {
        super(j);
        mode = m;
        type = 2+mode;
        ouv = r.nextInt(2); // les ouvertures sont tirés aléatoirement
        this.aide = aide;
    }

    // joue une pièce pour l'IA intermédiaire
    @Override
    public void joue() {
        ListePieces listePiecesDispo = copiePiecesDispo();
        Piece p_max = null;
        Piece p;
        int heur_max = 0;
        int rotation_max = 0;
        ListeValeur<Case, Piece> res;

        LinkedList<Case> listeCasesMax = null;

        if (listePiecesDispo.getTaille() > 18 && !aide){     // pour les 3 premiers coups on joue les ouvertures
            res = ouvertures(listePiecesDispo);
        } else {
            // pour chacune des pièces, on calcule l'ensemble de ses coups possibles et on garde le meilleur en fonction de l'heuristique
            while (listePiecesDispo.getTaille() > 0) {

                p = listePiecesDispo.getListe().get(0);
                LinkedList<Case> listeCases;

                LinkedList<ListeValeur<Case, Integer>> listeEmplacementPossible = jeu.positionPossibleConfig(p,jeu.getIDJoueurCourant(),jeu.getJoueurCourant().getIndiceTabCouleurCourant());

                for (int i = 0; i < listeEmplacementPossible.size(); i++) {
                    listeCases = listeEmplacementPossible.get(i).getListe();

                    // calcul des différents paramètres de l'heuristique
                    int rotation = listeEmplacementPossible.get(i).getValeur();
                    int poss_ouv = nb_possibilite_ouverte(jeu.getIDJoueurCourant(), listeCases, jeu.getJoueurCourant().getCouleurCourante().getListePiecesDispo().getTaille());
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
                listePiecesDispo.supprimer(p.getId());
            }
        // on effectue la rotation pour la pièce
        tournePiece(rotation_max,p_max);
        res = new ListeValeur<>(listeCasesMax, p_max);
        }

        // res peut ne pas être null mais il peut contenir des choses null
        if(res!=null && res.getListe()!=null && res.getValeur()!=null){
            dernierCoup = res;
            return;
        }

        dernierCoup=null;
    }

    @Override
    public String toString() {
        return "IAIntermediaire";
    }
}