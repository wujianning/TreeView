package com.wjn.treeview.view;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wujna on 2018/3/21.
 */

public class Node {
    private String state;
    private String ids;
    private String pIds;
    /**
     * 节点id
     */
    private int id;
    /**
     * 父节点id
     */
    private int pId;
    /**
     * 是否展开
     */
    private boolean isExpand = false;
    private boolean isChecked = false;
    private boolean isHideChecked = true;
    /**
     * 节点名字
     */
    private String name;
    /**
     * 节点级别
     */
    private int level;
    /**
     * 节点展示图标
     */
    private int icon;
    /**
     * 节点所含的子节点
     */
    private List<Node> childrenNodes = new ArrayList<Node>();
    /**
     * 节点的父节点
     */
    private Node parent;

    public Node() {
    }

    public Node(int id, int pId, String ids, String pIds, String name, String state) {
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

    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean isExpand() {
        return isExpand;
    }

    /**
     * 当父节点收起，其子节点也收起
     * @param isExpand
     */
    public void setExpand(boolean isExpand) {
        this.isExpand = isExpand;
        if (!isExpand) {

            for (Node node : childrenNodes) {
                node.setExpand(isExpand);
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return parent == null ? 0 : parent.getLevel() + 1;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public List<Node> getChildrenNodes() {
        return childrenNodes;
    }

    public void setChildrenNodes(List<Node> childrenNodes) {
        this.childrenNodes = childrenNodes;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    /**
     * 判断是否是根节点
     *
     * @return
     */
    public boolean isRoot() {
        return parent == null;
    }

    /**
     * 判断是否是叶子节点
     *
     * @return
     */
    public boolean isLeaf() {
        return childrenNodes.size() == 0;
    }


    /**
     * 判断父节点是否展开
     *
     * @return
     */
    public boolean isParentExpand()
    {
        if (parent == null)
            return false;
        return parent.isExpand();
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public boolean isHideChecked() {
        return isHideChecked;
    }

    public void setHideChecked(boolean isHideChecked) {
        this.isHideChecked = isHideChecked;
    }
}
