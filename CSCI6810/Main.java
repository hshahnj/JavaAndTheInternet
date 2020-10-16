import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws ParseException {
//        Account accnt = new Account("User", "Pass", "Pass1", "Harshil");
//        accnt.signUp();

        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
        int today = Calendar.getInstance().getTime().getDate();
        Date d1 = sdformat.parse("2019-04-15");
        System.out.println(today);
        System.out.println(d1);
    }
}
