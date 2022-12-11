import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;


public class Main {
    public static void main(String[] args) {
        System.out.println("1 задание: " + encrypt("Hello"));
        System.out.println("1 задание: " + decrypt(new int[] {72, 33, -73, 84, -12, -3, 13, -13, -68}));
        System.out.println("2 задание: " + canMove("Rook", "A8", "H8"));
        System.out.println("3 задание: " + canComplete("butl", "beatiful"));
        System.out.println("4 задание: " + sumDigProd(16, 28));
        System.out.println("5 задание: " + Arrays.toString(sameVowelGroup(new String[] {"toe", "ocelot", "maniac"})));
        System.out.println("6 задание: " + validateCard("1234567890123452"));
        System.out.println("7 задание: " + numToEng(126));
        System.out.println("8 задание: " + getSHA256Hash("password123"));
        System.out.println("9 задание: " + correctTitle("jOn SnoW, kINg IN thE noRth."));
        System.out.println("10 задание: \n " + hexLattice(7));
    }

    public static String encrypt(String s) {
        int[] arr = s.chars().toArray();
        for (int i = arr.length - 1; i > 0; --i) {
            arr[i] -= arr[i-1];
        }
        return Arrays.toString(arr);
    }

    public static String decrypt(int[] arr) {
        var res = new StringBuilder();
        res.append((char) arr[0]);
        for (int i = 1; i < arr.length; ++i)
            res.append((char) (arr[i] += arr[i - 1]));
        return res.toString();
    }

    public static boolean canMove(String figure, String start, String end) {
        int startRow = start.charAt(0) - 'A';
        int startCol = start.charAt(1) - '1';
        int endRow = end.charAt(0) - 'A';
        int endCol = end.charAt(1) - '1';
        if (startRow < 0 || startRow > 7 || startCol < 0 || startCol > 7 || endRow < 0 || endRow > 7 || endCol < 0 || endCol > 7)
            return false;
        return switch (figure) {
            case "Pawn" -> startRow == endRow && (endCol - startCol == 1 || (startCol == 1 && endCol - startCol == 2));
            case "Rook" -> startRow == endRow || startCol == endCol;
            case "Knight" ->
                    Math.abs(startRow - endRow) == 2 && Math.abs(startCol - endCol) == 1 || Math.abs(startRow - endRow) == 1 && Math.abs(startCol - endCol) == 2;
            case "Bishop" -> Math.abs(startRow - endRow) == Math.abs(startCol - endCol);
            case "Queen" ->
                    startRow == endRow || startCol == endCol || Math.abs(startRow - endRow) == Math.abs(startCol - endCol);
            case "King" -> Math.abs(startRow - endRow) <= 1 && Math.abs(startCol - endCol) <= 1;
            default -> false;
        };
    }

    public static boolean canComplete(String str, String s) {
        int j = 0;
        int n1 = str.length();
        for (int i = 0, n2 = s.length(); i < n2 && j < n1; ++i) {
            if (s.charAt(i) == str.charAt(j)) {
                ++j;
            }
        }
        return j == n1;
    }

    public static <arr> int sumDigProd(int... arr) {
        int x = Arrays.stream(arr).sum();
        int a;
        while (x > 9) {
            a = 1;
            while (x > 9) {
                a *= x % 10;
                x /= 10;
            }
            a *= x;
            x = a;
        }
        return x;
    }
    public static int countVowels(String word) {
        final String vowels = "aeiouy";
        StringBuilder unique = new StringBuilder();
        int sum = 0;
        for (char lit : word.toLowerCase().toCharArray()) {
            if (vowels.indexOf(lit) != -1 && !unique.toString().contains(lit + "")) {
                sum += lit;
                unique.append(lit);
            }
        }
        return sum;
    }
    public static String[] sameVowelGroup(String[] words) {
        String[] result = new String[words.length];
        int resultIdx = 0;
        int baseVowels = countVowels(words[0]);
        for (int i = 0; i < words.length; i++) {
            if (baseVowels == countVowels(words[i])) {
                result[resultIdx++] = words[i];
            }
        }
        return Arrays.copyOf(result, resultIdx);
    }

    public static boolean validateCard(String card) {
        int index_check = card.length();
        int parity = index_check % 2;
        int x;
        int sum = 0;
        index_check -= 1;
        for (int i = 0; i < index_check; i++) {
            x = card.charAt(i) - '0';
            if (i % 2 == parity) {
                x *= 2;
                if (x > 9)
                    x = 1 + x % 10;

            }
            sum += x;
        }
        return 10 - sum % 10 == card.charAt(index_check) - '0';
    }
    public static String numToEng (int n) {
        String[] digits = new String[] {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten",
        "eleven", "twelve", "thirten", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"};
        String[] des = new String[] {"twenty ", "thirty ", "fourty ", "fifty ", "sixty ", "seventy ", "eighty ", "ninety "};
        if (n == 0) {
            return "zero";
        }
        var res = new StringBuilder();
        int s_100 = n / 100;
        int s_011 = n - s_100 * 100;
        int s_001 = n % 10;
        if (s_100 != 0) {
            res.append(digits[s_100 - 1]);
            res.append(" hundred ");
        }
        if (s_011 < 20) {
            res.append(des[s_011 - 1]);
        }
        else {
            res.append(des[s_011 / 10 - 2]);
            if (s_001 != 0)
                res.append(digits[s_001 - 1]);
        }
        int n1 = res.length() - 1;
        if (res.charAt(n1) == ' ')
            res.deleteCharAt(n1);
        return res.toString();
    }
    public static String correctTitle(String s) {
        String[] words = new String[] {"in", "and", "the", "of"};
        String[] arr = s.split(" ");
        StringBuilder res = new StringBuilder();
        for (String x : arr) {
            x = x.toLowerCase();
            if (Arrays.asList(words).contains(x)) {
                if (x.charAt(x.length()-1) != '.')
                    res.append(x + " ");
                else
                    res.append(x);
            }
            else {
                if (x.charAt(x.length()-1) != '.')
                    res.append(x.substring(0, 1).toUpperCase() + x.substring(1) + " ");
                else
                    res.append(x.substring(0, 1).toUpperCase() + x.substring(1));
            }
        }
        return res.toString();
    }

    public static String getSHA256Hash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashInBytes = md.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hashInBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
public static String hexLattice(int n){
    if(n == 1)return " o ";
    int num = 1;
    int counter = 0;
    while(num < n){
        num = 3 * counter * (counter - 1) + 1;
        counter++;
    }
    if(num != n)return "Invalid";
    StringBuilder answer = new StringBuilder();
    int side  = counter - 1;
    int mid = side * 2 + 1;
    for(int i = 0; i < counter - 1; i++){
        for(int j = 0; j < mid - side - 2; j++){
            answer.append(" ");
        }
        for(int k = 0; k < side; k++){
            if(k == 0)answer.append("o");
            else answer.append(" o");
            ;           }
        for(int j = 0; j < (mid - side - 2); j++){
            answer.append(" ");
        }
        answer.append("\n");
        side++;
    }
    side -= 2;
    for(int i = 0; i < counter - 2; i++){
        for(int j = 0; j < (mid - side - 2); j++){
            answer.append(" ");
        }
        for(int k = 0; k < side; k++){
            if(k == 0)answer.append("o");
            else answer.append(" o");
        }
        for(int j = 0; j < (mid - side - 2); j++){
            answer.append(" ");
        }
        answer.append("\n");
        side--;
    }
    return answer.toString().trim();
}
}


