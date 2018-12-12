package com.wjn.treeview.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.wjn.treeview.R;

import java.util.List;

/**
 * Created by wujna on 2018/3/21.
 */

public class MyTreeListViewAdapter<T> extends TreeListViewAdapter<T> {

    public MyTreeListViewAdapter(ListView mTree, Context context, List<T> datas, int defaultExpandLevel, boolean isHide) throws IllegalArgumentException, IllegalAccessException {
        super(mTree, context, datas, defaultExpandLevel, isHide);
    }

    @Override
    public View getConvertView(Node node, int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.icon = (ImageView) convertView.findViewById(R.id.id_treenode_icon);
            viewHolder.label = (TextView) convertView.findViewById(R.id.id_treenode_name);
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.id_treeNode_check);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (node.getIcon() == -1) {
            viewHolder.icon.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.icon.setVisibility(View.VISIBLE);
            viewHolder.icon.setImageResource(node.getIcon());
        }

        if(node.isLeaf()){//叶子节点
            if (node.isHideChecked()) {//叶子节点要隐藏
                viewHolder.checkBox.setVisibility(View.INVISIBLE);
            } else {//叶子节点正常显示
                viewHolder.checkBox.setVisibility(View.VISIBLE);
                String state=node.getState();
                boolean b=node.isChecked();
                if("1".equals(state)){
                    b=true;
                }
                setCheckBoxBg(viewHolder.checkBox, b);
            }
        }else{//父节点不显示选择框
            viewHolder.checkBox.setVisibility(View.GONE);
        }
        viewHolder.label.setText(node.getName());
        return convertView;
    }

    /**
     * ViewHolder类
     * */

    private final class ViewHolder {
        private ImageView icon;
        private TextView label;
        private CheckBox checkBox;
    }

    /**
     * checkbox是否选中
     */

    private void setCheckBoxBg(CheckBox cb, boolean isChecked) {
        if (isChecked) {
            cb.setBackgroundResource(R.drawable.check_box_bg_check);
        } else {
            cb.setBackgroundResource(R.drawable.check_box_bg);
        }
    }

}

