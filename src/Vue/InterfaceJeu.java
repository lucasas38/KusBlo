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
        JPanel menuGauche;
        JPanel panelOpt;
        JPanel limGauche;
        JPanel limDroite;
        JPanel panGrey;
        JPanel panGrey2;
        boolean adaptAct;
        Bouton b;
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
            adaptAct=false;
            boutonMenu= b.menuJeu();
            pause= b.pause();
            resume =b.resume();
            aide=b.aide();
            joueurs=new PanneauJoueur[4];
            m=new MenuPiece(c);
            graph = new VueNiveau(c,im);
            mouseAdapt =new AdaptateurSouris(c);
            selAdapt= new AdaptateurSelPiece(graph,m,c,true);
            //Création des panneau joueur
            joueurs[0] = new PanneauJoueur(1,c,im);
            joueurs[1] = new PanneauJoueur(2,c,im);
            joueurs[2] = new PanneauJoueur(3,c,im);
            joueurs[3] = new PanneauJoueur(4,c,im);


            //Panel Gauche
            JPanel panelGauche = new JPanel();
            panelGauche.setLayout(new BoxLayout(panelGauche,BoxLayout.PAGE_AXIS));
            panelGauche.add(joueurs[0].pan);
            panelGauche.add(joueurs[3].pan);
            menuGauche= new JPanel(new GridLayout(2,2));
            menuGauche.add(boutonMenu);
            menuGauche.add(aide);
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


            //Panel Droite
            JPanel panelDroite = new JPanel();
            panelDroite.setLayout(new BoxLayout(panelDroite,BoxLayout.PAGE_AXIS));
            panelDroite.add(joueurs[1].pan);
            panelDroite.add(joueurs[2].pan);
            annuler=b.annuler();
            annuler.setEnabled(false);
            refaire=b.refaire();
            refaire.setEnabled(false);
            JPanel histo=new JPanel(new GridLayout(2,1));
            histo.add(annuler);
            histo.add(refaire);
            histo.setPreferredSize(new Dimension(getFrameW()/4,getFrameH()/3));
            panelDroite.add(histo);



            //Pannel principal

            frame.add(panelGauche,BorderLayout.WEST);
            frame.add(panelCentral,BorderLayout.CENTER);
            frame.add(panelDroite,BorderLayout.EAST);



            m.menuListePiece.addMouseListener(selAdapt);
            m.affichagePiece.addMouseListener(new AdaptateurSelPiece(graph, m,c,false));
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

        public MenuPiece getM() {
            return m;
        }

        public VueNiveau getGraph() {
            return graph;
        }

        //Supprime les listener du terrain de jeu
        public void delMouseClick(){
            adaptAct= mouseAdapt.activ;
            mouseAdapt.setActiv(false);
            c.setActivKeyAdapt(false);
        }
        public void actMouseClick(){
            mouseAdapt.setActiv(true);
            c.setActivKeyAdapt(true);
        }

        //Active le menu avec la liste
        public void setMenu1(int j, int coul){
            delMouseClick();
            m.setMenuListePiece(j,coul);

        }

        //Active le menu avec les adaptateurs
        public  void setMenu2(int numPiece){
            if(!getM().isPieceSelected()){
                mouseAdapt.setActiv(true);
                c.setActivKeyAdapt(true);
            }
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

        //redimensionne tous les pannel
        public void resizeAllPanel(){
            graph.resize(getFrameW(),getFrameH());
            m.resize(getFrameW(),getFrameH());
            joueurs[0].resize(getFrameW(),getFrameH());
            joueurs[1].resize(getFrameW(),getFrameH());
            joueurs[2].resize(getFrameW(),getFrameH());
            joueurs[3].resize(getFrameW(),getFrameH());
        }

        public JPanel getFrame(){
            return frame;
        }


        public void setMenuOpt(){
            delMouseClick();
            selAdapt.setActiv(false);
            refaire.setEnabled(false);
            annuler.setEnabled(false);
            pause.setEnabled(false);
            resume.setEnabled(false);
            aide.setEnabled(false);
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

        public void reprendre(){
            frame.remove(panelOpt);
            frame.remove((limGauche));
            frame.remove(limDroite);
            frame.remove(panGrey);
            frame.remove(panGrey2);
            boutonMenu.setEnabled(true);
            annuler.setEnabled(true);
            refaire.setEnabled(true);
            pause.setEnabled(true);
            resume.setEnabled(true);
            aide.setEnabled(true);
            frame.updateUI();
            if(adaptAct){
                actMouseClick();
            }
            selAdapt.setActiv(true);
            setResize();
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

    public void setAnnuler(boolean an) {
        annuler.setEnabled(an);
    }

    public void setRefaire(boolean re) {
        refaire.setEnabled(re);
    }

    public void setEnabledAide(boolean act){
            aide.setEnabled(act);
    }
}


