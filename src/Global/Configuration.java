package Global;

import java.io.*;
import java.util.NoSuchElementException;
import java.util.Properties;

public class Configuration {
    private static Configuration instance = null;
    Properties prop;
    String dirUser;

    public String getDirUser() {
        return dirUser;
    }

    public static InputStream charge(String nom) {
        return ClassLoader.getSystemClassLoader().getResourceAsStream(nom);
    }

    private Configuration() {
        dirUser = System.getProperty("user.dir");
        prop = new Properties();
        try {
            InputStream propIn = charge("defaut.cfg");
            prop.load(propIn);
            FileInputStream f = new FileInputStream(dirUser + File.separator + ".kusblo");
            prop = new Properties(prop);
            prop.load(f);
        } catch (Exception e) {
            System.err.println("Erreur lors de la lecture de la configuration : " + e);
        }
    }

    public static Configuration instance() {
        if (instance == null) {
            instance = new Configuration();
        }
        return instance;
    }

    public String lis(String cle) {
        String resultat = prop.getProperty(cle);
        if (resultat == null)
            throw new NoSuchElementException("Propriété " + cle + " non définie");
        return resultat;
    }

    public void ecris(String cle, String valeur) {
        Object res = prop.setProperty(cle,valeur);
        if (res == null){
            throw new NoSuchElementException("Propriété " + cle + " non définie");
        }else{
            sauvegarde();
        }
    }

    public void sauvegarde()
    {
        try{
            FileOutputStream fr = new FileOutputStream(dirUser + File.separator + ".kusblo");
            prop.store(fr, null);
            fr.close();
        }catch(Exception e){
            e.printStackTrace();
        }

    }

}
