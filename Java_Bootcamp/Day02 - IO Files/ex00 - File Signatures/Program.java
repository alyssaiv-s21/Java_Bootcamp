package ex00;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Program {
    private static String SIGNAUTRES = "ex00/signatures.txt";
    private static String RESULT_FILE = "ex00/result.txt";
    private static String INPUT_END = "42";
    private static HashMap<String, String> signatures = new HashMap<String, String>();

    public static void main(String[] argv) {
        try {
            OpenSignaturesFile();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            System.exit(-1);
        }
        Scanner sc = new Scanner(System.in);
        String filePath = sc.nextLine();
        while (!filePath.equals(INPUT_END)) {
            try {
                String fileSignature = OpenUserFile(filePath);
                String code = FindFileType(fileSignature);
                if (!code.equals("NO_RESULT")) {
                    System.out.println("PROCESSED");
                    try {
                        WriteToFile(code);
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                        System.out.println("Can't to write result in file");
                    }
                } else {
                    System.out.println("UNDEFINED");
                }
            } catch (IOException ex) {
                System.out.println("Can't open file, please check your input");
            }
            filePath = sc.nextLine();
        }
        sc.close();
    }

    private static void WriteToFile(String code) throws IOException {
        try (FileOutputStream file = new FileOutputStream(RESULT_FILE, true)) {
            byte[] codeByte = (code + "\n").getBytes();
            file.write(codeByte);
        }
    }

    private static String FindFileType(String fileCode) {
        for (String key : signatures.keySet()) {
            if (fileCode.startsWith(key)) {
                return signatures.get(key);
            }
        }
        return "NO_RESULT";
    }

    private static String OpenUserFile(String filePath) throws IOException {
        StringBuffer fileCode = new StringBuffer();
        try (FileInputStream file = new FileInputStream(filePath)) {
            byte[] buffer = new byte[8];
            file.read(buffer, 0, 8);
            for (int i = 0; i < 8; ++i) {
                fileCode.append(byteToHex(buffer[i]));
            }
        }
        return fileCode.toString();
    }

    private static String byteToHex(byte num) {
        char[] hexDigits = new char[2];
        hexDigits[0] = Character.forDigit((num >> 4) & 0xF, 16);
        hexDigits[1] = Character.forDigit((num & 0xF), 16);
        return new String(hexDigits);
    }

    private static void OpenSignaturesFile() throws IOException {
        Scanner sc = new Scanner(new FileInputStream(SIGNAUTRES));
        String line = sc.nextLine();
        while (sc.hasNextLine()) {
            String[] word = line.split(", ");
            String code = word[1].replaceAll("\\s", "").toLowerCase();
            signatures.put(code, word[0]);
            line = sc.nextLine();
        }
        sc.close();
    }
}