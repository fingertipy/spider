import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @Description
 * @Author dayu
 * @Date 2019/10/11 16:46
 * @Version v1.0
 */
public class Test {

    @org.junit.Test
    public void testConnection(){
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/douban?characterEncoding=UTF-8&serverTimezone=UTC", "root", "mysql");
            System.out.println(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
