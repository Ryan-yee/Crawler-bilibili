package com.ryanyee.Crawer_bilibili.model;

import java.util.List;

public class DanmuMessage {
    private final Object data;

    public DanmuMessage(Object data) {
        this.data = data;
    }

    private List<Object> getInfo() {
        return (List<Object>) this.data;  // Assuming 'data' is a list containing the actual info
    }

    private List<Object> getBasicInfo() {
        return (List<Object>) getInfo().get(0);
    }

    public int getPool() {
        return (int) getBasicInfo().get(0);
    }

    /**
     * 弹幕模式
     * (1从右至左滚动弹幕|6从左至右滚动弹幕|5顶端固定弹幕|4底端固定弹幕|7高级弹幕|8脚本弹幕)
     */
    public int getMode() {
        return (int) getBasicInfo().get(1);
    }

    public int getFontSize() {
        return (int) getBasicInfo().get(2);
    }

    public int getColor() {
        return (int) getBasicInfo().get(3);
    }

    /** 发送时间 */
    public int getTimestamp() {
        return (int) getBasicInfo().get(4);
    }

    /** 进入直播间时间(若来自Android客户端，则为随机数) */
    public int getEnterRoomTime() {
        return (int) getBasicInfo().get(5);
    }

    /** 用户ID的CRC32校验和(不需要用此字段来得到用户 ID) */
    public String getUserIdCrc32() {
        return (String) getBasicInfo().get(7);
    }

    /** 内容 */
    public String getMessage() {
        return (String) getInfo().get(1);
    }

    public List<Object> getUserInfo() {
        return (List<Object>) getInfo().get(2);
    }

    public int getUserId() {
        return (int) getUserInfo().get(0);
    }

    public String getNickname() {
        return (String) getUserInfo().get(1);
    }

    public int getIsAdmin() {
        return (int) getUserInfo().get(2);
    }

    public int getIsVip() {
        return (int) getUserInfo().get(3);
    }

    public int getIsSVip() {
        return (int) getUserInfo().get(4);
    }

    /**
     * 粉丝勋章信息
     * 注意, 如果弹幕发送者没有佩戴勋章则该字段为一个空 JsonArray
     * 未佩戴粉丝勋章时, 下面几个字段都会返回 null
     */
    public List<Object> getFansMedalInfo() {
        return (List<Object>) getInfo().get(3);
    }

    public boolean hasFansMedal() {
        return !getFansMedalInfo().isEmpty();
    }

    public Integer getFansMedalLevel() {
        return hasFansMedal() ? (Integer) getFansMedalInfo().get(0) : null;
    }

    public String getFansMedalName() {
        return hasFansMedal() ? (String) getFansMedalInfo().get(1) : null;
    }

    /** 粉丝勋章对应主播用户名 */
    public String getFansMedalAnchorNickName() {
        return hasFansMedal() ? (String) getFansMedalInfo().get(2) : null;
    }

    /** 粉丝勋章对应主播直播间号码 */
    public Integer getFansMedalAnchorRoomId() {
        return hasFansMedal() ? (Integer) getFansMedalInfo().get(3) : null;
    }

    /** 粉丝勋章背景颜色 */
    public Integer getFansMedalBackgroundColor() {
        return hasFansMedal() ? (Integer) getFansMedalInfo().get(4) : null;
    }

    public List<Object> getUserLevelInfo() {
        return (List<Object>) getInfo().get(4);
    }

    /**
     * UL, 发送者的用户等级, 非主播等级
     */
    public int getUserLevel() {
        return (int) getUserLevelInfo().get(0);
    }

    /**
     * 用户等级标识的边框的颜色, 通常为最后一个佩戴的粉丝勋章的颜色
     */
    public int getUserLevelBorderColor() {
        return (int) getUserLevelInfo().get(2);
    }

    /**
     * 用户排名, 可能为数字, 也可能是 ">50000"
     */
    public String getUserRank() {
        return (String) getUserLevelInfo().get(3);
    }

    /**
     * 用户头衔
     * 可能为空列表, 也可能是值为 "" 的列表
     * 可能有两项, 两项的值可能一样
     */
    public List<String> getUserTitles() {
        return (List<String>) getInfo().get(5);
    }

    /**
     * 校验信息
     * {
     *  "ts": 1553368447,
     *  "ct": "98688F2F"
     * }
     */
    public Object getCheckInfo() {
        return getInfo().get(9);
    }
}
