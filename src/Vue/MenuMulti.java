package Vue;

import Controleur.Controleur;
import Structures.BasicBackgroundPanel;

import javax.swing.*;
import java.awt.*;

public class MenuMulti {
    JPanel frame;
    Controleur cont;
    int w;
    int h ;
    Bouton b;

    public MenuMulti(Controleur c,Bouton bout,int width, int height){
        cont=c;
        w=width;
        h=height;
        b=bout;
        frame= new JPanel(new BorderLayout());

        //Création du panel Gauche
        JPanel panelGauche = new JPanel();
        panelGauche.setPreferredSize(new Dimension(w/4,h));


        //Création du panel centrale avec le logo
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BoxLayout(panelCentral,BoxLayout.PAGE_AXIS));
        BasicBackgroundPanel logo = new BasicBackgroundPanel(new ImageKusBlo().getLogo());
        logo.setPreferredSize(new Dimension(w/2,h/4));

        //création de la liste de boutons
        JPanel listeBoutons = new JPanel(new GridLayout(6,1));
        listeBoutons.add(new Bouton(cont).deuxJoueurs());
        listeBoutons.add(new Bouton(cont).quatreJoueur());
        listeBoutons.add(new Bouton(cont).deuxJdeuxIA());
        listeBoutons.add(new Bouton(cont).partiePerso());
        listeBoutons.add(new Bouton(c).load());
        listeBoutons.add(b.menuPrincpal());
        listeBoutons.setPreferredSize(new Dimension(w/2,3*h/4));

        panelCentral.add(logo,BorderLayout.NORTH);
        panelCentral.add(listeBoutons, BorderLayout.CENTER);

        //Création du panel droit
        JPanel panelDroit = new JPanel();
        panelDroit.setPreferredSize(new Dimension(w/4,h));


        frame.add(panelGauche, BorderLayout.WEST);
        frame.add(panelCentral, BorderLayout.CENTER);
        frame.add(panelDroit, BorderLayout.EAST);

    }

    public JPanel getFrame(){
        return frame;
    }
}
