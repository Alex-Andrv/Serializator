package sample;

import serializer.Serializable;

import java.util.Map;
import java.util.Set;

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
