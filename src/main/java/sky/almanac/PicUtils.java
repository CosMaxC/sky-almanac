package sky.almanac;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @program: sky-almanac
 * @description: 图片处理
 * @author: Cosmax C
 * @create: 2021-06-19 15:43
 **/
public class PicUtils {

    /**
     * 绘制黄历
     * @param todayInfo 今日信息
     * @param targetPath 目标目录
     */
    public static void drawHuangLi(TodayInfo todayInfo, String targetPath) {
        // 画图

        //获取图片的宽 固定
        int srcImgWidth = 648;

        //获取图片的高
        // 高60
        int titleHeight = 0;
        // 高 166-106 = 60
        int dateHeight = titleHeight + 60;
        // 中间高度
        int limit = 15;
        int luckMiddle = getImageHeight(todayInfo, limit, true);
        int unluckMiddle = getImageHeight(todayInfo, limit, false);
        int middle = dateHeight + 100;
        // 剩下高度 30 饮品 30 方位 30 大佬 中间 10 缝隙
        int bottomHeight = middle + luckMiddle + unluckMiddle;
        int srcImgHeight = bottomHeight + 160;

        // 加文字
        BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = bufImg.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        printGraph(graphics2D, 60, 100, luckMiddle, unluckMiddle, srcImgHeight, srcImgWidth, todayInfo, limit);

        // 保存
        outPutImage(targetPath, bufImg, "jpg");
    }

    /**
     * 输出图片
     * @param targetImgPath 目标位置
     * @param bufImg 图片字符流
     * @param suffix 后缀
     */
    private static void outPutImage(String targetImgPath, BufferedImage bufImg, String suffix) {
        FileOutputStream outImgStream = null;
        try {
            outImgStream = new FileOutputStream(targetImgPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (outImgStream == null) {
            return;
        }
        if (suffix == null || "".equals(suffix)) {
            suffix = "jpg";
        }
        try {
            ImageIO.write(bufImg, suffix, outImgStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("添加文字完成");
        try {
            outImgStream.flush();
            outImgStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加文字到图片
     * @param content 文字内容
     * @param x x轴
     * @param y y轴
     * @param color 颜色对象
     * @param font 字体对象
     * @param graphics2D 操作对象
     */
    private static void addText2Pic(String content, int x, int y, Color color, Font font, Graphics2D graphics2D) {

        //根据图片的背景设置水印颜色
        graphics2D.setColor(color);
        //设置字体
        graphics2D.setFont(font);
        //画出水印
        graphics2D.drawString(content, x, y);
//        graphics2D.dispose();
    }

    /**
     * 生成运气text
     * @param goodsList 运气list
     * @param graphics2D graphics2D
     * @param x x轴
     * @param y y轴
     * @param limit 每行限制字数
     * @param isLuck 宜|不宜
     */
    private static void addLuckText(List<Goods> goodsList, Graphics2D graphics2D, int x, int y, int limit, boolean isLuck) {
        AtomicInteger height = new AtomicInteger(y);
        goodsList.forEach(goods -> {
            String name = goods.getName();
            String luck;
            if (isLuck) {
                luck = goods.getGood();
            } else {
                luck = goods.getBad();
            }

            addText2Pic(
                    name,
                    x,
                    height.getAndAdd(30),
                    new Color(68, 68, 68, 255),
                    new Font("微软雅黑", Font.BOLD, 30),
                    graphics2D);
            height.addAndGet(5);
            addTextWithSwitch(graphics2D, luck, height, x, limit, 25, "微软雅黑", Font.PLAIN, new Color(119, 140, 145, 255));
            height.addAndGet(40);
        });
    }

    /**
     * 换行输出
     * @param graphics2D graphics2D
     * @param text 字符串
     * @param height 高度
     * @param weight 宽度
     * @param limit 一行极限
     * @param size 字体大小
     * @param fontName 字体名
     * @param style 字体类型
     */
    private static void addTextWithSwitch(Graphics2D graphics2D, String text, AtomicInteger height, int weight, int limit, int size, String fontName, int style, Color color) {
        limit -= 1;
        for (int i = 0; i < text.length(); i++) {
            int j = (i + limit) % limit;
            if (j == limit - 1) {
                addText2Pic(
                        text.charAt(i) + "",
                        weight + j * size,
                        height.getAndAdd(size),
                        color,
                        new Font(fontName, style, size),
                        graphics2D);
            } else {
                addText2Pic(
                        text.charAt(i) + "",
                        weight + j * size,
                        height.get(),
                        color,
                        new Font(fontName, style, size),
                        graphics2D);
            }
        }
    }

    /**
     * 将文字放在图片居中
     * @param gX 图片x
     * @param gY 图片y
     * @param gWidth 图片宽度
     * @param gHeight 图片高度
     * @param text 文本
     * @param font 字体
     * @param color 颜色
     * @param graphics2D graphics2D
     */
    private static void addMiddleTextToGraph(int gX, int gY, int gWidth, int gHeight, String text, Font font, Color color, Graphics2D graphics2D) {
        // 计算文字长度，计算居中的x点坐标
        FontMetrics fm = graphics2D.getFontMetrics(font);
        int textWidth = fm.stringWidth(text);
        int stringAscent = fm.getAscent();
        int stringDescent = fm.getDescent ();

        addText2Pic(text, (gX + gWidth - textWidth) / 2, gY + gHeight / 2 + (stringAscent - stringDescent) / 2, color, font, graphics2D);
    }

    /**
     * 绘制基本图案
     * @param graphics2D graphics2D
     * @param titleHeight 标题高度
     * @param dateHeight 日期高度
     * @param luckMiddle 宜 高度
     * @param unluckMiddle 不宜 高度
     * @param srcImgHeight 图片高度
     * @param srcImgWidth 图片宽度
     * @param todayInfo 今日信息
     * @param limit 限制字数
     */
    private static void printGraph(Graphics2D graphics2D, int titleHeight, int dateHeight, int luckMiddle, int unluckMiddle, int srcImgHeight, int srcImgWidth, TodayInfo todayInfo, int limit) {
        int height = 0;

        // 背景色
        graphics2D.setColor(new Color(255, 255, 255, 255));
        graphics2D.fillRect(0, 0, srcImgWidth, srcImgHeight);

        // 标题
        graphics2D.setColor(new Color(85, 85, 85, 255));
        graphics2D.fillRect(0, height, srcImgWidth, titleHeight);
        addText2Pic(
                "光之子黄历",
                8,
                46,
                new Color(187,187,187,255),
                new Font("微软雅黑",Font.BOLD,36),
                graphics2D);
        height += titleHeight;

        // 日期
        addMiddleTextToGraph(0,
                height,
                srcImgWidth,
                dateHeight,
                todayInfo.getDate(),
                new Font("微软雅黑",Font.BOLD,47),
                new Color(0,0,0,255),
                graphics2D);
        height += dateHeight;

        // 通用左侧
        int commonLuckLeftWidth = srcImgWidth / 3;
        // 宜
        // 左侧
        graphics2D.setColor(new Color(255,238,68, 255));
        graphics2D.fillRect(0, height, commonLuckLeftWidth, luckMiddle);
        addMiddleTextToGraph(0,
                height,
                commonLuckLeftWidth,
                luckMiddle,
                "宜",
                new Font("微软雅黑",Font.BOLD,80),
                new Color(0,0,0,255),
                graphics2D);

        // 右侧
        graphics2D.setColor(new Color(255, 255, 170, 255));
        graphics2D.fillRect(commonLuckLeftWidth, height, srcImgWidth - commonLuckLeftWidth, luckMiddle);
        // 获取运气数据
        addLuckText(todayInfo.getGoodList(), graphics2D, commonLuckLeftWidth + 20, height + 45, limit, true);
        height += luckMiddle;

        // 不宜
        graphics2D.setColor(new Color(255, 68, 68, 255));
        graphics2D.fillRect(0, height, commonLuckLeftWidth, unluckMiddle);
        addMiddleTextToGraph(0,
                height,
                commonLuckLeftWidth,
                unluckMiddle,
                "不宜",
                new Font("微软雅黑",Font.BOLD,80),
                new Color(255,255,255,255),
                graphics2D);
        // 右侧
        graphics2D.setColor(new Color(255, 221, 211, 255));
        graphics2D.fillRect(commonLuckLeftWidth, height, srcImgWidth - commonLuckLeftWidth, unluckMiddle);
        // 获取运气数据
        addLuckText(todayInfo.getBadList(), graphics2D, commonLuckLeftWidth + 20, height + 45, limit, false);


        height += unluckMiddle + 40;
        int marginLeft = 15;
        addText2Pic(
                "座位朝向:",
                marginLeft,
                height,
                new Color(0,0,0,255),
                new Font("微软雅黑",Font.BOLD,30),
                graphics2D);
        // 方向
        addText2Pic(
                "面向",
                marginLeft + 5 * 30,
                height,
                new Color(0,0,0,255),
                new Font("微软雅黑",Font.PLAIN,30),
                graphics2D);
        String situation = todayInfo.getSituation();
        addText2Pic(
                situation,
                marginLeft + 7 * 30,
                height,
                new Color(68,170,68,255),
                new Font("微软雅黑",Font.BOLD,30),
                graphics2D);
        addText2Pic(
                "玩游戏，黑屏最少。",
                marginLeft + 7 * 30 + situation.length() * 30,
                height,
                new Color(0,0,0,255),
                new Font("微软雅黑",Font.PLAIN,30),
                graphics2D);

        height += 50;

        addText2Pic(
                "今日宜饮:",
                marginLeft,
                height,
                new Color(0,0,0,255),
                new Font("微软雅黑",Font.BOLD,30),
                graphics2D);
        // 饮料
        addText2Pic(
                todayInfo.getDrink(),
                marginLeft + 30 * 5,
                height,
                new Color(0,0,0,255),
                new Font("微软雅黑",Font.PLAIN,30),
                graphics2D);

        height += 50;
        addText2Pic(
                "大佬亲近指数:",
                marginLeft,
                height,
                new Color(0,0,0,255),
                new Font("微软雅黑",Font.BOLD,30),
                graphics2D);
        // 星星
        addText2Pic(
                todayInfo.getStars(),
                marginLeft + 30 * 7,
                height,
                new Color(255,136,119,255),
                new Font("微软雅黑",Font.BOLD,30),
                graphics2D);


    }

    /**
     * 获取图片高度
     * @param todayInfo 今日实体
     * @param limit 一行限制
     * @param isluck 宜|不宜
     * @return 高度
     */
    private static int getImageHeight(TodayInfo todayInfo, int limit, boolean isluck) {
        AtomicInteger height = new AtomicInteger();
        java.util.List<Goods> goodList = todayInfo.getGoodList();
        List<Goods> badList = todayInfo.getBadList();
        height.addAndGet(80);
        if (isluck) {
            goodList.forEach(goods -> {
                height.addAndGet(30);
                height.addAndGet(5);
                height.addAndGet(((goods.getGood().length() / limit) + 1) * 25);
            });
        } else {
            badList.forEach(bad -> {
                height.addAndGet(30);
                height.addAndGet(5);
                height.addAndGet(((bad.getBad().length() / limit) + 1) * 25);
            });
        }

        return height.get();
    }
}
