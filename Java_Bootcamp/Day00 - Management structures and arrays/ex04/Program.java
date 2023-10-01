import java.util.Scanner;

public class Program {
    public static final int MAX_CODE_VALUE = 65635;
    public static final int AMOUNT_OF_NUMBER = 10;
    public static final int MAX_GRAPH_HEIGHT = 10;

    private static int[] frequency = new int[MAX_CODE_VALUE + 1];
    private static int[] topChar = new int[AMOUNT_OF_NUMBER + 1];
    private static int[] topQty = new int[AMOUNT_OF_NUMBER + 1];
    private static int[] topGraph = new int[AMOUNT_OF_NUMBER];

    public static void main(String[] argv) {
        Scanner sc = new Scanner(System.in);
        sc.useDelimiter("");
        char ch = '0';
        while (true) {
            ch = sc.next().charAt(0);
            if (ch == '\n') {
                break;
            }
            frequency[(int) ch] += 1;
        }
        sc.close();
        findMostFrequently();
        graphScaling();
        printGraph();
    }

    public static void findMostFrequently() {
        for (int i = 0; i < frequency.length; ++i) {
            int position = AMOUNT_OF_NUMBER;
            while (position != 0 && (frequency[i] > topQty[position])) {
                topQty[position] = topQty[position - 1];
                topChar[position] = topChar[position - 1];
                position--;
            }
            if (position != 10) {
                if (frequency[i] <= topQty[position]) {
                    topQty[position + 1] = frequency[i];
                    topChar[position + 1] = i;
                } else {
                    topQty[position] = frequency[i];
                    topChar[position] = i;
                }
            }
        }
    }

    public static void graphScaling() {
        if (topQty[0] > MAX_GRAPH_HEIGHT) {
            topGraph[0] = MAX_GRAPH_HEIGHT;
            for (int i = 1; i < AMOUNT_OF_NUMBER; ++i) {
                topGraph[i] = (topQty[i] * MAX_GRAPH_HEIGHT) / topQty[0];
            }
        } else {
            for (int i = 0; i < AMOUNT_OF_NUMBER; ++i) {
                topGraph[i] = topQty[i];
            }
        }
    }

    public static void printGraph() {
        for (int i = MAX_GRAPH_HEIGHT + 1; i >= 0; --i) {
            for (int j = 0; j < AMOUNT_OF_NUMBER && topQty[j] != 0; ++j) {
                if (topGraph[j] + 1 == i) {
                    System.out.print(topQty[j] + " ");
                    if (topQty[j] <= 9) {
                        System.out.print(" ");
                    }
                }
                if (topGraph[j] >= i && i != 0) {
                    System.out.print("#  ");
                }
                if (i == 0) {
                    System.out.print((char) topChar[j] + "  ");
                }
            }
            System.out.print("\n");
        }
    }
}