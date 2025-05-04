import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            String filePath = "clarissa.txt";
            String text = TextLoader.loadText(filePath);
            String palavra = "clarissa";

            WordCounter contador = new ParallelWordCounter(4); // ou SequentialWordCounter()

            long inicio = System.nanoTime();
            int total = contador.countOccurrences(text, palavra);
            long fim = System.nanoTime();

            double tempoSegundos = (fim - inicio) / 1_000_000_000.0;

            System.out.printf("'%s' aparece %d vezes.%n", palavra, total);
            System.out.printf("Tempo de execução: %.4f segundos.%n", tempoSegundos);

        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }
}
