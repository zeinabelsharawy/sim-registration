import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        Pattern p = Pattern.compile("(([A-Z].*[0-9])|([0-9].*[A-Z]))");
        Matcher m = p.matcher("321A");
        boolean b = m.find();
        System.out.println(b);
    }
}
