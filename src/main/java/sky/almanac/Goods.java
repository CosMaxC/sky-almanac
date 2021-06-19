package sky.almanac;

import lombok.Data;

/**
 * @program: sky-almanac
 * @description: 内容信息
 * @author: Cosmax C
 * @create: 2021-06-18 16:33
 **/
@Data
public class Goods {

    private String name;
    private String good;
    private String bad;
    private Boolean isWeekend;


    public Goods(String name, String good, String bad, boolean isWeekend) {
        this.name = name;
        this.good = good;
        this.bad = bad;
        this.isWeekend = isWeekend;
    }
}
