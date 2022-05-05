package Vue;

import javax.swing.*;
import java.awt.*;
import java.io.File;

import static javax.imageio.ImageIO.read;

public class ImageKusBlo {
    Image gris;
    Image choco;
    Image grisRouge;

    ImageKusBlo(){
        gris = getImage("Gris2.png");
        choco=getImage("ChocoTest.png");
        grisRouge= getImage("GrisRouge.png");
    }

    public Image getImage(String s){
        try{
            ImageIcon icon = new ImageIcon(read(new File("./res/img/"+s)));
            return icon.getImage();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
