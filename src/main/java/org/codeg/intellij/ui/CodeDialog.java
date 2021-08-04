package org.codeg.intellij.ui;

import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import org.apache.commons.collections.CollectionUtils;
import org.apache.http.util.TextUtils;
import org.codeg.intellij.builder.*;
import org.codeg.intellij.config.Cache;
import org.codeg.intellij.config.Config;
import org.codeg.intellij.entity.ClassEntity;
import org.codeg.intellij.entity.FieldEntity;
import org.codeg.intellij.listener.LabelCellRenderer;
import org.codeg.intellij.listener.TipsComboBoxUI;
import org.codeg.intellij.util.DBUtils;
import org.codeg.intellij.util.RegexUtils;

import javax.swing.*;
import java.awt.event.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author liufei
 * @date 2018/12/24 17:11
 */
public class CodeDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextArea editTP;
    private JButton mapperDirBtn;
    private JButton serviceDirBtn;
    private JButton daoDirBtn;
    private JPanel editPanel;
    private JButton entityDirBtn;
    private JRadioButton overrideRBtn;
    private JRadioButton appendRBtn;
    private JLabel serviceLabel;
    private JLabel daoLabel;
    private JButton buttonSetting;
    private JComboBox<String> mapperDir;
    private JComboBox<String> entityDir;
    private JComboBox<String> serviceDir;
    private JComboBox<String> daoDir;
    private JLabel mapperLabel;
    private JButton sqlExampleBtn;
    private JLabel entityLabel;
    private Project project;

    public CodeDialog(Project project) {
        this.project = project;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        try {
            initUI();
            initData();
            initListener();
        } catch (Exception e) {
            reset();
        }

    }

    private void initUI() {
        entityDir.setUI(new TipsComboBoxUI());
        ((TipsComboBoxUI) entityDir.getUI()).tips();
        mapperDir.setUI(new TipsComboBoxUI());
        ((TipsComboBoxUI) mapperDir.getUI()).tips();
        daoDir.setUI(new TipsComboBoxUI());
        ((TipsComboBoxUI) daoDir.getUI()).tips();
        serviceDir.setUI(new TipsComboBoxUI());
        ((TipsComboBoxUI) serviceDir.getUI()).tips();
    }

    private void openSettingDialog() {
        SettingDialog settingDialog = new SettingDialog(project);
        settingDialog.setSize(900, 620);
        settingDialog.setLocationRelativeTo(null);
        settingDialog.setVisible(true);
    }

    private void initData() {
        daoDir.setSelectedItem(Cache.getInstant().getDaoPath());
        serviceDir.setSelectedItem(Cache.getInstant().getServicePath());
        entityDir.setSelectedItem(Cache.getInstant().getEntityPath());
        mapperDir.setSelectedItem(Cache.getInstant().getMapperPath());

        setComboBoxItems(mapperDir, Cache.getInstant().getMapperPathItem());
        setComboBoxItems(entityDir, Cache.getInstant().getEntityPathItem());
        setComboBoxItems(serviceDir, Cache.getInstant().getServicePathItem());
        setComboBoxItems(daoDir, Cache.getInstant().getDaoPathItem());
        onAppend();
    }

    private void initListener() {
        buttonOK.addActionListener(e -> confirmOnOK());
        buttonCancel.addActionListener(e -> onCancel());
        appendRBtn.addActionListener(e -> onAppend());
        overrideRBtn.addActionListener(e -> onOverride());
        mapperDirBtn.addActionListener(e -> chooseDir(mapperDir));
        serviceDirBtn.addActionListener(e -> chooseDir(serviceDir));
        daoDirBtn.addActionListener(e -> chooseDir(daoDir));
        entityDirBtn.addActionListener(e -> chooseDir(entityDir));
        buttonSetting.addActionListener(e -> openSettingDialog());

        // 方向键控制选择选项，以及delete建删除
        entityDir.setRenderer(new LabelCellRenderer<>(entityDir, "entityPathItem"));
        mapperDir.setRenderer(new LabelCellRenderer<>(mapperDir, "mapperPathItem"));
        daoDir.setRenderer(new LabelCellRenderer<>(daoDir,"daoPathItem"));
        serviceDir.setRenderer(new LabelCellRenderer<>(serviceDir, "servicePathItem"));

        sqlExampleBtn.addActionListener(e -> editTP.setText(ExampleBuilder.DEMO_SQL));

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

    private void setComboBoxItems(JComboBox<String> comboBox, Set<String> items) {
        if (CollectionUtils.isNotEmpty(items)) {
            for (String item : items) {
                comboBox.addItem(item);
            }
        }
    }

    // 选择目录
    private void chooseDir(JComboBox<String> jComboBox) {
        FileChooserDescriptor descriptor = new FileChooserDescriptor(false, true, false, false, false, false);
        VirtualFile virtualFile = FileChooser.chooseFile(descriptor, project, null);
        if (Objects.nonNull(virtualFile)) {
            final String path = virtualFile.getPath();
            // 不存在item才新增
            boolean existsItem = false;
            for (int i = 0; i < jComboBox.getItemCount(); i++) {
                String existsPath = jComboBox.getItemAt(i);
                if (existsPath.equals(path)) {
                    existsItem = true;
                    break;
                }
            }
            if (!existsItem) {
                jComboBox.addItem(path);
            }
            jComboBox.setSelectedItem(path);
        }

    }

    private void confirmOnOK() {
        String sql = editTP.getText().trim();
        if (checkAndCache(sql)) {
            return;
        }
        ConfirmDialog confirmDialog = new ConfirmDialog(this, project, appendRBtn.isSelected());
        confirmDialog.setSize(600, 300);
        confirmDialog.setLocationRelativeTo(null);
        confirmDialog.setVisible(true);
    }

    // 二次确认才提交
    public void onOK() {
        try {
            this.setAlwaysOnTop(false);
            // 保存配置
            Cache.getInstant().save();

            // 开始生成
            String sql = editTP.getText().trim();
            Map<String, String> entityMap = RegexUtils.parseOriginSql(sql);
            if (entityMap.isEmpty()) {
                Toast.make(project, editPanel, MessageType.ERROR, "sql无法解析，请检查重试");
                return;
            }
            // 生成文件
            for (String key : entityMap.keySet()) {
                final ClassEntity classEntity = DBUtils.getClassEntity(key, Config.getInstant().getTbPrefixType(),
                        Config.getInstant().getTbPrefix());
                final List<FieldEntity> fieldEntities = DBUtils.getFieldEntities(entityMap.get(key),
                        Config.getInstant().getFdPrefixType(), Config.getInstant().getFdPrefix(), Config.getInstant().isLocalDateChk());
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
            dispose();
        } catch (Exception e) {
            Messages.showErrorDialog(project, "未知错误! 可能是路径权限或者路径不正确,已清除缓存路径,请检查重试.", "未知错误");
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
            Toast.make(project, editPanel, MessageType.ERROR, "建表语句不能为空");
            return true;
        }
        if (TextUtils.isEmpty((String) entityDir.getSelectedItem())) {
            Toast.make(project, entityDir, MessageType.ERROR, "实体类路径不能为空");
            return true;
        }
        // cache paths
        String servicePath = (String) serviceDir.getSelectedItem();
        Cache.getInstant().setServicePath(servicePath);
        String daoPath = (String) daoDir.getSelectedItem();
        Cache.getInstant().setDaoPath(daoPath);
        String entityPath = (String) entityDir.getSelectedItem();
        Cache.getInstant().setEntityPath(entityPath);
        String mapperPath = (String) mapperDir.getSelectedItem();
        Cache.getInstant().setMapperPath(mapperPath);

        // item cache
        Cache.getInstant().addServicePathItem(servicePath);
        Cache.getInstant().addDaoPathItem(daoPath);
        Cache.getInstant().addEntityPathItem(entityPath);
        Cache.getInstant().addMapperPathItem(mapperPath);

        return false;
    }

    private void onCancel() {
        dispose();
    }

    public void setProject(Project project) {
        this.project = project;
    }

}
