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

    public IAAleatoire(Controleur c){
        r = new Random();
        cont = c;
    }


    @Override
    public void joue() {

        ListePieces listePiecesDispo = copiePiecesDispo();

        int indexPiecesDispo;
        Piece p;

        while(listePiecesDispo.getTaille()>0){
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
        System.out.println("Ia ne peut plus jouer");


//        cont.jeu.passerTour();
        cont.setMenu1();

    }

    public ListePieces copiePiecesDispo(){
        ListePieces listePieces = new ListePieces();

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
