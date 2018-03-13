# jiajuwulian
20180313
增加Android项目，开始Android作为用户客户端的登录
20180312
在手机上实现设备的模型，服务器仅做转发及管理业务

20180309
正式加入JSON，开始使用JSON来处理通信，
定义了YongHu，Jiedian两个类，还要定义Shebei，在SheBei下要有Diandeng,LED等子类
要写一个后台程序，用于添加和绑定设备，或者用户界面，来添加或绑定设备

开始使用JSON来整理通信，类似http协议
客户端连接到服务器后，发送{"QingQiu":"DengLu","WoShi":"xxx"}来登录服务器
计划加入一个Android客户端项目，进行前后台的同步开发
20180302
开始Json格式数据的处理,
可以参考Json-java的内容，学习使用Json
考虑到Json可以嵌套，所以，较为复杂
另外了解到socket是在tcp上做了边界，应该每一次socketwrite操作发出的是一个包
udp是带边界的发包
所以在通信中不用考虑边界问题，
20180228
我的物联网基础项目，
使用json进行通信，完整的项目包括java服务器，mysql，android转发，蓝牙转433，433到设备。

