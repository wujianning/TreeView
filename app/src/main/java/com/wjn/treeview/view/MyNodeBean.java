package com.wjn.treeview.view;

/**
 * Created by wujna on 2018/3/21.
 */

public class MyNodeBean {
    private String state;
    private String ids;
    private String pIds;
    /**
     * 节点Id
     */
    private int id;
    /**
     * 节点父id
     */
    private int pId;
    /**
     * 节点name
     */
    private String name;
    /**
     *
     */
    private String desc;
    /**
     * 节点名字长度
     */
    private long length;


    public MyNodeBean(int id, int pId, String ids, String pIds, String name, String state) {
        super();
        this.id = id;
        this.pId = pId;
        this.ids=ids;
        this.pIds=pIds;
        this.name = name;
        this.state=state;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getPid() {
        return pId;
    }
    public void setPid(int pId) {
        this.pId = pId;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getpIds() {
        return pIds;
    }

    public void setpIds(String pIds) {
        this.pIds = pIds;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public long getLength() {
        return length;
    }
    public void setLength(long length) {
        this.length = length;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
