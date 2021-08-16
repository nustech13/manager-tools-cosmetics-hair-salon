package doan;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author MINHTHUAN
 */
public class ChuanHoa {
    public static String chuanHoaHoTen(String hoTen){
        String a = hoTen;
        
        if(!a.equals("")){
            a = a.trim();
            a = a.toLowerCase();
            a = a.replaceAll("\\s+", " ");
            String[] temp = a.split(" ");
            a = "";
            for (String temp1 : temp) {
            a += Character.toUpperCase(temp1.charAt(0)) + temp1.substring(1) + " ";
            }
        }
        
        return a;
    }
    public static boolean kiemTraEmail(String email){
        String a = "^[A-Za-z]+[A-Za-z0-9]*@+[A-Za-z0-9]*+[\\.A-Za-z0-9]*"; 
        return email.matches(a);
    }
    public static boolean kiemTraPhone(String phone){
        String a = "^0+[1-9]+[0-9]{8}";
        return phone.matches(a);
    }
    public static boolean kiemTraDateToday(Date date) throws ParseException{
        int kt = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String td = LocalDate.now().toString();
        Date today = sdf.parse(td);
        kt = today.compareTo(date);
        return kt != -1;
        
    }
    public static boolean KiemTraDate18Tuoi(Date date) throws ParseException{
        int kt = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String td = LocalDate.now().toString();
        String[] temp = td.split("-");
        int nam = Integer.valueOf(temp[0]) - 18;
        String birth = nam + "-" + temp[1] + "-" + temp[2];
        Date today = sdf.parse(birth);
        kt = today.compareTo(date);
        return kt != -1;
    }
    public static boolean soSanhDate(Date date1, Date date2){
        int kt = date1.compareTo(date2);
        return kt != -1;
    }
}
