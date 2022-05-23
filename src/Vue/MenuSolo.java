package Vue;

import Controleur.Controleur;
import Structures.BasicBackgroundPanel;

import javax.swing.*;
import java.awt.*;

public class MenuSolo {
    JPanel frame;
    Controleur cont;
    int w;
    int h ;
    Bouton b;
    ImageKusBlo im;

    public MenuSolo(Controleur c,Bouton bout, ImageKusBlo ima){
        cont=c;
        w=c.getFrameW();
        h=c.getFrameH();
        b=bout;
        frame= new JPanel(new BorderLayout());
        im= ima;

        //Création du panel Gauche
        JPanel panelGauche = new JPanel(new BorderLayout());
        panelGauche.setPreferredSize(new Dimension(w/4,h));
        BasicBackgroundPanel fondG = new BasicBackgroundPanel(im.fondG);
        panelGauche.add(fondG);


        //Création du panel centrale avec le logo
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BoxLayout(panelCentral,BoxLayout.PAGE_AXIS));
        BasicBackgroundPanel logo = new BasicBackgroundPanel(new ImageKusBlo().getLogo());
        logo.setPreferredSize(new Dimension(w/2,h/4));

        //création de la liste de boutons
        JPanel listeBoutons = new JPanel(new GridLayout(6,1));
        listeBoutons.add(b.vsUneIAf());
        listeBoutons.add(b.vsUneIAi());
        listeBoutons.add(b.vsUneIAd());
        listeBoutons.add(b.vsMultIa());
        listeBoutons.add(b.menuPrincpal());
        listeBoutons.setPreferredSize(new Dimension(w/2,3*h/4));

        panelCentral.add(logo,BorderLayout.NORTH);
        panelCentral.add(listeBoutons, BorderLayout.CENTER);

        //Création du panel droit
        JPanel panelDroit = new JPanel(new BorderLayout());
        panelDroit.setPreferredSize(new Dimension(w/4,h));
        BasicBackgroundPanel fondD = new BasicBackgroundPanel(im.fondD);
        panelDroit.add(fondD);


        frame.add(panelGauche, BorderLayout.WEST);
        frame.add(panelCentral, BorderLayout.CENTER);
        frame.add(panelDroit, BorderLayout.EAST);

    }

    public JPanel getFrame(){
        return frame;
    }
}
