package Vue;

import Controleur.Controleur;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Bouton {
    Controleur c;

    Bouton(Controleur cont){
        c=cont;
    }

    public JButton retourListePiece(){
        JButton button = new JButton("Liste des pièces");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.setMenu1();
            }
        });
        return button;
    }

    public JButton rotaHoraire(){
        JButton button = new JButton("RotaHoraire");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.rotaHoraire();
            }
        });
        return button;
    }

    public JButton rotaAntiHoraire(){
        JButton button = new JButton("AntiHoraire");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.antiHoraire();
            }
        });
        return button;
    }

    public JButton flip(){
        JButton button = new JButton("Flip");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.flip();
            }
        });
        return button;
    }

    public JButton skipTour(){
        JButton button = new JButton("Abandon");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.finCouleur();
                c.passerTour();
            }
        });
        return button;
    }

    public JButton menuJeu(){
        JButton button = new JButton("Menu");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.showMenuOpt();
            }
        });
        return button;
    }

    public JButton reprendre(){
        JButton button = new JButton("Reprendre");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.reprendre();
            }
        });
        return button;
    }

    public JButton menuPrincpal(){
        JButton button = new JButton("Menu Principal");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.menu();
            }
        });
        return button;
    }

    public JButton newGame(){
        JButton button = new JButton("Nouvelle Partie");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.newGame(4,1,1,1,1, true);
            } // les paramètres ne sont pas utilisés dans ce cas
        });
        return button;
    }

    public JButton exit(){
        JButton button = new JButton("Quitter");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        return button;
    }

    public JButton save(){
        JButton button = new JButton("Sauvegarder");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.save();
            }
        });
        return button;
    }

    public JButton load(){
        JButton button = new JButton("Charger");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.load();
            }
        });
        return button;
    }

    public JButton solo(){
        JButton button = new JButton("Solo");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.setMenuSolo();
            }
        });
        return button;
    }

    public JButton multi(){
        JButton button = new JButton("Multijoueur");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.setMenuMulti();
            }
        });
        return button;
    }


    public JButton vsUneIAf(){
        JButton button = new JButton("Contre une IA (facile)");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.newGame(2,0,1,0,0,false);
            }
        });
        return button;
    }

    public JButton vsUneIAi(){
        JButton button = new JButton("Contre une IA (intermédiaire)");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.newGame(2,0,2,0,0,false);
            }
        });
        return button;
    }

    public JButton vsUneIAd(){
        JButton button = new JButton("Contre une IA (difficile)");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.newGame(4,2,1,2,2,false);
            }
        });
        return button;
    }

    public JButton vsMultIa(){
        JButton button = new JButton("Contre plusieurs IA (inter)"); // actuellement utilisé pour faire s'affronter des IA
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.newGame(4,2,2,2,2,false);
            }
        });
        return button;
    }
    public JButton deuxJoueurs(){
        JButton button = new JButton("2 Joueurs");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.newGame(2,0,0,0,0,false);
            }
        });
        return button;
    }
    public JButton quatreJoueur(){
        JButton button = new JButton("4 Joueurs");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.newGame(4,0,0,0,0,false);
            }
        });
        return button;
    }


    public JButton deuxJdeuxIA(){
        JButton button = new JButton("2 Joueurs et 2 IA");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.newGame(4,0,0,1,1,false);
            }
        });
        return button;
    }

    public JButton partiePerso(){
        JButton button = new JButton("Partie personnalisée");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.newGame(4,0,0,1,1,false);
            }
        });
        return button;
    }

    public JButton annuler(){
        JButton button = new JButton("Annuler");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.annuler();
            }
        });
        return button;
    }

    public JButton refaire(){
        JButton button = new JButton("Refaire");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.refaire();
            }
        });
        return button;
    }
}
