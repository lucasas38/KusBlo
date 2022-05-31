package Vue;

import Controleur.Controleur;

import javax.swing.*;
import java.awt.*;

public class MenuRegle {
    JPanel frame;
    Controleur cont;
    Bouton b;
    ImageKusBlo im;
    JButton load;
    int w;
    int h ;
    BasicBackgroundPanel[] regle;

    int page;

    public MenuRegle(Controleur c,Bouton bout,ImageKusBlo ima){
        cont=c;
        w=c.getFrameW();
        h=c.getFrameH();
        b=bout;
        im=ima;
        page=1;
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

        //création de la liste de boutons
        JPanel boutonMenu= new JPanel();
        boutonMenu.add(b.retourJeu());

        JPanel centre= new JPanel(new BorderLayout());
        regle = new BasicBackgroundPanel[4];
        regle[0]=new BasicBackgroundPanel(im.regle[0]);
        regle[1]=new BasicBackgroundPanel(im.regle[1]);
        regle[2]=new BasicBackgroundPanel(im.regle[2]);
        regle[3]=new BasicBackgroundPanel(im.regle[3]);
        centre.add(regle[page-1]);

        panelCentral.add(logo,BorderLayout.NORTH);
        panelCentral.add(centre,BorderLayout.CENTER);


        panelCentral.add(boutonMenu, BorderLayout.SOUTH);
        //panelCentral.setBackground(new Color(0,0,0,0));

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


        frame.add(panelCentral, BorderLayout.CENTER);
        frame.setComponentZOrder(panelCentral, 0);
        frame.updateUI();


    }

    public JPanel getFrame(){
        return frame;
    }
}
