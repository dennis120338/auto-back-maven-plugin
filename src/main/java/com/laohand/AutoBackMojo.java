package com.laohand;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Dennis，dennis120338@gmail.com
 * @date 2020/4/26
 */
@Mojo(name = "auto-back", defaultPhase = LifecyclePhase.PACKAGE)
public class AutoBackMojo extends AbstractMojo {
    @Parameter
    private String path;

    @Override
    public void execute() {
        if (path == null || "".equals(path)) {
            path = "/data0/back/";
        }
        MavenProject mavenProject = (MavenProject) getPluginContext().get("project");
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String strDate = simpleDateFormat.format(date);
        String trueBackPath = path + "/" + mavenProject.getGroupId() + "/" + mavenProject.getArtifactId() + "/" + strDate + "/";
        getLog().info("Back to " + trueBackPath);
        try {
            copyDir(new File(mavenProject.getBasedir().getAbsolutePath()), new File(trueBackPath));
        } catch (IOException e) {
            getLog().error(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 递归拷贝目录
     *
     * @param src
     * @param dest
     * @throws IOException
     */
    private void copyDir(File src, File dest) throws IOException {
        if (!src.exists()) {
            getLog().error("Src Not Exists src=" + src.getAbsolutePath());
        } else if (src.isFile()) {
            if (dest.isDirectory()) {
                getLog().error("goal can not be a file, dst=" + dest.getAbsolutePath());
            } else {
                FileInputStream fis = new FileInputStream(src);
                FileOutputStream fos = new FileOutputStream(dest);
                byte[] arr = new byte[1024 * 8];
                int len;
                while ((len = fis.read(arr)) != -1) {
                    fos.write(arr, 0, len);
                }
                fis.close();
                fos.close();
            }
        } else {
            if (dest.isFile()) {
                getLog().error("goal can not be a file, dst=" + dest.getAbsolutePath());
            } else {
                if (!dest.exists()) {
                    dest.mkdirs();
                }
                String[] subFiles = src.list();
                if (subFiles != null) {
                    for (String subFile : subFiles) {
                        copyDir(new File(src, subFile), new File(dest, subFile));
                    }
                }
            }
        }
    }
}
