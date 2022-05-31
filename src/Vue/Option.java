package Vue;

import Controleur.Controleur;
import Global.Configuration;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

public class Option {
        JPanel frame;
        Controleur cont;
        int w;
        int h ;
        Bouton b;
        ImageKusBlo im;
        JPanel panActAnim;
        JPanel panActAide;
        JPanel panSlide;
        JSlider slider;
        JPanel listeOption;
        JButton menuPrin;
        JButton retour;
        boolean estDepuisJeu;

        public Option(Controleur c,Bouton bout, ImageKusBlo ima){
            cont=c;
            w=c.getFrameW();
            h=c.getFrameH();
            b=bout;
            frame= new JPanel(new BorderLayout());
            im= ima;
            estDepuisJeu=false;
            menuPrin=b.menuPrincpal();
            retour=b.retourJeu();

            //Création du panel Gauche
            JPanel panelGauche = new JPanel(new BorderLayout());
            panelGauche.setPreferredSize(new Dimension(w/4,h));
            BasicBackgroundPanel fondG = new BasicBackgroundPanel(im.fondG);
            panelGauche.add(fondG);


            //Création du panel centrale avec le logo
            JPanel panelCentral = new JPanel();
            panelCentral.setLayout(new BoxLayout(panelCentral,BoxLayout.PAGE_AXIS));
            BasicBackgroundPanel logo = new BasicBackgroundPanel(im.getLogo());
            logo.setPreferredSize(new Dimension(w/2,h/4));

            //création de la liste de boutons
            listeOption = new JPanel(new GridLayout(6,1));
            listeOption.setPreferredSize(new Dimension(w/2,3*h/4));
            listeOption.add(new JPanel());
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
            slider.setPaintTrack(false);
            slider.setBorder(BorderFactory.createLineBorder(Color.black));
            slider.setInverted(true);
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

            JPanel panNivAide= new JPanel(new GridLayout(1,2));
            panNivAide.setBorder(BorderFactory.createLineBorder(Color.black));
            JPanel labelNivAide = new JPanel();
            labelNivAide.add(new JLabel("Niveau du bouton aide"));
            panNivAide.add(labelNivAide);
            JPanel panRadio= new JPanel();
            JComboBox niveauAide = new JComboBox();
            niveauAide.addItem("Aide Basique");
            niveauAide.addItem("Aide Intermédiaire");
            niveauAide.addItem("Aide Avancée");
            switch (Integer.parseInt(Configuration.instance().lis("NiveauAide"))){
                case 1:
                    niveauAide.setSelectedItem("Aide Basique");
                    break;
                case 2:
                    niveauAide.setSelectedItem("Aide Intermédiaire");
                    break;
                case 3:
                    niveauAide.setSelectedItem("Aide Avancée");
                    break;
            }


            niveauAide.addActionListener(event -> {
                JComboBox comboBox = (JComboBox) event.getSource();
                Object selected = comboBox.getSelectedItem();
                if (selected.toString().equals("Aide Basique")) {
                    Configuration.instance().ecris("NiveauAide",""+1);
                } else if(selected.toString().equals("Aide Intermédiaire")){
                    Configuration.instance().ecris("NiveauAide",""+2);
                }else{
                    Configuration.instance().ecris("NiveauAide",""+3);
                }
            });
            panRadio.add(niveauAide);
            panNivAide.add(panRadio);

            listeOption.add(panNivAide);

            panActAide=new JPanel();
            listeOption.add(panActAide);
            listeOption.add(menuPrin);

            panelCentral.add(logo,BorderLayout.NORTH);
            panelCentral.add(listeOption, BorderLayout.CENTER);

            //Création du panel droit
            JPanel panelDroit = new JPanel(new BorderLayout());
            panelDroit.setPreferredSize(new Dimension(w/4,h));
            BasicBackgroundPanel fondD = new BasicBackgroundPanel(im.fondD);
            panelDroit.add(fondD);

            //Modification des boutons selon la configuration actuelle
            activerAnim(Boolean.parseBoolean(Configuration.instance().lis("AnimActive")));
            activerAide(Boolean.parseBoolean(Configuration.instance().lis("AidePiecePosable")));


            frame.add(panelGauche, BorderLayout.WEST);
            frame.add(panelCentral, BorderLayout.CENTER);
            frame.add(panelDroit, BorderLayout.EAST);

        }


        public JPanel getFrame(){
            return frame;
        }

        public void activerAnim(boolean activer){
            //Affiche le sélecteur de vitesse des animations IA
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

    public void activerAide(boolean activer){
        //Change l'affichage du bouton activer aide
        panActAide.removeAll();
        if(activer){
            panActAide.add(b.desactAidePiece());
        }else{
            panActAide.add(b.actAidePiece());
        }
        panActAide.updateUI();
    }

    public void updateBoutRetour(boolean depuisJeu){
        //Affiche le bouton retour en fonction du point de départ
        if(depuisJeu && !estDepuisJeu){
            listeOption.remove(menuPrin);
            listeOption.add(retour);
            estDepuisJeu=true;
            listeOption.updateUI();
        }else if(!depuisJeu && estDepuisJeu){
            listeOption.remove(retour);
            listeOption.add(menuPrin);
            estDepuisJeu=false;
            listeOption.updateUI();
        }
    }


}


