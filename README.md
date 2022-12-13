## Simple type serialization

Simple type serialization - serialize java type to Json

**NOTE:** If you want to serialize your own class, that class must implement the Serializable marker interface

**NOTE:** This realisation note complete and can be extension in a future.

### Exemple

We have SomeClass:

```
public class SomeClass implements Serializable {
private int result;
private String error;
private Map<String, Set<RemoteFile>> keywords;

    public SomeClass(int result, String error, Map<String, Set<RemoteFile>> keywords) {
        this.result = result;
        this.error = error;
        this.keywords = keywords;
    }
}
```
and RemoteFile:
```
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
```

Serialization result of SomeClass:

```
{
	"result": "int",
	"error": "String",
	"keywords": [{
		"String": [{
			"isDirectory": "boolean",
			"name": "String",
			"filePath": "String",
			"size": "long",
			"file": "File"
		}]
	}]
}
```

