import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TextLoader {

    public static String loadText(String filePath) throws IOException {
        return Files.readString(Path.of(filePath));
    }
}
