public class RemoteFile {
    public boolean isDirectory;
    public String name;
    public String filePath;
    public long size = -1;

    public RemoteFile(boolean isDirectory, String name, String filePath, long size) {
        this.isDirectory = isDirectory;
        this.name = name;
        this.filePath = filePath;
        this.size = size;
    }
}
