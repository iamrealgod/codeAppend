package org.codeg.intellij;

import org.codeg.intellij.ui.CodeDialog;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;

/**
 *
 * @author liufei
 * @date 2018/12/24 17:11
 */
public class MainAction extends AnAction implements DumbAware{

    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        Project project = e.getData(PlatformDataKeys.PROJECT);
        CodeDialog jsonD = new CodeDialog(project);
        jsonD.setProject(project);
        jsonD.setSize(600, 400);
        jsonD.setLocationRelativeTo(null);
        jsonD.setVisible(true);
    }
}
