package com.wjn.treeview.view;

import com.wjn.treeview.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wujna on 2018/3/21.
 */

public class TreeHelper {

    /**
     * 根据所有节点获取可见节点
     *
     * @param allNodes
     * @return
     */

    public static List<Node> filterVisibleNode(List<Node> allNodes) {
        List<Node> visibleNodes = new ArrayList<Node>();
        for (Node node : allNodes) {
            // 如果为根节点，或者上层目录为展开状态
            if (node.isRoot() || node.isParentExpand()) {
                setNodeIcon(node);
                visibleNodes.add(node);
            }
        }
        return visibleNodes;
    }

    /**
     * 获取排序的所有节点
     *
     * @param datas
     * @param defaultExpandLevel
     * @return
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */

    public static <T> List<Node> getSortedNodes(List<T> datas, int defaultExpandLevel, boolean isHide) throws IllegalAccessException, IllegalArgumentException {
        List<Node> sortedNodes = new ArrayList<Node>();
        // 将用户数据转化为List<Node>
        List<Node> nodes = convertData2Nodes(datas, isHide);
        // 拿到根节点
        List<Node> rootNodes = getRootNodes(nodes);
        // 排序以及设置Node间关系
        for (Node node : rootNodes) {
            addNode(sortedNodes, node, defaultExpandLevel, 1);
        }
        return sortedNodes;
    }

    /**
     * 把一个节点上的所有的内容都挂上去
     */

    private static void addNode(List<Node> nodes, Node node, int defaultExpandLeval, int currentLevel) {
        nodes.add(node);
        if (defaultExpandLeval >= currentLevel) {
            node.setExpand(true);
        }

        if (node.isLeaf())
            return;
        for (int i = 0; i < node.getChildrenNodes().size(); i++) {
            addNode(nodes, node.getChildrenNodes().get(i), defaultExpandLeval, currentLevel + 1);
        }
    }

    /**
     * 获取所有的根节点
     *
     * @param nodes
     * @return
     */
    public static List<Node> getRootNodes(List<Node> nodes) {
        List<Node> rootNodes = new ArrayList<Node>();
        for (Node node : nodes) {
            if (node.isRoot()) {
                rootNodes.add(node);
            }
        }

        return rootNodes;
    }

    /**
     * 将泛型datas转换为node
     *
     * @param datas
     * @return
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static <T> List<Node> convertData2Nodes(List<T> datas, boolean isHide)
            throws IllegalAccessException, IllegalArgumentException {
        List<Node> nodes = new ArrayList<Node>();
        Node node = null;

        for (T t : datas) {
            int id = -1;
            int pId = -1;
            String ids=null;
            String pids=null;
            String name = null;
            String state = null;

            Class<? extends Object> clazz = t.getClass();
            Field[] declaredFields = clazz.getDeclaredFields();
            /**
             * 与MyNodeBean实体一一对应
             */
            for (Field f : declaredFields) {
                if ("id".equals(f.getName())) {
                    f.setAccessible(true);
                    id = f.getInt(t);
                }

                if ("pId".equals(f.getName())) {
                    f.setAccessible(true);
                    pId = f.getInt(t);
                }

                if ("ids".equals(f.getName())) {
                    f.setAccessible(true);
                    ids = (String) f.get(t);
                }

                if ("pIds".equals(f.getName())) {
                    f.setAccessible(true);
                    pids = (String) f.get(t);
                }

                if ("state".equals(f.getName())) {
                    f.setAccessible(true);
                    state = (String) f.get(t);
                }

                if ("name".equals(f.getName())) {
                    f.setAccessible(true);
                    name = (String) f.get(t);
                }

                if ("desc".equals(f.getName())) {
                    continue;
                }

                if ("length".equals(f.getName())) {
                    continue;
                }

                if (id == -1 && pId == -1 && ids==null&& pids==null&&name == null) {
                    break;
                }
            }

            node = new Node(id, pId,ids,pids, name,state);
            node.setHideChecked(isHide);
            nodes.add(node);
        }

        /**
         * 比较nodes中的所有节点，分别添加children和parent
         */
        for (int i = 0; i < nodes.size(); i++) {
            Node n = nodes.get(i);
            for (int j = i + 1; j < nodes.size(); j++) {
                Node m = nodes.get(j);
                if (n.getId() == m.getpId()) {
                    n.getChildrenNodes().add(m);
                    m.setParent(n);
                } else if (n.getpId() == m.getId()) {
                    n.setParent(m);
                    m.getChildrenNodes().add(n);
                }
            }
        }

        for (Node n : nodes) {
            setNodeIcon(n);
        }
        return nodes;
    }

    /**
     * 设置打开，关闭icon
     *
     * @param node
     */
    public static void setNodeIcon(Node node) {
        if (node.getChildrenNodes().size() > 0 && node.isExpand()) {
            node.setIcon(R.drawable.tree_expand);
        } else if (node.getChildrenNodes().size() > 0 && !node.isExpand()) {
            node.setIcon(R.drawable.tree_econpand);
        } else
            node.setIcon(-1);
    }

    public static void setNodeChecked(Node node, boolean isChecked) {
        // 自己设置是否选择
        node.setChecked(isChecked);
        /**
         * 非叶子节点,子节点处理
         */
        setChildrenNodeChecked(node, isChecked);
        /** 父节点处理 */
        setParentNodeChecked(node);

    }

    /**
     * 非叶子节点,子节点处理
     */
    private static void setChildrenNodeChecked(Node node, boolean isChecked) {
        node.setChecked(isChecked);
        if (!node.isLeaf()) {
            for (Node n : node.getChildrenNodes()) {
                // 所有子节点设置是否选择
                setChildrenNodeChecked(n, isChecked);
            }
        }
    }

    /**
     * 设置父节点选择
     *
     * @param node
     */
    private static void setParentNodeChecked(Node node) {

        /** 非根节点 */
        if (!node.isRoot()) {
            Node rootNode = node.getParent();
            boolean isAllChecked = true;
            for (Node n : rootNode.getChildrenNodes()) {
                if (!n.isChecked()) {
                    isAllChecked = false;
                    break;
                }
            }

            if (isAllChecked) {
                rootNode.setChecked(true);
            } else {
                rootNode.setChecked(false);
            }
            setParentNodeChecked(rootNode);
        }
    }

}
