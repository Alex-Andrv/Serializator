package serializer;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import sample.RemoteFile;
import sample.SomeClass;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class SerializationTest {

    @Test
    public void simpleTest() throws IOException, IllegalAccessException {


        String actualString = Serializer.toJson(SomeClass.class);

        System.out.println(actualString);

        List<String> expectedList = Files.readAllLines(Paths.get("src/test/resourses/resourses.json"));

        List<String> actualList = List.of(actualString.split("\n"));

        Assertions.assertEquals(expectedList.size(), actualList.size());

        for (int i = 0; i < expectedList.size(); i++) {
            Assertions.assertEquals(
                    expectedList.get(i),
                    actualList.get(i),
                    String.format("lines with numbers = %d are different", i));
        }
    }
}
