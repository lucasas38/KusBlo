package Vue;

import Controleur.Controleur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

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
        JPanel panelOpt;
        JPanel limGauche;
        JPanel limDroite;
        boolean adaptAct=false;
        int w;
        int h;


        public InterfaceJeu(Controleur cont, int width, int height, ImageKusBlo im){
            c=cont;
            frame= new JPanel(new BorderLayout());
            //frame.setSize(w, h);
            w=width;
            h=height;
            setResize();
            boutonMenu= new Bouton(c).menuJeu();

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
            JPanel menu= new JPanel(new BorderLayout());
            menu.add(boutonMenu,BorderLayout.CENTER);
            menu.setPreferredSize(new Dimension(getFrameW()/4,getFrameH()/3));
            panelGauche.add(menu);


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
            JPanel histo=new JPanel(new BorderLayout());
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
        public void refreshPanJoueur(int couleur, int piece){
            switch (couleur){
                case 1:

                    j4.refreshAffichage(piece);
                    //j4.pan.updateUI();
                    break;
                case 2:
                    cleanTour();
                    j2.setTour();
                    j1.refreshAffichage(piece);
                    //j1.pan.updateUI();
                    break;
                case 3:
                    cleanTour();
                    j3.setTour();
                    j2.refreshAffichage(piece);
                    break;
                case 4:
                    cleanTour();
                    j4.setTour();
                    j3.refreshAffichage(piece);
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

          /*  frame.addWindowStateListener(new WindowStateListener() {
                public void windowStateChanged(WindowEvent event) {
                    boolean isMaximized = (event.getNewState() & Frame.MAXIMIZED_BOTH) == Frame.MAXIMIZED_BOTH;
                    boolean wasMaximized =(event.getOldState() & Frame.MAXIMIZED_BOTH) == Frame.MAXIMIZED_BOTH;

                    if (isMaximized && !wasMaximized) {
                        resizeAllPanel();
                    } else if (wasMaximized && !isMaximized) {
                        resizeAllPanel();
                    }
                }
            });*/
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
            boutonMenu.setEnabled(false);
            panelOpt= new JPanel(new BorderLayout());
            limGauche=new JPanel();
            limGauche.setBackground(new Color(0,0,0,75));
            limGauche.setPreferredSize(new Dimension(getFrameW()/4,getFrameH()));
            limDroite= new JPanel();
            limDroite.setPreferredSize(new Dimension(getFrameW()/4,getFrameH()));
            limDroite.setBackground(new Color(0,0,0,75));
            JPanel panTest = new JPanel();
            panTest.setLayout(new GridLayout(6,1,5,5));
            panTest.add(new Bouton(c).reprendre());
            panTest.add(new Bouton(c).newGame());
            panTest.add(new Bouton(c).menuPrincpal());
            panTest.add(Box.createHorizontalGlue());
            panelOpt.setPreferredSize(new Dimension(getFrameW()/2,getFrameH()));
            panTest.setBackground(new Color(0,0,0,75));
            panelOpt.add(limGauche,BorderLayout.WEST);
            panelOpt.add(panTest,BorderLayout.CENTER);
            panelOpt.add(limDroite,BorderLayout.EAST);
            JPanel panGrey=new JPanel(new BorderLayout());
            JPanel panGrey2=new JPanel(new BorderLayout());
            panGrey.setBackground(new Color(0,0,0,75));
            panGrey2.setBackground(new Color(2,0,0,75));
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
            frame.updateUI();
            if(adaptAct){
                actMouseClick();

            }
            selAdapt.setActiv(true);
        }
    }


