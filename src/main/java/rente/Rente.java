package rente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description spider
 * @Author      dayu
 * @Date        2019/10/11 15:05
 * @Version     v1.0
 */
public class Rente {
    public static String DOU_BAN_URL = "https://www.douban.com/group/baoanzufang/discussion?start={pageStart}";

    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException, InterruptedException {

        Class.forName("com.mysql.jdbc.Driver");
        String sqlUrl = "jdbc:mysql://localhost:3306/douban?characterEncoding=UTF-8&serverTimezone=UTC";
        Connection conn = DriverManager.getConnection(sqlUrl, "root", "mysql");
        Statement stat = conn.createStatement();

        int pageStrat = 0;
        while(true) {
            try {
                URL url = new URL(DOU_BAN_URL.replace("{pageStart}",pageStrat+""));
                System.out.println("当前页面：" + DOU_BAN_URL.replace("{pageStart}",pageStrat+""));
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                //设置请求方式
                connection.setRequestMethod("GET");
                // 10秒超时
                connection.setConnectTimeout(10000);
                connection.setReadTimeout(10000);
                connection.setRequestProperty("Cookie", "bid=V2DRL4mfkjQ; __utmc=30149280; ll=\"118282\"; ap_v=0,6.0; __utma=30149280.99766166.1562306946.1562306946.1562576595.2; __utmz=30149280.1562576595.2.2.utmcsr=baidu|utmccn=(organic)|utmcmd=organic; _vwo_uuid_v2=D9D4E2A5332E5A947211DB67195B046E0|f16585ce5703078b60379b23b6c2511c; _pk_ref.100001.8cb4=%5B%22%22%2C%22%22%2C1562576609%2C%22https%3A%2F%2Fwww.baidu.com%2Flink%3Furl%3DycPrEWSD7HPKD3Le3_o7exduYmeHJviKaSFDkIcZ_ySfiYQBn2JmESv06ar0C8V5%26wd%3D%26eqid%3Da30218e4000357c9000000065d2306da%22%5D; _pk_ses.100001.8cb4=*; __utmt=1; __yadk_uid=if3s4kXzuN3b4C2MM7pLNu0hjTSgxYbQ; _pk_id.100001.8cb4=de663b935f718c9c.1562306945.2.1562576680.1562306945.; __utmb=30149280.20.6.1562576680698");
                connection.setRequestProperty("Host", "www.douban.com");
                connection.setRequestProperty("Referer", "https://www.douban.com/group/longgangzufang/discussion?start=25");
                connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36");
                //连接
                connection.connect();
                //得到响应码
                int responseCode = connection.getResponseCode();

                if(responseCode == HttpURLConnection.HTTP_OK){
                    //得到响应流
                    InputStream inputStream = connection.getInputStream();
                    //获取响应
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    String returnStr = "";
                    String line;
                    while ((line = reader.readLine()) != null){
                        returnStr+=line + "\r\n";
                    }
                    //该干的都干完了,记得把连接断了
                    reader.close();
                    inputStream.close();
                    connection.disconnect();
                    Pattern p = Pattern.compile("<a href=\"([^\"]*)\" title=\"[^\"]*\" class=\"\">[^\"]*</a>");
                    Matcher m = p.matcher(returnStr);
                    while(m.find()) {
                        Thread.sleep(500);
                        try {
                            String tempUrlStr = m.group(1);
                            System.out.println("当前链接：" + tempUrlStr);
                            URL tempUrl = new URL(tempUrlStr);
                            HttpURLConnection tempConnection = (HttpURLConnection)tempUrl.openConnection();
                            //设置请求方式
                            tempConnection.setRequestMethod("GET");
                            // 10秒超时
                            tempConnection.setConnectTimeout(10000);
                            tempConnection.setReadTimeout(10000);
                            tempConnection.setRequestProperty("Cookie", "bid=V2DRL4mfkjQ; __utmc=30149280; ll=\"118282\"; ap_v=0,6.0; __utma=30149280.99766166.1562306946.1562306946.1562576595.2; __utmz=30149280.1562576595.2.2.utmcsr=baidu|utmccn=(organic)|utmcmd=organic; _vwo_uuid_v2=D9D4E2A5332E5A947211DB67195B046E0|f16585ce5703078b60379b23b6c2511c; _pk_ref.100001.8cb4=%5B%22%22%2C%22%22%2C1562576609%2C%22https%3A%2F%2Fwww.baidu.com%2Flink%3Furl%3DycPrEWSD7HPKD3Le3_o7exduYmeHJviKaSFDkIcZ_ySfiYQBn2JmESv06ar0C8V5%26wd%3D%26eqid%3Da30218e4000357c9000000065d2306da%22%5D; _pk_ses.100001.8cb4=*; __utmt=1; __yadk_uid=if3s4kXzuN3b4C2MM7pLNu0hjTSgxYbQ; _pk_id.100001.8cb4=de663b935f718c9c.1562306945.2.1562576680.1562306945.; __utmb=30149280.20.6.1562576680698");
                            tempConnection.setRequestProperty("Host", "www.douban.com");
                            tempConnection.setRequestProperty("Referer", "https://www.douban.com/group/baoanzufang/discussion?start=25");
                            tempConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36");
                            //连接
                            tempConnection.connect();
                            //得到响应码
                            int tempResponseCode = tempConnection.getResponseCode();

                            if(tempResponseCode == HttpURLConnection.HTTP_OK){
                                //得到响应流
                                InputStream tempInputStream = tempConnection.getInputStream();
                                //获取响应
                                BufferedReader tempReader = new BufferedReader(new InputStreamReader(tempInputStream));
                                String tempReturnStr = "";
                                String tempLine;
                                while ((tempLine = tempReader.readLine()) != null){
                                    tempReturnStr += tempLine + "\r\n";
                                }
                                Pattern p2 = Pattern.compile("\"text\": \"([^\"]*)\",\r\n" +
                                        "	\"name\": \"([^\"]*)\",\r\n" +
                                        "	\"url\": \"([^\"]*)\",\r\n" +
                                        "  \"commentCount\": \"[^\"]*\",\r\n" +
                                        "  \"dateCreated\": \"([^\"]*)\",");
                                Matcher m2 = p2.matcher(tempReturnStr);
                                while(m2.find()) {
                                    stat.executeUpdate("INSERT INTO rente(title,content,releas_time,url,create_time) VALUES ('" + m2.group(2).replaceAll("[\\x{10000}-\\x{10FFFF}]", "") + "','" + m2.group(1).replaceAll("[\\x{10000}-\\x{10FFFF}]", "") + "','" + m2.group(4).replace("T"," ") + "','" + m2.group(3) + "',now());");
                                }
                                tempReader.close();
                                tempInputStream.close();
                                tempConnection.disconnect();
                            }

                        }catch(Exception e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("换页");
                }
            }catch(Exception e) {
                e.printStackTrace();
            }
            pageStrat+=25;
        }
    }
}
