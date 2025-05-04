import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ParallelWordCounter implements WordCounter {
    private final int threadCount;

    public ParallelWordCounter(int threadCount) {
        this.threadCount = threadCount;
    }

    @Override
    public int countOccurrences(String text, String word) {
        int length = text.length();
        int blockSize = length / threadCount;

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        List<Future<Integer>> results = new ArrayList<>();

        for (int i = 0; i < threadCount; i++) {
            int start = i * blockSize;
            int end = (i == threadCount - 1) ? length : (i + 1) * blockSize;

            // Corrigir divisões no meio das palavras
            while (start > 0 && Character.isLetter(text.charAt(start - 1))) start--;
            while (end < text.length() && Character.isLetter(text.charAt(end))) end++;

            String chunk = text.substring(start, end);
            Callable<Integer> task = () -> countInChunk(chunk, word);
            results.add(executor.submit(task));
        }

        int total = 0;
        try {
            for (Future<Integer> result : results) {
                total += result.get();
            }
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Erro ao contar: " + e.getMessage());
        } finally {
            executor.shutdown();
        }

        return total;
    }

    private int countInChunk(String text, String word) {
        int count = 0;
        int index = 0;
        String lowerText = text.toLowerCase();
        String target = word.toLowerCase();

        while ((index = lowerText.indexOf(target, index)) != -1) {
            boolean isWordBoundaryBefore = (index == 0 || !Character.isLetter(lowerText.charAt(index - 1)));
            boolean isWordBoundaryAfter = (index + target.length() == lowerText.length()
                    || !Character.isLetter(lowerText.charAt(index + target.length())));

            if (isWordBoundaryBefore && isWordBoundaryAfter) {
                count++;
            }
            index += target.length();
        }

        return count;
    }
}
