package com.chenhp.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class txtFileSplit {

    public static final String FILE_PATH = "F:/chenhp/陈和平/公司资料/银行存管接口/资金明细文件/3005-ALEVE0101-20171004_2"; // 读取文件内容路径
    public static final String NEW_FILE_PATH = "F:/chenhp/陈和平/公司资料/银行存管接口/资金明细文件/存管资金明细_01.sql"; // 写入到新文件路径

    public static void main(String[] args) {
//        WriteFileUtil.deleteFile(NEW_FILE_PATH);// 删除源文件
        // readTxtFile();
//        readTxtFileAppendSql();
//         readTxtFileAppendTitle();
        /*String [] fileName = getFileName("F:\\chenhp\\陈和平\\公司资料\\银行存管接口\\资金明细文件\\1");
        for(String name:fileName)
        {
            System.out.println(name);
        }
        System.out.println("--------------------------------");
        ArrayList<String> listFileName = new ArrayList<String>(); 
        getAllFileName("F:\\chenhp\\陈和平\\公司资料\\银行存管接口\\资金明细文件\\1",listFileName);
        for(String name:listFileName)
        {
            System.out.println(name);
        }
        */
        
//        FindFilesMethod1("F:\\chenhp\\陈和平\\公司资料\\银行存管接口\\资金明细文件\\1");
        
        File file = new File("F:\\chenhp\\陈和平\\公司资料\\银行存管接口\\资金明细文件\\1"+File.separator);  
        list(file);  
        
    }
    
    public static void list(File file) {
        if (file.isDirectory())// 判断file是否是目录
        {
            File[] lists = file.listFiles();
            if (lists != null) {
                for (int i = 0; i < lists.length; i++) {
                    list(lists[i]);// 是目录就递归进入目录内再进行判断
                }
            }
        }
        if (file.isFile()) {
            if(file.toString() != null){
                String path = file.toString();
                if(path.contains("ALEVE0101")){
                    System.out.println(file);// file不是目录，就输出它的路径名，这是递归的出口
                }
            }
        }
    }
    
    public static void FindFilesMethod1(String path){

        List<Map<String, Object>> files=new ArrayList<Map<String,Object>>();
        List<Map<String, Object>> folders=new ArrayList<Map<String,Object>>();

         File file=new File(path);
         File[] tempList = file.listFiles();
         System.out.println("该目录下对象个数："+tempList.length);
         for (int i = 0; i < tempList.length; i++) {
              if (tempList[i].isFile()) {
                  Map<String,Object> filesMap=new HashMap<String, Object>();
                  filesMap.put("fileUrl", tempList[i].toString().replaceAll("\\\\", "/"));
                  filesMap.put("fileName", tempList[i].getName().toString().replaceAll("\\\\", "/"));
                  files.add(filesMap);
              }
              if (tempList[i].isDirectory()) {
                  Map<String,Object> foldersMap=new HashMap<String, Object>();
                  foldersMap.put("folderUrl", tempList[i].toString().replaceAll("\\\\", "/"));
                  foldersMap.put("folderName", tempList[i].getName().toString().replaceAll("\\\\", "/"));
                  folders.add(foldersMap);           
              }
         }
        for(Map<String, Object> realfile:files){
              System.out.println("文件   路径："+realfile.get("fileUrl")+"&文件名:"+realfile.get("fileName"));
        }
        for(Map<String, Object> realfolder:folders){
              System.out.println("文件夹路径："+realfolder.get("folderUrl")+"&文件夹名:"+realfolder.get("folderName"));
        }    
    }
    public static String [] getFileName(String path)
    {
        File file = new File(path);
        String [] fileName = file.list();
        return fileName;
    }
    public static void getAllFileName(String path,ArrayList<String> fileName)
    {
        File file = new File(path);
        File [] files = file.listFiles();
        String [] names = file.list();
        if(names != null)
        fileName.addAll(Arrays.asList(names));
        for(File a:files)
        {
            if(a.isDirectory())
            {
                getAllFileName(a.getAbsolutePath(),fileName);
            }
        }
    }
    
    
    /**
     * @Description 截取文件内容
     * @Date 2017年11月1日 上午9:30:14
     */
    public static void readTxtFile() {
        try {
            String encoding = "UTF-8";
            File file = new File(FILE_PATH);
            if (file.isFile() && file.exists()) { // 判断文件是否存在
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    if (lineTxt.length() >= 1) {
                        String content = subStrUtil(lineTxt, 11, 29, ""); // 截取到的内容
                        WriteFileUtil.writeMethod(NEW_FILE_PATH, content, true);// 写入文件名称
                        WriteFileUtil.writeSpaceMethod(NEW_FILE_PATH, true);// 换行
                        System.out.println(content);
                    }
                }
                read.close();
            } else {
                System.out.println("找不到指定的文件:" + FILE_PATH);
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
    }

    /**
     * @Description 生成sql语句
     * @Date 2017年11月1日 上午9:30:52
     */
    public static void readTxtFileAppendSql() {
        try {
            String encoding = "UTF-8";
            File file = new File(FILE_PATH);
            if (file.isFile() && file.exists()) { // 判断文件是否存在
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    String idStr = subStrUtil(lineTxt, 50, 79, "").trim(); // 截取到的内容
                    String authStr = subStrUtil(lineTxt, 163 - 20, 182 - 20, ""); // 截取到的内容

                    if (lineTxt.length() >= 1) {
                        String content = "UPDATE rd_borrow_tender AS t1 SET t1.sequence_id ='" + idStr
                                + "', t1.auth_code = '" + authStr + "'  WHERE t1.id = " + idStr + ";";
                        WriteFileUtil.writeMethod(NEW_FILE_PATH, content, true);// 写入文件名称
                        WriteFileUtil.writeSpaceMethod(NEW_FILE_PATH, true);// 换行
                        System.out.println(content);
                    }
                }
                read.close();
            } else {
                System.out.println("找不到指定的文件:" + FILE_PATH);
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
    }


    /**
     * @Description 分离内容
     * @Date 2017年11月1日 上午9:30:52
     */
    public static void readTxtFileAppendTitle() {
        try {
            String encoding = "GBK";
            File file = new File(FILE_PATH);
            if (file.isFile() && file.exists()) { // 判断文件是否存在
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    StringBuffer sb = new StringBuffer();
                    String splitStr = "，";
                    int chineseCount = getChineseCount(lineTxt);
                    sb.append(subStrUtil(lineTxt, 1, 4, splitStr));
                    sb.append(subStrUtil(lineTxt, 5, 23, splitStr));
                    sb.append(subStrUtil(lineTxt, 24, 40, splitStr));
                    sb.append(subStrUtil(lineTxt, 41, 43, splitStr));
                    sb.append(subStrUtil(lineTxt, 44, 44, splitStr));
                    sb.append(subStrUtil(lineTxt, 45, 52, splitStr));
                    sb.append(subStrUtil(lineTxt, 53, 60, splitStr));
                    sb.append(subStrUtil(lineTxt, 61, 68, splitStr));
                    sb.append(subStrUtil(lineTxt, 69, 76, splitStr));
                    sb.append(subStrUtil(lineTxt, 77, 82, splitStr));
                    sb.append(subStrUtil(lineTxt, 83, 88, splitStr));
                    sb.append(subStrUtil(lineTxt, 89, 92, splitStr));
                    sb.append(subStrUtil(lineTxt, 93, 134-chineseCount, splitStr));
                    sb.append(subStrUtil(lineTxt, 135 - chineseCount, 151 - chineseCount, splitStr));
                    sb.append(subStrUtil(lineTxt, 152 - chineseCount, 170 - chineseCount, splitStr));
                    sb.append(subStrUtil(lineTxt, 171 - chineseCount, 171 - chineseCount, splitStr));
                    sb.append(subStrUtil(lineTxt, 172 - chineseCount, 371 - chineseCount, splitStr));
                    if (lineTxt.length() >= 1) {
                        String content = sb.toString();
                        WriteFileUtil.writeMethod(NEW_FILE_PATH, content, true);// 写入文件名称
                        WriteFileUtil.writeSpaceMethod(NEW_FILE_PATH, true);// 换行
                        System.out.println(content);
                    }
                }
                read.close();
            } else {
                System.out.println("找不到指定的文件:" + FILE_PATH);
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
    }
    
    private static int getChineseCount(String str){
        int count = 0;  
        Pattern pattern = Pattern.compile("[\u4e00-\u9fa5]");  
        int length = 0;  
        if (str != null) {  
            char c[] = str.toCharArray();  
            length = c.length;  
            for (int i = 0; i < length; i++) {  
                Matcher matcher = pattern.matcher(String.valueOf(c[i]));  
                if (matcher.matches()) {  
                    count++;  
                }  
            }  
        } 
        return count;
    }
    
    /**
     * @Description 截取字符串
     * @param content
     * @param startIndex
     * @param endIndex
     * @param title
     * @param spiltStr
     * @return
     * @Date 2017年11月1日 下午2:22:02
     */
    public static String subStrUtil(String content, int startIndex, int endIndex, String spiltStr) {
        String str = "";
        if (content.length() >= endIndex) {
            str += content.substring(startIndex - 1, endIndex).trim(); // 截取到的内容
            str += spiltStr;
        }
        return str;
    }

}
