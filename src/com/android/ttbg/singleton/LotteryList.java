package com.android.ttbg.singleton;

public class LotteryList {  
	  
    /* 持有私有静态实例，防止被引用，此处赋值为null，目的是实现延迟加载 */  
    private static LotteryList instance = null;  
  
    /* 私有构造方法，防止被实例化 */  
    private LotteryList() {  
    }  
  
    /* 1:懒汉式，静态工程方法，创建实例 */  
    //调用方式
    //LotteryList.getInstance().method();
    public static LotteryList getInstance() {  
        if (instance == null) {  
            instance = new LotteryList();  
        }  
        return instance;  
    }  
}  