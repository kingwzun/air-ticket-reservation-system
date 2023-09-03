package com.atrs.airticketreservationsystem.common;

public class SystemConstants {
    //默认密码
    public static final String DEFAULT_PASSWORD = "123123";
    //默认代理等级
    public static final Integer DEFAULT_LEVEL = 0;
    //默认航班状态-未起飞
    public static final Integer DEFAULT_STATUS_FLIGHT = 0;
    //用户已经使用了该票
    public static final Integer IS_USE_TICKET = 1;
    //最晚退票时间
    public static final Integer LATEST_CANCEL = 3;
    //默认账号注册状态
    public static final String DEFAULT_REGISTER_ACCOUNT_STATUS = "0";
    //默认用户vip等级
    public static final Long DEFAULT_ACCOUNT_VIP_LEVEL = 0L;
    public static final Double DEFAULT_ACCOUNT_TOTAL_EXPENSES = 0.0;
    //账号封禁
    public static final String ACCOUNT_STATUS_BAN = "0";
    //默认使用飞机时间
    public static final Long DEFAULT_SERVICE_YEARS = 0L;
    //设置是否升舱
    public static final Long IS_UPGRADE = 1L;
    //设置升舱的座位类型
    public static final Long SET_UPGRADE_SET_TYPE = 1L;
    //账号默认状态
    public static final String DEFAULT_STATUS = "1";
    //更新帐号状态
    public static final String UPDATE_USER_STATUS = "1";
    //未起飞飞机状态
    public static final Integer NOT_FLY = 0;

    public static final Integer DELAY = 2;
    //设置管理员的vip等级，用于校验区分用户
    public static final Long ADMIN_VIP_STATUS = -1L;
    //代理vip等级
    public static final Long AGENT_VIP_STATUS = -2L;
    //头像生成文件夹
    public static final String IMAGE_UPLOAD_DIR = "F:\\img";
    //头像生成
    public static final String IMAGE_UPLOAD_DIR_AVATAR = "avatar";
    //头像存储文件夹
    public static final String IMAGE_UPLOAD_DIR_FIND = "F:\\img\\avatar\\";
    //经济舱舱位数量
    public static final String ECONOMY_CLASS_NUM = "economyClassNum";
    //头等舱舱位数量
    public static final String FIRST_CLASS_NUM = "firstClassNum";
    //购买时默认的4项字段，是否是升舱订单
    public static final Integer DEFAULT_IS_UPGRADE_ORDER = 0;
    //是否是取消的订单
    public static final Long DEFAULT_IS_CANCEL = 0L;

    //是否已经使用
    public static final Integer DEFAULT_IS_USED = 0;
    //是否升舱
    public static final Long DEFAULT_IS_UPGRADE = 0L;
    public static final Long CANCEL_ORDER = 1L;

}
