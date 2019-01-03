package org.codeg.intellij.util;

import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.io.FileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 *
 * @author liufei
 * @date 2018/12/26 17:08
 */
public class FileUtils {

    public static void createFile(String filePath, String content) {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                if (!file.createNewFile()) {
                    Messages.showErrorDialog("create file '"+filePath+"' error","FILE ERROR");
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        OutputStreamWriter osw = null;
        try {
            // 向目标文件中写入内容
            // FileWriter(File file, boolean append)，append为true时为追加模式，false或缺省则为覆盖模式
            FileOutputStream fos = new FileOutputStream(file);
            osw = new OutputStreamWriter(fos, "UTF-8");
            osw.write(content);
            osw.flush();
        } catch (IOException e) {
            Messages.showErrorDialog("unknown error! please retry.","未知错误");
        } finally {
            if (null != osw) {
                try {
                    osw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String readContent(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            Messages.showErrorDialog("file '"+filePath+"' not exists","ERROR");
            return null;
        }
        // 读取内容
        try {
            return FileUtil.loadFile(file, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Messages.showErrorDialog("read file '"+filePath+"' error","ERROR");
        return null;
    }

    public static boolean generateDir(String dir) {
        File file = new File(dir);
        if (!file.exists()) {
            return file.mkdir();
        }
        return true;
    }
}
