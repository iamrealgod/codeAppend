package org.codeg.intellij.ui;

import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import org.apache.http.util.TextUtils;
import org.codeg.intellij.builder.DaoBuilder;
import org.codeg.intellij.builder.EntityBuilder;
import org.codeg.intellij.builder.MapperBuilder;
import org.codeg.intellij.builder.ServiceBuilder;
import org.codeg.intellij.config.Cache;
import org.codeg.intellij.config.Config;
import org.codeg.intellij.entity.ClassEntity;
import org.codeg.intellij.entity.FieldEntity;
import org.codeg.intellij.util.DBUtils;
import org.codeg.intellij.util.RegexUtils;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author liufei
 * @date 2018/12/24 17:11
 */
public class CodeDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextArea editTP;
    private JTextField mapperDir;
    private JTextField serviceDir;
    private JTextField daoDir;
    private JButton mapperDirBtn;
    private JButton serviceDirBtn;
    private JButton daoDirBtn;
    private JPanel editPanel;
    private JTextField entityDir;
    private JButton entityDirBtn;
    private JRadioButton overrideRBtn;
    private JRadioButton appendRBtn;
    private JLabel serviceLabel;
    private JLabel daoLabel;
    private JButton buttonSetting;
    private Project project;

    public CodeDialog(Project project) {
        this.project = project;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        try {
            initData();
            initListener();
        } catch (Exception e) {
            reset();
        }

    }

    private void openSettingDialog() {
        SettingDialog settingDialog = new SettingDialog(project);
        settingDialog.setSize(900, 620);
        settingDialog.setLocationRelativeTo(null);
        settingDialog.setVisible(true);
    }

    private void initData() {
        daoDir.setText(Cache.getInstant().getDaoPath());
        serviceDir.setText(Cache.getInstant().getServicePath());
        entityDir.setText(Cache.getInstant().getEntityPath());
        mapperDir.setText(Cache.getInstant().getMapperPath());
        onAppend();
    }

    private void initListener() {
        buttonOK.addActionListener(e -> onOK());
        buttonCancel.addActionListener(e -> onCancel());
        appendRBtn.addActionListener(e -> onAppend());
        overrideRBtn.addActionListener(e -> onOverride());
        mapperDirBtn.addActionListener(e -> chooseDir(mapperDir));
        serviceDirBtn.addActionListener(e -> chooseDir(serviceDir));
        daoDirBtn.addActionListener(e -> chooseDir(daoDir));
        entityDirBtn.addActionListener(e -> chooseDir(entityDir));
        buttonSetting.addActionListener(e -> openSettingDialog());

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
    private void onAppend() {
        serviceLabel.setVisible(false);
        serviceDir.setVisible(false);
        serviceDirBtn.setVisible(false);
        daoLabel.setVisible(false);
        daoDir.setVisible(false);
        daoDirBtn.setVisible(false);
    }

    private void onOverride() {
        serviceLabel.setVisible(true);
        serviceDir.setVisible(true);
        serviceDirBtn.setVisible(true);
        daoLabel.setVisible(true);
        daoDir.setVisible(true);
        daoDirBtn.setVisible(true);
    }

    // 选择目录
    private void chooseDir(JTextField jTextField) {
        FileChooserDescriptor descriptor = new FileChooserDescriptor(false, true, false, false, false, false);
        VirtualFile virtualFile = FileChooser.chooseFile(descriptor, project, null);
        if (Objects.nonNull(virtualFile)) {
            final String path = virtualFile.getPath();
            jTextField.setText(path);
        }

    }

    private void onOK() {
        try {
            // add your code here
            this.setAlwaysOnTop(false);
            String sql = editTP.getText().trim();
            if (checkAndCache(sql)) {
                return;
            }
            Map<String, String> entityMap = RegexUtils.parseOriginSql(sql);
            if (Objects.isNull(entityMap) || entityMap.isEmpty()) {
                Toast.make(project, editPanel, MessageType.ERROR, "sql code maybe not a real sql statement");
                return;
            }
            // 生成文件
            for (String key : entityMap.keySet()) {
                final ClassEntity classEntity = DBUtils.getClassEntity(key, Config.getInstant().getTbPrefixType(),
                        Config.getInstant().getTbPrefix());
                final List<FieldEntity> fieldEntities = DBUtils.getFieldEntities(entityMap.get(key),
                        Config.getInstant().getFdPrefixType(), Config.getInstant().getFdPrefix());
                if (appendRBtn.isSelected()) {
                    // 追加属性到实体文件
                    EntityBuilder.appendEntityFile(classEntity, fieldEntities);
                    // 追加属性到Mapper文件
                    MapperBuilder.appendMapperFile(classEntity, fieldEntities);
                } else {
                    // 生成实体文件
                    EntityBuilder.buildEntityFile(classEntity, fieldEntities);
                    // 生成Mapper文件
                    MapperBuilder.buildMapperFile(classEntity, fieldEntities);
                    // 生成service文件
                    ServiceBuilder.buildServiceFile(classEntity);
                    // 生成dao文件
                    DaoBuilder.buildDaoFile(classEntity);
                }
            }
            Messages.showMessageDialog(project, "generate successful!", "SuccessMessage", Messages.getInformationIcon());
            dispose();
        } catch (Exception e) {
            Messages.showErrorDialog(project,"unknown error! we clear the cache and config, please retry.","未知错误");
            reset();
        }
    }

    private void reset() {
        // 重置所有配置
        Config.reset();
        Cache.reset();
        dispose();
    }

    private boolean checkAndCache(String sql) {
        if (TextUtils.isEmpty(sql)) {
            Toast.make(project, editPanel, MessageType.ERROR, "sql code maybe not empty");
            return true;
        }
        if (TextUtils.isEmpty(entityDir.getText())) {
            Toast.make(project, entityDir, MessageType.ERROR, "entity dir maybe not empty");
            return true;
        }
        // cache paths
        Cache.getInstant().setServicePath(serviceDir.getText());
        Cache.getInstant().setDaoPath(daoDir.getText());
        Cache.getInstant().setEntityPath(entityDir.getText());
        Cache.getInstant().setMapperPath(mapperDir.getText());
        Cache.getInstant().save();
        return false;
    }

    private void onCancel() {
        dispose();
    }

    public void setProject(Project project) {
        this.project = project;
    }

}
