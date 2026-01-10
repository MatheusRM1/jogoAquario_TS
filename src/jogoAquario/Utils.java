package jogoAquario;

public class Utils {
    public static String repeat(String s, int n) {
        if (s == null) return null;
        if (n <= 0) return "";
        StringBuilder sb = new StringBuilder(s.length() * n);
        for (int i = 0; i < n; i++) {
            sb.append(s);
        }
        return sb.toString();
    }
}
