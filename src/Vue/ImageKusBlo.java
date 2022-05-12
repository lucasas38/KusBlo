package Vue;

import javax.swing.*;
import java.awt.*;
import java.io.File;

import static javax.imageio.ImageIO.read;

public class ImageKusBlo {
    Image gris;
    Image rouge;
    Image grisRouge;
    Image selRouge;
    Image selBleu;
    Image selVert;
    Image selJaune;
    Image couleurJ1;
    Image couleurJ2;
    Image couleurJ3;
    Image couleurJ4;
    Image selJ1;
    Image selJ2;
    Image selJ3;
    Image selJ4;
    Image noPos;
    Image noPosB;
    Image noPosR;
    Image noPosV;
    Image noPosJ;
    Image logo;
    Image[] selAnimRouge;

    ImageKusBlo(){
        gris = getImage("Gris.png");
        rouge =getImage("Rouge.png");
        grisRouge=getImage("visRouge.png");
        selBleu= getImage("selBleu.png");
        selRouge= getImage("selRouge.png");
        selJaune= getImage("selJaune.png");
        selVert= getImage("selVert.png");
        couleurJ1=getImage("Bleu.png");
        couleurJ2=getImage("Rouge.png");
        couleurJ3=getImage(("Jaune.png"));
        couleurJ4=getImage(("Vert.png"));
        selJ1=getImage("visBleu4.png");
        selJ2=getImage("visRouge4.png");
        selJ3=getImage(("visJaune4.png"));
        selJ4=getImage(("visVert4.png"));
        noPos=getImage("cantPos.png");
        noPosB=getImage("cantPosB.png");
        noPosR=getImage("cantPosR.png");
        noPosV=getImage("cantPosV.png");
        noPosJ=getImage("cantPosJ.png");
        logo=getImage("logo.png");
        selAnimRouge = new Image[5];
        selAnimRouge[0]=gris;


        for(int k=1; k<5;k++){
            selAnimRouge[k]= getImage("visRouge"+k+".png");
        }
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

    public Image coulJoueur(int j){
        switch (j){
            case 1:
                return couleurJ1;
            case 2:
                return couleurJ2;
            case 3:
                return couleurJ3;
            case 4:
                return couleurJ4;
            default:
                return gris;
        }
    }

    public Image selCouleur(int j){
        switch (j){
            case 1:
                return selBleu;
            case 2:
                return selRouge;
            case 3:
                return selJaune;
            case 4:
                return selVert;
            default:
                return gris;
        }
    }

    public Image animJoueur(int j, int cFond){
        switch (j){
            case 1:
                return selJ1;
            case 2:
                return selJ2;
            case 3:
                return selJ3;
            case 4:
                return selJ4;
            default:
                return noPosi(cFond);
        }
    }

    public Image getLogo() {
        return logo;
    }

    public Image noPosi(int couleur){
        switch (couleur){
            case 1 :
                return noPosB;
            case 2 :
                return noPosR;
            case 3 :
                return noPosJ;
            case 4 :
                return noPosV;
            default:
                return noPos;
        }
    }

    public int imToInt(Image couleur){
        if(couleur==noPosB || couleur==couleurJ1) {
            return 1;
        } else if(couleur==noPosR || couleur==couleurJ2){
            return 2;
        }else  if(couleur==noPosJ || couleur==couleurJ3){
            return 3;
        }else if(couleur==noPosV || couleur==couleurJ4){
            return 4;
        }else{
            return 0;
        }
    }
}
