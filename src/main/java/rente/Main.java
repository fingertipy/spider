package rente;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Description
 * @Author dayu
 * @Date 2019/11/11 14:22
 * @Version v1.0
 */
public class Main {
    public static void main(String[] args) {
        test2();
    }

    public static void test1(){
        List<RankVo> list = new ArrayList<RankVo>();
        list.add(new RankVo(0, 2));
        list.add(new RankVo(0, 1));
        list.add(new RankVo(0, 3));
        Collections.sort(list);
        list.forEach(item -> System.out.println(item));
    }

    public static void test2(){
        List<RankVo> list = new ArrayList<RankVo>();
        list.add(new RankVo(2, 0));
        list.add(new RankVo(1, 0));
        list.add(new RankVo(3, 0));
        Collections.sort(list);
        list.forEach(item -> System.out.println(item));
    }
}
