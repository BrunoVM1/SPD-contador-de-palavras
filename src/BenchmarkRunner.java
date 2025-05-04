import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

public class BenchmarkRunner {

    private static final int REPETICOES = 30;
    private static final String PALAVRA = "clarissa";

    public static void main(String[] args) throws IOException {
        String texto = TextLoader.loadText("clarissa.txt");

        rodarBenchmark("Sequencial", new SequentialWordCounter(), texto);
        rodarBenchmark("Paralelo-2", new ParallelWordCounter(2), texto);
        rodarBenchmark("Paralelo-4", new ParallelWordCounter(4), texto);
        rodarBenchmark("Paralelo-6", new ParallelWordCounter(6), texto);
        rodarBenchmark("Paralelo-8", new ParallelWordCounter(8), texto);
    }

    private static void rodarBenchmark(String nome, WordCounter contador, String texto) throws IOException {
        String nomeArquivo = nome.toLowerCase(Locale.ROOT) + ".txt";
        FileWriter csv = new FileWriter(nomeArquivo);
        csv.write("Execução,Tempo (s)\n");

        double soma = 0;

        for (int i = 1; i <= REPETICOES; i++) {
            long inicio = System.nanoTime();
            contador.countOccurrences(texto, PALAVRA);
            long fim = System.nanoTime();

            double tempo = (fim - inicio) / 1_000_000_000.0;
            soma += tempo;

            csv.write(i + "," + String.format(Locale.US, "%.6f", tempo) + "\n");
        }

        double media = soma / REPETICOES;
        csv.write("Média," + String.format(Locale.US, "%.6f", media) + "\n");
        csv.close();

        System.out.printf("Benchmark %s finalizado. Média: %.3f segundos.%n", nome, media);
    }
}
