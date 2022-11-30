import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;

public class SomeClass implements Serializable{
    private int result;
    private String error;
    private Map<String, Set<RemoteFile>> keywords;

    public SomeClass(int result, String error, Map<String, Set<RemoteFile>> keywords) {
        this.result = result;
        this.error = error;
        this.keywords = keywords;
    }

    @Override
    public String toJson() {
        StringJoiner keywordsJoiner = new StringJoiner(", ");
        for (var entry : keywords.entrySet()) {
            String key = entry.getKey();
            Set<RemoteFile> remoteFiles = entry.getValue();
            StringJoiner remoteFilesJoiner = new StringJoiner(", ");
            for (RemoteFile remoteFile : remoteFiles) {
                remoteFilesJoiner.add(remoteFile.toJson().replace("\n", "\n\t\t"));
                // not a very beautiful solution with tabs.
            }
            keywordsJoiner.add(String.format("""
                    {
                            "%s": [%s]
                        }""",
                    key, remoteFilesJoiner));
            // not a very beautiful solution with tabs
        }

        return String.format("""
                {
                    "result": "%s",
                    "keywords": [%s],
                    "error": "%s"
                }""",
                result, keywordsJoiner, error);
    }

}
