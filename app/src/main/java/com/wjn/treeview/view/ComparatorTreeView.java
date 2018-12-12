package com.wjn.treeview.view;

import java.util.Comparator;

/**
 * Created by wujna on 2018/3/23.
 */

public class ComparatorTreeView implements Comparator<MyNodeBean> {

    @Override
    public int compare(MyNodeBean myNodeBean, MyNodeBean t1) {
        int flag=myNodeBean.getpIds().compareTo(t1.getpIds());
        if(flag>0){
            return 1;
        }else if(flag==0){
            return 0;
        }else{
            return -1;
        }
    }
}
