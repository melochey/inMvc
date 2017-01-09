package com.example.rose;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.example.dao.LivingWordsDao;
import com.example.dao.TasklistDao;
import com.example.model.LivingWords;
import com.example.model.Tasklist;
import com.example.util.servletUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class DealLivingWords {
//	private static ApplicationContext ctx = new ClassPathXmlApplicationContext("application.xml");
//	private static LivingWordsDao livingWordsDao=(LivingWordsDao) ctx.getBean("livingWordsDao");
	public static void main(String[] m){

		DealLivingWords d=new DealLivingWords();
		d.setFilePath("E:\\roseWords\\2016-11-03\\1487390");
		d.setFmId(1487390);
		d.setCategoryId("20161103");
		d.saveComments();
	}
	
	private String filePath;
	
	private int fmId;
	
	
	public int getFmId() {
		return fmId;
	}

	public void setFmId(int fmId) {
		this.fmId = fmId;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	private String categoryId;
	
	public void saveComments(){
		Tasklist task=new Tasklist();
		task.setFmid(fmId);
		task.setCategoryid(categoryId);
		task.setFinished(1);
		TasklistDao td=(TasklistDao)servletUtil.getContext().getBean("tasklistDao");
		td.update(task);
		LivingWordsDao livingWordsDao=(LivingWordsDao) servletUtil.getContext().getBean("livingWordsDao");
		LivingWords d=new LivingWords();
		d.setFmid(fmId);
		d.setCategoryId(categoryId);
		livingWordsDao.delete(d);
		File f=new File(filePath);
		File[] files = f.listFiles();
		if(files==null){
			return;//还未抓取
		}
		List<File> fileList = new LinkedList<File>();
		for (File a : files) {
		    fileList.add(a);
		}
		Collections.sort(fileList, new Comparator<File>(){
			public int compare(File o1, File o2) {
				String f1=o1.getName();
				int l=f1.indexOf(".txt");
				int l2=f1.lastIndexOf("\\");
				int a1=Integer.valueOf(f1.substring(l2+1, l));
				String f2=o2.getName();
				int l21=f2.indexOf(".txt");
				int l22=f2.lastIndexOf("\\");
				int a2=Integer.valueOf(f2.substring(l22+1, l21));
				if(a1>a2){
					return -1;
				}else if(a1<a2){
					return 1;
				}
				return 0;
			}
			
		});
		Reader reader = null;
		try {
			for(int n=0;n<fileList.size();n++){
					File t=fileList.get(n);
					StringBuffer sb=new StringBuffer();
					reader = new InputStreamReader(new FileInputStream(t));
					int tempchar;
		            while ((tempchar = reader.read()) != -1) {
		                // 对于windows下，\r\n这两个字符在一起时，表示一个换行。
		                // 但如果这两个字符分开显示时，会换两次行。
		                // 因此，屏蔽掉\r，或者屏蔽\n。否则，将会多出很多空行。
		                if (((char) tempchar) != '\r') {
		                	sb.append((char) tempchar);
		                }
		            }
					JSONArray array=JSONArray.fromObject(sb.toString());;
					for(int i=0;i<array.size();i++){
						JSONObject obj = (JSONObject) array.get(i);
						LivingWords e=new LivingWords() ;
						e.setCategoryId(categoryId);
						e.setComment(obj.get("comment").toString());
						e.setFmid(fmId);
						e.setUrl(obj.get("url").toString());
						e.setName(obj.get("name").toString());
						e.setCreateTiem(new Date());
						livingWordsDao.insert(e);
					}
					//删掉已经导入的数据
//					String newPath="E:\\oldRose\\"+this.categoryId+"\\"+this.fmId;
//					File n=new File(newPath);
//					if(!n.exists()){
//						n.mkdirs();
//					}
//					newPath+="\\"+fileName;
//					File f1=new File(newPath);
//					if(!f1.exists()){
//						f1.createNewFile();
//					}
//					FileOutputStream   out=new FileOutputStream(f1);
//					out.write(sb.toString().getBytes());
					String fileName=t.getName();
					System.out.println(fileName);
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	 public static void readFileByBytes(String fileName) {
	        File file = new File(fileName);
	        InputStream in = null;
	        try {
	            System.out.println("以字节为单位读取文件内容，一次读一个字节：");
	            // 一次读一个字节
	            in = new FileInputStream(file);
	            int tempbyte;
	            while ((tempbyte = in.read()) != -1) {
	                System.out.write(tempbyte);
	            }
	            in.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	            return;
	        }
	        
	    }
	 /**
	     * 以字符为单位读取文件，常用于读文本，数字等类型的文件
	     */
	    public static void readFileByChars(String fileName) {
	        File file = new File(fileName);
	        Reader reader = null;
	        try {
	            System.out.println("以字符为单位读取文件内容，一次读一个字节：");
	            // 一次读一个字符
	            reader = new InputStreamReader(new FileInputStream(file));
	            int tempchar;
	            while ((tempchar = reader.read()) != -1) {
	                // 对于windows下，\r\n这两个字符在一起时，表示一个换行。
	                // 但如果这两个字符分开显示时，会换两次行。
	                // 因此，屏蔽掉\r，或者屏蔽\n。否则，将会多出很多空行。
	                if (((char) tempchar) != '\r') {
	                    System.out.print((char) tempchar);
	                }
	            }
	            reader.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}
