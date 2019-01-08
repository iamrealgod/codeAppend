package org.codeg.intellij.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageType;
import org.codeg.intellij.config.Config;
import org.codeg.intellij.config.Enums.AppendType;
import org.codeg.intellij.config.Enums.PrefixType;
import org.codeg.intellij.util.StringUtils;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SettingDialog extends JDialog {
    private Project project;
    private JPanel contentPane;
    private JButton buttonSave;
    private JButton buttonCancel;
    private JRadioButton getSetRbtn;
    private JRadioButton lombokRbtn;
    private JRadioButton tbPrefixDelRbtn;
    private JRadioButton tbPrefixStetRbtn;
    private JRadioButton tbPrefixDelCustomRbtn;
    private JTextField tbPrefix;
    private JRadioButton fdPrefixDelRbtn;
    private JRadioButton fdPrefixStetRbtn;
    private JRadioButton fdPrefixDelCustomRbtn;
    private JTextField fdPrefix;

    public SettingDialog(Project project) {
        this.project = project;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonSave);
        initData();
        initListener();
    }

    private void initData() {
        // TODO 暂不支持GET/SET
        getSetRbtn.setVisible(false);
        getSetRbtn.setSelected(false);
        //getSetRbtn.setSelected(AppendType.GETSET.name().equals(Config.getInstant().getAppendType()));
        lombokRbtn.setSelected(AppendType.LOMBOK.name().equals(Config.getInstant().getAppendType()));
        tbPrefixDelRbtn.setSelected(PrefixType.DEL.name().equals(Config.getInstant().getTbPrefixType()));
        tbPrefixStetRbtn.setSelected(PrefixType.STET.name().equals(Config.getInstant().getTbPrefixType()));
        tbPrefixDelCustomRbtn.setSelected(PrefixType.OTHER.name().equals(Config.getInstant().getTbPrefixType()));
        fdPrefixDelRbtn.setSelected(PrefixType.DEL.name().equals(Config.getInstant().getFdPrefixType()));
        fdPrefixStetRbtn.setSelected(PrefixType.STET.name().equals(Config.getInstant().getFdPrefixType()));
        fdPrefixDelCustomRbtn.setSelected(PrefixType.OTHER.name().equals(Config.getInstant().getFdPrefixType()));
        tbPrefix.setText(Config.getInstant().getTbPrefix());
        fdPrefix.setText(Config.getInstant().getFdPrefix());
    }

    private void initListener() {
        buttonSave.addActionListener(e -> onSave());
        buttonCancel.addActionListener(e -> onCancel());
        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onSave() {
        // add your code here
        // TODO 暂不支持GET/SET
        Config.getInstant().setAppendType(AppendType.LOMBOK.name());
        // 表名前缀
        if (tbPrefixDelRbtn.isSelected()) {
            Config.getInstant().setTbPrefixType(PrefixType.DEL.name());
        } else if (tbPrefixStetRbtn.isSelected()) {
            Config.getInstant().setTbPrefixType(PrefixType.STET.name());
        } else {
            if (StringUtils.isBlank(tbPrefix.getText())) {
                Toast.make(project, tbPrefix, MessageType.ERROR, "this value maybe not empty!");
                return;
            }
            Config.getInstant().setTbPrefixType(PrefixType.OTHER.name());
            Config.getInstant().setTbPrefix(tbPrefix.getText());
        }
        // 字段前缀
        if (fdPrefixDelRbtn.isSelected()) {
            Config.getInstant().setFdPrefixType(PrefixType.DEL.name());
        } else if (fdPrefixStetRbtn.isSelected()) {
            Config.getInstant().setFdPrefixType(PrefixType.STET.name());
        } else {
            if (StringUtils.isBlank(fdPrefix.getText())) {
                Toast.make(project, fdPrefix, MessageType.ERROR, "this value maybe not empty!");
                return;
            }
            Config.getInstant().setFdPrefixType(PrefixType.OTHER.name());
            Config.getInstant().setFdPrefix(fdPrefix.getText());
        }

        Config.getInstant().save();
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        SettingDialog dialog = new SettingDialog(null);
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
