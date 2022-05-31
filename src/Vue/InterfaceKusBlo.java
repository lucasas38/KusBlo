package Vue;

import Controleur.Controleur;

import javax.swing.*;

public class InterfaceKusBlo implements Runnable {
    JFrame frame;
    InterfaceJeu interJ;
    MenuPrincipal menu;
    MenuSolo menuSol;
    MenuMulti menuMult;
    MenuPartiePerso menuPerso;
    MenuRegle menuRegle;

    Option option;
    Controleur c;
    ImageKusBlo im;
    Bouton b;
    AdaptateurClavier keyAdapt;


    public InterfaceKusBlo(Controleur cont){
        c=cont;
    }

    public static void demarrer(Controleur cont) {
        InterfaceKusBlo vue = new InterfaceKusBlo(cont);
        cont.ajouteInterfaceUtilisateur(vue);
        SwingUtilities.invokeLater(vue);
    }

    public void run(){
        im= new ImageKusBlo();
        b=new Bouton(c, im);
        keyAdapt=new AdaptateurClavier(c);
        frame = new JFrame("KusBlo");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //On génère et affiche le menu principal
        menu=new MenuPrincipal(c,b,im);
        menuSol=new MenuSolo(c,b,im);
        menuMult=new MenuMulti(c,b,im);
        menuPerso=new MenuPartiePerso(c,b,im);
        menuRegle=new MenuRegle(c,b,im);
        option= new Option(c,b,im);
        frame.addKeyListener(keyAdapt);
        frame.setFocusable(true);
        frame.requestFocusInWindow();
        frame.setContentPane(menu.getFrame());
        frame.setVisible(true);
    }

    //Affiche le menu
    public void setMenu(){
        frame.setContentPane(menu.getFrame());
        frame.revalidate();
    }

    public void setMenuSolo(){
        frame.setContentPane(menuSol.getFrame());
        frame.revalidate();
    }

    public void setMenuMulti(){
        frame.setContentPane(menuMult.getFrame());
        frame.revalidate();
    }

    public void setMenuPerso(){
        frame.setContentPane(menuPerso.getFrame());
        frame.revalidate();
    }

    public void setOption(){
        frame.setContentPane(option.getFrame());
        frame.revalidate();
    }

    public void setMenuRegle() {
        frame.setContentPane(menuRegle.getFrame());
        frame.revalidate();
    }

    //Affiche un nouveau jeu
    public void setInterJeu(){
        interJ= new InterfaceJeu(c ,b,im);
        frame.setContentPane(interJ.getFrame());
        interJ.resizeAllPanel();
        frame.revalidate();
        c.initAide();
    }

    public void showInterJeu(){
        frame.setContentPane(interJ.getFrame());
        interJ.resizeAllPanel();
        frame.revalidate();
    }

    public InterfaceJeu getInterJ(){
        return interJ;
    }

    public int getW(){
        return frame.getWidth();
    }
    public int getH(){
        return frame.getHeight();
    }

    public void charger(){
        interJ.charger();
    }

    public int[] getListDiff(){
       return menuPerso.getListeDiff();
    }

    public int getPersoNbJoueur(){
        return menuPerso.getNbJoueur();
    }

    public void setActivKeyAdapt(boolean activ){
        keyAdapt.setActiv(activ);
    }

    public void setAnnuler(boolean an) {
        interJ.setAnnuler(an);
    }

    public void setRefaire(boolean re) {
        interJ.setRefaire(re);
    }

    public void updateNameIA(int numCoulIA, int diff){
        interJ.updateNameIA(numCoulIA,diff);
    }

    public void actAnim(boolean activer){
        option.activerAnim(activer);
    }

    public void actAide(boolean activer){
        option.activerAide(activer);
    }

    public void resetKeyList(){
        frame.removeKeyListener(keyAdapt);
        frame.addKeyListener(keyAdapt);
        frame.setFocusable(true);
        frame.requestFocusInWindow();
    }

    public void refreshLoad(){
        menu.refreshLoad();
    }

    public void updateRetourOption(boolean depuisJeu){
        option.updateBoutRetour(depuisJeu);
    }

    public void changePage(boolean nextPage){
        if(nextPage){
            menuRegle.nextPage();
        }else{
            menuRegle.precPage();
        }
    }

}
