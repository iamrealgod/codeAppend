package org.codeg.intellij.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.ui.Messages;
import org.codeg.intellij.builder.ExampleBuilder;
import org.codeg.intellij.config.Cache;
import org.codeg.intellij.config.Config;
import org.codeg.intellij.config.Enums.AppendType;
import org.codeg.intellij.config.Enums.PrefixType;
import org.codeg.intellij.util.StringUtils;

import javax.swing.*;
import java.awt.event.KeyAdapter;
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
    private JCheckBox columnChkBtn;
    private JTextArea mapperExample;
    private JCheckBox mybatisPlusCheckBox;
    private JTextArea entityExample;

    public SettingDialog(Project project) {
        this.project = project;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonSave);
        initData();
        initListener();
    }

    private void reset() {
        // 重置所有配置
        Config.reset();
        Cache.reset();
        dispose();
    }

    private void initData() {
        try {
            mybatisPlusCheckBox.setSelected(Config.getInstant().getMybatisPlusChk());
            columnChkBtn.setSelected(Config.getInstant().getColumnChk());
            getSetRbtn.setSelected(AppendType.GETSET.name().equals(Config.getInstant().getAppendType()));
            lombokRbtn.setSelected(AppendType.LOMBOK.name().equals(Config.getInstant().getAppendType()));
            tbPrefixDelRbtn.setSelected(PrefixType.DEL.name().equals(Config.getInstant().getTbPrefixType()));
            tbPrefixStetRbtn.setSelected(PrefixType.STET.name().equals(Config.getInstant().getTbPrefixType()));
            tbPrefixDelCustomRbtn.setSelected(PrefixType.OTHER.name().equals(Config.getInstant().getTbPrefixType()));
            fdPrefixDelRbtn.setSelected(PrefixType.DEL.name().equals(Config.getInstant().getFdPrefixType()));
            fdPrefixStetRbtn.setSelected(PrefixType.STET.name().equals(Config.getInstant().getFdPrefixType()));
            fdPrefixDelCustomRbtn.setSelected(PrefixType.OTHER.name().equals(Config.getInstant().getFdPrefixType()));
            tbPrefix.setText(Config.getInstant().getTbPrefix());
            fdPrefix.setText(Config.getInstant().getFdPrefix());
            initExample();
        } catch (Exception e) {
            Messages.showErrorDialog(project,"unknown error! we clear the cache and config, please retry.","未知错误");
            reset();
        }
    }

    private void initExample() {
        String appendType = getSetRbtn.isSelected() ? AppendType.GETSET.name() : AppendType.LOMBOK.name();
        String tbPrefixType = tbPrefixDelCustomRbtn.isSelected() ? PrefixType.OTHER.name() : (tbPrefixDelRbtn.isSelected() ? PrefixType.DEL.name() : PrefixType.STET.name());
        String tbPrefixStr = tbPrefixDelCustomRbtn.isSelected() && StringUtils.isNotBlank(tbPrefix.getText()) ? tbPrefix.getText() : "pre";
        String fdPrefixType = fdPrefixDelCustomRbtn.isSelected() ? PrefixType.OTHER.name() : (fdPrefixDelRbtn.isSelected() ? PrefixType.DEL.name() : PrefixType.STET.name());
        String fdPrefixStr = fdPrefixDelCustomRbtn.isSelected() && StringUtils.isNotBlank(fdPrefix.getText()) ? fdPrefix.getText() : "pre";
        entityExample.setText(ExampleBuilder.buildExampleEntity(columnChkBtn.isSelected(), appendType, tbPrefixType, tbPrefixStr, fdPrefixType, fdPrefixStr));
        mapperExample.setText(ExampleBuilder.buildExampleMapper(tbPrefixType, tbPrefixStr, fdPrefixType, fdPrefixStr));
    }

    private void initListener() {
        buttonSave.addActionListener(e -> onSave());
        buttonCancel.addActionListener(e -> onCancel());

        // example listener
        mybatisPlusCheckBox.addActionListener(e -> initExample());
        columnChkBtn.addActionListener(e -> initExample());
        getSetRbtn.addActionListener(e -> initExample());
        lombokRbtn.addActionListener(e -> initExample());
        tbPrefixDelRbtn.addActionListener(e -> initExample());
        tbPrefixStetRbtn.addActionListener(e -> initExample());
        tbPrefixDelCustomRbtn.addActionListener(e -> initExample());
        fdPrefixDelRbtn.addActionListener(e -> initExample());
        fdPrefixStetRbtn.addActionListener(e -> initExample());
        fdPrefixDelCustomRbtn.addActionListener(e -> initExample());
        tbPrefix.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                initExample();
            }
        });
        tbPrefix.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                initExample();
            }
        });
        fdPrefix.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                initExample();
            }
        });
        fdPrefix.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                initExample();
            }
        });

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
        try {
            // getset or lombok
            if (getSetRbtn.isSelected()) {
                Config.getInstant().setAppendType(AppendType.GETSET.name());
            } else {
                Config.getInstant().setAppendType(AppendType.LOMBOK.name());
            }
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
            // tableField
            Config.getInstant().setColumnChk(columnChkBtn.isSelected());
            Config.getInstant().setMybatisPlusChk(mybatisPlusCheckBox.isSelected());

            Config.getInstant().save();
            dispose();
        } catch (Exception e) {
            Messages.showErrorDialog(project,"unknown error! we clear the cache and config, please retry.","未知错误");
            reset();
        }
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
