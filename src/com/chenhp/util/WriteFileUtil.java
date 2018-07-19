package com.chenhp.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
/**
 * 
 * @ClassName WriteFileUtil
 * @Description 自动复制代码到固定路径
 * @author 陈和平(chenhp2018@163.com)
 * @Date 2017年6月30日 下午1:24:40
 * @version 2.2.2
 */
public class WriteFileUtil {
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		String content = "This is the content to write into file";
		final String UPDATE_NEW_FILE_PATH = "E:/filename.txt";
//		writeMethod(UPDATE_NEW_FILE_PATH, content,true);
	}

	/**
	 * 处理内容中，文件名与路径分离方法
	 * @param path
	 * @param content1
	 * @param content2
	 * @param flag
	 * @param spaceLength  分割距离
	 */
	public static void writeContentSplitMethod(String path, String content1,String content2, boolean flag,int spaceLength){
		String content = "";
		String space = "";
		for(int i = 0; i <= spaceLength-content1.length(); i++){
			space = space+" ";
		}
		writeSpaceMethod(path,true);
		content = " cp -rf "+content1+space+content2;
		writeMethod(path, content,flag);
	}
	
	
	
	
	/**
	 * 写入内容到文件方法
	 * @param path	写入的文件所在路径
	 * @param content			写入内容
	 * @param flag		追加标志
	 */
	public static void writeMethod(String path, String content,boolean flag) {
		try {

			File file = new File(path);
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile(), flag);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);

			bw.flush();
			bw.close();

		} catch (IOException e) {
			System.out.println("找不到文件路径："+path);
		}
	}
	/**
	 * 写入内容到文件方法---换行方法
	 * @param path	写入的文件所在路径
	 * @param flag		追加标志
	 */
	public static void writeSpaceMethod(String path, boolean flag) {
		try {
			
			File file = new File(path);
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			
			FileWriter fw = new FileWriter(file.getAbsoluteFile(), flag);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.newLine();
			
			bw.flush();
			bw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/** 
	  * 删除单个文件 
	  *  
	  * @param fileName 
	  *            要删除的文件的文件名 
	  * @return 单个文件删除成功返回true，否则返回false 
	  */  
	 public static boolean deleteFile(String fileName) {  
	  File file = new File(fileName);  
	  // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除  
	  try{
		  if (file.exists() && file.isFile()) {  
			   if (file.delete()) {  
//			    System.out.println("删除单个文件" + fileName + "成功！");  
			    return true;  
			   } else {  
//			    System.out.println("删除单个文件" + fileName + "失败！");  
			    return true;  
			   }  
			  } else {  
//			   System.out.println("删除单个文件失败：" + fileName + "不存在！");  
			   return true;  
			  }  
	  }catch(Exception e){
		  System.out.println("删除单个文件失败：" + fileName + "不存在！");  
	  }
	  return true;
	 
	 }
	
	/**
	 * 复制单个文件内容
	 * 
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf.txt
	 * @param newPath
	 *            String 复制后路径 如：f:/fqf.txt
	 * @return boolean
	 */
	@SuppressWarnings({ "unused", "resource" })
	public static void copyFile(String oldPath, String newPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { // 文件存在时
				InputStream inStream = new FileInputStream(oldPath); // 读入原文件
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				int length;
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 字节数 文件大小
					System.out.println(bytesum);
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
		} catch (Exception e) {
			System.out.println("复制单个文件操作出错");
			e.printStackTrace();
		}
	}

}
