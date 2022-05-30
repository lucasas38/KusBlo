package Vue;

import Global.Configuration;

import javax.swing.*;
import java.awt.*;
import java.io.File;

import static javax.imageio.ImageIO.read;

public class ImageKusBlo {
    Image gris;
    Image rouge;
    Image grisRouge;
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
    Image aide;
    Image fondG;
    Image fondD;
    Image fondC;
    Image logo;
    Image tuto;
    Image pause;
    Image resume;
    Image undo;
    Image redo;
    Image[] selAnimRouge;

    //Récupère toutes les images
     public ImageKusBlo(){
        gris = getImage("Gris.png");
        rouge =getImage("Rouge.png");
        grisRouge=getImage("visRouge.png");
        couleurJ1=getImage("Bleu.png");
        couleurJ2=getImage("Rouge.png");
        couleurJ3=getImage(("Jaune.png"));
        couleurJ4=getImage(("Vert.png"));
        selJ1=getImage("visBleu4.png");
        selJ2=getImage("visRouge4.png");
        selJ3=getImage(("visJaune4.png"));
        selJ4=getImage(("visVert4.png"));
        aide=getImage("visTemp.png");
        noPos=getImage("cantPos.png");
        noPosB=getImage("cantPosB.png");
        noPosR=getImage("cantPosR.png");
        noPosV=getImage("cantPosV.png");
        noPosJ=getImage("cantPosJ.png");
        logo=getImage("logo.png");
        fondG =getImage("fondG.png");
        fondD =getImage("fondD.png");
        fondC =getImage("fondC.png");
        pause = getImage("pause.png");
        resume= getImage("resume.png");
        undo = getImage("undo.png");
        redo = getImage("redo.png");
        tuto=getImage("tuto.png");
        selAnimRouge = new Image[5];
    }

    //récupère l'image demandée
    public Image getImage(String s){
        try{
            ImageIcon icon = new ImageIcon(read(Configuration.instance().charge("img/"+s)));
            return icon.getImage();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }



    //Renvoie l'image correspondante au jouer
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




    //Renvoie l'image de sélection correspondante au joueur
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

    //Renvoie le logo
    public Image getLogo() {
        return logo;
    }


    //Renvoie l'image hachuré en fonction du fond
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

    //Renvoie la valeur correspondant à l'image du fond
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

    public Image imageFondAide(Image ima){
         if(ima==aide){
             return gris;
         }else{
             return ima;
         }
    }
}
