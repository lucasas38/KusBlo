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
}
