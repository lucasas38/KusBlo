package Vue;

import Controleur.Controleur;
import Structures.BasicBackgroundPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MenuMulti {
    JPanel frame;
    Controleur cont;
    int w;
    int h ;
    Bouton b;

    public MenuMulti(Controleur c,Bouton bout){
        cont=c;
        w=c.getFrameW();
        h=c.getFrameH();
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
        listeBoutons.add(b.deuxJoueurs());
        listeBoutons.add(b.quatreJoueur());
        listeBoutons.add(b.deuxJdeuxIA());
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

        setResize();

    }

    public JPanel getFrame(){
        return frame;
    }

    public void setResize(){
        frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                resizeAllPanel();
            }
        });

    }

    public void resizeAllPanel(){
        w= cont.getFrameW();
        h=cont.getFrameH();
    }
}
