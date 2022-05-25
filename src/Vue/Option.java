package Vue;

import Controleur.Controleur;
import Global.Configuration;
import Structures.BasicBackgroundPanel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.Hashtable;

public class Option {
        JPanel frame;
        Controleur cont;
        int w;
        int h ;
        Bouton b;
        ImageKusBlo im;
        JPanel panActAnim;
        JPanel panSlide;
        JSlider slider;

        public Option(Controleur c,Bouton bout, ImageKusBlo ima){
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
            JPanel listeOption = new JPanel(new GridLayout(6,1));
            listeOption.setPreferredSize(new Dimension(w/2,3*h/4));
            panActAnim = new JPanel();
            listeOption.add(panActAnim);


            panSlide= new JPanel(new BorderLayout());
            panSlide.add(new JLabel("Vitesse des animations IA :"),BorderLayout.NORTH);
            slider = new JSlider(0, 5000, 100);
            Hashtable<Integer, JLabel> table = new Hashtable<Integer, JLabel>();
            table.put (0, new JLabel("Rapide"));
            table.put (5000, new JLabel("Lente"));
            slider.setLabelTable (table);
            slider.setValue(Integer.parseInt(Configuration.instance().lis("VitesseAnim")));
            slider.setInverted(true);
            slider.setPaintTrack(true);
            slider.setPaintTicks(true);
            slider.setPaintLabels(true);
            slider.setMinorTickSpacing(500);
            panSlide.add(slider);
            slider.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent event) {
                    int value = slider.getValue();
                    Configuration.instance().ecris("VitesseAnim",""+value);
                }
            });
            listeOption.add(panSlide);
            listeOption.add(b.menuPrincpal());

            panelCentral.add(logo,BorderLayout.NORTH);
            panelCentral.add(listeOption, BorderLayout.CENTER);

            //Création du panel droit
            JPanel panelDroit = new JPanel(new BorderLayout());
            panelDroit.setPreferredSize(new Dimension(w/4,h));
            BasicBackgroundPanel fondD = new BasicBackgroundPanel(im.fondD);
            panelDroit.add(fondD);
            activerAnim(Boolean.parseBoolean(Configuration.instance().lis("AnimActive")));

            frame.add(panelGauche, BorderLayout.WEST);
            frame.add(panelCentral, BorderLayout.CENTER);
            frame.add(panelDroit, BorderLayout.EAST);

        }


        public JPanel getFrame(){
            return frame;
        }

        public void activerAnim(boolean activer){
            panActAnim.removeAll();
            if(activer){
                panActAnim.add(b.desactAnim());
                panSlide.add(new JLabel("Vitesse des animations IA :"),BorderLayout.NORTH);
                panSlide.add(slider);
            }else{
                panActAnim.add(b.actAnim());
                panSlide.removeAll();
            }
            panActAnim.updateUI();
            panSlide.updateUI();
        }
}


