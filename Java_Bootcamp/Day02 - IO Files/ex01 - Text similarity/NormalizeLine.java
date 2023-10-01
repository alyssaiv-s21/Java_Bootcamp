package ex01;

public class NormalizeLine {
    public static String NormalizeLne(String input) {
        StringBuilder newLine = new StringBuilder();
        char[] line = input.toLowerCase().toCharArray();
        for (int i = 0; i < line.length; ++i) {
            if (Character.isLetter(line[i]) || line[i] == ' ') {
                newLine.append(line[i]);
            } else if (line[i] == '/') {
                newLine.append(" ");
            }
        }
        return newLine.toString();
    }
}