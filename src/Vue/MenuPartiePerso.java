package Vue;

import Controleur.Controleur;
import Structures.BasicBackgroundPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuPartiePerso {
    JPanel frame;
    Controleur cont;
    int w;
    int h ;
    Bouton b;
    JPanel affichageJoueurG;
    JPanel affichageJoueurD;
    JPanel J1;
    JPanel J2;
    JPanel J3;
    JPanel J4;
    JPanel[] diff;
    int[] listeDiff;
    int nbJoueur;


    public MenuPartiePerso(Controleur c,Bouton bout){
        cont=c;
        w=c.getFrameW();
        h=c.getFrameH();
        b=bout;
        frame= new JPanel(new BorderLayout());
        diff = new JPanel[8];
        listeDiff=new int[4];
        listeDiff[0]=0;
        listeDiff[1]=0;
        listeDiff[2]=0;
        listeDiff[3]=0;
        nbJoueur=2;
        J1=createJ(1);
        J2=createJ(2);
        J3=createJ(3);
        J4=createJ(4);

        //Création du panel Logo
        JPanel panelHaut = new JPanel(new GridLayout(1,3));
        panelHaut.setPreferredSize(new Dimension(w,h/8));
        panelHaut.add(b.menuPrincpal());
        BasicBackgroundPanel logo = new BasicBackgroundPanel(new ImageKusBlo().getLogo());
        logo.setPreferredSize(new Dimension(w/3,h/8));
        panelHaut.add(logo);
        panelHaut.add(new JPanel(new BorderLayout()));

        //Création panel Central
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BoxLayout(panelCentral,BoxLayout.PAGE_AXIS));
        JPanel selectionNbJoueur = new JPanel(new GridLayout(1,4));
        JPanel affichageJoueur = new JPanel(new GridLayout(1,2));
        selectionNbJoueur.setPreferredSize(new Dimension(w,h/8));
        selectionNbJoueur.add(new JLabel("Type de partie"));
        JComboBox typeJeu = new JComboBox();
        typeJeu.addItem("2 Joueurs");
        typeJeu.addItem("4 Joueurs");

        typeJeu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                JComboBox comboBox = (JComboBox) event.getSource();
                Object selected = comboBox.getSelectedItem();
                if (selected.toString().equals("2 Joueurs")) {
                    setTypePartie(true);
                    affichageJoueur.updateUI();
                } else{
                    setTypePartie(false);
                    affichageJoueur.updateUI();

                }
            }
        });
        selectionNbJoueur.add(typeJeu);
        selectionNbJoueur.add(new JPanel());
        selectionNbJoueur.add(new JPanel());

        affichageJoueur.setPreferredSize(new Dimension(w,5*h/8));
        affichageJoueurG=new JPanel((new GridLayout(2,1)));
        affichageJoueurD=new JPanel((new GridLayout(2,1)));
        affichageJoueurG.add(J1);
        affichageJoueurD.add(J2);
        affichageJoueur.add(affichageJoueurG);
        affichageJoueur.add(affichageJoueurD);

        panelCentral.add(selectionNbJoueur);
        panelCentral.add(affichageJoueur);

        //Création du panel bas
        JPanel panelBas = new JPanel(new BorderLayout());
        panelBas.setPreferredSize(new Dimension(w,h/8));
        panelBas.add(b.lancerPartie(),BorderLayout.SOUTH);


        frame.add(panelHaut,BorderLayout.NORTH);
        frame.add(panelCentral,BorderLayout.CENTER);
        frame.add(panelBas,BorderLayout.SOUTH);
        setResize();

    }

    public JPanel getFrame(){
        return frame;
    }

    public JPanel createJ(int joueur){
        JPanel panJ = new JPanel(new GridLayout(3,3));
        panJ.setBackground(Color.gray);
        panJ.setBorder(BorderFactory.createLineBorder(Color.black));
        panJ.add(new JLabel("Joueur "+ joueur));
        panJ.add(new JPanel());
        panJ.add(new JLabel("Type de joueur"));
        JComboBox listeTypeJoueur = new JComboBox();
        listeTypeJoueur.addItem("Humain");
        listeTypeJoueur.addItem("IA");

        listeTypeJoueur.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                JComboBox comboBox = (JComboBox) event.getSource();
                Object selected = comboBox.getSelectedItem();
                if (selected.toString().equals("IA")) {
                    setDiff(true,joueur);
                    listeDiff[joueur-1]=1;
                    panJ.updateUI();
                } else{
                    setDiff(false,joueur);
                    listeDiff[joueur-1]=0;
                    panJ.updateUI();
                }
            }
        });
        panJ.add(listeTypeJoueur);
        JPanel panDiff= new JPanel();
        JPanel selDiff= new JPanel();
        diff[joueur-1] = panDiff;
        diff[joueur+3] = selDiff;

        panJ.add(diff[joueur-1]);
        panJ.add(diff[joueur+3]);
        return panJ;
    }

    public void setDiff(boolean IA, int joueur){
        if(IA){
            diff[joueur-1].add(new JLabel("Difficulté"));
            JComboBox difficulte = new JComboBox();
            difficulte.addItem("Facile");
            difficulte.addItem("Intermédiaire");
            difficulte.addItem("Difficile (inter)");
            difficulte.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    JComboBox comboBox = (JComboBox) event.getSource();
                    Object selected = comboBox.getSelectedItem();
                    if (selected.toString().equals("Facile")) {
                        listeDiff[joueur-1]=1;
                    } else if(selected.toString().equals("Intermédiaire")){
                        listeDiff[joueur-1]=2;
                    } else{
                        listeDiff[joueur-1]=2;
                    }
                }
            });
            diff[joueur+3].add(difficulte);

        }else{
            diff[joueur-1].removeAll();
            diff[joueur+3].removeAll();
        }
    }

    public void setTypePartie(boolean deuxJoueur){
        if(deuxJoueur){
            affichageJoueurD.remove(J3);
            affichageJoueurG.remove(J4);
            nbJoueur=2;
        }else{
            affichageJoueurG.add(J4);
            affichageJoueurD.add(J3);
            nbJoueur=4;
        }
    }


    public int[] getListeDiff() {
        return listeDiff;
    }

    public int getNbJoueur() {
        return nbJoueur;
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
