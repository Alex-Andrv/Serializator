public class RemoteFile implements Serializable {
    private boolean isDirectory;
    private String name;
    private String filePath;
    private long size = -1;

    public RemoteFile(boolean isDirectory, String name, String filePath, long size) {
        this.isDirectory = isDirectory;
        this.name = name;
        this.filePath = filePath;
        this.size = size;
    }

    @Override
    public String toJson() {
        return String.format(
                    """
                {
                    "isDirectory": "%s",
                    "filePath": "%s",
                    "name": "%s",
                    "size": "%s"
                }""",
                isDirectory, name, filePath, size);
    }
}
