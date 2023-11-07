package org.zaproxy.zap.extension.customactivescan.view;

import javax.swing.text.*;
import java.util.Enumeration;

public class SwingStyle {

    private final static org.apache.logging.log4j.Logger LOGGER4J =
            org.apache.logging.log4j.LogManager.getLogger();
    public static String STYLE_NAME = "###UNIQUE_SWING_STYLE###";

    private StyleContext styleContext = null;

    SwingStyle() {
        styleContext =  new StyleContext();
        styleContext.addStyle(STYLE_NAME, styleContext.getStyle((StyleContext.DEFAULT_STYLE)));
        if (LOGGER4J.isDebugEnabled()) {
            for (Enumeration<?> names = styleContext.getStyleNames(); names.hasMoreElements(); ) {
                String name = (String) names.nextElement();
                LOGGER4J.debug("[" + name + "]");
            }
        }
    }

    public StyleContext getStyleContext() {
        return styleContext;
    }

    public StyledDocument createStyledDocument() {
        return new DefaultStyledDocument(styleContext);
    }

    public static Style getDefaultStyle(StyledDocument doc) {
        Style style = doc.getStyle(STYLE_NAME);
        if (style == null) {
            return doc.getStyle(StyleContext.DEFAULT_STYLE);
        }
        return style;
    }

    public static String getDefaultStyleName(StyledDocument doc){
        Style style = doc.getStyle(STYLE_NAME);
        if (style == null) {
            return StyleContext.DEFAULT_STYLE;
        }
        return STYLE_NAME;
    }

    /**
     * clear All [character] attributes and set attributes as default.
     * existing [character] attributes is removed.
     * but remain other component attributes such as image component.
     * @param doc StyledDocument
     */
    public static void clearAllCharacterAttributes(StyledDocument doc) {
        Style defaultStyle = getDefaultStyle(doc);
        // replace "true" means overwrite char attributes with defaultStyle. existing character attributes is deleted.
        // but remain other component attributes such as image component.
        doc.setCharacterAttributes(0, doc.getLength(), defaultStyle, true);
    }

}
