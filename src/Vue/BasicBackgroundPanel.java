package Vue;

import javax.swing.*;
import java.awt.*;

public class BasicBackgroundPanel extends JPanel
{
    private Image background;
    private boolean vide;

    public BasicBackgroundPanel(Image background)
    {
        this.vide=true;
        this.background = background;
        setLayout( new BorderLayout() );
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        g.drawImage(background, 0, 0,getWidth(),getHeight(), null);
    }

    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(background.getWidth(this), background.getHeight(this));
    }

    public void changeBackground(Image i){
        this.background=i;
        this.updateUI();
    }

    public Image getBackgroundImage() {
        return background;
    }

    public void setVide(boolean b){
        this.vide =b;
    }

    public boolean estVide(){
        return  this.vide;
    }
}

