package Vue;

import Controleur.Controleur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MenuRegle {
    JPanel frame;
    Controleur cont;
    Bouton b;
    ImageKusBlo im;
    JPanel panelGauche;
    JPanel panelDroit;
    BasicBackgroundPanel logo;
    JButton pageSuiv;
    JButton pagePrec;
    JPanel centre;
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
        pagePrec=b.pagePrec();
        pageSuiv=b.pageSuiv();

        //Création du panel Gauche
        panelGauche = new JPanel(new BorderLayout());
        panelGauche.setPreferredSize(new Dimension(w/4,h));
        BasicBackgroundPanel fondG = new BasicBackgroundPanel(im.fondG);
        panelGauche.add(fondG);

        //Création du panel centrale avec le logo
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BoxLayout(panelCentral,BoxLayout.PAGE_AXIS));
        logo = new BasicBackgroundPanel(im.getLogo());
        logo.setPreferredSize(new Dimension(w/2,h/4));

        //création de la liste de boutons
        JPanel boutonMenu= new JPanel(new GridLayout(1,3));
        JPanel panBoutPrec = new JPanel();
        panBoutPrec.add(pagePrec);
        boutonMenu.add(panBoutPrec);
        pagePrec.setEnabled(false);

        JPanel panBoutRetour=new JPanel();
        panBoutRetour.add(b.retourJeu());
        boutonMenu.add(panBoutRetour);

        JPanel panBoutSuiv = new JPanel();
        panBoutSuiv.add(pageSuiv);
        boutonMenu.add(panBoutSuiv);

        centre= new JPanel(new BorderLayout());
        regle = new BasicBackgroundPanel[4];
        regle[0]=new BasicBackgroundPanel(im.regle[0]);
        regle[1]=new BasicBackgroundPanel(im.regle[1]);
        regle[2]=new BasicBackgroundPanel(im.regle[2]);
        regle[3]=new BasicBackgroundPanel(im.regle[3]);
        centre.add(regle[page-1]);

        panelCentral.add(logo,BorderLayout.NORTH);
        panelCentral.add(centre,BorderLayout.CENTER);


        panelCentral.add(boutonMenu, BorderLayout.SOUTH);

        //Création du panel droit
        panelDroit = new JPanel(new BorderLayout());
        panelDroit.setPreferredSize(new Dimension(w/4,h));
        BasicBackgroundPanel fondD = new BasicBackgroundPanel(im.fondD);
        panelDroit.add(fondD);



        frame.add(panelGauche, BorderLayout.WEST);
        frame.add(panelCentral, BorderLayout.CENTER);
        frame.add(panelDroit, BorderLayout.EAST);


        frame.updateUI();
        setResize();


    }

    public void nextPage(){
        pagePrec.setEnabled(true);
        centre.removeAll();
        page++;
        centre.add(regle[page-1]);
        if(page==4){
            pageSuiv.setEnabled(false);
        }
        centre.updateUI();
    }

    public void precPage(){
        pageSuiv.setEnabled(true);
        centre.removeAll();
        page--;
        centre.add(regle[page-1]);
        if(page==1){
            pagePrec.setEnabled(false);
        }
        centre.updateUI();
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
        logo.setPreferredSize(new Dimension(w/2,h/4));
    }
}
