public class SequentialWordCounter implements WordCounter {

    @Override
    public int countOccurrences(String text, String word) {
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
