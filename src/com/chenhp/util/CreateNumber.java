package com.chenhp.util;

/**
 * @ClassName CreateNumber
 * @Description 生成叠加数字
 * @author 陈和平(chenhp2018@163.com)
 * @Date 2017年7月3日 上午9:03:50
 * @version 2.2.2
 */
public class CreateNumber {

    public static void main(String[] args) {
        setSeriNumber(30);
    }

    /**
     * @Description 生成叠加数字
     * @param a
     * @Date 2017年7月3日 上午9:04:23
     */
    public static void setSeriNumber(int a) {
        for (int i = 1; i <= a; i++) {
            if(i<10){
                System.out.println("0"+i + "、");
            }else{
                System.out.println(i + "、");
            }
        }
    }
}
