package sky.almanac;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @program: sky-almanac
 * @description: goods启动设置
 * @author: Cosmax C
 * @create: 2021-06-18 23:29
 **/

@Data
public class GoodsConfiguration {
    // TODO: 2021/6/19 转私有方法

    private static List<Goods> goodsList;

    private static List<String> drinkList;

    private static List<String> situationList;

    private static List<String> toolList;

    public GoodsConfiguration() {

        goodsList = getGoodList();

        drinkList = getDrinkList();

        situationList = getSituationList();

        toolList = getToolList();

    }

    public List<String> getToolList() {
        return Arrays.asList("手", "脚", "安卓", "iOS", "网抑云游戏");

    }

    public List<String> getSituationList() {
        return Arrays.asList("北方","东北方","东方","东南方","南方","西南方","西方","西北方");
    }

    public List<String> getDrinkList() {
        return Arrays.asList("水","茶","红茶","绿茶","咖啡","奶茶","可乐","鲜奶","白啤酒","黑啤酒","果味汽水","苏打水","运动饮料","酸奶","酒");

    }

    /**
     * 获取初始事件list
     * @return list
     */
    public List<Goods> getGoodList() {
        List<Goods> goods = new ArrayList<>();
        goods.add(new Goods("带萌新", "萌新二话不说给你掏蜡烛", "11翼猛新送你重生", false));
        goods.add(new Goods("驯龙", "皮皮虾见你就跑", "5条皮皮虾抱着你一顿狂亲", false));
        goods.add(new Goods("遇境挂机", "听着琴声入睡", "7个小黑在你面前叭叭叭", false));
        goods.add(new Goods("去四龙图", "花全炸了", "遇上铁打的花和打铁的皮皮虾", false));
        goods.add(new Goods("献祭", "嗖的一下把石像全点了，很快嗷~", "你还没进门就倒下了~", true));
        goods.add(new Goods("白天跑图", "今天白天跑图无串线", "八人门一个人也没有", false));
        goods.add(new Goods("白天练琴", "周围没人打扰你~", "一个水母叫的小黑把你带走", false));
        goods.add(new Goods("晚上练琴", "你弹起来真好听~", "一个小黑拿起屁琴和你对线", false));
        goods.add(new Goods("处cp", "你的cp对你一心一意", "你的cp还想找亿个", false));
        goods.add(new Goods("看攻略", "你一次就习得技巧", "尝试了亿次才发现bug已修复~", false));
        goods.add(new Goods("装萌新", "好心的巨佬带你跑完全图", "云野的方丈一眼就看出你不是人~大威天龙！", false));
        goods.add(new Goods("找小金人", "收了小金人还白嫖了一颗心", "收1个裂4个", false));
        goods.add(new Goods("去办公室", "饮茶先啦~", "小蓝在你眼前炫耀她的蓝色斗篷~", false));
        goods.add(new Goods("找先祖", "先祖挥手示意你过来~", "先祖都下班了", true));
        goods.add(new Goods("在雨林挂机", "动听的音乐让你进入甜美的梦乡", "您失去了所有的星光...", true));
        goods.add(new Goods("扔纸船", "百万光之子觉得很赞>_<", "扔出去的纸船如泼出去的水，找也找不到~", true));
        goods.add(new Goods("去沉船图", "黑屏都拦不住你，我说的！", "黑屏了吧，叫你别去~", true));
        goods.add(new Goods("互心", "", "", false));
        goods.add(new Goods("互火", "一颗心从天儿降！", "你的好友今天不上线！", false));
        goods.add(new Goods("用%t玩光遇", "闪退几率最低", "呀，遇境也串线了！", false));
        goods.add(new Goods("找崽崽", "今天买一送一", "", false));
        goods.add(new Goods("找监护", "买监护送CP", "", false));
        goods.add(new Goods("吃体塑", "声控游戏，你将有如神助", "谢谢你，陌生人~", true));
        goods.add(new Goods("晚上跑图", "大佬带你躺平", "你带了7个人串了49次线~", false));
        goods.add(new Goods("打卡景点", "月色真美，风也很温柔", "人好多，请有序排队入场", false));
        goods.add(new Goods("爬雪隐峰", "享受美好时光~", "张东升约你去爬山~", false));
        goods.add(new Goods("跑图", "", "", false));
        goods.add(new Goods("上微博", "小光说今天送蜡烛！", "今天要维护呢！", true));
        goods.add(new Goods("上AB站", "还需要理由吗？", "满屏兄贵亮瞎你的眼", true));
        goods.add(new Goods("拍视频", "奥斯卡向你抛来橄榄枝~", "路人小黑获得最佳主角奖~", true));
        return goods;
    }


}
