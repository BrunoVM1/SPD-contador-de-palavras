import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SequentialWordCounter implements WordCounter {

    @Override
    public int countOccurrences(String text, String word) {
        int count = 0;

        // Criar padrão com limites de palavra (\b) e escapando a palavra (Pattern.quote)
        Pattern pattern = Pattern.compile("\\b" + Pattern.quote(word.toLowerCase()) + "\\b");
        Matcher matcher = pattern.matcher(text.toLowerCase());

        // Contar todas as ocorrências
        while (matcher.find()) {
            count++;
        }

        return count;
    }
}
