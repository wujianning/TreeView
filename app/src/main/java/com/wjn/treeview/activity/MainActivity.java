package com.wjn.treeview.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.wjn.treeview.R;
import com.wjn.treeview.constant.MyConstant;
import com.wjn.treeview.utils.BooleanUtils;
import com.wjn.treeview.view.ComparatorTreeView;
import com.wjn.treeview.view.MyNodeBean;
import com.wjn.treeview.view.MyTreeListViewAdapter;
import com.wjn.treeview.view.Node;
import com.wjn.treeview.view.TreeListViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TreeListViewAdapter.OnTreeNodeClickListener{

    private ListView treeLv;
    private MyTreeListViewAdapter<MyNodeBean> adapter;
    private List<MyNodeBean> mDatas = new ArrayList<MyNodeBean>();
    private List<MyNodeBean> mDatass = new ArrayList<MyNodeBean>();
    private boolean isHide = false; //标记是显示Checkbox还是隐藏
    private int defaultExpandLevel=2;//默认展开几级
    private HashMap<String,Integer> idhashmap;
    private HashMap<String,Integer> pIdhashmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        treeLv=findViewById(R.id.activity_treeview_listview);


        parseSparePartsData(MyConstant.myData);

    }

    /**
     * TreeView点击
     * */

    @Override
    public void onClick(Node node, int position) {
        if (node.isLeaf()) {//叶子节点
            node.setHideChecked(false);//子节点始终不隐藏选择框
            String state=node.getState();//Node状态
            String id=node.getIds();//NodeId
            String name=node.getName();//NodeName
            Node fathernode=node.getParent();//父Node
            String fathername="";
            if(null!=fathernode){//逐级获取父节点的NodeName
                boolean b=!fathernode.isLeaf();
                while (b){
                    fathername=fathernode.getName()+"-"+fathername;
                    fathernode=fathernode.getParent();
                    if(null!=fathernode){
                        b=!fathernode.isLeaf();
                    }else{
                        b=false;
                    }
                }
            }
        }
    }

    /**
     * TreeView选中
     * */

    @Override
    public void onCheckChange(Node node, int position, List<Node> checkedNodes) {

    }

    /**
     * Json解析备件数据
     * */

    private void parseSparePartsData(String context){
        try {
            JSONArray array=new JSONArray(context);
            int num=array.length();
            JSONObject object=null;
            MyNodeBean myNodeBean=null;
            idhashmap=new HashMap<>();
            pIdhashmap=new HashMap<>();
            for(int i=0;i<num;i++){
                object= (JSONObject) array.opt(i);
                String idstr=object.getString("id");
                String pidstr=object.getString("pId");
                String name=object.getString("name");
                String state="0";
                if(BooleanUtils.isEmpty(idstr)){
                    idstr="0";
                }
                if(BooleanUtils.isEmpty(pidstr)){
                    pidstr="0";
                }
//                if(null!=sparepartsmap&&sparepartsmap.containsKey(idstr)){
//                    state="1";
//                }
                myNodeBean=new MyNodeBean(0,0,idstr,pidstr,name,state);
                mDatass.add(myNodeBean);
                //将集合中的数据按pidstr倒序排序
                ComparatorTreeView comparatorTreeView=new ComparatorTreeView();
                Collections.sort(mDatass, comparatorTreeView);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        int nums=mDatass.size();
        if(nums>0){
            for(int i=0;i<nums;i++){
                MyNodeBean myNodeBean=mDatass.get(i);
                String idstr=myNodeBean.getIds();
                String pidstr=myNodeBean.getpIds();
                String name=myNodeBean.getName();
                String state=myNodeBean.getState();

                /**
                 * 算法1
                 * */

                if(!BooleanUtils.isEmpty(idstr)&&!BooleanUtils.isEmpty(pidstr)){
                    //Id 在Pid集合中找
                    if(pIdhashmap.containsKey(idstr)){
                        Integer value=pIdhashmap.get(idstr);
                        idhashmap.put(idstr,value);
                    }else{
                        idhashmap.put(idstr,(i+1));
                    }
                    //Pid 在Id集合中找
                    if(idhashmap.containsKey(pidstr)){
                        Integer value=idhashmap.get(pidstr);
                        pIdhashmap.put(pidstr,value);
                    }else{
                        pIdhashmap.put(pidstr,(i+1));
                    }
                }

                int id=idhashmap.get(idstr);
                int pid=pIdhashmap.get(pidstr);

                myNodeBean=new MyNodeBean(id,pid,idstr,pidstr,name,state);
                mDatas.add(myNodeBean);
            }
        }

        if(mDatas.size()>0){//有数据
            treeLv.setVisibility(View.VISIBLE);
            try {
                adapter = new MyTreeListViewAdapter<MyNodeBean>(treeLv, this, mDatas, defaultExpandLevel, isHide);
                treeLv.setAdapter(adapter);
                adapter.setOnTreeNodeClickListener(this);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

    }


}
