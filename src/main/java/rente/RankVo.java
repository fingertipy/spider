package rente;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Description
 * @Author dayu
 * @Date 2019/11/11 14:12
 * @Version v1.0
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RankVo implements Comparable<RankVo>{
    private int preg;
    private int cred;

    @Override
    public int compareTo(RankVo rankVo) {
        return rankVo.getCred() - this.cred;
    }
}
