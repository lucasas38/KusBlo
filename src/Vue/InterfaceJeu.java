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
        MenuPiece m;
        PanneauJoueur j1;
        PanneauJoueur j2;
        PanneauJoueur j3;
        PanneauJoueur j4;
        AdaptateurSouris mouseAdapt;
        AdaptateurSelPiece selAdapt;
        AdaptateurClavier keyAdapt;
        Controleur c;
        JButton boutonMenu;
        JButton annuler;
        JButton refaire;
        JButton pause;
        JButton resume;
        JPanel menuGauche;
        JPanel panelOpt;
        JPanel limGauche;
        JPanel limDroite;
        boolean adaptAct=false;
        Bouton b;
        int w;
        int h;


        public InterfaceJeu(Controleur cont, Bouton bout, ImageKusBlo im){
            c=cont;
            c.setPause(false);
            frame= new JPanel(new BorderLayout());
            //frame.setSize(w, h);
            w=c.getFrameW();
            h=c.getFrameH();
            setResize();
            b=bout;
            boutonMenu= b.menuJeu();
            pause= b.pause();
            resume =b.resume();

            m=new MenuPiece(c);
            graph = new VueNiveau(c,im);
            mouseAdapt =new AdaptateurSouris(graph,m,c);
            keyAdapt=new AdaptateurClavier(c, mouseAdapt,m);
            selAdapt= new AdaptateurSelPiece(graph,m,c,true);
            //Création des panneau joueur
            j1 = new PanneauJoueur(1,c,im);
            j2 = new PanneauJoueur(2,c,im);
            j3 = new PanneauJoueur(3,c,im);
            j4 = new PanneauJoueur(4,c,im);


            //Panel Gauche
            JPanel panelGauche = new JPanel();
            panelGauche.setLayout(new BoxLayout(panelGauche,BoxLayout.PAGE_AXIS));
            panelGauche.add(j1.pan);
            panelGauche.add(j4.pan);
            menuGauche= new JPanel(new GridLayout(2,1));
            menuGauche.add(boutonMenu);
            menuGauche.add(pause);
            menuGauche.setPreferredSize(new Dimension(getFrameW()/4,getFrameH()/3));
            panelGauche.add(menuGauche);


            //Panel central
            JPanel panelCentral = new JPanel(new BorderLayout());
            JPanel repartiteur =new JPanel(); //réparti le jeu et le menu du bas
            repartiteur.setLayout((new BoxLayout(repartiteur,BoxLayout.PAGE_AXIS)));
            repartiteur.add(graph.panelJeu);
            setMenu1(c.getActJoueur(),c.getActCouleur());
            repartiteur.add(m.menu);
            panelCentral.add(repartiteur,BorderLayout.CENTER);
            m.menuType1.addMouseListener(selAdapt);
            m.affichagePiece.addMouseListener(new AdaptateurSelPiece(graph, m,c,false));
            graph.panelJeu.addMouseMotionListener(mouseAdapt);
            graph.panelJeu.addMouseListener(mouseAdapt);
            graph.panelJeu.addMouseWheelListener(mouseAdapt);
            frame.addKeyListener(keyAdapt);

            //Panel Droite
            JPanel panelDroite = new JPanel();
            panelDroite.setLayout(new BoxLayout(panelDroite,BoxLayout.PAGE_AXIS));
            panelDroite.add(j2.pan);
            panelDroite.add(j3.pan);
            annuler=b.annuler();
            refaire=b.refaire();
            JPanel histo=new JPanel(new GridLayout(2,1));
            histo.add(annuler);
            histo.add(refaire);
            histo.setPreferredSize(new Dimension(getFrameW()/4,getFrameH()/3));
            panelDroite.add(histo);



            //Pannel principal

            frame.add(panelGauche,BorderLayout.WEST);
            frame.add(panelCentral,BorderLayout.CENTER);
            frame.add(panelDroite,BorderLayout.EAST);



            j1.setTour();
            frame.updateUI();
            //Ajout à la fenêtre + affichage
            //frame.add(frame);
            //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //frame.setVisible(true);

        }

        public int getFrameH(){
            return c.getFrameH();
        }

        public int getFrameW(){
            return c.getFrameW();
        }

        public MenuPiece getM() {
            return m;
        }

        public VueNiveau getGraph() {
            return graph;
        }

        //Supprime les listener du terrain de jeu
        public void delMouseClick(){
            if(mouseAdapt.activ){
                adaptAct=true;
            }else{
                adaptAct=false;
            }
            mouseAdapt.setActiv(false);
            keyAdapt.setActiv(false);
        }
        public void actMouseClick(){
            mouseAdapt.setActiv(true);
            keyAdapt.setActiv(true);
        }

        //Active le menu avec la liste
        public void setMenu1(int j, int coul){
            delMouseClick();
            m.setMenuType1(j,coul);

        }

        //Active le menu avec les adaptateurs
        public  void setMenu2(int numPiece){
            if(!getM().isPieceSelected()){
                mouseAdapt.setActiv(true);
                keyAdapt.setActiv(true);
            }
            m.setMenuType2(numPiece);
        }

        //Met à jour uniquement le panneau du joueur qui a joué (qui est donc le joueur précedent
        public void refreshPanJoueur(int couleur, int piece, boolean undo, Piece p){
            switch (couleur){
                case 1:
                    cleanTour();
                    j1.setTour();
                    j4.refreshAffichage(piece,undo,p);
                    break;
                case 2:
                    cleanTour();
                    j2.setTour();
                    j1.refreshAffichage(piece,undo,p);
                    break;
                case 3:
                    cleanTour();
                    j3.setTour();
                    j2.refreshAffichage(piece,undo,p);
                    break;
                case 4:
                    cleanTour();
                    j4.setTour();
                    j3.refreshAffichage(piece,undo,p);
                    break;
                default:
                    break;
            }
        }

        public void setTour(int coul){
            cleanTour();
            switch (coul){
                case 1:
                    j1.setTour();
                    break;
                case 2:
                    j2.setTour();
                    break;
                case 3:
                    j3.setTour();
                    break;
                case 4:
                    j4.setTour();
                    break;
                default:
                    break;
            }
        }

        public void setScore(int coul, int score){
            switch (coul){
                case 1:
                    j1.setScore(score);
                    break;
                case 2:
                    j2.setScore(score);
                    break;
                case 3:
                    j3.setScore(score);
                    break;
                case 4:
                    j4.setScore(score);
                    break;
                default:
                    break;
            }
        }
        public void cleanTour(){
            j1.delTour();
            j2.delTour();
            j3.delTour();
            j4.delTour();
        }


        //Gère le redimensionnement, bug à régler avec le plein écran
        public void setResize(){
            frame.addComponentListener(new ComponentAdapter() {
                public void componentResized(ComponentEvent componentEvent) {
                    resizeAllPanel();
                }
            });

        }

        //redimensionne tous les pannel
        public void resizeAllPanel(){
            graph.resize(getFrameW(),getFrameH());
            m.resize(getFrameW(),getFrameH());
            j1.resize(getFrameW(),getFrameH());
            j2.resize(getFrameW(),getFrameH());
            j3.resize(getFrameW(),getFrameH());
            j4.resize(getFrameW(),getFrameH());
        }

        public JPanel getFrame(){
            return frame;
        }


        public void setMenuOpt(){
            delMouseClick();
            selAdapt.setActiv(false);
            refaire.setEnabled(false);
            annuler.setEnabled(false);
            boutonMenu.setEnabled(false);
            panelOpt= new JPanel(new BorderLayout());
            limGauche=new JPanel();
            limGauche.setBackground(new Color(0,0,0,75));
            limGauche.setPreferredSize(new Dimension(getFrameW()/4,getFrameH()));
            limDroite= new JPanel();
            limDroite.setPreferredSize(new Dimension(getFrameW()/4,getFrameH()));
            limDroite.setBackground(new Color(0,0,0,75));
            JPanel listeBoutons = new JPanel();
            listeBoutons.setLayout(new GridLayout(6,1,5,5));
            listeBoutons.add(b.reprendre());
            listeBoutons.add(b.newGame());
            listeBoutons.add(b.save());
            listeBoutons.add(b.load());
            listeBoutons.add(b.menuPrincpal());
            listeBoutons.add(Box.createHorizontalGlue());
            panelOpt.setPreferredSize(new Dimension(getFrameW()/2,getFrameH()));
            listeBoutons.setBackground(new Color(0,0,0,75));
            panelOpt.add(limGauche,BorderLayout.WEST);
            panelOpt.add(listeBoutons,BorderLayout.CENTER);
            panelOpt.add(limDroite,BorderLayout.EAST);
            JPanel panGrey=new JPanel(new BorderLayout());
            JPanel panGrey2=new JPanel(new BorderLayout());
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

        public void reprendre(){
            frame.remove(panelOpt);
            frame.remove((limGauche));
            frame.remove(limDroite);
            boutonMenu.setEnabled(true);
            annuler.setEnabled(true);
            refaire.setEnabled(true);
            frame.updateUI();
            if(adaptAct){
                actMouseClick();
            }
            selAdapt.setActiv(true);
        }

        public void charger(){
            graph.charger();
        }

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
    }


