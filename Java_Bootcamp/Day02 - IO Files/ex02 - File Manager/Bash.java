package ex02;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

class Bash {
    private String currentFolder;
    private Path currentPath;

    public Bash(String folder) {
        currentFolder = folder;
        currentPath = Paths.get(currentFolder);
        if (!Files.exists(currentPath) || !Files.isDirectory(currentPath)) {
            System.out.println("This folder doesn't exist");
            System.exit(-1);
        }
    }

    public void mvCommand(String what, String where) throws IOException {
        Path src = Paths.get(currentFolder + "//" + what).normalize();
        Path dest;
        if (where.length() > 0 && where.startsWith("/")) {
            dest = Paths.get(where).normalize();
        } else {
            dest = Paths.get(currentFolder + "//" + where).normalize();
        }
        if (Files.exists(dest) && Files.isDirectory(dest)) {
            dest = Paths.get(currentFolder + "//" + where + "//" + src.getFileName()).normalize();
            Files.move(src, dest, StandardCopyOption.REPLACE_EXISTING);
        } else {
            Files.move(src, dest, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    public void lsCommand() throws IOException {
        File dir = new File(currentFolder);
        for (File item : dir.listFiles()) {
            long size = 0;
            if (item.isDirectory()) {
                size = getFolderSize(item) / 1024;
            } else {
                size = item.length() / 1024;
            }
            System.out.println(item.getName() + " " + size + " KB");
        }
    }

    private long getFolderSize(File dir) throws IOException {
        long length = 0;
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    length += file.length();
                } else {
                    length += getFolderSize(file);
                }
            }
        }
        return length;
    }

    public void cdCommand(String folderName) {
        if (folderName.length() > 0 && folderName.startsWith("/")) {
            Path newPath = Paths.get(folderName).normalize();
            if (Files.exists(newPath) && Files.isDirectory(newPath)) {
                currentPath = newPath;
                currentFolder = newPath.toString();
                System.out.println(newPath.toString());
            } else {
                System.out.println("This folder doesn't exist");
            }
        } else {
            String newFolder = currentFolder + "//" + folderName;
            Path newPath = Paths.get(newFolder).normalize();
            if (Files.exists(newPath) && Files.isDirectory(newPath)) {
                currentPath = newPath;
                currentFolder = newPath.toString();
                System.out.println(newPath.toString());
            } else {
                System.out.println("This folder doesn't exist");
            }
        }
    }
}
