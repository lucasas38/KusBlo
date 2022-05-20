package Controleur;

import Modele.*;
import Structures.Case;
import Structures.ListeValeur;

import java.util.LinkedList;

public class IAAleatoire extends IA {

    public IAAleatoire(Jeu j){
        super(j);
    }


    @Override
    public ListeValeur<Case,Piece> joue() {

        ListePieces listePiecesDispo = copiePiecesDispo();

        int indexPiecesDispo;
        Piece p;

        while(listePiecesDispo.getTaille()>0){
            indexPiecesDispo = r.nextInt(listePiecesDispo.getTaille());
            p = listePiecesDispo.getListe().get(indexPiecesDispo);

            LinkedList<ListeValeur<Case,Integer>> listeEmplacementPossible = jeu.positionPossibleConfig(p,jeu.getIDJoueurCourant(),jeu.getJoueurCourant().getIndiceTabCouleurCourant());

            if(listeEmplacementPossible.size()>0){
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

                ListeValeur<Case,Piece> res = new ListeValeur<>(listeCases,p);
                return res;

            }else{
                listePiecesDispo.supprimer(p.getId());
            }
        }
        System.out.println("Ia ne peut plus jouer");

        return null;

    }

    @Override
    public String toString(){
        return "IAAleatoire";
    }

    @Override
    public int getType(){
        return 1;
    }

}
