package Structures;

import javax.swing.*;
import java.awt.*;

public class BasicBackgroundPanel extends JPanel
{
    private Image background;

    public BasicBackgroundPanel(Image background)
    {
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
}

