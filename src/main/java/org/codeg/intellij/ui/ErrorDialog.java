package org.codeg.intellij.ui;

import javax.swing.*;

/**
 *
 * @author liufei
 * @date 2018/12/24 17:11
 */
public class ErrorDialog extends JFrame {
    private JPanel contentPane;
    private JButton okButton;
    private JTextPane editTP;
    private JScrollPane scrollPane;

    public ErrorDialog(String errorInfo) {
        setContentPane(contentPane);
        setTitle("Error Info");
        getRootPane().setDefaultButton(okButton);
        this.setAlwaysOnTop(true);
        editTP.setText(errorInfo);
        okButton.addActionListener(e -> dispose());
        editTP.setCaretPosition(0);

    }
}
