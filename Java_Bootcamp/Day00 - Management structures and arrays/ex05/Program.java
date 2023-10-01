import java.util.Scanner;

public class Program {
    public static final int MAX_STUDENT_NUMBER = 10;
    public static final int DAYS_IN_MONTH = 30;
    public static final int LESSONS_IN_DAY = 6;
    private static final String[] DAY_OF_WEEK = { "TU", "WE", "TH", "FR", "SA", "SU", "MO", "TU", "WE", "TH", "FR",
            "SA", "SU", "MO", "TU", "WE", "TH", "FR", "SA", "SU", "MO", "TU", "WE", "TH", "FR", "SA", "SU", "MO",
            "TU", "WE" };
    private static final String[] TIME_OF_DAY = { "1", "2", "3", "4", "5", "6" };

    private static String[][] timeTable = new String[DAYS_IN_MONTH][LESSONS_IN_DAY];
    private static int[][][] attendance = new int[MAX_STUDENT_NUMBER][DAYS_IN_MONTH][LESSONS_IN_DAY];
    private static String[] studentNames = new String[MAX_STUDENT_NUMBER];

    public static void main(String[] argv) {
        Scanner sc = new Scanner(System.in);
        String userInput = sc.nextLine();
        int count = 0;
        while (!userInput.equals(".") && count != MAX_STUDENT_NUMBER) {
            studentNames[count] = userInput;
            ++count;
            userInput = sc.nextLine();
        }
        userInput = sc.nextLine();
        while (!userInput.equals(".")) {
            String[] timeDay = userInput.split(" ");
            for (int i = 0; i < DAYS_IN_MONTH; ++i) {
                for (int j = 0; j < LESSONS_IN_DAY; ++j) {
                    if (timeDay[1].equals(DAY_OF_WEEK[i]) && timeDay[0].equals(TIME_OF_DAY[j])) {
                        timeTable[i][j] = "ok";
                    }
                }
            }
            userInput = sc.nextLine();
        }
        userInput = sc.nextLine();
        while (!userInput.equals(".")) {
            String[] record = userInput.split(" ");
            for (int i = 0; i < MAX_STUDENT_NUMBER; ++i) {
                for (int j = 0; j < DAYS_IN_MONTH; ++j) {
                    for (int k = 0; k < LESSONS_IN_DAY; ++k) {
                        if (record[0].equals(studentNames[i]) && record[1].equals(TIME_OF_DAY[k])
                                && j == (Integer.parseInt(record[2]) - 1)) {
                            if (record[3].equals("NOT_HERE")) {
                                attendance[i][j][k] = -1;
                            } else {
                                attendance[i][j][k] = 1;
                            }
                        }
                    }
                }
            }
            userInput = sc.nextLine();
        }
        sc.close();
        printTimetable();
    }

    public static void printTimetable() {
        System.out.printf("%11s", " ");
        for (int i = 0; i < DAYS_IN_MONTH; ++i) {
            for (int j = 0; j < LESSONS_IN_DAY; ++j) {
                if (timeTable[i][j] != null) {
                    System.out.printf("%1d:00 %s%3d|", (j + 1), DAY_OF_WEEK[i], (i + 1));
                }
            }
        }
        System.out.println("");
        for (int k = 0; k < MAX_STUDENT_NUMBER; ++k) {
            if (studentNames[k] != null) {
                System.out.printf("%10s|", studentNames[k]);
                for (int i = 0; i < DAYS_IN_MONTH; ++i) {
                    for (int j = 0; j < LESSONS_IN_DAY; ++j) {
                        if (timeTable[i][j] != null) {
                            if (attendance[k][i][j] == 0) {
                                System.out.printf("%10s|", " ");
                            } else {
                                System.out.printf("%10d|", attendance[k][i][j]);
                            }

                        }
                    }
                }
                System.out.print("\n");
            }
        }
    }
}