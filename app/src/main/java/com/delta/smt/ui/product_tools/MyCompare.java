package com.delta.smt.ui.product_tools;

import com.delta.smt.entity.ProductWorkItem;

import java.util.Comparator;

/**
 * Created by Shaoqiang.Zhang on 2017/1/13.
 */

public class MyCompare  implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        ProductWorkItem item1= (ProductWorkItem) o1;
        ProductWorkItem item2= (ProductWorkItem) o2;
        return item1.getPlayOnLineTime().compareTo(item2.getPlayOnLineTime());
    }
}
