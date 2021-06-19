# sky-almanac
一个基于java的光遇老黄历，界面参考以及seed参数伪代码生成算法来源于[ziyuguoguo/Laohuangli-1](https://github.com/ziyuguoguo/Laohuangli-1)

## 目录
- [背景](#背景)
- [使用方法](#使用方法)
- [文件结构](#文件结构)
- [实现效果](#实现效果)
- [参考](#参考)
- [许可证](#许可证)

## 背景
奇怪的光遇迷，最近看到好多游戏有黄历，顺手模仿制作一个生成黄历图片的小工具。
## 使用方法
```
   // 通过用户名生成一个黄历实体
   TodayInfo demoName = TodayInfoUtils.getTodayInfoByUserName("YOUT USERNAME");
   // 设置保存路径， 默认在class下的demo.jpg
   String path = Objects.requireNonNull(HelloPicDemoApplication.class.getClassLoader().getResource("")).getPath();
   path += "/demo.jpg";
   // 导出图片
   PicUtils.drawHuangLi(demoName, path);
```

## 文件结构
光遇老黄历
```
sky-almanac
│  .gitignore
│  pom.xml
│  README.md
└─src
   └─main
     └─java
     │  └─sky
     │      └─almanac
     │              Goods.java                         --- 黄历内容信息实体
     │              GoodsConfiguration.java            --- 黄历基本信息配置
     │              HelloPicDemoApplication.java       --- 黄历启动测试类
     │              PicUtils.java                      --- 黄历图片工具类
     │              TodayInfo.java                     --- 黄历日期对应实体
     │              TodayInfoUtils.java                --- 黄历日期实体工具类
     │
     └─resources
```
## 实现效果
![](./example/demo.jpg)

## 参考
* [ziyuguoguo/Laohuangli-1](https://github.com/ziyuguoguo/Laohuangli-1)

## 许可证
* [MIT License](./LICENSE)
