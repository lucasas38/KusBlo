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


    IAIntermediaire(Jeu j, int m) {
        super(j);
        mode = m;
        type = 2+mode;
    }

    // joue une pièce pour l'IA intermédiaire
    @Override
    public void joue() {
        ListePieces listePiecesDispo = copiePiecesDispo();
        Piece p_max = null;
        Piece p;
        int heur_max = 0;
        int rotation_max = 0;
        ListeValeur<Case, Piece> res =null;

        LinkedList<Case> listeCasesMax = null;

        if (listePiecesDispo.getTaille() > 18){     // pour les 3 premiers coups (ouvertures)
            res = ouvertures(listePiecesDispo);
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
                res = new ListeValeur<>(listeCasesMax, p_max); // bon emplacement ?

                listePiecesDispo.supprimer(p.getId());
            }

        }

        // res peut ne pas être null mais il peut contenir des choses null
        // a réfléchir pour savoir si c'est la bonne solution
        if(res!=null && res.getListe()!=null && res.getValeur()!=null){
            dernierCoup = res;
            return;
        }

        System.out.println("Ia ne peut plus jouer");
        dernierCoup=null;
    }

    @Override
    public String toString() {
        return "IAIntermediaire";
    }
}