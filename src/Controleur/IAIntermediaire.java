package Controleur;

import Modele.Couleur;
import Modele.Jeu;
import Modele.ListePieces;
import Modele.Piece;
import Structures.Case;
import Structures.ListeValeur;

import java.util.*;

public class IAIntermediaire extends IA {


    IAIntermediaire(Jeu j) {
        super(j);
    }

    @Override
    public ListeValeur<Case, Piece> joue() {
        ListePieces listePiecesDispo = copiePiecesDispo();
        int indexPiecesDispo;
        Piece p_max = null;
        Piece p;
        int valeur_max = 0;
        int rotation_max = 0;
        Case emplacement_max = null;
        ListeValeur<Case, Piece> res =null;

        LinkedList<Case> listeCasesMax = null;

        while (listePiecesDispo.getTaille() > 0) {

            p = listePiecesDispo.getListe().get(0);
            LinkedList<Case> listeCases = null;

            LinkedList<ListeValeur<Case, Integer>> listeEmplacementPossible = jeu.positionPossibleConfig(p);

            for (int i = 0; i < listeEmplacementPossible.size(); i++) { //tous les emplacment et les rotations pour une piece

                listeCases = listeEmplacementPossible.get(i).getListe();
                int rotation = listeEmplacementPossible.get(i).getValeur();
                int valeur = 0;
                valeur =  4*p.getTaille()+ 2*nb_possibilite_ouverte(jeu.getIDJoueurCourant(), listeCases) + 2*nb_possibilite_bloquees(jeu.getIDJoueurCourant(), listeCases) -nb_case_bloquees(listeCases);
                if(valeur<0) valeur=0;
                if (valeur >= valeur_max) {
                    valeur_max = valeur;
                    p_max = p;
                    listeCasesMax=listeCases;
                    rotation_max = rotation;
                }

            }
            int i=0;
            while(i<rotation_max){
                if(i == 4){
                    p.rotationSymetrique();
                }else{
                    p.rotationHoraire();
                }
                i++;
            }
            res = new ListeValeur<>(listeCasesMax, p_max);

            listePiecesDispo.supprimer(p.getId());
        }


        if(res!=null){
            return res;
        }

        System.out.println("Ia ne peut plus jouer");

        jeu.finCouleur();

        return null;

    }

    @Override
    public String toString() {
        return "IAIntermediaire";
    }

    @Override
    public void setR() {

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
        for (int i = 0; i < voisins.size(); i++) {
            if (listeCases.contains(voisins.get(i))) {
                voisinEstPasDansListeCase = false;
            }
        }

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
        HashSet<Case> l_coin_piece = CoinsPieces(listeCases, jeu.getIDJoueurCourant()); //on doit recuperer la liste des nouveau coins
        if(l_coin_piece == null){
            return 0;
        }
        Iterator<Case> it_coin = l_coin_piece.iterator();
        while (it_coin.hasNext()) {
            Case cb = it_coin.next();
            if (jeu.getNiveau().getGrille()[cb.getX()][cb.getY()] == 0) {
                res++;
            }
        }
        return res;
    }


    public int nb_case_bloquees( LinkedList<Case> listeCases) {
        int res = 0;
        LinkedList<Case> l_voisins;
        LinkedList<Case> voisins_visites= new LinkedList<>();
        Iterator<Case> it = listeCases.iterator();
        while (it.hasNext()) {
            Case ca = it.next();
            l_voisins = jeu.getNiveau().voisinsCase(ca);

            //System.out.println(l_voisins.toString());
            Iterator<Case> it_vois = l_voisins.iterator();
            while (it_vois.hasNext()) {
                Case cb = it_vois.next();

                if (jeu.getNiveau().getGrille()[cb.getX()][cb.getY()] == 0 && !voisins_visites.contains(cb) && !listeCases.contains(cb)) {
                    res++;
                    voisins_visites.add(cb);
                }
            }
            l_voisins.clear();
        }
        return res;
    }


    public int nb_possibilite_bloquees(int idCouleur, LinkedList<Case> listeCase) {
        int res = 0;
        for (int j = 0; j < jeu.getNbJoueurs(); j++) {
            Couleur[] C = new Couleur[2];
            C =jeu.getJoueur(j+1).getListeCouleur();

            for (int i = 0; i < jeu.getJoueur(j+1).getNbCouleurs(); i++) {
                if(C[i].getId() != idCouleur) {
                    //System.out.println("couleur: " + i + C[i].getId());
                    //  System.out.println();
                    HashSet<Case> l_coin = C[i].getListeCoins();
                    // System.out.println("Les coins :" + l_coin.toString());
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