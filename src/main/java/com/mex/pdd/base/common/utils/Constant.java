package com.mex.pdd.base.common.utils;

/**
 * 常量
 *
 * @author theodo
 * @email 36780272@qq.com
 * @date 2017年10月15日 下午1:23:52
 */
public class Constant {

    /**
     * 超级管理员ID
     */
    public static final int SUPER_ADMIN = 1;
    public static final int DEFAULT_BATCH_SIZE = 50000;
    public static final Integer STATE_ON = 1;
    //活动点击链接
    public static final String CAMPAIGN_CLK_URL_TPL = "http://monitor.destroyer.ad-mex.com/click?api=2&offer_id=%s&channel_id=%s&" +
            "subchannel={subchannel}&channel_click_id={channel_click_id}&idfa={idfa}&google_advertiser_id={google_advertiser_id}&androidid={androidid}&ip={ip}&ua={ua}&" +
            "s1={s1}&s2={s2}&s3={s3}&s4={s4}&s5={s5}";
    //全扣量
    public static final Integer MAX_DEDUCT = 100;
    //默认时间格式
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String CURR_USE_KEY = "T(com.mex.pdd.base.common.controller.utils.ShiroUtils).getUser()";

    /**
     * 数据权限过滤
     */
    public static final String SQL_FILTER = "sql_filter";

    /**
     * 菜单类型
     *
     * @author theodo
     * @email 36780272@qq.com
     * @date 2016年11月15日 下午1:24:29
     */
    public enum MenuType {
        /**
         * 目录
         */
        CATALOG(0),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2);

        private int value;

        private MenuType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 定时任务状态
     *
     * @author theodo
     * @email 36780272@qq.com
     * @date 2016年12月3日 上午12:07:22
     */
    public enum ScheduleStatus {
        /**
         * 正常
         */
        NORMAL(0),
        /**
         * 暂停
         */
        PAUSE(1);

        private int value;

        private ScheduleStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 云服务商
     */
    public enum CloudService {
        /**
         * 七牛云
         */
        QINIU(1),
        /**
         * 阿里云
         */
        ALIYUN(2),
        /**
         * 腾讯云
         */
        QCLOUD(3);

        private int value;

        private CloudService(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

}
