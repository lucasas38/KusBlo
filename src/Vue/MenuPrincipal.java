package Vue;

import Controleur.Controleur;
import Structures.BasicBackgroundPanel;
import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipal {
    JFrame frame;
    Controleur cont;

    public MenuPrincipal(Controleur c){
        cont=c;
        frame = new JFrame("KusBlo");
        frame.setSize(800, 600);
        JPanel mainPanel = new JPanel(new BorderLayout());

        //Création du panel Gauche
        JPanel panelGauche = new JPanel();
        panelGauche.setPreferredSize(new Dimension(frame.getWidth()/4,frame.getHeight()));


        //Création du panel centrale avec le logo
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BoxLayout(panelCentral,BoxLayout.PAGE_AXIS));
        BasicBackgroundPanel logo = new BasicBackgroundPanel(new ImageKusBlo().getLogo());
        logo.setPreferredSize(new Dimension(frame.getWidth()/2,frame.getHeight()/4));

        //création de la liste de boutons
        JPanel listeBoutons = new JPanel(new GridLayout(4,1));
        listeBoutons.add(new Bouton(cont).newGame());
        listeBoutons.add(new Bouton(cont).exit());
        listeBoutons.setPreferredSize(new Dimension(frame.getWidth()/2,3*frame.getHeight()/4));

        panelCentral.add(logo,BorderLayout.NORTH);
        panelCentral.add(listeBoutons, BorderLayout.CENTER);

        //Création du panel droit
        JPanel panelDroit = new JPanel();
        panelDroit.setPreferredSize(new Dimension(frame.getWidth()/4,frame.getHeight()));


        mainPanel.add(panelGauche, BorderLayout.WEST);
        mainPanel.add(panelCentral, BorderLayout.CENTER);
        mainPanel.add(panelDroit, BorderLayout.EAST);
        frame.add(mainPanel);
    }

    public JFrame getFrame(){
        return frame;
    }
}
