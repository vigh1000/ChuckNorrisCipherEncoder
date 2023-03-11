package chucknorris;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        boolean run = true;
        while (run) {
            p("Please input operation (encode/decode/exit):");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();

            switch (input) {
                case "encode":
                    p("Input string:");
                    input = scanner.nextLine();
                    p("Encoded string:");
                    p(encrypt(input));
                    break;
                case "decode":
                    p("Input encoded string:");
                    input = scanner.nextLine();
                    p(decrypt(input));
                    break;
                case "exit":
                    p("Bye!");
                    run = false;
                    break;
                default:
                    p("There is no '" + input + "' operation");
            }
        }
    }

    private static String decrypt(String input) {
        StringBuilder decryptedString = new StringBuilder();

        //Check 1: The encoded message includes characters other than 0 or spaces;
        if (input.replaceAll("0", "").replaceAll("1", "").replaceAll(" ", "").length() > 0) return "Encoded string is not valid.";


        //splitten
        String[] splittedBlocks = input.split(" ");

        //The number of blocks is odd;
        if (splittedBlocks.length % 2 == 1) return "Encoded string is not valid.";

        StringBuilder tempBinary = new StringBuilder();
        for (int i = 0; i < splittedBlocks.length; i = i + 2) {
            if (splittedBlocks[i].equals("0")) {
                tempBinary.append(String.format("%" + splittedBlocks[i + 1].length() + "s", "").replace(' ', '1'));
            } else if (splittedBlocks[i].equals("00")) {
                tempBinary.append(String.format("%" + splittedBlocks[i + 1].length() + "s", "").replace(' ', '0'));
            } else {
                //The first block of each sequence is not 0 or 00;
                return "Encoded string is not valid.";
            }
        }
        //p(tempBinary.toString());

        //The length of the decoded binary string is not a multiple of 7.
        if (tempBinary.length() % 7 != 0) return "Encoded string is not valid.";

        //einzelne Binarys zurück in Character
        for (int j = 0; j < tempBinary.length(); j = j + 7) {
            double totalCharValue = 0;
            for (int k = 6, l = 0; k >= 0; k--, l++) {
                totalCharValue = totalCharValue + (Math.pow(2, k) * Integer.parseInt(String.valueOf(tempBinary.charAt(j + l))));
                //System.out.println(totalCharValue);
            }
            int charValueAsInt = (int) totalCharValue;
            decryptedString.append(Character.valueOf((char) charValueAsInt));
        }

        return ("Decoded string: \r\n" + decryptedString.toString());
    }

    private static String encrypt(String input) {
        StringBuilder binaryRepresentation = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            char thisChar = input.charAt(i);
            binaryRepresentation.append(String.format("%7s", Integer.toBinaryString(thisChar)).replace(' ', '0'));
            //p(binaryRepresentation.toString());
        }
        StringBuilder encryptedString = new StringBuilder();
        StringBuilder tempBinary = new StringBuilder();
        for (int j = 0; j < binaryRepresentation.length(); j++) {
            if ((j + 1 < binaryRepresentation.length()) && (binaryRepresentation.charAt(j) == binaryRepresentation.charAt(j + 1))) {
                tempBinary.append(binaryRepresentation.charAt(j));
                continue;
            }
            tempBinary.append(binaryRepresentation.charAt(j));
            if (j != 0) encryptedString.append(" ");
            if (tempBinary.toString().contains("1")) {
                encryptedString.append("0 ");
            } else {
                encryptedString.append("00 ");
            }
            encryptedString.append((String.format("%" + tempBinary.length() + "s", "")).replace(' ', '0'));

            tempBinary = new StringBuilder();
        }
        return encryptedString.toString();
    }

    public static void p(String output) {
        System.out.println(output);
    }
}