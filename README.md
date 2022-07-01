# ShoutInvite
BungeeCord喊话邀请插件

## 功能

### 发送公告消息

可以设置公告的间隔，设置看不到公告的服务器

目前支持按顺序发送

### 喊话邀请
可在配置文件中配置消息

支持冷却时间、过期时间

自定义服务器名、自定义看不到喊话消息的服务器

## 指令
用法： /hh <消息>

/hhpb 屏蔽所有喊话消息（除了自己的）

/shoutinvite reload 重载配置文件

## 变量
需要LiteBans前置

查询7天内被后台封禁的数量（%litebans_bans_7_console%）和被玩家封禁的数量（%litebans_bans_7_player%）

## 展望未来
计划支持数据库（鸽了）
