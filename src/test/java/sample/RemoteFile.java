package sample;

import serializer.Serializable;

import java.io.File;

public class RemoteFile implements Serializable {
    public boolean isDirectory;
    public String name;
    public String filePath;
    public long size = -1;
    public File file = null;

    public RemoteFile(boolean isDirectory, String name, String filePath, long size) {
        this.isDirectory = isDirectory;
        this.name = name;
        this.filePath = filePath;
        this.size = size;
    }
}
