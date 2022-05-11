package Controleur;

import Modele.*;
import Structures.Case;
import Structures.CoupleListeValeur;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

public class IAAleatoire extends IA {

    Random r;
    Controleur cont;

    IAAleatoire(Controleur c){
        r = new Random();
        cont = c;
    }


    @Override
    public void joue() {
        ListeChaine listePiecesDispo = copiePiecesDispo();

        int indexPiecesDispo;
        Piece p;

        int compteur=0;
        while(listePiecesDispo.getTaille()>0){
            System.out.println("compteur "+(++compteur));
            indexPiecesDispo = r.nextInt(listePiecesDispo.getTaille());
            p = listePiecesDispo.getListe().get(indexPiecesDispo);

            LinkedList<CoupleListeValeur<Case,Integer>> listeEmplacementPossible = cont.jeu.positionPossibleConfig(p);

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

                cont.joueIA(p,listeCases);
                return;
            }else{
                listePiecesDispo.supprimer(p.getId());
            }
        }
        System.out.println("Ia ne pas plus jouer, elle passe sont tour");
        cont.passerTour();

    }

    public ListeChaine copiePiecesDispo(){
        ListeChaine listePieces = new ListeChaine();

        LinkedList<Piece> liste = new LinkedList<>();
        Iterator<Piece> it = cont.jeu.getJoueur(cont.jeu.getIDJoueurCourant()).getCouleurCourante().getListePiecesDispo().iterateur();
        Piece p;
        while (it.hasNext()){
            p = it.next();
            liste.addLast(p);
        }
        listePieces.setListe(liste);

        return listePieces;
    }
}
