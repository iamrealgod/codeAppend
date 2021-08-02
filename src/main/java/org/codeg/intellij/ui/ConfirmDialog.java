package org.codeg.intellij.ui;

import org.codeg.intellij.config.Cache;

import javax.swing.*;
import javax.swing.plaf.basic.BasicHTML;
import javax.swing.text.View;
import java.awt.*;
import java.awt.event.*;

public class ConfirmDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextArea pathLabel;

    private CodeDialog codeDialog;

    public ConfirmDialog(CodeDialog codeDialog) {
        this.codeDialog = codeDialog;

        String showLabelStr = "entityPath:" + Cache.getInstant().getEntityPath() + "\n\n" +
                "mapperPath:" + Cache.getInstant().getMapperPath() + "\n\n" +
                "servicePath:" + Cache.getInstant().getServicePath() + "\n\n" +
                "daoPath:" + Cache.getInstant().getDaoPath();
        pathLabel.append(showLabelStr);

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());
        buttonCancel.addActionListener(e -> onCancel());
        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        // add your code here
        codeDialog.onOK();
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
