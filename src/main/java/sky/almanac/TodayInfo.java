package sky.almanac;

import lombok.Data;

import java.util.List;

/**
 * @program: sky-almanac
 * @description: 今日情况
 * @author: Cosmax C
 * @create: 2021-06-18 16:35
 **/

@Data
public class TodayInfo {

    /**
     * 宜list
     */
    private List<Goods> goodList;

    /**
     * 不宜list
     */
    private List<Goods> badList;

    /**
     * 方位
     */
    private String situation;

    /**
     *  饮料
     */
    private String drink;

    /**
     * 星星
     */
    private String stars;

    /**
     * 日期
     */
    private String date;
}
