package Vue;

import Controleur.Controleur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MenuPrincipal {
    JPanel frame;
    Controleur cont;
    Bouton b;
    ImageKusBlo im;
    JButton load;
    JPanel panelGauche;
    JPanel panelDroit;
    JPanel panelCentral;
    BasicBackgroundPanel logo;
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
        panelGauche = new JPanel(new BorderLayout());
        panelGauche.setPreferredSize(new Dimension(w/4,h));
        BasicBackgroundPanel fondG = new BasicBackgroundPanel(im.fondG);
        panelGauche.add(fondG);

        //Création du panel centrale avec le logo
        panelCentral = new JPanel();
        panelCentral.setLayout(new BoxLayout(panelCentral,BoxLayout.PAGE_AXIS));
        logo = new BasicBackgroundPanel(im.getLogo());
        logo.setPreferredSize(new Dimension(w/2,h/4));
        //logo.setBackground(new Color(0,0,0,80));

        //création de la liste de boutons
        JPanel listeBoutons = new JPanel(new GridLayout(6,1));
        //listeBoutons.setBackground(new Color(0,0,0,80));
        listeBoutons.add(b.solo());
        listeBoutons.add(b.multi());
        listeBoutons.add(b.partiePerso());
        load=b.load();
        listeBoutons.add(load);
        load.setEnabled(c.canLoad());
        listeBoutons.add(b.optionMenu());
        listeBoutons.add(b.exit());
        listeBoutons.setPreferredSize(new Dimension(w/2,3*h/4));

        panelCentral.add(logo,BorderLayout.NORTH);
        panelCentral.add(listeBoutons, BorderLayout.CENTER);


        panelDroit = new JPanel(new BorderLayout());
        panelDroit.setPreferredSize(new Dimension(w/4,h));
        BasicBackgroundPanel fondD = new BasicBackgroundPanel(im.fondD);
        panelDroit.add(fondD);


        frame.add(panelGauche, BorderLayout.WEST);
        frame.add(panelCentral, BorderLayout.CENTER);
        frame.add(panelDroit, BorderLayout.EAST);
        frame.updateUI();

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

    //redimensionne tous les pannel
    public void resizeAllPanel(){
        w=cont.getFrameW();
        h=cont.getFrameH();
        panelGauche.setPreferredSize(new Dimension(w/4,h));
        panelDroit.setPreferredSize(new Dimension(w/4,h));
    }

    public void refreshLoad(){
        load.setEnabled(cont.canLoad());
    }
}
