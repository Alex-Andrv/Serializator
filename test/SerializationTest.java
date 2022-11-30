import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SerializationTest {

    @Test
    public void simpleTest() throws IOException {
        RemoteFile remote_file_1 = new RemoteFile(false, "file1", "path1", 128);
        RemoteFile remote_file_2 = new RemoteFile(false, "file2", "path2", 1024);
        RemoteFile remote_file_3 = new RemoteFile(true, "file3", "path3", 1024);
        RemoteFile remote_file_4 = new RemoteFile(false, "file4", "path4", 1024);
        RemoteFile remote_file_5 = new RemoteFile(true, "file5", "path5", 2048);
        RemoteFile remote_file_6 = new RemoteFile(false, "file6", "path6", 1024);

        SomeClass remoteFile = new SomeClass(10, "test_error_string",
                Map.of("test_1", Set.of(remote_file_1, remote_file_2),
                        "test_2", Set.of(remote_file_3, remote_file_4),
                        "test_3", Set.of(remote_file_5, remote_file_6)));

        String actualString = remoteFile.toJson();

        List<String> expectedList = Files.readAllLines(Paths.get("test/resourses/resourses.json"));

        List<String> actualList = List.of(actualString.split("\n"));

        assertEquals(expectedList.size(), actualList.size());

        for (int i = 0; i < expectedList.size(); i++) {
            assertEquals(
                    expectedList.get(i),
                    actualList.get(i),
                    String.format("lines with numbers = %d are different", i));
        }
    }


}
