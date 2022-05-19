package Vue;

import Controleur.Controleur;
import Structures.BasicBackgroundPanel;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipal {
    JPanel frame;
    Controleur cont;
    Bouton b;
    ImageKusBlo im;
    int w;
    int h ;

    public MenuPrincipal(Controleur c,Bouton bout,ImageKusBlo ima){
        cont=c;
        w=c.getFrameW();
        h=c.getFrameH();
        b=bout;
        im=ima;
        frame= new JPanel(new BorderLayout());

        //Création du panel Gauche
        JPanel panelGauche = new JPanel(new BorderLayout());
        panelGauche.setPreferredSize(new Dimension(w/4,h));
        panelGauche.setBackground(new Color(0,0,0,0));


        //Création du panel centrale avec le logo
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BoxLayout(panelCentral,BoxLayout.PAGE_AXIS));
        BasicBackgroundPanel logo = new BasicBackgroundPanel(new ImageKusBlo().getLogo());
        logo.setPreferredSize(new Dimension(w/2,h/4));
        logo.setBackground(new Color(0,0,0,80));

        //création de la liste de boutons
        JPanel listeBoutons = new JPanel(new GridLayout(6,1));
        listeBoutons.setBackground(new Color(0,0,0,80));
        listeBoutons.add(b.solo());
        listeBoutons.add(b.multi());
        listeBoutons.add(b.load());
        listeBoutons.add(b.exit());
        listeBoutons.setPreferredSize(new Dimension(w/2,3*h/4));

        panelCentral.add(logo,BorderLayout.NORTH);
        panelCentral.add(listeBoutons, BorderLayout.CENTER);
        panelCentral.setBackground(new Color(0,0,0,0));

        //Création du panel droit
        JPanel panelDroit = new JPanel();
        panelDroit.setPreferredSize(new Dimension(w/4,h));
        panelDroit.setBackground(new Color(0,0,0,20));

        JPanel panelGaucheFond= new JPanel();
        panelGaucheFond.setLayout(new BoxLayout(panelGaucheFond,BoxLayout.PAGE_AXIS));
        JPanel pan=new JPanel(new BorderLayout());
        BasicBackgroundPanel fondG = new BasicBackgroundPanel(im.fondG);
        pan.add(fondG,BorderLayout.CENTER);
        pan.setPreferredSize(new Dimension(w/4,h));
        panelGaucheFond.add(pan);

        JPanel panelCentralFond = new JPanel(new GridLayout(1,1));
        panelCentralFond.setPreferredSize(new Dimension(w/2,h));
        BasicBackgroundPanel fondC = new BasicBackgroundPanel(im.fondC);
        fondC.setPreferredSize(new Dimension(w/2,h));
        panelCentralFond.add(fondC);

        JPanel panelDroitFond = new JPanel(new GridLayout(1,1));
        panelDroitFond.setPreferredSize(new Dimension(w/4,h));
        BasicBackgroundPanel fondD = new BasicBackgroundPanel(im.fondD);
        fondD.setPreferredSize(new Dimension(w/4,h));
        panelDroitFond.add(fondD);
        panelGaucheFond.add(panelGauche);
        panelGaucheFond.setComponentZOrder(panelGauche,0);

        frame.add(panelGauche, BorderLayout.WEST);
        frame.add(panelGaucheFond,BorderLayout.WEST);
        frame.add(panelCentralFond,BorderLayout.CENTER);
        frame.add(panelDroitFond,BorderLayout.EAST);


        //frame.add(panelGauche, BorderLayout.WEST);
        frame.add(panelCentral, BorderLayout.CENTER);
        //frame.add(panelDroit, BorderLayout.EAST);

        //frame.setComponentZOrder(panelGaucheFond, 0);
        frame.setComponentZOrder(panelCentral, 0);
        //frame.setComponentZOrder(panelDroit, 0);
        frame.updateUI();


    }

    public JPanel getFrame(){
        return frame;
    }
}
