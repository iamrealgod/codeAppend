package org.codeg.intellij.listener;

import com.intellij.ide.ui.laf.darcula.ui.DarculaComboBoxUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class TipsComboBoxUI extends DarculaComboBoxUI {

    public TipsComboBoxUI() {
        super();
    }

    public void tips() {
        if (listBox != null) {
            listBox.addMouseMotionListener(new MouseMotionListener() {
                Component oldCom;
                Component curCom;

                public void mouseMoved(MouseEvent e) {
                    curCom = listBox.getCellRenderer()
                            .getListCellRendererComponent(listBox, listBox.getSelectedValue(), listBox.getSelectedIndex(),
                                    true, true);
                    if (oldCom == null || oldCom != curCom) {
                        oldCom = curCom;
                    }
                    if (oldCom instanceof JComponent) {
                        ((JComponent) oldCom).setToolTipText("" + listBox.getSelectedValue());
                    }
                }

                public void mouseDragged(MouseEvent e) {
                }
            });
        }
    }
}