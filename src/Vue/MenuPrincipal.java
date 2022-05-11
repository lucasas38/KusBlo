package Vue;

import Controleur.Controleur;
import Structures.BasicBackgroundPanel;
import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipal {
    JFrame frame;
    Controleur cont;

    public MenuPrincipal(Controleur c){
        cont=c;
        frame = new JFrame("KusBlo");
        frame.setSize(800, 600);
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BoxLayout(panelCentral,BoxLayout.PAGE_AXIS));
        BasicBackgroundPanel logo = new BasicBackgroundPanel(new ImageKusBlo().getLogo());
        logo.setPreferredSize(new Dimension(frame.getWidth()/2,frame.getHeight()/4));
        panelCentral.add(logo,BorderLayout.NORTH);
        JPanel listeBoutons = new JPanel(new GridLayout(4,1));
        listeBoutons.add(new Bouton(cont).newGame());
        listeBoutons.add(new Bouton(cont).exit());

        listeBoutons.setPreferredSize(new Dimension(frame.getWidth()/2,3*frame.getHeight()/4));
        panelCentral.add(listeBoutons, BorderLayout.CENTER);
        mainPanel.add(panelCentral, BorderLayout.CENTER);
        JPanel panelGauche = new JPanel();
        panelGauche.setPreferredSize(new Dimension(frame.getWidth()/4,frame.getHeight()));

        JPanel panelDroit = new JPanel();
        panelDroit.setPreferredSize(new Dimension(frame.getWidth()/4,frame.getHeight()));
        mainPanel.add(panelGauche, BorderLayout.WEST);
        mainPanel.add(panelDroit, BorderLayout.EAST);
        frame.add(mainPanel);
    }

    public JFrame getFrame(){
        return frame;
    }
}
