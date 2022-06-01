package Vue;

import Controleur.Controleur;
import Modele.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class InterfaceJeu {
        JPanel frame;
        VueNiveau graph;
        MenuJeu m;
        PanneauJoueur[] joueurs;
        AdaptateurSouris mouseAdapt;
        AdaptateurSelPiece selAdapt;
        Controleur c;
        JButton boutonMenu;
        JButton annuler;
        JButton refaire;
        JButton pause;
        JButton resume;
        JButton aide;
        JButton menuRegle;
        JButton load;
        JPanel menuGauche;
        JPanel histo;
        JPanel panelOpt;
        JPanel limGauche;
        JPanel limDroite;
        JPanel panGrey;
        JPanel panGrey2;
        JPanel panelGauche;
        JPanel panelCentral;
        JPanel panelDroite;
        Bouton b;
        boolean listeAct;
        int w;
        int h;


        public InterfaceJeu(Controleur cont, Bouton bout, ImageKusBlo im){
            c=cont;
            c.setPause(false);
            frame= new JPanel(new BorderLayout());
            w=c.getFrameW();
            h=c.getFrameH();
            setResize();
            b=bout;
            //Création des différents boutons
            boutonMenu= b.menuJeu();
            int minDim = Math.min(getFrameW()/8,getFrameH()/16);
            pause= b.pause();
            Image img = im.pause ;
            Image newimg = img.getScaledInstance( minDim, minDim,  java.awt.Image.SCALE_SMOOTH ) ;
            ImageIcon icon = new ImageIcon( newimg );
            pause.setIcon(icon);
            pause.setBorder(BorderFactory.createEmptyBorder());

            resume =b.resume();
            img = im.resume ;
            newimg = img.getScaledInstance( minDim, minDim,  java.awt.Image.SCALE_SMOOTH ) ;
            icon = new ImageIcon( newimg );
            resume.setIcon(icon);
            resume.setBorder(BorderFactory.createEmptyBorder());

            menuRegle=b.menuRegle();
            aide=b.aide();
            joueurs=new PanneauJoueur[4];
            m=new MenuJeu(c,b,im);
            graph = new VueNiveau(c,im);
            mouseAdapt =new AdaptateurSouris(c);
            selAdapt= new AdaptateurSelPiece(graph,m,c);
            //Création des panneau joueur
            joueurs[0] = new PanneauJoueur(1,c,im);
            joueurs[1] = new PanneauJoueur(2,c,im);
            joueurs[2] = new PanneauJoueur(3,c,im);
            joueurs[3] = new PanneauJoueur(4,c,im);

            //Panel Gauche
            panelGauche = new JPanel();
            panelGauche.setLayout(new BoxLayout(panelGauche,BoxLayout.PAGE_AXIS));
            panelGauche.add(joueurs[0].pan);
            panelGauche.add(joueurs[3].pan);
            menuGauche= new JPanel(new GridLayout(2,2));
            menuGauche.add(boutonMenu);
            menuGauche.add(aide);
            menuGauche.add(menuRegle);
            menuGauche.add(pause);
            menuGauche.setPreferredSize(new Dimension(getFrameW()/4,getFrameH()/8));
            panelGauche.add(menuGauche);


            //Panel central
            panelCentral = new JPanel(new BorderLayout());
            JPanel repartiteur =new JPanel(); //réparti le jeu et le menu du bas
            repartiteur.setLayout((new BoxLayout(repartiteur,BoxLayout.PAGE_AXIS)));
            repartiteur.add(graph.panelJeu);
            setMenu1(c.getActJoueur(),c.getActCouleur());
            repartiteur.add(m.menu);
            panelCentral.add(repartiteur,BorderLayout.CENTER);


            //Panel Droite
            panelDroite = new JPanel();
            panelDroite.setLayout(new BoxLayout(panelDroite,BoxLayout.PAGE_AXIS));
            panelDroite.add(joueurs[1].pan);
            panelDroite.add(joueurs[2].pan);

            int minDim2 = Math.min(getFrameW()/8,getFrameH()/8);

            annuler=b.annuler();
            Image img2 = im.undo ;
            Image newimg2 = img2.getScaledInstance( minDim2, minDim2,  java.awt.Image.SCALE_SMOOTH ) ;
            ImageIcon icon2 = new ImageIcon( newimg2 );
            annuler.setIcon(icon2);
            annuler.setBorder(BorderFactory.createEmptyBorder());
            annuler.setEnabled(false);

            refaire=b.refaire();
            refaire.setEnabled(false);
            img2 = im.redo ;
            newimg2 = img2.getScaledInstance( minDim2, minDim2,  java.awt.Image.SCALE_SMOOTH ) ;
            icon2 = new ImageIcon( newimg2 );
            refaire.setIcon(icon2);
            refaire.setBorder(BorderFactory.createEmptyBorder());

            histo=new JPanel(new GridLayout(1,2));
            histo.add(annuler);
            histo.add(refaire);
            histo.setPreferredSize(new Dimension(getFrameW()/4,getFrameH()/8));
            panelDroite.add(histo);




            frame.add(panelGauche,BorderLayout.WEST);
            frame.add(panelCentral,BorderLayout.CENTER);
            frame.add(panelDroite,BorderLayout.EAST);



            m.menuListePiece.addMouseListener(selAdapt);
            graph.panelJeu.addMouseMotionListener(mouseAdapt);
            graph.panelJeu.addMouseListener(mouseAdapt);
            graph.panelJeu.addMouseWheelListener(mouseAdapt);
            joueurs[0].setTour();
            frame.updateUI();

        }

        public int getFrameH(){
            return c.getFrameH();
        }

        public int getFrameW(){
            return c.getFrameW();
        }

        public MenuJeu getM() {
            return m;
        }

        public VueNiveau getGraph() {
            return graph;
        }

        //Supprime les listener du terrain de jeu
        public void delListener(){
            mouseAdapt.setActiv(false);
            c.setActivKeyAdapt(false);
        }
        public void actListener(){
            mouseAdapt.setActiv(true);
            c.setActivKeyAdapt(true);
        }

        //Active le menu avec la liste
        public void setMenu1(int j, int coul){
            delListener();
            m.setMenuListePiece(j,coul);

        }

        //Active le menu avec les adaptateurs
        public  void setMenu2(int numPiece){
            actListener();;
            m.setMenuPieceUnique(numPiece);
        }

        //Met à jour uniquement le panneau du joueur qui a joué (qui est donc le joueur précedent
        public void refreshPanJoueur(int indiceCouleur, int piece, boolean undo, Piece p){
            cleanTour();
            joueurs[indiceCouleur].setTour();
            joueurs[indiceCouleur].refreshAffichage(piece,undo,p);
        }

        public void setTour(int couleur){
            cleanTour();
            joueurs[couleur-1].setTour();
        }

        public void setScore(int couleur, int score){
            joueurs[couleur-1].setScore(score);
        }
        public void cleanTour(){
            joueurs[0].delTour();
            joueurs[1].delTour();
            joueurs[2].delTour();
            joueurs[3].delTour();
        }


        //Gère le redimensionnement, bug à régler avec le plein écran
        public void setResize(){
            frame.addComponentListener(new ComponentAdapter() {
                public void componentResized(ComponentEvent componentEvent) {
                    resizeAllPanel();
                }
            });

        }

        //redimensionne tous les panels
        public void resizeAllPanel(){
            graph.resize(getFrameW(),getFrameH());
            m.resize(getFrameW(),getFrameH());
            joueurs[0].resize(getFrameW(),getFrameH());
            joueurs[1].resize(getFrameW(),getFrameH());
            joueurs[2].resize(getFrameW(),getFrameH());
            joueurs[3].resize(getFrameW(),getFrameH());
            menuGauche.setPreferredSize(new Dimension(getFrameW()/4,getFrameH()/8));
            histo.setPreferredSize(new Dimension(getFrameW()/4,getFrameH()/8));
        }

        public JPanel getFrame(){
            return frame;
        }


        //Affiche le menu option du jeu et met en pause le jeu
        public void setMenuOpt(){
            if(mouseAdapt.activ)
                listeAct=true;
            delListener();

            selAdapt.setActiv(false);
            refaire.setEnabled(false);
            annuler.setEnabled(false);
            pause.setEnabled(false);
            resume.setEnabled(false);
            aide.setEnabled(false);
            boutonMenu.setEnabled(false);
            menuRegle.setEnabled(false);

            panelOpt= new JPanel(new BorderLayout());
            limGauche=new JPanel();
            limGauche.setBackground(new Color(0,0,0,75));
            limGauche.setPreferredSize(new Dimension(getFrameW()/4,getFrameH()));

            limDroite= new JPanel();
            limDroite.setPreferredSize(new Dimension(getFrameW()/4,getFrameH()));
            limDroite.setBackground(new Color(0,0,0,75));

            JPanel listeBoutons = new JPanel();
            listeBoutons.setLayout(new GridLayout(7,1,5,5));
            listeBoutons.add(b.reprendre());
            listeBoutons.add(b.newGame());
            listeBoutons.add(b.save());
            load= b.load();
            load.setEnabled(c.canLoad());
            listeBoutons.add(load);
            listeBoutons.add(b.optionJeu());
            listeBoutons.add(b.menuPrincpal());
            listeBoutons.add(Box.createHorizontalGlue());

            panelOpt.setPreferredSize(new Dimension(getFrameW()/2,getFrameH()));
            listeBoutons.setBackground(new Color(0,0,0,75));
            panelOpt.add(limGauche,BorderLayout.WEST);
            panelOpt.add(listeBoutons,BorderLayout.CENTER);
            panelOpt.add(limDroite,BorderLayout.EAST);

            panGrey=new JPanel(new BorderLayout());
            panGrey2=new JPanel(new BorderLayout());
            panGrey.setBackground(new Color(0,0,0,75));
            panGrey2.setBackground(new Color(0,0,0,75));
            panelOpt.setBackground(new Color(0,0,0,75));

            frame.add(panGrey,BorderLayout.WEST);
            frame.add(panelOpt,BorderLayout.CENTER);
            frame.add(panGrey2,BorderLayout.EAST);

            frame.setComponentZOrder(panGrey, 0);
            frame.setComponentZOrder(panGrey2, 0);
            frame.setComponentZOrder(panelOpt, 0);
            frame.updateUI();

        }

        //Enlève le menu et reprend le jeu en son état actuel
        public void reprendre(){
            frame.removeAll();
            frame.add(panelGauche,BorderLayout.WEST);
            frame.add(panelCentral,BorderLayout.CENTER);
            frame.add(panelDroite,BorderLayout.EAST);

            boutonMenu.setEnabled(true);
            if(c.peutAnnuler())
            annuler.setEnabled(true);
            if(c.peutRefaire())
            refaire.setEnabled(true);
            pause.setEnabled(true);
            resume.setEnabled(true);
            if(!c.isPause())
            aide.setEnabled(true);
            frame.updateUI();
            if(listeAct){
                actListener();
                listeAct=false;
            }
            selAdapt.setActiv(true);
            menuRegle.setEnabled(true);
            setResize();
        }

        public void charger(){
            graph.charger();
        }


        //Modifie le bouton pause
        public void changePauseMenu(boolean mettrePause){
            if(mettrePause){
                menuGauche.remove(resume);
                menuGauche.add(pause);
            }else{
                menuGauche.remove(pause);
                menuGauche.add(resume);
            }
            menuGauche.updateUI();
        }

        public void setAnnuler(boolean an) {
            annuler.setEnabled(an);
        }

        public void setRefaire(boolean re) {
            refaire.setEnabled(re);
        }

        public void setEnabledAide(boolean act){
                aide.setEnabled(act);
        }

        public void actMenu1(int cool,boolean act){
                if(act){
                    joueurs[cool-1].setMenu1();
                }else{
                    joueurs[cool-1].delMenu1();
                }
        }
        public void updateNameIA(int numIACouleur, int diff){
                joueurs[numIACouleur].updateNameIA(diff);
        }


        public void refreshLoad(){
                load.setEnabled(c.canLoad());
        }
}


