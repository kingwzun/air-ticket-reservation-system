# air-ticket-reservation-system
机票预订系统后端
# [浏览器前端链接](https://github.com/kingwzun/air-ticket-reservation-system-pc)
# [安卓，小程序前端链接](https://github.com/kingwzun/air-ticket-reservation-system-uniapp)
## 软件开发环境
设备	配置
操作系统	Windows 11 专业版
数据库
消息队列	Mysql、Redis
RabbitMQ
开发工具	IDEA、HBuilderX
浏览器	Chrome
后端框架	SpringBoot
前端框架	Vue、UniApp
Web服务器	Tomcat8
分辨率	1920*1080、2560*1600

# 需求分析
## 原始功能需求
分为管理员、客户、订票代理三种角色
管理员细分为运营部管理员和系统管理员，包含：用户管理、航班查询、业绩管理、航线信息管理、舱位信息管理、会员服务管理、公告管理、员工管理、订票代理管理和统计分析（出票率、上座率）等功能。
客户细分为客户和会员客户，包含：个人信息管理、航班查询、历史购票记录查询、购票管理、退票管理、托运行李、飞机升舱、购买保险、快捷购买返程票、学生认证、身份认证、会员服务和统计分析等功能。
订票代理包含：代理信息管理、航班查询、业绩管理、客户购票管理、客户保险管理、客户升舱管理、客户托运行李管理、客户退票管理和统计分析等功能。
注意：在购票时需要注意，同一航班机票有头等舱与经济舱区别，价格也要分别设置；在购票时直接选择座位；每种飞机类型座位数量也不会相同，需要分别设置；统计做成报表形式（根据情况使用折线图、饼图、柱状图等）。
## 用例图
管理员角色系统用例图
![image](https://github.com/kingwzun/air-ticket-reservation-system/assets/75526768/2d279f8a-38b7-40fe-9627-203617d7b0c9)

客户角色系统用例图
![image](https://github.com/kingwzun/air-ticket-reservation-system/assets/75526768/09b600ec-4b1e-488a-bc68-d16641a1328a)
订票代理角色系统用例图
![image](https://github.com/kingwzun/air-ticket-reservation-system/assets/75526768/450e1af6-e530-43ea-a072-fd992552a87c)
# 系统设计
## 功能模块划分
根据需求分析，设计出系统划分为人员管理模块、航行管理模块、飞机管理模块、订单管理模块、公告管理模块、统计模块。模块结构图如图2-2所示。
图2-2 机票预定系统模块结构图
![image](https://github.com/kingwzun/air-ticket-reservation-system/assets/75526768/416184ae-5b18-46ac-9236-78405a862c6d)
## 实体类的设计
共设计实体类18个，实体类图如图3-1、3-2所示。
![image](https://github.com/kingwzun/air-ticket-reservation-system/assets/75526768/f921a40c-bdb9-4e80-a182-6eeab8249621)
![image](https://github.com/kingwzun/air-ticket-reservation-system/assets/75526768/65b306be-cebe-4100-9a1d-015cc17d9144)
## 数据库设计
数据库关系图如图4-1所示。
![image](https://github.com/kingwzun/air-ticket-reservation-system/assets/75526768/c98b462c-9940-40b9-a848-aa235d831aea)
