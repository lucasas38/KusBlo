package Vue;

import javax.swing.*;
import java.awt.*;
import java.io.File;

import static javax.imageio.ImageIO.read;

public class ImageKusBlo {
    Image gris;
    Image blanc;
    Image rouge;
    Image grisRouge;
    Image selRouge;

    ImageKusBlo(){
        gris = getImage("Gris.png");
        blanc=getImage("Blanc.png");
        rouge =getImage("Rouge.png");
        grisRouge=getImage("visRouge.png");
        selRouge= getImage("selRouge.png");
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
