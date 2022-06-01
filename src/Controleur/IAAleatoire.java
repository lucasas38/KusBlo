package Controleur;

import Modele.*;
import Structures.Case;
import Structures.ListeValeur;

import java.util.LinkedList;

public class IAAleatoire extends IA {

    public IAAleatoire(Jeu j){
        super(j);
        type = 1;
    }


    @Override
    public void joue() {
        // on copie la liste des pièces dispo
        ListePieces listePiecesDispo = copiePiecesDispo();

        int indexPiecesDispo;
        Piece p;

        while(listePiecesDispo.getTaille()>0){
            // on tire une pièce au hasard
            indexPiecesDispo = r.nextInt(listePiecesDispo.getTaille());
            p = listePiecesDispo.getListe().get(indexPiecesDispo);

            // on calcule tous les coups possible pour cette pièce
            LinkedList<ListeValeur<Case,Integer>> listeEmplacementPossible = jeu.positionPossibleConfig(p,jeu.getIDJoueurCourant(),jeu.getJoueurCourant().getIndiceTabCouleurCourant());

            if(listeEmplacementPossible.size()>0){
                // on tire au hasard un emplacement parmi ceux calculés précédemment
                int indexEmplacementPossible = r.nextInt(listeEmplacementPossible.size());

                LinkedList<Case> listeCases = listeEmplacementPossible.get(indexEmplacementPossible).getListe();
                int i = 0;
                int rotation = listeEmplacementPossible.get(indexEmplacementPossible).getValeur();
                while(i<rotation){
                    if(i == 4){
                        p.rotationSymetrique();
                    }else{
                        p.rotationHoraire();
                    }
                    i++;
                }

                // on joue la pièce à l'emplacement trouvé
                ListeValeur<Case,Piece> res = new ListeValeur<>(listeCases,p);
                dernierCoup = res;
                return;

            }else{
                // si la pièce n'était pas jouable, on la supprime de la liste et on recommence avec une nouvelle pièce
                listePiecesDispo.supprimer(p.getId());
            }
        }
        System.out.println("Ia ne peut plus jouer");

        dernierCoup=null;

    }

    @Override
    public String toString(){
        return "IAAleatoire";
    }

}
