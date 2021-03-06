/* AppletWarningPane.java
 Copyright (C) 2008 Red Hat, Inc.

 This file is part of IcedTea.

 IcedTea is free software; you can redistribute it and/or
 modify it under the terms of the GNU General Public License as published by
 the Free Software Foundation, version 2.

 IcedTea is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with IcedTea; see the file COPYING.  If not, write to
 the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 02110-1301 USA.

 Linking this library statically or dynamically with other modules is
 making a combined work based on this library.  Thus, the terms and
 conditions of the GNU General Public License cover the whole
 combination.

 As a special exception, the copyright holders of this library give you
 permission to link this library with independent modules to produce an
 executable, regardless of the license terms of these independent
 modules, and to copy and distribute the resulting executable under
 terms of your choice, provided that you also meet, for each linked
 independent module, the terms and conditions of the license of that
 module.  An independent module is a module which is not derived from
 or based on this library.  If you modify this library, you may extend
 this exception to your version of the library, but you are not
 obligated to do so.  If you do not wish to do so, delete this
 exception statement from your version.
 */
package net.sourceforge.jnlp.security.dialogs;

import net.adoptopenjdk.icedteaweb.IcedTeaWebConstants;
import net.sourceforge.jnlp.JNLPFile;
import net.sourceforge.jnlp.runtime.Translator;
import net.sourceforge.jnlp.security.SecurityDialog;
import net.sourceforge.jnlp.security.dialogresults.DialogResult;
import net.sourceforge.jnlp.security.dialogresults.SetValueHandler;
import net.sourceforge.jnlp.security.dialogresults.YesNo;
import net.sourceforge.jnlp.security.dialogs.remember.RememberPanel;
import net.sourceforge.jnlp.security.dialogs.remember.RememberPanelResult;
import net.sourceforge.jnlp.security.dialogs.remember.RememberableDialog;
import net.sourceforge.jnlp.util.logging.OutputController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class MissingPermissionsAttributePanel extends SecurityDialogPanel implements  RememberableDialog{

    private final static Logger LOG = LoggerFactory.getLogger(MissingPermissionsAttributePanel.class);

    private RememberPanel rememberPanel;
    
    public MissingPermissionsAttributePanel(SecurityDialog x, String title, String codebase) {
        super(x);
        try {
            addComponents(title, codebase);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        if (x != null) {
            x.getViwableDialog().setMinimumSize(new Dimension(400, 400));
        }
    }

    protected final void addComponents(String title, String codebase) throws IOException {

        URL imgUrl = this.getClass().getResource("/net/sourceforge/jnlp/resources/warning.png");
        ImageIcon icon = null;
        Image img = ImageIO.read(imgUrl);
        icon = new ImageIcon(img);
        String topLabelText = Translator.R("MissingPermissionsMainTitle", title, codebase);
        String bottomLabelText = Translator.R("MissingPermissionsInfo");

        JLabel topLabel = new JLabel(htmlWrap(topLabelText), icon, SwingConstants.CENTER);
        topLabel.setFont(new Font(topLabel.getFont().toString(),
                Font.BOLD, 12));
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.add(topLabel, BorderLayout.CENTER);
        topPanel.setPreferredSize(new Dimension(400, 80));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JEditorPane bottomLabel = new JEditorPane("text/html", htmlWrap(bottomLabelText));
        bottomLabel.setEditable(false);
        bottomLabel.addHyperlinkListener(new HyperlinkListener() {
            @Override
            public void hyperlinkUpdate(HyperlinkEvent e) {
                try {
                    if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                        Desktop.getDesktop().browse(e.getURL().toURI());
                    }
                } catch (IOException ex) {
                    LOG.error(IcedTeaWebConstants.DEFAULT_ERROR_MESSAGE, ex);
                } catch (URISyntaxException ex) {
                    LOG.error(IcedTeaWebConstants.DEFAULT_ERROR_MESSAGE, ex);
                }
            }
        });
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.add(bottomLabel, BorderLayout.CENTER);
        infoPanel.setPreferredSize(new Dimension(400, 80));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        bottomLabel.setBackground(infoPanel.getBackground());

        //run and cancel buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton yes = new JButton(Translator.R("ButYes"));
        JButton no = new JButton(Translator.R("ButNo"));
        rememberPanel = new RememberPanel(codebase);
        yes.addActionListener(SetValueHandler.createSetValueListener(parent, YesNo.yes()));
        no.addActionListener(SetValueHandler.createSetValueListener(parent, YesNo.no()));
        initialFocusComponent = no;
        buttonPanel.add(yes);
        buttonPanel.add(no);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        //all of the above
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(topPanel);
        add(infoPanel);
        add(buttonPanel);
        add(rememberPanel);
        validate();

    }

    public static void main(String[] args) {
        MissingPermissionsAttributePanel w = new MissingPermissionsAttributePanel(null, "HelloWorld", "http://nbblah.url");
        JFrame f = new JFrame();
        f.setSize(400, 400);
        f.add(w, BorderLayout.CENTER);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
    
    @Override
    public RememberPanelResult getRemeberAction() {
        return rememberPanel.getRememberAction();
    }

    @Override
    public DialogResult getValue() {
        return parent.getValue();
    }

    @Override
    public JNLPFile getFile() {
        return parent.getFile();
    }
    
    @Override
    public DialogResult readValue(String s) {
        return YesNo.readValue(s);
    }

    @Override
    public DialogResult getDefaultNegativeAnswer() {
        return YesNo.no();
    }

    @Override
    public DialogResult getDefaultPositiveAnswer() {
        return YesNo.yes();
    }

    @Override
    public DialogResult readFromStdIn(String what) {
        return YesNo.readValue(what);
    }
    
    @Override
    public String helpToStdIn() {
        return YesNo.no().getAllowedValues().toString();
    }

}
