package Vue;

import Controleur.Controleur;
import Structures.BasicBackgroundPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class MenuPartiePerso {
    JPanel frame;
    Controleur cont;
    int w;
    int h ;
    Bouton b;
    JPanel J1;
    JPanel J2;
    JPanel J3;
    JPanel J4;
    JPanel[] diff;
    int[] listeDiff;


    public MenuPartiePerso(Controleur c,Bouton bout,int width, int height){
        cont=c;
        w=width;
        h=height;
        b=bout;
        frame= new JPanel(new BorderLayout());
        diff = new JPanel[8];
        listeDiff=new int[4];
        listeDiff[0]=0;
        listeDiff[1]=0;
        listeDiff[2]=0;
        listeDiff[3]=0;
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
        JPanel panelCentral = new JPanel(new GridLayout(2,2));
        panelCentral.setPreferredSize(new Dimension(w,3*h/8));
        panelCentral.add(J1);
        panelCentral.add(J2);
        panelCentral.add(J4);
        panelCentral.add(J3);

        //Création du panel bas
        JPanel panelBas = new JPanel(new BorderLayout());
        panelBas.setPreferredSize(new Dimension(w,h/8));
        panelBas.add(b.lancerPartie(),BorderLayout.SOUTH);


        frame.add(panelHaut,BorderLayout.NORTH);
        frame.add(panelCentral,BorderLayout.CENTER);
        frame.add(panelBas,BorderLayout.SOUTH);

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
            difficulte.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    JComboBox comboBox = (JComboBox) event.getSource();
                    Object selected = comboBox.getSelectedItem();
                    if (selected.toString().equals("Facile")) {
                        listeDiff[joueur-1]=1;
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

    public int[] getListeDiff() {
        return listeDiff;
    }
}
