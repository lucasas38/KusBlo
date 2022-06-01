package Vue;

import Controleur.Controleur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuPartiePerso {
    JPanel frame;
    Controleur cont;
    Bouton b;
    JPanel affichageJoueur;
    JPanel affichageJoueurG;
    JPanel affichageJoueurD;
    JPanel panelBas;
    JPanel panelHaut;
    BasicBackgroundPanel logo;
    JPanel selectionNbJoueur;
    JPanel J1;
    JPanel J2;
    JPanel J3;
    JPanel J4;
    JPanel[] diff;
    int[] listeDiff;
    int nbJoueur;
    ImageKusBlo im;
    Color[] colorJ;


    public MenuPartiePerso(Controleur c,Bouton bout, ImageKusBlo ima){
        cont=c;
        b=bout;
        im=ima;
        frame= new JPanel(new BorderLayout());
        diff = new JPanel[8];
        listeDiff=new int[4];
        listeDiff[0]=0;
        listeDiff[1]=0;
        listeDiff[2]=0;
        listeDiff[3]=0;
        colorJ= new Color[4];
        colorJ[0]=new Color(102, 140, 255);
        colorJ[1]=new Color(255, 214, 51);
        colorJ[2]=new Color(255, 77, 77);
        colorJ[3]=new Color(102, 255, 102);
        nbJoueur=2;
        J1=createJ(1);
        J2=createJ(2);
        J3=createJ(3);
        J4=createJ(4);

        //Création du panel Logo
        panelHaut = new JPanel(new GridLayout(1,3));
        panelHaut.setPreferredSize(new Dimension(cont.getFrameW(),cont.getFrameH()/8));
        JPanel panelMenu= new JPanel();
        panelMenu.add(b.menuPrincpal());
        panelHaut.add(panelMenu);
        logo = new BasicBackgroundPanel(im.getLogo());
        logo.setPreferredSize(new Dimension(cont.getFrameW()/3,cont.getFrameH()/8));
        panelHaut.add(logo);
        panelHaut.add(new JPanel(new BorderLayout()));

        //Création panel Central
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BoxLayout(panelCentral,BoxLayout.PAGE_AXIS));
        selectionNbJoueur = new JPanel(new GridLayout(1,4));
        affichageJoueur = new JPanel(new GridLayout(1,2,20,0));
        selectionNbJoueur.setPreferredSize(new Dimension(cont.getFrameW(),cont.getFrameH()/8));
        JPanel panLab= new JPanel();
        panLab.add(new JLabel("Choisissez un type de partie :"));
        selectionNbJoueur.add(panLab);
        JPanel panCombo = new JPanel();
        JComboBox typeJeu = new JComboBox();
        typeJeu.addItem("Partie à 2 Joueurs");
        typeJeu.addItem("Partie à 4 Joueurs");

        typeJeu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                JComboBox comboBox = (JComboBox) event.getSource();
                Object selected = comboBox.getSelectedItem();
                if (selected.toString().equals("Partie à 2 Joueurs")) {
                    setTypePartie(true);
                    affichageJoueur.updateUI();
                } else{
                    setTypePartie(false);
                    affichageJoueur.updateUI();

                }
            }
        });
        panCombo.add(typeJeu);
        selectionNbJoueur.add(panCombo);
        selectionNbJoueur.add(new JPanel());
        selectionNbJoueur.add(new JPanel());

        affichageJoueur.setPreferredSize(new Dimension(cont.getFrameW(),5*cont.getFrameH()/8));
        affichageJoueurG=new JPanel((new GridLayout(2,1,0,20)));
        affichageJoueurD=new JPanel((new GridLayout(2,1,0,20)));
        affichageJoueurG.add(J1);
        affichageJoueurD.add(J2);
        affichageJoueur.add(affichageJoueurG);
        affichageJoueur.add(affichageJoueurD);

        panelCentral.add(selectionNbJoueur);
        panelCentral.add(affichageJoueur);

        //Création du panel bas
        panelBas = new JPanel(new BorderLayout());
        panelBas.setPreferredSize(new Dimension(cont.getFrameW(),cont.getFrameH()/8));
        JPanel lancerPartie= new JPanel();
        lancerPartie.add(b.lancerPartie());
        panelBas.add(lancerPartie,BorderLayout.SOUTH);


        frame.add(panelHaut,BorderLayout.NORTH);
        frame.add(panelCentral,BorderLayout.CENTER);
        frame.add(panelBas,BorderLayout.SOUTH);
        setResize();

    }

    public JPanel getFrame(){
        return frame;
    }

    //Créer le menu de sélection pour un joueur
    public JPanel createJ(int joueur){
        JPanel panJ = new JPanel(new GridLayout(3,3));
        panJ.setBackground(colorJ[joueur-1]);
        panJ.setBorder(BorderFactory.createLineBorder(Color.black));


        JPanel panNumJoueur= new JPanel();
        panNumJoueur.add(new JLabel("Joueur "+ joueur));
        panNumJoueur.setBackground(colorJ[joueur-1]);
        panJ.add(panNumJoueur);


        JPanel panVide = new JPanel();
        panVide.setBackground(colorJ[joueur-1]);
        panVide.setBorder(BorderFactory.createLineBorder(colorJ[joueur-1]));
        panJ.add(panVide);


        JPanel panTypeJoueur =new JPanel();
        panTypeJoueur.setBackground(colorJ[joueur-1]);
        panTypeJoueur.add(new JLabel("Type de joueur"));
        panJ.add(panTypeJoueur);


        JPanel panCombo1=new JPanel();
        panCombo1.setBackground(colorJ[joueur-1]);
        panCombo1.setBorder(BorderFactory.createLineBorder(colorJ[joueur-1]));
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
        panCombo1.add(listeTypeJoueur);
        panJ.add(panCombo1);


        JPanel panDiff= new JPanel();
        panDiff.setBackground(colorJ[joueur-1]);
        panDiff.setBorder(BorderFactory.createLineBorder(colorJ[joueur-1]));


        JPanel selDiff= new JPanel();
        selDiff.setBackground(colorJ[joueur-1]);
        selDiff.setBorder(BorderFactory.createLineBorder(colorJ[joueur-1]));

        diff[joueur-1] = panDiff;
        diff[joueur+3] = selDiff;
        panJ.add(diff[joueur-1]);
        panJ.add(diff[joueur+3]);
        return panJ;
    }

    //Créer le sélecteur de difficulté
    public void setDiff(boolean IA, int joueur){
        if(IA){
            diff[joueur-1].removeAll();
            diff[joueur+3].removeAll();
            diff[joueur-1].add(new JLabel("Difficulté"));
            JComboBox difficulte = new JComboBox();
            difficulte.addItem("Facile");
            difficulte.addItem("Intermédiaire");
            difficulte.addItem("Difficile");
            difficulte.addItem("Intermédiaire Ouvrante");
            difficulte.addItem("Intermédiaire Taille");
            difficulte.addItem("Intermédiaire Pattern");
            difficulte.addItem("Intermédiaire Bloquante");
            difficulte.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    JComboBox comboBox = (JComboBox) event.getSource();
                    Object selected = comboBox.getSelectedItem();
                    if (selected.toString().equals("Facile")) {
                        listeDiff[joueur-1]=1;
                    } else if(selected.toString().equals("Intermédiaire")){
                        listeDiff[joueur-1]=2;
                    } else if(selected.toString().equals("Intermédiaire Ouvrante")){
                        listeDiff[joueur-1]=3;
                    } else if(selected.toString().equals("Intermédiaire Taille")){
                        listeDiff[joueur-1]=4;
                    } else if(selected.toString().equals("Intermédiaire Pattern")){
                        listeDiff[joueur-1]=5;
                    } else if(selected.toString().equals("Intermédiaire Bloquante")){
                        listeDiff[joueur-1]=6;
                    } else{
                        listeDiff[joueur-1]=7;
                    }
                }
            });
            diff[joueur+3].add(difficulte);

        }else{
            diff[joueur-1].removeAll();
            diff[joueur+3].removeAll();
        }
    }

    //Selection du type de partie
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

    //redimensionne tous les panels
    public void resizeAllPanel(){
        affichageJoueur.setPreferredSize(new Dimension(cont.getFrameW(),5*cont.getFrameH()/8));
        panelBas.setPreferredSize(new Dimension(cont.getFrameW(),cont.getFrameH()/8));
        logo.setPreferredSize(new Dimension(cont.getFrameW()/3,cont.getFrameH()/8));
        selectionNbJoueur.setPreferredSize(new Dimension(cont.getFrameW(),cont.getFrameH()/8));
        panelHaut.setPreferredSize(new Dimension(cont.getFrameW(),cont.getFrameH()/8));
    }


}
