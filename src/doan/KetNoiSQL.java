package doan;

import java.sql.*;
public class KetNoiSQL {

    
    public static Connection layKetNoi(){
       Connection ketNoi = null;
       String uRL = "jdbc:sqlserver://;databaseName=QLTIEMCATTOC";
       String userName = "sa";
       String password = "123456789";
       try{
           Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
           ketNoi = DriverManager.getConnection(uRL, userName, password);
           System.out.println("Ket noi CSDL thanh cong!");
       }
       catch(ClassNotFoundException | SQLException ex){
           System.out.println("Ket noi CSDL khong thanh cong!");
       }
       return ketNoi;
    }
    
    
}
