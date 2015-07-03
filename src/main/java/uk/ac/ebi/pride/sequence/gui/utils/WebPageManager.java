package uk.ac.ebi.pride.sequence.gui.utils;

import java.awt.*;
import java.net.URI;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: Enrique
 * Date: 22/04/15
 * Time: 0:54
 * To change this template use File | Settings | File Templates.
 */


/**
 * Class to show web page in browser
 */

public class WebPageManager {

    public WebPageManager(){}

    private static URI UrlToUri(String url){
       return URI.create(url);
    }

    public static void openWebPage (String url) {
        URI uri = UrlToUri(url);
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(uri);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
