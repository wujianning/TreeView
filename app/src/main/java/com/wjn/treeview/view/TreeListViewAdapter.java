package com.wjn.treeview.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wujna on 2018/3/21.
 */

public abstract class TreeListViewAdapter<T> extends BaseAdapter {

    protected Context mContext;
    /**
     * 存储所有可见的Node
     */
    protected List<Node> mNodes;
    protected LayoutInflater mInflater;
    /**
     * 存储所有的Node
     */
    protected List<Node> mAllNodes;

    /**
     * 点击的回调接口
     */
    private OnTreeNodeClickListener onTreeNodeClickListener;

    public interface OnTreeNodeClickListener {
        /**
         * 处理node click事件
         * @param node
         * @param position
         */
        void onClick(Node node, int position);
        /**
         * 处理checkbox选择改变事件
         * @param node
         * @param position
         * @param checkedNodes
         */
        void onCheckChange(Node node, int position, List<Node> checkedNodes);
    }

    public void setOnTreeNodeClickListener(
            OnTreeNodeClickListener onTreeNodeClickListener) {
        this.onTreeNodeClickListener = onTreeNodeClickListener;
    }

    /**
     *
     * @param mTree
     * @param context
     * @param datas
     * @param defaultExpandLevel
     *            默认展开几级树
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public TreeListViewAdapter(ListView mTree, Context context, List<T> datas,
                               int defaultExpandLevel, boolean isHide)
            throws IllegalArgumentException, IllegalAccessException {
        mContext = context;
        /**
         * 对所有的Node进行排序
         */
        mAllNodes = TreeHelper
                .getSortedNodes(datas, defaultExpandLevel, isHide);
        /**
         * 过滤出可见的Node
         */
        mNodes = TreeHelper.filterVisibleNode(mAllNodes);
        mInflater = LayoutInflater.from(context);

        /**
         * 设置节点点击时，可以展开以及关闭；并且将ItemClick事件继续往外公布
         */
        mTree.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                expandOrCollapse(position);

                if (onTreeNodeClickListener != null) {
                    onTreeNodeClickListener.onClick(mNodes.get(position),
                            position);
                }
            }

        });

    }

    /**
     * 相应ListView的点击事件 展开或关闭某节点
     *
     * @param position
     */
    public void expandOrCollapse(int position) {
        Node n = mNodes.get(position);

        if (n != null)// 排除传入参数错误异常
        {
            if (!n.isLeaf()) {
                n.setExpand(!n.isExpand());
                mNodes = TreeHelper.filterVisibleNode(mAllNodes);
                notifyDataSetChanged();// 刷新视图
            }
        }
    }

    @Override
    public int getCount() {
        return mNodes.size();
    }

    @Override
    public Object getItem(int position) {
        return mNodes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Node node = mNodes.get(position);

        convertView = getConvertView(node, position, convertView, parent);
        // 设置内边距
        convertView.setPadding(node.getLevel() * 30, 3, 3, 3);
        if (!node.isHideChecked()) {
            //获取各个节点所在的父布局
            RelativeLayout myView = (RelativeLayout) convertView;
            //父布局下的CheckBox
            CheckBox cb = (CheckBox) myView.getChildAt(1);
            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

                @Override
                public void onCheckedChanged(CompoundButton buttonView,
                                             boolean isChecked) {
                    TreeHelper.setNodeChecked(node, isChecked);
                    List<Node> checkedNodes = new ArrayList<Node>();
                    for(Node n:mAllNodes){
                        if(n.isChecked()){
                            checkedNodes.add(n);
                        }
                    }

                    onTreeNodeClickListener.onCheckChange(node,position,checkedNodes);
                    TreeListViewAdapter.this.notifyDataSetChanged();
                }

            });
        }

        return convertView;
    }

    public abstract View getConvertView(Node node, int position, View convertView, ViewGroup parent);

    /**
     * 更新
     * @param isHide
     */
    public void updateView(boolean isHide){
        for(Node node:mAllNodes){
            node.setHideChecked(isHide);
        }

        this.notifyDataSetChanged();
    }

}
