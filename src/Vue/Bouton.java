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

    public JButton rotaHorraire(){
        JButton button = new JButton("RotaHorraire");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.rotaHorraire();
            }
        });
        return button;
    }
}
