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
import java.util.Objects;

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
    private JTextField mapperSuffix;
    private JLabel exampleClassName;
    private JLabel exampleMapperName;
    private JCheckBox dateChkBtn;
    private JCheckBox builderChkBtn;

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
            mapperSuffix.setText(Config.getInstant().getMapperSuffix());
            dateChkBtn.setSelected(Config.getInstant().isDateChk());
            builderChkBtn.setSelected(Config.getInstant().isBuilderChk());
            initExample();
        } catch (Exception e) {
            Messages.showErrorDialog(project,"初始化配置出错! 已重置配置项,重试看看.","初始化配置出错");
            reset();
        }
    }

    private void initExample() {
        String appendType = getSetRbtn.isSelected() ? AppendType.GETSET.name() : AppendType.LOMBOK.name();
        String tbPrefixType = tbPrefixDelCustomRbtn.isSelected() ? PrefixType.OTHER.name() : (tbPrefixDelRbtn.isSelected() ? PrefixType.DEL.name() : PrefixType.STET.name());
        String tbPrefixStr = tbPrefixDelCustomRbtn.isSelected() && StringUtils.isNotBlank(tbPrefix.getText()) ? tbPrefix.getText() : "pre";
        String fdPrefixType = fdPrefixDelCustomRbtn.isSelected() ? PrefixType.OTHER.name() : (fdPrefixDelRbtn.isSelected() ? PrefixType.DEL.name() : PrefixType.STET.name());
        String fdPrefixStr = fdPrefixDelCustomRbtn.isSelected() && StringUtils.isNotBlank(fdPrefix.getText()) ? fdPrefix.getText() : "pre";
        String mapperSuffixStr = Objects.isNull(mapperSuffix.getText()) ? StringUtils.EMPTY : mapperSuffix.getText();
        entityExample.setText(ExampleBuilder.buildExampleEntityContent(columnChkBtn.isSelected(), appendType, tbPrefixType, tbPrefixStr, fdPrefixType, fdPrefixStr, builderChkBtn.isSelected()));
        mapperExample.setText(ExampleBuilder.buildExampleMapperContent(tbPrefixType, tbPrefixStr, fdPrefixType, fdPrefixStr));
        exampleClassName.setText(ExampleBuilder.buildExampleEntityName(tbPrefixType, tbPrefixStr));
        exampleMapperName.setText(ExampleBuilder.buildExampleMapperName(tbPrefixType, tbPrefixStr, mapperSuffixStr));
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
        fdPrefix.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                initExample();
            }
        });
        mapperSuffix.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                initExample();
            }
        });
        dateChkBtn.addActionListener(e -> initExample());
        builderChkBtn.addActionListener(e -> initExample());

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
                    Toast.make(project, tbPrefix, MessageType.ERROR, "这个不能为空!");
                    return;
                }
                Config.getInstant().setTbPrefixType(PrefixType.OTHER.name());
            }
            Config.getInstant().setTbPrefix(Objects.isNull(tbPrefix.getText()) ? StringUtils.EMPTY : tbPrefix.getText());
            // 字段前缀
            if (fdPrefixDelRbtn.isSelected()) {
                Config.getInstant().setFdPrefixType(PrefixType.DEL.name());
            } else if (fdPrefixStetRbtn.isSelected()) {
                Config.getInstant().setFdPrefixType(PrefixType.STET.name());
            } else {
                if (StringUtils.isBlank(fdPrefix.getText())) {
                    Toast.make(project, fdPrefix, MessageType.ERROR, "这个不能为空!");
                    return;
                }
                Config.getInstant().setFdPrefixType(PrefixType.OTHER.name());
            }
            Config.getInstant().setFdPrefix(Objects.isNull(fdPrefix.getText()) ? StringUtils.EMPTY : fdPrefix.getText());
            // tableField
            Config.getInstant().setColumnChk(columnChkBtn.isSelected());
            Config.getInstant().setMybatisPlusChk(mybatisPlusCheckBox.isSelected());
            Config.getInstant().setDateChk(dateChkBtn.isSelected());
            Config.getInstant().setBuilderChk(builderChkBtn.isSelected());
            Config.getInstant().setMapperSuffix(Objects.isNull(mapperSuffix.getText()) ? StringUtils.EMPTY : mapperSuffix.getText());

            Config.getInstant().save();
            dispose();
        } catch (Exception e) {
            Messages.showErrorDialog(project,"保存配置出错! 已重置配置, 重试看看.","保存配置出错");
            reset();
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

}
