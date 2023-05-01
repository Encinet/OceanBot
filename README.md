# OceanBot

## ~~结构~~ (已过时)

event 为游戏事件

common 为游戏QQ公用功能

QQ 为QQ专用(比如QQ事件 QQ自动对话)

until 为工具

```tree
└── src
    └── main
        ├── java
        │   ├── org
        │   │   └── encinet
        │   │       └── oceanbot
        │   │           ├── OceanBot.java 启动点
        │   │           ├── QQ
        │   │           │   ├── Core.java Mirai控制中心
        │   │           │   ├── consciousness 自动对话
        │   │           │   │   ├── AI 机器学习语言对话
        │   │           │   │   │   ├── AI.java
        │   │           │   │   │   └── Memory.java
        │   │           │   │   ├── CS.java 自动对话获取
        │   │           │   │   └── response 固定语言对话
        │   │           │   │       ├── Audio.java 音频
        │   │           │   │       └── Text.java 文字
        │   │           │   └── event QQ事件
        │   │           │       ├── Friend.java 好友
        │   │           │       ├── Group.java 群聊(包括群临时对话)
        │   │           │       └── Other.java 其他
        │   │           ├── command mc命令
        │   │           │   ├── MCCommand.java 对接Ocean命令
        │   │           │   ├── Maintenance.java 维护模式命令
        │   │           │   └── Sign.java 签到命令
        │   │           ├── common 通用
        │   │           │   ├── Function.java.old 旧版命令系统
        │   │           │   └── occommand 新版Ocean命令
        │   │           │       ├── BasicCommand.java 命令基础类
        │   │           │       ├── OcCommand.java 命令处理
        │   │           │       └── commands 一堆命令
        │   │           │           ├── banlist.java
        │   │           │           ├── bind.java
        │   │           │           ├── exec.java
        │   │           │           ├── group.java
        │   │           │           ├── help.java
        │   │           │           ├── info.java
        │   │           │           ├── list.java
        │   │           │           └── whois.java
        │   │           ├── event mc事件
        │   │           │   ├── PlayerLogin.java 玩家登陆
        │   │           │   ├── PlayerMessage.java 玩家信息
        │   │           │   └── PlayerNum.java 玩家数量
        │   │           ├── file 文件
        │   │           │   ├── Config.java 配置文件
        │   │           │   └── Whitelist.java
        │   │           └── until 工具
        │   │               ├── HttpUnit.java 网络工具
        │   │               ├── Money.java 经济控制
        │   │               ├── Process.java 处理
        │   │               ├── QQCommandSender.java 命令发送者
        │   │               ├── QQUntil.java QQ工具
        │   │               ├── TextUntil.java 文本工具
        │   │               ├── TopList.java 基础排行榜
        │   │               ├── Verify.java 验证码
        │   │               └── record 记录
        │   │                   ├── BindData.java 绑定数据
        │   │                   └── Data.java 验证码数据
        │   └── top
        │       └── iseason
        │           └── bukkittemplate
        │               ├── BukkitPlugin.java
        │               ├── BukkitTemplate.java
        │               ├── DisableHook.java
        │               ├── PluginBootStrap.java
        │               ├── ReflectionUtil.java
        │               ├── dependency
        │               │   ├── DependencyDownloader.java
        │               │   ├── PluginDependency.java
        │               │   └── XmlParser.java
        │               └── loader
        │                   └── IsolatedClassLoader.java
        └── resources
            ├── config.yml
            └── plugin.yml
```