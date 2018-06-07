package com.mp.commons.constant;

/**
 * Created by wushenjun on 2018/5/31.
 */
public enum Privilege {
    ANY(Privilege.Category.NONE, 999, "", ""),

    ;

    private Privilege.Category category;
    private int privCode;
    private String column;
    private String tipsId;

    private Privilege(Privilege.Category category, int privCode, String colunm, String tipsId) {
        this.setCategory(category);
        this.privCode = privCode;
        this.column = colunm;
        this.tipsId = tipsId;
    }

    public String column() {
        return this.column;
    }

    public String tipsId() {
        return this.tipsId;
    }

    public int getPrivCode() {
        return this.privCode;
    }

    public void setPrivCode(int privCode) {
        this.privCode = privCode;
    }

    public Privilege.Category getCategory() {
        return this.category;
    }

    public void setCategory(Privilege.Category category) {
        this.category = category;
    }

    public static enum Category {
        NONE,
        BLACK_LIST,
        WHITE_LIST,
        CAIGOU_CON,
        STORE,
        STORE_MGR,
        DOC_EXCHANGE,
        BUSI_XFER;

        private Category() {
        }
    }
}
