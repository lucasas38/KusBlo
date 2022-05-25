package Controleur;

import Global.Configuration;
import Modele.*;
import Structures.Case;
import Structures.ListeValeur;
import Structures.Trio;
import Vue.InterfaceKusBlo;
import Vue.MenuPiece;

import java.util.LinkedList;
import java.util.Random;

public class Controleur {
    Jeu jeu;
    InterfaceKusBlo inter;
    IA[] ia;
    boolean animActiv;
    boolean pause;
    int oldX;
    int oldY;

    public Controleur(){
        animActiv = Boolean.parseBoolean(Configuration.instance().lis("AnimActive"));
    }

    public void addIA(int type_ia,int idJoueur){
        // les "case" 2,4,5,6 sont des IA intermédiaires avec chacune une heuristique différente (2 étant la meilleure)
        switch (type_ia){
            case 1:
                ia[idJoueur-1] = new IAAleatoire(this.jeu);
                break;
            case 2:
                ia[idJoueur-1] = new IAIntermediaire(this.jeu, 0,false);
                break;
            case 3:
                ia[idJoueur-1] = new IAIntermediaire(this.jeu, 1,false);
                break;
            case 4:
                ia[idJoueur-1] = new IAIntermediaire(this.jeu, 2,false);
                break;
            case 5:
                ia[idJoueur-1] = new IAIntermediaire(this.jeu, 3,false);
                break;
            case 6:
                ia[idJoueur-1] = new IADifficile(this.jeu);
                break;
            default:
                ia[idJoueur-1] = new IAAleatoire(this.jeu);
                break;
        }
    }

    public void ajouteInterfaceUtilisateur(InterfaceKusBlo v) {
        inter = v;
    }

    public void setMenu1(){
        inter.getInterJ().getGraph().stopTimer();
        if(pause){
            setMenu5();
        }else{
            setScoreToutLesJoueurs();

            //on verifie si la couleur peut encore jouer des pieces
            jeu.getJoueurCourant().setRestePieceJouableCouleur(jeu.getJoueurCourant().getIndiceTabCouleurCourant(),
                    jeu.restePieceJouable(jeu.getIDJoueurCourant(),jeu.getJoueurCourant().getIndiceTabCouleurCourant()));


        if(isFinJeu()){
            inter.getInterJ().cleanTour();
            inter.getInterJ().delMouseClick();
            int maxScore=-10000;
            int[] vainqueurs= new int[4];
            int nbVainqueur=1;
            for (int i=1;i<jeu.getNbJoueurs()+1;i++){
                if(jeu.getJoueur(i).getScore()>maxScore){
                    maxScore=jeu.getJoueur(i).getScore();
                    nbVainqueur=1;
                    vainqueurs[nbVainqueur-1]=i;
                }else if(jeu.getJoueur(i).getScore()==maxScore){
                    nbVainqueur++;
                    vainqueurs[nbVainqueur-1]=i;
                }
            }
            inter.getInterJ().getM().setMenuFinPartie(vainqueurs,nbVainqueur);
            setScoreToutLesJoueurs();

            }else {
                if (jeu.getJoueur(jeu.getIDJoueurCourant()).getCouleurCourante().isRestePieceJouable()) {
                        inter.getInterJ().setTour(jeu.getNumCouleurCourante());
                        if(ia[jeu.getIDJoueurCourant()-1] != null){
                            inter.getInterJ().delMouseClick();
                            inter.getInterJ().setEnabledAide(false);
                            joueIA();
                        }else{
                            inter.getInterJ().setEnabledAide(true);
                            inter.getInterJ().setMenu1(jeu.getIDJoueurCourant(), jeu.getNumCouleurCourante());
                            inter.getInterJ().actMenu1(jeu.getNumCouleurCourante(), true);
                        }
                } else {
                    passerTour();
                }
            }
        }

    }

    public void setMenu2(int l, int c){
        int numPiece= l*7+c;
        inter.getInterJ().setMenu2(numPiece);
    }

    public void click(int x, int y){
        delVisu(oldX,oldY);
        inter.getInterJ().actMenu1(jeu.getNumCouleurCourante(), false);
        Piece piece= inter.getInterJ().getM().getPiece();
        int decx=piece.getDecx();
        int decy=piece.getDecy();
        inter.getInterJ().delMouseClick();
        inter.getInterJ().getGraph().poserPiece(jeu.getNumCouleurCourante(), x, y, piece.getMatrice(),decx,decy);
        jeu.jouerPiece(jeu.getIDJoueurCourant(),jeu.getJoueurCourant().getIndiceTabCouleurCourant(),inter.getInterJ().getM().getNumPiece(), jeu.tradMatrice(piece, x-decx,y-decy ),false);
        inter.getInterJ().getM().resetBorder();
        Trio<Piece,Integer,Integer> passe = jeu.getHistorique().getPasse().getFirst();
        Piece pPrec = passe.getE1();
        Integer idJoueurPrec = passe.getE2();
        Integer indTabCouleurJoueurPrec=passe.getE3();
        int idCouleurPrec = jeu.getJoueur(idJoueurPrec).getCouleur(indTabCouleurJoueurPrec).getId();
        inter.getInterJ().refreshPanJoueur( idCouleurPrec-1,pPrec.getId(),false,null);
        setMenu1();
        inter.setAnnuler(true);
        inter.setRefaire(false);
    }

    public void joueIA2(){
        jeu.jouerPiece(jeu.getIDJoueurCourant(),jeu.getJoueurCourant().getIndiceTabCouleurCourant(),ia[jeu.getIDJoueurCourant()-1].dernierCoup.getValeur().getId(), ia[jeu.getIDJoueurCourant()-1].dernierCoup.getListe(),false);
        inter.getInterJ().getM().resetBorder();
        Trio<Piece,Integer,Integer> passe = jeu.getHistorique().getPasse().getFirst();
        Piece pPrec = passe.getE1();
        Integer idJoueurPrec = passe.getE2();
        Integer indTabCouleurJoueurPrec=passe.getE3();
        int idCouleurPrec = jeu.getJoueur(idJoueurPrec).getCouleur(indTabCouleurJoueurPrec).getId();
        inter.getInterJ().refreshPanJoueur( idCouleurPrec-1,pPrec.getId(),false,null);
        setMenu1();
        inter.setAnnuler(true);
        inter.setRefaire(false);
    }


    public void joueIA(){
        ia[jeu.getIDJoueurCourant()-1].joue();
        if(ia[jeu.getIDJoueurCourant()-1].dernierCoup != null){
            if(animActiv){
                inter.getInterJ().getGraph().poserPieceIA(ia[jeu.getIDJoueurCourant()-1].dernierCoup.getListe(),jeu.getNumCouleurCourante());
            }else{
                inter.getInterJ().getGraph().poserPiece(jeu.getNumCouleurCourante(), ia[jeu.getIDJoueurCourant()-1].dernierCoup.getListe());
                joueIA2();
            }
        }else{
            setMenu1();
        }
    }

    public boolean estPosable2(int x, int y){
        Piece piece = inter.getInterJ().getM().getPiece();
        int decx= piece.getDecx();
        int decy= piece.getDecy();
        Niveau n= jeu.getNiveau();
        return n.estPosable(piece, x-decx, y-decy);
    }

    public  boolean estPosable(int x, int y){
        Piece piece = inter.getInterJ().getM().getPiece();
        int decx = piece.getDecx();
        int decy= piece.getDecy();
        for(int i=0;i<5;i++){
            for(int j=0; j<5; j++){
                if(x+i-decx>19 || x+i-decx<0 || y+j-decy>19 || y+j-decy<0 ){
                    if(piece.getMatrice()[i][j]!=0){
                        return  false;
                    }
                }
            }
        }
        return true;
    }

    public  boolean estPosableRegle(int x, int y){
        Piece piece = inter.getInterJ().getM().getPiece();
        int decx=piece.getDecx();
        int decy= piece.getDecy();
        return  jeu.estPosableRegle(jeu.tradMatrice(piece,x-decx,y-decy),jeu.getIDJoueurCourant(),jeu.getJoueurCourant().getIndiceTabCouleurCourant());
    }

    public  Piece getPiece(int j, int p){
        return  jeu.getJoueur(j).getCouleurCourante().getListePiecesDispo().getPiece(p);
    }

    public ListePieces getListPiece(int couleur){
        if(getNbJoueur()==2){
            if(couleur>2){
                return jeu.getJoueur(couleur-2).getListeCouleur()[1].getListePiecesDispo();
            } else {
                return jeu.getJoueur(couleur).getListeCouleur()[0].getListePiecesDispo();
            }

        } else{
            return jeu.getJoueur(couleur).getCouleurCourante().getListePiecesDispo();
        }

    }

    public void visualiser(int x, int y,int[][] grille, int decx,int decy,boolean error){
        if(error){
            inter.getInterJ().getGraph().visualiser(5,x,y,grille,decx,decy);
        } else{
            inter.getInterJ().getGraph().visualiser(jeu.getNumCouleurCourante(),x,y,grille,decx,decy);
        }
    }

    public void visualiser(int x, int y,boolean error){
        Piece p= inter.getInterJ().getM().getPiece();
        if(error){
            inter.getInterJ().getGraph().visualiser(5,x,y,p.getMatrice(),p.getDecx(),p.getDecy());
        } else{
            inter.getInterJ().getGraph().visualiser(jeu.getNumCouleurCourante(),x,y,p.getMatrice(),p.getDecx(),p.getDecy());
        }
    }

    public void delVisu(int x, int y){
        Piece p =inter.getInterJ().getM().getPiece();
        inter.getInterJ().getGraph().supprimerVisualisation(x,y,p.getMatrice(), p.getDecx(),p.getDecy());
    }

    public int getActCouleur(){
        return jeu.getNumCouleurCourante();
    }

    public int getActJoueur(){
        return jeu.getIDJoueurCourant();
    }

    public boolean contientPiece(int id){
        return jeu.getJoueur(jeu.getIDJoueurCourant()).getCouleurCourante().getListePiecesDispo().contient(id);
    }

    public void antiHoraire(){
        MenuPiece m= inter.getInterJ().getM();
        m.getPiece().rotationAntiHoraire();
        m.refreshPiece(jeu.getNumCouleurCourante());
    }

    public void rotaHoraire(){
        MenuPiece m= inter.getInterJ().getM();
        m.getPiece().rotationHoraire();
        m.refreshPiece(jeu.getNumCouleurCourante());

    }

    public void flip(){
        MenuPiece m= inter.getInterJ().getM();
        m.getPiece().rotationSymetrique();
        m.refreshPiece(jeu.getNumCouleurCourante());
    }

    public void toucheClavier(String touche) {
        switch (touche) {
            case "Left":
                rotaHoraire();
                break;
            case "Right":
                antiHoraire();
                break;
            case "Up":
            case "Down":
                flip();
                break;
            case "Quit":
                System.exit(0);
                break;
            default:
                System.out.println("Touche inconnue : " + touche);
        }
    }
    public int getNbJoueur(){
        return  jeu.getNbJoueurs();
    }

    public void selPiece(int l, int c){
        setMenu2(l,c);
        inter.getInterJ().getM().selPiece(l*7+c+1);
    }

    public void passerTour(){
        jeu.passerTour(jeu.getIDJoueurCourant(),jeu.getJoueurCourant().getIndiceTabCouleurCourant());
        setMenu1();
    }

    public int getFrameH(){
        return inter.getH();
    }

    public int getFrameW(){
        return inter.getW();
    }

    public void newGame(int nbJoueur, int j1,int j2,int j3, int j4, boolean rejouer){
        if(inter.getInterJ() != null){
            inter.getInterJ().getGraph().stopTimer();
        }
        int[] joueur = new int[4];

        if(rejouer){
            nbJoueur=jeu.getNbJoueurs();
            for(int i=0; i<nbJoueur; i++){
                if(ia[i]!=null){
                    joueur[i]=ia[i].getTypeIA();// à changer pour garder la même IA qu'avant
                }else{
                    joueur[i]=0;
                }
            }

        }else{
            joueur[0]=j1;
            joueur[1]=j2;
            joueur[2]=j3;
            joueur[3]=j4;
        }

        jeu = new Jeu(nbJoueur);
        ia = new IA[jeu.getNbJoueurs()];
        for(int i=0; i<nbJoueur; i++){
            if(joueur[i]!=0){
                addIA(joueur[i],i+1);
            }
        }

        inter.setInterJeu();

        for(int i=0; i<nbJoueur; i++){
            if(ia[i]!=null){
                if(nbJoueur==2){
                    inter.updateNameIA(i+2,ia[i].getTypeIA());
                }
                inter.updateNameIA(i,ia[i].getTypeIA());
            }else{
                joueur[i]=0;
            }
        }
        //utilisé pour les test, merci de ne pas l'enlever !!
        /*addIA(2,1, 0);
        addIA(1,2, 2);
        addIA(2,3, 2);
        addIA(1,4, 0);*/

        setMenu1();
    }

    public void menu(){
        inter.setMenu();
    }

    public boolean isFinJeu(){
        for(int i=1; i<jeu.getNbJoueurs()+1;i++){
            if(jeu.getJoueur(i).isPeutJouer()){
                return false;
            }
        }
        return true;
    }

    public  void setMenu4(){
        inter.getInterJ().getM().setMenuTourIA();
    }

    public  void setMenu5(){
        inter.getInterJ().getM().setMenuHistorique();
    }

    public void visuIA(LinkedList<Case> listeCase, int couleur){
        inter.getInterJ().getGraph().visualiser(couleur,listeCase);
    }

    public void poserPiece(LinkedList<Case> listeCase, int couleur){
        inter.getInterJ().getGraph().poserPiece(couleur,listeCase);
    }

    public void showMenuOpt(){
        inter.getInterJ().getGraph().stopTimerAide();
        inter.getInterJ().getGraph().stopTimer();
        inter.getInterJ().setMenuOpt();
    }

    public void reprendre(){
        inter.getInterJ().getGraph().starTimerAide();
        if(ia[jeu.getIDJoueurCourant()-1]!=null && !pause){
            inter.getInterJ().getGraph().startTimer();
        }
        inter.getInterJ().reprendre();
        if(!jeu.getHistorique().peutAnnuler()){
            inter.setAnnuler(false);
        }
        if(!jeu.getHistorique().peutRefaire()){
            inter.setRefaire(false);
        }
    }

    public void save(){
        Sauvegarde sauvegarde = new Sauvegarde();
        sauvegarde.ecrire(jeu,ia);
    }

    public void load(){
        Chargement chargement = new Chargement();
        chargement.lire();
        jeu=chargement.getJeu(); //: recupere Jeu jeu;
        ia=chargement.getIa();// : recupere IA[] ia;
        if(jeu != null && ia != null){
            inter.setInterJeu();
            inter.charger();
            pause();
            updateBoutPause(false);
            if(jeu.getHistorique().peutAnnuler()){
                inter.setAnnuler(true);
            }
            if(jeu.getHistorique().peutRefaire()){
                inter.setRefaire(true);
            }
        }

    }

    public void setMenuSolo(){
        inter.setMenuSolo();
    }

    public void setMenuMulti(){
        inter.setMenuMulti();
    }

    public void setMenuPerso(){
        inter.setMenuPerso();
    }

    public int getCouleur(int i, int j){
        return jeu.getNiveau().getGrille()[i][j];
    }

    public void pause(){
        System.out.println("pause");
        inter.getInterJ().actMenu1(getActCouleur(),false);
        if(ia[getActJoueur()-1]!=null && !pause){
            inter.getInterJ().getGraph().supprimerVisualisation(ia[jeu.getIDJoueurCourant()-1].dernierCoup.getListe());
            inter.getInterJ().setTour(getActCouleur());
        }

        stopTimer();
        setPause(true);
        setMenu5();
    }

    public void annuler(){
        if(!pause){
            pause();
        }

        if(jeu.getHistorique().peutAnnuler()){

            Trio<Piece,Integer,Integer> passe = jeu.getHistorique().getPasse().getFirst();
            Piece pPrec = passe.getE1();
            Integer idJoueurPrec = passe.getE2();
            Integer indTabCouleurJoueurPrec=passe.getE3();

            inter.getInterJ().delMouseClick();

            int idCouleurPrec = jeu.getJoueur(idJoueurPrec).getCouleur(indTabCouleurJoueurPrec).getId();

            inter.getInterJ().getGraph().retirerPiece(pPrec.getListeCases());
            jeu.annuler();
            inter.getInterJ().getM().resetBorder();
            inter.getInterJ().refreshPanJoueur(idCouleurPrec-1,pPrec.getId(),true, pPrec);
            inter.getInterJ().setTour(getActCouleur());

        }else{
            System.out.println("Pas de coup antérieur");
        }
        setMenu5();
        setScoreToutLesJoueurs();
        if(!jeu.getHistorique().peutAnnuler()){
            inter.setAnnuler(false);
        }

        inter.setRefaire(true);
    }



    public void refaire(){
        Trio<Piece,Integer,Integer> prochain = jeu.getHistorique().refaire();
        if(prochain!=null){
            Piece pProchain = prochain.getE1();
            Integer idJoueurProc = prochain.getE2();
            Integer indTabCouleurJoueurProc = prochain.getE3();
            int idCouleurProc = jeu.getJoueur(idJoueurProc).getCouleur(indTabCouleurJoueurProc).getId();

            inter.getInterJ().delMouseClick();
            inter.getInterJ().getGraph().poserPiece(idCouleurProc,pProchain.getListeCases());
            jeu.jouerPiece(idJoueurProc,indTabCouleurJoueurProc,pProchain.getId(), pProchain.getListeCases(),true);

            //met à jour piece jouable de la couleur
            jeu.getJoueur(idJoueurProc).setRestePieceJouableCouleur(indTabCouleurJoueurProc,jeu.restePieceJouable(idJoueurProc,indTabCouleurJoueurProc));

            //met à jour le joueur et la couleur car pas fait dans jouerPiece (car refaire == true)
            Integer idJoueurFutur;
            Integer indTabCouleurJoueurFutur;

            if(jeu.getHistorique().getFutur().isEmpty()){
                //passe a la couleur suivante du joueur qui vient de refaire
                jeu.getJoueur(idJoueurProc).setCouleurCourant();

                //passe au joueur suivant avec sa bonne couleur (deja a jour ici)
                jeu.setJoueurCourant();

                //si au moins un jour peut encore jouer
                if(!isFinJeu()){
                    //tant que le joueur courant ne peut pas jouer (aucune couleur peut jouer)
                    //on passe au joueur suivant jusqu'a avoir un qui peut jouer
                    while(!jeu.getJoueurCourant().isPeutJouer()){
                        jeu.setJoueurCourant();
                    }
                    //lorsqu'on a un joueur qui peut jouer
                    //on verifie si sa couleur peut jouer
                    //si couleur peut pas jouer , passe a la suivante
                    //marche pour 4 et 2 joueurs
                    // car pour 2 joueurs : 2 couleurs max donc passe a la suivante(et on sait qu'elle peut jouer)
                    // car pour 4 : juste une couleur donc si joueur peut jouer, sa couleur peut aussi
                    if(!jeu.getJoueurCourant().getCouleurCourante().isRestePieceJouable()) {
                        jeu.getJoueurCourant().setCouleurCourant();
                    }
                }
            }else{
                //passe a la couleur suivante pour celui qui vient de refaire
                jeu.getJoueur(idJoueurProc).setCouleurCourant();

                idJoueurFutur = jeu.getHistorique().getFutur().getFirst().getE2();
                indTabCouleurJoueurFutur = jeu.getHistorique().getFutur().getFirst().getE3();
                //passe au joueur suivant avec sa bonne couleur
                jeu.setJoueur(idJoueurFutur);
                jeu.getJoueur(idJoueurFutur).setCouleur(indTabCouleurJoueurFutur);
            }

            inter.getInterJ().getM().resetBorder();
            inter.getInterJ().refreshPanJoueur(idCouleurProc-1,pProchain.getId(),false, null);
            inter.getInterJ().setTour(getActCouleur());

            setMenu5();
            setScoreToutLesJoueurs();
            if(!jeu.getHistorique().peutRefaire()){
                inter.setRefaire(false);
            }

            inter.setAnnuler(true);

        }

    }

    private void setScoreToutLesJoueurs() {
        for (int i=1;i<jeu.getNbJoueurs()+1;i++){
            inter.getInterJ().setScore(i,jeu.getJoueur(i).getScore());
            if(jeu.getNbJoueurs()==2){
                inter.getInterJ().setScore(i+2,jeu.getJoueur(i).getScore());
            }
        }
    }

    public void aide(int type){
        IA aide;
        Random r = new Random();
        switch (type){
            case 1:
                aide = new IAAleatoire(jeu);
                break;
            case 2:
                aide = new IAIntermediaire(jeu,r.nextInt(5),true);
                break;
            case 3:
                aide = new IADifficile(jeu);
                break;
            default:
                aide = new IAAleatoire(jeu);
                break;
        }


        aide.joue();
        if(aide.dernierCoup != null){
            desactiverAide();
            inter.getInterJ().getGraph().visAide(aide.dernierCoup.getListe());
        }


    }

    public void stopTimer(){
        inter.getInterJ().getGraph().stopTimer();
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public void updateBoutPause(boolean mettrePause){
        inter.getInterJ().changePauseMenu(mettrePause);
    }

    public int[] getListDiff(){
        return inter.getListDiff();
    }

    public int getPersoNbJoueur(){
        return inter.getPersoNbJoueur();
    }

    public int getOldX() {
        return oldX;
    }

    public int getOldY() {
        return oldY;
    }

    public void setOldX(int oldX) {
        this.oldX = oldX;
    }

    public void setOldY(int oldY) {
        this.oldY = oldY;
    }

    public int getHautCaseGrille(){
        return inter.getInterJ().getGraph().hauteurCase();
    }

    public int getLargeCaseGrille(){
        return inter.getInterJ().getGraph().largeurCase();
    }

    public void setActivKeyAdapt(boolean activ){
        inter.setActivKeyAdapt(activ);
    }

    public void desactiverAide(){
        inter.getInterJ().setEnabledAide(false);
    }

    public void supprVisAide(LinkedList<Case> listeCase){
        inter.getInterJ().setEnabledAide(true);
        inter.getInterJ().getGraph().supprVisAide(listeCase);
    }

    public void setOption(){
        inter.setOption();
    }

    public void actAnim(boolean activer){
        if(activer){
            Configuration.instance().ecris("AnimActive","true");
        }else{
            Configuration.instance().ecris("AnimActive","false");
        }
        animActiv=activer;

        inter.actAnim(activer);
    }

}
