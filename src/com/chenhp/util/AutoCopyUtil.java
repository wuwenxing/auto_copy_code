package com.chenhp.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
/**
 * 
 * @ClassName autoCopyUtil
 * @Description 自动复制代码到固定路径
 * @author 陈和平(chenhp2018@163.com)
 * @Date 2017年6月30日 下午1:24:29
 * @version 2.2.2
 */
public class AutoCopyUtil {

	public static final String FILE_PATH = "E:/code_update_list_.txt";						//读取文件内容
	public static final String REPLACE_OLD_PATH_01 = "F:/neoworkspace-sit/p2p_web/target/";				//原始路径
	public static final String REPLACE_OLD_PATH_02 = "F:/neoworkspace-sit/p2p_manage/target/";		//原始路径
	public static final String REPLACE_OLD_PATH_03 = "F:/neoworkspace-sit/p2p_quartz/target/";		//原始路径
	public static final String REPLACE_OLD_PATH_04 = "F:/neoworkspace-sit/p2p_static/target/";		//原始路径
//	public static final String REPLACE_OLD_PATH_03 = "F:/eclipse_mars_A/p2p_web/target/";				//原始路径
//	public static final String REPLACE_OLD_PATH_04 = "F:/eclipse_mars_A/p2p_web/target/";				//原始路径
//	public static final String REPLACE_OLD_PATH_05 = "F:/eclipse_mars_A/p2p_web/target/";				//原始路径
//	public static final String REPLACE_OLD_PATH_06 = "F:/eclipse_mars_A/p2p_web/target/";				//原始路径
//	public static final String REPLACE_OLD_PATH_07 = "F:/eclipse_mars_A/p2p_web/target/";				//原始路径
//	public static final String REPLACE_OLD_PATH_08 = "F:/eclipse_mars_A/p2p_web/target/";				//原始路径
	public static final String REPLACE_NEW_PATH = "E:/update_file/p2p/";					//替换后新路径
	public static final String UPDATE_NEW_FILE_PATH = "E:/update_file/p2p/update_sh_file.sh";				//日志文件路径
	public static final String UPDATE_NEW_FILE_DES_PATH = "E:/update_file/p2p/说明.txt";				//日志文件路径
	public static final String PATH_REPALCE = "E:/update_file/p2p";		//日志文件内容格式化
	public static final int SPACE_LENGTH = 120;
	

	public static void main(String[] args) {
		System.out.println("-------------------------自动复制文件开始！---------------------------");
		// 读文件包含路径的内容，并读取内容里面的路径，并复制该路径的文件
		System.out.println("开始读取文件内容>>>"+FILE_PATH);
		System.out.println("准备复制>>>");
//		WriteFileUtil.deleteFile(UPDATE_NEW_FILE_PATH) ;//删除源文件
//		WriteFileUtil.writeMethod(UPDATE_NEW_FILE_PATH,"#/bin/bash",true);	//写入首行
		WriteFileUtil.deleteFile(UPDATE_NEW_FILE_DES_PATH) ;//删除源文件
		readTxtFile(FILE_PATH);
		System.out.println("-------------------------自动复制文件结束！----------------------------");
	}

	/**
	 * 功能：Java读取txt文件的内容 步骤：
	 * 1：先获得文件句柄 
	 * 2：获得文件句柄当做是输入一个字节码流，需要对这个输入流进行读取
	 * 3：读取到输入流后，需要读取生成字节流 
	 * 4：一行一行的输出。readline()。 备注：需要考虑的是异常情况
	 * 
	 * @param filePath
	 */
	public static void readTxtFile(String filePath) {
		try {
			String encoding = "UTF-8";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					// 获取文件中的路径
					String oldPath1 = lineTxt.replaceAll(" ", "").trim();
					String oldPath2 = oldPath1.replaceAll("\\\\\\\\", "/");
					String oldPath = oldPath2.replaceAll("\\\\", "/");
					//分类文件及文件夹
					File fileOld = new File(oldPath);
					if(!"".equals(oldPath)){
						String newPath = "";
						newPath = oldPath.replaceAll(REPLACE_OLD_PATH_01, REPLACE_NEW_PATH);
						newPath = newPath.replaceAll(REPLACE_OLD_PATH_02, REPLACE_NEW_PATH);
						newPath = newPath.replaceAll(REPLACE_OLD_PATH_03, REPLACE_NEW_PATH);
						newPath = newPath.replaceAll(REPLACE_OLD_PATH_04, REPLACE_NEW_PATH);
						//根据路径复制单个文件
						if(fileOld.isFile()){
							System.out.println("正在复制文件："+oldPath);
							moveFile(oldPath, newPath);//调用文件拷贝的方法  
							System.out.println("复制成功：-------->产生新文件：" + newPath);
							newPath = newPath.replaceAll(PATH_REPALCE, "");
//							WriteFileUtil.writeContentSplitMethod(UPDATE_NEW_FILE_PATH, newPath, newPath, true, SPACE_LENGTH);//写入文件名称
							
							WriteFileUtil.writeMethod(UPDATE_NEW_FILE_DES_PATH, newPath,true);//写入文件名称
							WriteFileUtil.writeSpaceMethod(UPDATE_NEW_FILE_DES_PATH,true);//换行
						}else if(fileOld.isDirectory()){//如果是路径的话，复制文件夹方法
							System.out.println("存在文件夹及所有内容！");
							System.out.println("正在复制文件夹："+oldPath);
				        	copy(oldPath, newPath);
				        	System.out.println("复制成功：-------->产生新文件：" + newPath);
						}else{
							System.err.println("复制失败："+oldPath+" 请检查该文件路径是否正确！");
						}
					}
				}
				read.close();
			} else {
				System.out.println("找不到指定的文件:"+FILE_PATH);
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
	}
	/**
	 * 复制文件
	 * 
	 * @param oldPath
	 * @param newPath
	 */
	@SuppressWarnings("resource")
	public static void moveFile(String oldPath, String newPath) {
		try {
			String[] aa = newPath.split("/");
			String fileName = aa[aa.length - 1];
			String[] bb = newPath.split("/" + fileName);
			String dirName = bb[0];
			File file = new File(dirName);
			// 如果文件夹不存在则创建
			if (!file.exists() && !file.isDirectory()) {
				// System.out.println("//创建新目录"+dirNameReplace);
				file.mkdirs();
			} else {
				// System.out.println("//目录存在");
			}

			FileInputStream input = new FileInputStream(oldPath);// 可替换为任何路径何和文件名
			FileOutputStream output = new FileOutputStream(newPath);// 可替换为任何路径何和文件名
			int in = input.read();
			while (in != -1) {
				output.write(in);
				in = input.read();
			}
		} catch (IOException e) {
			System.out.println(e.toString());
		}
	}
	/**
	 * 复制文件夹及所有内容的方法
	 * 迭代文件夹的内容
	 */
	private static void copy(String src, String des) {  
        	File file1=new File(src);  
        	File[] fs=file1.listFiles();  
        	File file2=new File(des);  
        	if(!file2.exists()){  
        		file2.mkdirs();  
        	}  
        	for (File f : fs) {  
        		if(f.isFile()){  
        			fileCopy(f.getPath(),des+"/"+f.getName()); //调用文件拷贝的方法  
        		}else if(f.isDirectory()){  
        			copy(f.getPath(),des+"/"+f.getName());  
        		}  
        	}  
          
    }  
    /** 
     * 文件拷贝的方法 
     */  
    private static void fileCopy(String src, String des) {  
        BufferedReader br=null;  
        PrintStream ps=null;  
        try {  
            br=new BufferedReader(new InputStreamReader(new FileInputStream(src)));  
            ps=new PrintStream(new FileOutputStream(des));  
            String s=null;  
            while((s=br.readLine())!=null){  
                ps.println(s);  
                ps.flush();  
            }  
        } catch (FileNotFoundException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }finally{  
                try {  
                    if(br!=null)  br.close();  
                    if(ps!=null)  ps.close();  
                } catch (IOException e) {  
                    // TODO Auto-generated catch block  
                    e.printStackTrace();  
                }  
        }  
    }  
  
	
}
