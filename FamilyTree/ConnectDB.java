package FamilyTree;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.DatabaseMetaData;
//begin

public class ConnectDB {
    public static void connect(){
        Connection conn =null;
        try{
            String url =" jdbc:sqlite:C:/sqlite/JTP.db";
            conn=DriverManager.getConnection(url);
            System.out.println("Connection to DB has been established.");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }finally {
            try{
                if(conn!=null){
                    conn.close();
                }
            }catch (SQLException ex){
                System.out.println(ex.getMessage());
            }
        }

    }
    public static void createNewDB(String fileName){
        String url ="jdbc:sqlite:C:/sqlite/" + fileName;
        try{
            Connection con= DriverManager.getConnection(url);
            if (con != null) {
               DatabaseMetaData meta=con.getMetaData();
                System.out.println("The driver name is" + meta.getDriverName());
                System.out.println("A new database has been created");
            }

        }catch (SQLException exc){
            System.out.println(exc.getMessage());
        }
    }
    public static void main(String[]args){
        connect();
        createNewDB("SSSIT.db");
    }

}
