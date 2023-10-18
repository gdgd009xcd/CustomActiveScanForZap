package org.zaproxy.zap.extension.customactivescan.view;

import org.parosproxy.paros.Constant;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.zaproxy.zap.extension.customactivescan.ExtensionAscanRules.MESSAGE_PREFIX;

@SuppressWarnings("serial")
public class RegexTestDialog extends GridBagJDialog<RegexTestDialog.PaneContents>{
    private final static org.apache.logging.log4j.Logger LOGGER4J =
            org.apache.logging.log4j.LogManager.getLogger();

    // these parameters are set value by createMainPanelContent method.
    // these parameters MUST NOT initialize at declaration here.
    // because createMainPanelContent method is called before parameter initialization process in this class
    // regex text
    private JTextPane regexTextPane;

    private JTabbedPane tabbedPane;

    private List<SearchTextPane> searchTextPaneList;

    // no used parameters in createMainPanelContent method
    private RegexTestOptionPane optionPane = null;

    private Dialog dialog = null;
    private Frame frame = null;
    private DisposeChildInterface parent = null;

    static final String SELECTEDGROUP_STYLENAME = "selectedGroupStyle";
    static final String CURRENT_SELECTEDGROUP_STYLENAME = "currentSelectedGroupStyle";
    static final String SELECTED_STYLENAME = "selectedStyle";

    static class SearchTextPane {
        // search text
        JTextPane searchTextPane;
        // attribute List for searched texts
        List<RegexSelectedTextPos> foundTextAttrPos;
        // caretPosition for searchd text at first
        ArrayList<Integer> findplist;

        SearchTextPane() {
            searchTextPane = null;
            foundTextAttrPos = new ArrayList<>();
            findplist = new ArrayList<>();
        }
    }

    static class PaneTitleAndContent {
        String title;
        String content;
        PaneTitleAndContent(){
            title = "";
            content = "";
        }
        PaneTitleAndContent(String title, String content) {
            this.title = title;
            this.content = content;
        }
    }

    static class PaneContents {
        public List<PaneTitleAndContent> paneTitleAndContentList;
        public String regexText;
        PaneContents(String regexText) {
            this.regexText = regexText;
            paneTitleAndContentList = new ArrayList<>();
        }

        public void addTitleAndContent(String title, String content) {
            PaneTitleAndContent paneTitleAndContent = new PaneTitleAndContent(title, content);
            paneTitleAndContentList.add(paneTitleAndContent);
        }
    }

    private JTextField targetTextField;


    public RegexTestDialog(Dialog dialog, String title, ModalityType modalityType, PaneContents paneContents) {
        super(dialog, title, modalityType, paneContents, GridBagConstraints.BOTH);
        this.dialog = dialog;
    }

    public RegexTestDialog(Frame frame, DisposeChildInterface parent, String title, ModalityType modalityType, PaneContents paneContents) {
        super(frame, title, modalityType, paneContents, GridBagConstraints.BOTH);
        this.frame = frame;
        this.parent = parent;
    }

    private JPanel createRegexTestDialogContent(PaneContents paneContents) {
        searchTextPaneList = new ArrayList<>();
        JPanel panel = new JPanel();
        GridBagLayout gridBagLayout = new GridBagLayout();
        panel.setLayout(gridBagLayout);

        GridBagConstraints gbc = new GridBagConstraints();

        // buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        // Regex test button
        JButton regexTestButton = new JButton("Search");
        regexTestButton.addActionListener(e ->{
            regexSearchActionPerformed(e);
        });
        buttonPanel.add(regexTestButton);
        // button for clear attributes of found text
        JButton clearTestButton = new JButton("Clear");
        clearTestButton.addActionListener(e ->{
            clearTestActionPerformed(e);
        });
        buttonPanel.add(clearTestButton);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1d;
        gbc.weighty = 0d;// 0 means do not resize height mainpanel
        gbc.insets = new Insets(5, 5, 5, 5);
        gridBagLayout.setConstraints(buttonPanel, gbc);
        panel.add(buttonPanel);

        // Regex text pane
        LineBorder regexTextBorderLine = new LineBorder(Color.BLUE, 1, true);
        TitledBorder regexTextTitledBorder = new TitledBorder(regexTextBorderLine,
                "Regex text",
                TitledBorder.LEFT,
                TitledBorder.TOP);
        JScrollPane regexTextScroller = new JScrollPane();
        regexTextScroller.setBorder(regexTextTitledBorder);
        regexTextScroller.setPreferredSize(new Dimension(400,100));
        regexTextScroller.setMinimumSize(new Dimension(400, 100));
        regexTextScroller.setAutoscrolls(true);
        this.regexTextPane = new JTextPane();
        this.regexTextPane.setText(paneContents.regexText);
        regexTextScroller.setViewportView(regexTextPane);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1d;
        gbc.weighty = 0d;// 0 means do not resize height mainpanel
        gbc.insets = new Insets(5, 5, 5, 5);
        gridBagLayout.setConstraints(regexTextScroller, gbc);
        panel.add(regexTextScroller);

        // search text pane
        LineBorder searchTextBorderLine = new LineBorder(Color.BLUE, 1, true);
        TitledBorder searchTextTitledBorder = new TitledBorder(searchTextBorderLine,
                "Search Panes",
                TitledBorder.LEFT,
                TitledBorder.TOP);

        tabbedPane = new JTabbedPane();
        tabbedPane.setBorder(searchTextTitledBorder);
        for(PaneTitleAndContent titleAndContent: paneContents.paneTitleAndContentList) {
            SearchTextPane searchTextPane = new SearchTextPane();
            JScrollPane searchTextScroller = new JScrollPane();
            searchTextScroller.setPreferredSize(new Dimension(400, 500));
            searchTextScroller.setAutoscrolls(true);
            searchTextScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            searchTextPane.searchTextPane = new JTextPane();
            searchTextPane.searchTextPane.setEditorKit(new TextPaneWrapEditorKit());
            StyledDocument doc = searchTextPane.searchTextPane.getStyledDocument();
            createStyles(doc);


            searchTextPane.searchTextPane.setText(titleAndContent.content);
            searchTextPane.searchTextPane.setCaretPosition(0);
            searchTextScroller.setViewportView(searchTextPane.searchTextPane);

            tabbedPane.add(titleAndContent.title, searchTextScroller);
            searchTextPaneList.add(searchTextPane);
        }


        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1d;
        gbc.weighty = 1d;// 0 means do not resize height mainpanel
        gbc.insets = new Insets(5, 5, 5, 5);
        gridBagLayout.setConstraints(tabbedPane, gbc);
        panel.add(tabbedPane);

        return panel;
    }


    private void createStyles(StyledDocument doc) {
        Style defaultStyle = doc.getStyle(StyleContext.DEFAULT_STYLE);
        Style selectedGroupStyle = doc.addStyle(SELECTEDGROUP_STYLENAME, defaultStyle);
        StyleConstants.setForeground(selectedGroupStyle, Color.BLACK);
        StyleConstants.setBackground(selectedGroupStyle, Color.RED);
        Style currentSelectedGroupStyle = doc.addStyle(CURRENT_SELECTEDGROUP_STYLENAME, defaultStyle);
        StyleConstants.setForeground(currentSelectedGroupStyle, Color.WHITE);
        StyleConstants.setBackground(currentSelectedGroupStyle, Color.RED);
        Style selectedStyle = doc.addStyle(SELECTED_STYLENAME, defaultStyle);
        StyleConstants.setForeground(selectedStyle, Color.BLUE);
        StyleConstants.setBackground(selectedStyle, Color.RED);
    }

    private void removeStyles(StyledDocument doc) {
        Style selectedGroupStyle = doc.getStyle(SELECTEDGROUP_STYLENAME);
        if (selectedGroupStyle != null) {
            doc.removeStyle(SELECTEDGROUP_STYLENAME);
        }
        Style currentSelectedGroupStyle = doc.getStyle(CURRENT_SELECTEDGROUP_STYLENAME);
        if (currentSelectedGroupStyle != null) {
            doc.removeStyle(CURRENT_SELECTEDGROUP_STYLENAME);
        }
        Style selectedStyle = doc.getStyle(SELECTED_STYLENAME);
        if (selectedStyle != null) {
            doc.removeStyle(SELECTED_STYLENAME);
        }
    }

    @Override
    protected Component createMainPanelContent(Component mainPanel, PaneContents paneContents) {
        return createRegexTestDialogContent(paneContents);
    }

    @Override
    protected void okBtnActionPerformed() {
        if (this.regexTextPane.getText() != null && this.targetTextField != null) {
            this.targetTextField.setText(this.regexTextPane.getText());
        }
        if (this.parent != null) {
            this.parent.disposeChild();
        } else {
            dispose();
        }
    }

    @Override
    protected void cancelBtnActionPerformed() {
        if (this.parent != null) {
            this.parent.disposeChild();
        } else {
            dispose();
        }
    }

    public static class RegexSelectedTextPos {
        int st;
        int et;
        String styleName;

        RegexSelectedTextPos(String styleName, int st, int et) {
            this.st = st;
            this.et = et;
            this.styleName = styleName;
        }

        int getStartPos() {
            return this.st;
        }

        int getEndPos() {
            return this.et;
        }

        String getStyleName() {
            return this.styleName;
        }

    }

    private void clearTestActionPerformed(ActionEvent e) {
        int selectedTabbedPaneIndex = this.tabbedPane.getSelectedIndex();
        if (selectedTabbedPaneIndex > -1) {
            SearchTextPane searchTextPane = this.searchTextPaneList.get(selectedTabbedPaneIndex);
            clearSearchedInfo(searchTextPane);
        }
    }

    /**
     * clear searched text info and remove styles from searched text in StyledDocument.
     * this method must call after JTextPane.setText was called.
     * @param searchTextPane
     */
    private void clearSearchedInfo(SearchTextPane searchTextPane) {
        if (searchTextPane != null) {
            StyledDocument doc = searchTextPane.searchTextPane.getStyledDocument();

            Style defaultStyleForALL = StyleContext.
                    getDefaultStyleContext().
                    getStyle(StyleContext.DEFAULT_STYLE);
            doc.setCharacterAttributes(0, doc.getLength(), defaultStyleForALL, true);
            removeStyles(doc);
            createStyles(doc);
            if (searchTextPane.foundTextAttrPos != null) {
                searchTextPane.foundTextAttrPos.clear();
            }
            if (searchTextPane.findplist != null) {
                searchTextPane.findplist.clear();
            }
        }
    }

    /**
     * clear All searched text info and remove styles from searched text in StyledDocument.
     * this method must call after JTextPane.setText was called.
     */
    public void clearAllSearchedInfo() {
        for(SearchTextPane searchTextPane: searchTextPaneList) {
            LOGGER4J.debug("clearAllSearchedInfo");
            clearSearchedInfo(searchTextPane);
        }
    }

    public void regexSearchActionPerformed(ActionEvent e) {
        // clear attrubutes in searchTextPane
        clearTestActionPerformed(null);

        int selectedTabbedPaneIndex = this.tabbedPane.getSelectedIndex();
        if (selectedTabbedPaneIndex > -1) {
            SearchTextPane searchTextPane = this.searchTextPaneList.get(selectedTabbedPaneIndex);

            String regex = regexTextPane.getText();

            StyledDocument doc = searchTextPane.searchTextPane.getStyledDocument();


            if (regex == null || regex.isEmpty()) { // if you do it, Too many patterns matched.
                return;
            }

            String original = null;
            try {
                original = doc.getText(0, doc.getLength());
            } catch (BadLocationException ex) {
                Logger.getLogger(RegexTestDialog.class.getName()).log(Level.SEVERE, null, ex);
            }

            searchTextPane.findplist = new ArrayList<>();

            //parse Regex
            Pattern compiledregex = null;
            Matcher m = null;
            try {

                compiledregex = Pattern.compile(regex, Pattern.MULTILINE);

                m = compiledregex.matcher(original);
            } catch (Exception ex) {
                LOGGER4J.error("Exception:" + ex.getMessage(), ex);
                return;
            }


            int cpt = 0;

            int fcount = 0;
            while (m.find()) {
                fcount++;
                int spt0 = -1;
                int ept0 = -1;
                int spt = -1;
                int ept = -1;
                int gcnt = m.groupCount();
                String matchval = null;
                if (gcnt > 0) {
                    spt0 = m.start();
                    ept0 = m.end();
                    for (int n = 0; n < gcnt; n++) {
                        spt = m.start(n + 1);
                        ept = m.end(n + 1);
                        matchval = m.group(n + 1);

                    }
                    if (matchval == null) {
                        matchval = m.group();
                    }
                    if (spt0 > spt) {
                        spt0 = spt;
                    }
                    if (ept0 < ept) {
                        ept0 = ept;
                    }
                    // spt0--->spt<matchval>ept-->ept0
                } else {//Nothing Groups...
                    spt0 = m.start();
                    ept0 = m.end();
                    matchval = m.group();
                }
                if (spt0 >= 0 && ept0 >= 0) {

                    try {

                        // spt0--->spt<matchval>ept-->ept0

                        if (ept0 > spt0) {
                            Style style = doc.getStyle(SELECTED_STYLENAME);
                            doc.setCharacterAttributes(spt0, ept0 - spt0, style, false);
                            RegexSelectedTextPos rpos = new RegexSelectedTextPos(SELECTED_STYLENAME, spt0, ept0);
                            searchTextPane.foundTextAttrPos.add(rpos);
                        }

                        if (ept > spt) {
                            String styleName = "";
                            if (fcount == 1) {
                                styleName = CURRENT_SELECTEDGROUP_STYLENAME;
                            } else {
                                styleName = SELECTEDGROUP_STYLENAME;
                            }
                            Style style = doc.getStyle(styleName);
                            doc.setCharacterAttributes(spt, ept - spt, style, false);
                            RegexSelectedTextPos rpos = new RegexSelectedTextPos(styleName, spt, ept);
                            searchTextPane.foundTextAttrPos.add(rpos);
                        }

                        //int pos = OriginalText.getCaretPosition();
                        int pos = doc.getLength();
                        searchTextPane.findplist.add(ept0);
                    } catch (Exception ex) {
                        LOGGER4J.error("Exception:" + ex.getMessage(), ex);
                    }
                }
            }

            if (searchTextPane.findplist.size() > 0) {
                searchTextPane.searchTextPane.setCaretPosition(searchTextPane.findplist.get(0));
                int foundCount = searchTextPane.findplist.size();
                String message = String.format(
                        Constant.messages.getString(MESSAGE_PREFIX + "regexsearch.formatfound"),
                        foundCount);
                /**
                JOptionPane.showMessageDialog(this,
                        message,
                        Constant.messages.getString(MESSAGE_PREFIX + "regexsearch.title"),
                        JOptionPane.INFORMATION_MESSAGE);
                 **/
                RegexTestOptionPane.RegexTestOptions options = new RegexTestOptionPane.RegexTestOptions(message, searchTextPane);
                disposeChild();
                optionPane = new RegexTestOptionPane(
                        this,
                        Constant.messages.getString(MESSAGE_PREFIX + "regexsearch.title"),
                        ModalityType.DOCUMENT_MODAL, options, GridBagConstraints.NONE);
                optionPane.setVisible(true);
            } else {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(this,
                        Constant.messages.getString(MESSAGE_PREFIX + "regexsearch.formatnotfound"),
                        Constant.messages.getString(MESSAGE_PREFIX + "regexsearch.title"),
                        JOptionPane.QUESTION_MESSAGE);
            }
        }
    }

    public void disposeChild() {
        if (this.optionPane != null) {
            this.optionPane.dispose();
            this.optionPane = null;
        }
    }
    public void setRegexTextField(JTextField targetTextField) {
        this.targetTextField = null;
        if (this.regexTextPane != null) {
            if(targetTextField != null) {
                this.targetTextField = targetTextField;
                this.regexTextPane.setText(targetTextField.getText());
            }
        }
    }

    public void selectTabbedPane(int index) {
        this.tabbedPane.setSelectedIndex(index);
    }


    /**
     * move scollbars to left/top on each scrollPane
     */
    public void resetScrollBarToLeftTop() {
        int tabCount = this.tabbedPane.getTabCount();
        for(int i=0; i < tabCount; i++) {
            JScrollPane scrollPane = (JScrollPane) this.tabbedPane.getComponentAt(i);
            JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
            JScrollBar horizontalBar = scrollPane.getHorizontalScrollBar();
            verticalBar.setValue(verticalBar.getMinimum());
            horizontalBar.setValue(horizontalBar.getMinimum());
        }
    }

    public void updateContentsWithPaneContents(PaneContents paneContents) {
        int i = 0;
        for(PaneTitleAndContent titleAndContent: paneContents.paneTitleAndContentList) {
            SearchTextPane searchTextPane = searchTextPaneList.get(i++);
            searchTextPane.searchTextPane.setText(titleAndContent.content);
            searchTextPane.searchTextPane.setSelectionStart(0);
            searchTextPane.searchTextPane.setSelectionEnd(0);
            searchTextPane.searchTextPane.setCaretPosition(0);
        }
        resetScrollBarToLeftTop();
    }

}
