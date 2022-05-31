package Vue;

import Controleur.Controleur;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;


public class Bouton {
    Controleur c;
    ImageKusBlo im;

    Bouton(Controleur cont, ImageKusBlo ima){
        c=cont;
        im=ima;
    }

    public JButton menuJeu(){
        JButton button = new JButton("Menu");
        button.addActionListener(e ->{
            c.showMenuOpt();
            c.stopTimerAide();
            if(c.dernierCoupAide()!=null)
                c.supprVisAide(c.dernierCoupAide().getListe());
            c.desactiverAide();});
        return button;
    }

    public JButton reprendre(){
        JButton button = new JButton("Reprendre");
        button.addActionListener(e -> c.reprendre());
        return button;
    }

    public JButton menuPrincpal(){
        JButton button = new JButton("Menu Principal");
        button.addActionListener(e -> {
            c.menu();
            c.refreshLoad();
        });
        return button;
    }

    public JButton newGame(){
        JButton button = new JButton("Recommencer");
        // les paramètres ne sont pas utilisés dans ce cas
        button.addActionListener(e -> c.newGame(4,1,1,1,1, true));
        return button;
    }

    public JButton exit(){
        JButton button = new JButton("Quitter");
        button.addActionListener(e -> System.exit(0));
        return button;
    }

    public JButton save(){
        JButton button = new JButton("Sauvegarder");
        button.addActionListener(e -> {c.save();
        c.refreshLoad();
        });
        return button;
    }

    public JButton load(){
        JButton button = new JButton("Charger");
        button.addActionListener(e -> c.load());
        return button;
    }

    public JButton solo(){
        JButton button = new JButton("Solo");
        button.addActionListener(e -> c.setMenuSolo());
        return button;
    }

    public JButton multi(){
        JButton button = new JButton("Multijoueur");
        button.addActionListener(e -> c.setMenuMulti());
        return button;
    }


    public JButton vsUneIAf(){
        JButton button = new JButton("Contre une IA (facile)");

        button.addActionListener(e -> randomNewGame2Joueurs(1));
        return button;
    }

    public JButton vsUneIAi(){
        JButton button = new JButton("Contre une IA (intermédiaire)");
        button.addActionListener(e -> randomNewGame2Joueurs(2));
        return button;
    }

    public JButton vsUneIAd(){
        JButton button = new JButton("Contre une IA (difficile)");
        button.addActionListener(e -> randomNewGame2Joueurs(7));
        return button;
    }

    public JButton vsMultIa(){
        JButton button = new JButton("Contre plusieurs IA");
        button.addActionListener(e -> randomNewGame4Joueurs(3,1));
        return button;
    }
    public JButton deuxJoueurs(){
        JButton button = new JButton("2 Joueurs");
        button.addActionListener(e -> c.newGame(2,0,0,0,0,false));
        return button;
    }
    public JButton quatreJoueur(){
        JButton button = new JButton("4 Joueurs");
        button.addActionListener(e -> c.newGame(4,0,0,0,0,false));
        return button;
    }


    public JButton deuxJdeuxIA(){
        JButton button = new JButton("2 Joueurs et 2 IA");
        button.addActionListener(e -> randomNewGame4Joueurs(2,2));
        return button;
    }

    public JButton partiePerso(){
        JButton button = new JButton("Partie personnalisée");
        button.addActionListener(e -> c.setMenuPerso());
        return button;
    }

    public JButton annuler(){
        JButton button = new JButton();
        button.addActionListener(e -> {
            c.updateBoutPause(false);
            c.desactiverAide();
            c.annuler();
            c.stopTimerAide();
            if(c.dernierCoupAide()!=null)
                c.supprVisAide(c.dernierCoupAide().getListe());
            c.desactiverAide();
        });
        return button;
    }

    public JButton refaire(){
        JButton button = new JButton();
        button.addActionListener(e -> {
            c.updateBoutPause(false);
            c.refaire();
            c.stopTimerAide();
            if(c.dernierCoupAide()!=null)
                c.supprVisAide(c.dernierCoupAide().getListe());
            c.desactiverAide();});

        return button;
    }

    public JButton pause(){
        JButton button = new JButton(new ImageIcon(im.pause));
        button.addActionListener(e -> {
            c.updateBoutPause(false);
            c.desactiverAide();
            c.pause();
            c.stopTimerAide();
            if(c.dernierCoupAide()!=null)
                c.supprVisAide(c.dernierCoupAide().getListe());
            c.desactiverAide();
        });
        return button;
    }

    public JButton resume(){
        JButton button = new JButton(new ImageIcon(im.resume));
        button.addActionListener(e -> {
            c.updateBoutPause(true);
            c.setPause(false);
            c.setMenu1();
            c.resetKeyList();
        });
        return button;
    }

    public JButton lancerPartie(){
        JButton button = new JButton("Commencer la partie");
        button.addActionListener(e -> {
            int[] l=c.getListDiff();
            int nbJoueur =c.getPersoNbJoueur();
            c.newGame(nbJoueur,l[0],l[1],l[2],l[3],false);
        });
        return button;
    }

    public JButton aide(){
        JButton button = new JButton("Aide");
        button.addActionListener(e -> {
            c.aide();
            c.resetKeyList();
        });
        return button;
    }

    public JButton optionMenu(){
        JButton button = new JButton("Option");
        button.addActionListener(e -> {
            c.updateRetourOption(false);
            c.setOption();
        });
        return button;
    }

    public JButton optionJeu(){
        JButton button = new JButton("Option");
        button.addActionListener(e -> {
            c.updateRetourOption(true);
            c.setOption();
        });
        return button;
    }

    public JButton retourJeu(){
        JButton button = new JButton("Retour");
        button.addActionListener(e -> c.retourJeu());
        return button;
    }

    public JButton actAnim(){
        JButton button = new JButton("Activer Animation");
        button.addActionListener(e -> c.actAnim(true));
        return button;
    }

    public JButton desactAnim(){
        JButton button = new JButton("Désactiver Animation");
        button.addActionListener(e -> c.actAnim(false));
        return button;
    }

    public JButton actAidePiece(){
        JButton button = new JButton("Activer Aide pièces jouables");
        button.addActionListener(e -> c.actAide(true));
        return button;
    }

    public JButton desactAidePiece(){
        JButton button = new JButton("Désactiver Aide pièces jouables");
        button.addActionListener(e -> c.actAide(false));
        return button;
    }

    public JButton menuRegle(){
        JButton button = new JButton("Règles");
        button.addActionListener(e -> {
            c.updateBoutPause(false);
            c.desactiverAide();
            c.pause();
            c.stopTimerAide();
            if(c.dernierCoupAide()!=null)
                c.supprVisAide(c.dernierCoupAide().getListe());
            c.desactiverAide();
            c.setMenuregle();
        });
        return button;
    }

    public JButton pageSuiv(){
        JButton button = new JButton("Page Suivante");
        button.addActionListener(e -> c.changePage(true));
        return button;
    }

    public JButton pagePrec(){
        JButton button = new JButton("Page précédente");
        button.addActionListener(e -> c.changePage(false));
        return button;
    }

    public void randomNewGame2Joueurs(int typeIA){
        Random r = new Random();
        int j1 = 0;
        int j2 = 0;
        if(r.nextBoolean()){
            j2 = typeIA;
        }else{
            j1 = typeIA;
        }
        c.newGame(2,j1,j2,0,0,false);
    }

    public void randomNewGame4Joueurs(int nombreIA, int nombreJoueurs){
        if(nombreIA+nombreJoueurs != 4){
            c.newGame(4,0,1,2,7,false);
        }else{
            Random r = new Random();
            int[] joueurs = new int[4];
            int nbIA = 0,nbJoueurs = 0;
            int i=0;
            while(i<4 && (nbJoueurs < nombreJoueurs && nbIA < nombreIA)){
                //si ia
                if(r.nextBoolean()){
                    //aleatoire entre facile, intermediaire et difficile
                    joueurs[i] = r.nextInt(3)+1;
                    //si difficile on met son bon numero (le 7)
                    if(joueurs[i] == 3){
                        joueurs[i] = 7;
                    }
                    nbIA++;
                }else{
                    joueurs[i] = 0;
                    nbJoueurs++;
                }
                i++;
            }

            //si on a deja 2 joueurs, il faut rajouter 1 ou 2 ia
            if(nbJoueurs >= nombreJoueurs){
                while(nbIA<nombreIA){
                    joueurs[i] = r.nextInt(3)+1;
                    //si difficile on met son bon numero (le 7)
                    if(joueurs[i] == 3){
                        joueurs[i] = 7;
                    }
                    nbIA++;
                    i++;
                }
            }else{
                while(nbJoueurs<nombreJoueurs){
                    joueurs[i] = 0;
                    nbJoueurs++;
                    i++;
                }
            }
            c.newGame(4,joueurs[0],joueurs[1],joueurs[2],joueurs[3],false);
        }
    }

}
