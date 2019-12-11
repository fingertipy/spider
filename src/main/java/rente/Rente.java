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
    //public static String AREA_GROUP  = "baoanzufang";
    public static String AREA_GROUP  = "nanshanzufang";
    public static String DOU_BAN_URL = "https://www.douban.com/group/" + AREA_GROUP + "/discussion?start={pageStart}";

    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException, InterruptedException {

        int count = 0;

        Class.forName("com.mysql.jdbc.Driver");
        String sqlUrl = "jdbc:mysql://localhost:3306/douban?characterEncoding=UTF-8&serverTimezone=UTC";
        Connection conn = DriverManager.getConnection(sqlUrl, "root", "mysql");
        Statement stat = conn.createStatement();

        int pageStrat = 0;
        while(true) {
            try {
                Thread.sleep(5000);
                URL url = new URL(DOU_BAN_URL.replace("{pageStart}",pageStrat+""));
                System.out.println("当前页面：" + DOU_BAN_URL.replace("{pageStart}",pageStrat+""));
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                //设置请求方式
                connection.setRequestMethod("GET");
                // 10秒超时
                connection.setConnectTimeout(10000);
                connection.setReadTimeout(10000);
                connection.setRequestProperty("Cookie", "ll=\"118282\"; bid=QJabbJe5cJU; gr_user_id=57895035-6456-44cc-a6b2-52657bf1efb5; _vwo_uuid_v2=D829068CC51F524D66B60D86ACFE0B2F7|5e0e6d2dbf05b205c7ca067a2b8d4124; __yadk_uid=6EHhhCeyGQ9G5OPZjJWkwsnqP8GHqip0; __gads=ID=8d589df0a0dbfb11:T=1569207315:S=ALNI_MZ2-HVHG_6q51jW3mxlChUdFInKyg; viewed=\"27034717_10426640_27038538_26373138_24700704_3897837\"; douban-fav-remind=1; __utmv=30149280.20187; __utmz=30149280.1571969966.12.5.utmcsr=baidu|utmccn=(organic)|utmcmd=organic; _pk_ref.100001.8cb4=%5B%22%22%2C%22%22%2C1574675295%2C%22https%3A%2F%2Fwww.baidu.com%2Flink%3Furl%3Dia2qvkDq_wTrgbZbTg7lVGfraG9CuaUteatN7VR_uN5t2eoEYVtLepopV3zD429ZxNH7Uvztvt0YpBETwkxd5K%26wd%3D%26eqid%3Da97df2df00137cc9000000065db25b6c%22%5D; _pk_ses.100001.8cb4=*; __utma=30149280.1080090324.1567999882.1574327131.1574675296.15; __utmc=30149280; __utmt=1; ck=k1U4; ap_v=0,6.0; push_noty_num=0; push_doumail_num=0; _pk_id.100001.8cb4=1a08abb4a33a9ed6.1568107009.13.1574675361.1574327130.; __utmb=30149280.5.10.1574675296");
                connection.setRequestProperty("Host", "www.douban.com");
                connection.setRequestProperty("Referer", "https://www.douban.com/group/" + AREA_GROUP + "/discussion?start=25");
                connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36");
                //连接
                connection.connect();
                //得到响应码
                int responseCode = connection.getResponseCode();
                System.out.println("code:" + responseCode);
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
                        try {
                            Thread.sleep(1000);
                            String tempUrlStr = m.group(1);
                            System.out.println("当前链接：" + tempUrlStr);
                            URL tempUrl = new URL(tempUrlStr);
                            HttpURLConnection tempConnection = (HttpURLConnection)tempUrl.openConnection();
                            //设置请求方式
                            tempConnection.setRequestMethod("GET");
                            // 10秒超时
                            tempConnection.setConnectTimeout(10000);
                            tempConnection.setReadTimeout(10000);
                            tempConnection.setRequestProperty("Cookie", "ll=\"118282\"; bid=QJabbJe5cJU; gr_user_id=57895035-6456-44cc-a6b2-52657bf1efb5; _vwo_uuid_v2=D829068CC51F524D66B60D86ACFE0B2F7|5e0e6d2dbf05b205c7ca067a2b8d4124; __yadk_uid=6EHhhCeyGQ9G5OPZjJWkwsnqP8GHqip0; __gads=ID=8d589df0a0dbfb11:T=1569207315:S=ALNI_MZ2-HVHG_6q51jW3mxlChUdFInKyg; viewed=\"27034717_10426640_27038538_26373138_24700704_3897837\"; douban-fav-remind=1; __utmv=30149280.20187; __utmz=30149280.1571969966.12.5.utmcsr=baidu|utmccn=(organic)|utmcmd=organic; _pk_ref.100001.8cb4=%5B%22%22%2C%22%22%2C1574675295%2C%22https%3A%2F%2Fwww.baidu.com%2Flink%3Furl%3Dia2qvkDq_wTrgbZbTg7lVGfraG9CuaUteatN7VR_uN5t2eoEYVtLepopV3zD429ZxNH7Uvztvt0YpBETwkxd5K%26wd%3D%26eqid%3Da97df2df00137cc9000000065db25b6c%22%5D; _pk_ses.100001.8cb4=*; __utma=30149280.1080090324.1567999882.1574327131.1574675296.15; __utmc=30149280; __utmt=1; ck=k1U4; ap_v=0,6.0; push_noty_num=0; push_doumail_num=0; _pk_id.100001.8cb4=1a08abb4a33a9ed6.1568107009.13.1574675361.1574327130.; __utmb=30149280.5.10.1574675296");
                            tempConnection.setRequestProperty("Host", "www.douban.com");
                            tempConnection.setRequestProperty("Referer", "https://www.douban.com/group/" + AREA_GROUP + "/discussion?start=25");
                            tempConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36");
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
                            count++;
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
            if (count > 1000) break;
        }
    }
}
