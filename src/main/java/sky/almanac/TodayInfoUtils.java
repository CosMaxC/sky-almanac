package sky.almanac;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.List;

/**
 * @program: sky-almanac
 * @description: 获取今日兴运工具类
 * @author: Cosmax C
 * @create: 2021-06-18 16:45
 **/
public class TodayInfoUtils {

    /**
     * 字符串转md5
     * @param str 字符
     * @return md5
     */
    private static String strToMD5(String str) {
        //返回实现指定摘要算法的 MessageDigest 对象。
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("md5");
        } catch (NoSuchAlgorithmException e) {
            return "";
        }
        //先将字符串转换成byte数组，再用byte 数组更新摘要
        md5.update(str.getBytes());
        //哈希计算，即加密
        byte[] nStr = md5.digest();
        //加密的结果是byte数组，将byte数组转换成字符串
        return bytes2Hex(nStr);
    }

    /**
     * 字节转字符串
     * @param bts bytes数组
     * @return 字符串
     */
    private static String bytes2Hex(byte[] bts) {
        StringBuilder des = new StringBuilder();
        String tmp;

        for (byte bt : bts) {
            tmp = (Integer.toHexString(bt & 0xFF));
            if (tmp.length() == 1) {
                des.append("0");
            }
            des.append(tmp);
        }
        return des.toString();
    }

    /**
     * 字符串转long
     * @param string 字符串
     * @return long
     */
    private static long longHash(String string) {
        long h = 314159265L;
        int l = string.length();
        char[] chars = string.toCharArray();

        for (int i = 0; i < l; i++) {
            h = 31*h + chars[i];
        }

        //负数转正数
        if (h < 0) {
           h = Math.abs(h);
        }
        return h;
    }

    /**
     * 字符串转base64
     * @param str 字符串
     * @return base64
     */
    private static String strToBase64(String str) {
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] textByte;
        textByte = str.getBytes(StandardCharsets.UTF_8);
        return encoder.encodeToString(textByte);
    }

    /**
     * 字符串编码long
     * @param str 字符串
     * @return long
     */
    private static long strToLong(String str) {
        return longHash(strToBase64(strToMD5(str)));
    }

    /**
     * 根据用户名获取当日黄历
     * @param userName 用户名
     * @return 黄历信息
     */
    public static TodayInfo getTodayInfoByUserName(String userName){

        return getLuckInfoToToday(userName);
    }

    /**
     * “随机”都是伪随机概念，以当前的天为+用户名生成的id种子。
     * @param dayUserSeed 用户日期seed
     * @param indexSeed 索引seed
     * @return 随机数
     */
    private static int random(long dayUserSeed, long indexSeed) {
        long n = dayUserSeed % 11117L;
        for (long i = 0; i < 100 + indexSeed; i++) {
            n = n * n;
            n = n % 11117L;
        }
        return (int)n;
    }

    /**
     * 获取运气信息
     * @param userName 用户名
     * @return 实体
     */
    private static TodayInfo getLuckInfoToToday(String userName) {
        // TODO: 2021/6/19 获取游戏内容
        GoodsConfiguration goodsConfiguration = new GoodsConfiguration();

        // 用户seed
        long userSeed = strToLong(strToBase64(strToMD5(userName)));

        // 获取日期
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH) + 1;
        int day = now.get(Calendar.DATE);
        int week = now.get(Calendar.DAY_OF_WEEK);

        // 判断周末
        boolean isWeekend = week == Calendar.SATURDAY || week == Calendar.SUNDAY;

        // 生成种子
        long dayUserSeed = 10000 * year + 100 * month + day + userSeed;

        TodayInfo todayInfo = new TodayInfo();
        List<Goods> goods = filterWeekend(goodsConfiguration.getGoodList(), isWeekend);

        int numGood = random(dayUserSeed, 98) % 3 + 2;
        int numBad = random(dayUserSeed, 87) % 3 + 2;
        List<Goods> goodList = new ArrayList<>();
        List<Goods> badList = new ArrayList<>();

        // 随机获取几个
        List<Goods> eventList = pickRandomActivity(goods, numGood + numBad, dayUserSeed, goodsConfiguration.getToolList());
        for (int i = 0; i < numGood; i++) {
            goodList.add(eventList.get(i));
        }
        for (int i = 0; i < numBad; i++) {
            badList.add(eventList.get(numGood + i));
        }
        todayInfo.setGoodList(goodList);
        todayInfo.setBadList(badList);

        // 获取方向
        todayInfo.setSituation(getSituation(dayUserSeed, goodsConfiguration.getSituationList()));

        // 获取饮料
        todayInfo.setDrink(getDrink(dayUserSeed, goodsConfiguration.getDrinkList()));

        // 获取星星
        todayInfo.setStars(getStars(dayUserSeed));

        // 返回日期信息
        todayInfo.setDate(getDateStr(year, month, day, week));
        return todayInfo;
    }

    /**
     * 获取年月日 星期
     * @param year 年
     * @param month 月
     * @param day 日
     * @param week 周几
     * @return 今天是xx年xx月xx日 星期x
     */
    private static String getDateStr(int year, int month, int day, int week) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        String weekDay = weekDays[week - 1];
        return "今天是"+ year +"年"+ month +"月"+ day +"日 " + weekDay;


    }

    /**
     * 获取星星
     * @param dayUserSeed 种子
     * @return 星星字符串
     */
    private static String getStars(long dayUserSeed) {
        
        return star(random(dayUserSeed, 6) % 5 + 1);
    }

    /**
     * 通过个数获取星星字符串
     * @param num 个数
     * @return 字符串
     */
    private static String star(int num) {
        StringBuilder result = new StringBuilder();
        int i = 0;
        while (i < num) {
            result.append("★");
            i ++;
        }
        while (i < 5) {
            result.append("☆");
            i ++;
        }
        return result.toString();
    }

    /**
     * 获取饮料
     * @param dayUserSeed 种子
     * @param drinkList 饮料list
     * @return 饮料字符串
     */
    private static String getDrink(long dayUserSeed, List<String> drinkList) {
        List<String> list = pickRandom(drinkList, 2, dayUserSeed);
        return list.get(0) + ", " + list.get(1);
    }

    /**
     * 获取方位
     * @param dayUserSeed 种子
     * @param situationList 方位list
     * @return 方位字符串
     */
    private static String getSituation(long dayUserSeed, List<String> situationList) {
        return situationList.get(random(dayUserSeed, 2) % situationList.size());
    }

    /**
     * 从 activities 中随机挑选 size 个
     * @param activities 事件集
     * @param size 个数
     * @param dayUserSeed 种子
     * @param toolList 工具list
     * @return list
     */
    private static List<Goods> pickRandomActivity(List<Goods> activities, int size, long dayUserSeed, List<String> toolList) {
        List<Goods> pickedEvents = pickRandom(activities, size, dayUserSeed);
        List<Goods> resultEvents = new ArrayList<>();
        pickedEvents.forEach(pickedEvent -> {
            pickedEvent.setName(parse(pickedEvent.getName(), dayUserSeed, toolList));
            resultEvents.add(pickedEvent);
        });
        return resultEvents;
    }

    /**
     * 随机挑选size个
     * @param activities 事件集
     * @param size 个数
     * @param dayUserSeed 种子
     * @return list
     */
    private static <T> List<T> pickRandom(List<T> activities, int size, long dayUserSeed) {
        List<T> result = new ArrayList<>(activities);
        for (int j = 0; j < result.size() - size; j++) {
            int index = random(dayUserSeed, j) % result.size();
            result.remove(index);
        }
        return result;
    }

    /**
     * 占位符转换
     * @param name 名称
     * @param dayUserSeed 种子
     * @param toolList 工具list
     * @return 转换结果
     */
    private static String parse(String name, long dayUserSeed, List<String> toolList) {

        if (name.contains("%t")) {
            name = name.replaceAll("%t", toolList.get(random(dayUserSeed, 11) % toolList.size()));
        }
        return name;
    }

    /**
     * 过滤周末内容
     * @param isWeekend 是否周末
     * @return 过滤内容
     */
    private static List<Goods> filterWeekend(List<Goods> goodsList, boolean isWeekend) {
        if (isWeekend) {
            List<Goods> result = new ArrayList<>();
            goodsList.forEach(goods -> {
                if (goods.getIsWeekend()) {
                    result.add(goods);
                }
            });
            return result;
        }
        return goodsList;
    }

}
