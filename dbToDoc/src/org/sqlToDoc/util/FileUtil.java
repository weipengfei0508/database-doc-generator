package org.sqlToDoc.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 
 * Description: �ļ�������
 * 
 * @author jiujiya
 * @version 1.0 
 */
public class FileUtil {

    /**
     * д����
     * @param file
     * @param content
     * @throws Exception 
     */
    public static void createFile(String path, String content) throws Exception {
    	File file = new File(path);
    	if(!file.exists())    
    	{    
    	    try {    
    	        file.createNewFile();    
    	    } catch (IOException e) {
                UtilHiddleException.hiddleException(e);
    	    }    
    	}else {
    		throw new Exception("�ļ������޷�����" + file.getAbsolutePath());
    	}
        BufferedWriter bw = null;
        try {
            FileWriter fw = new FileWriter(file, true);
            bw = new BufferedWriter(fw);
            bw.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * ׷������
     * @param file
     * @param content
     */
    public static void writeToTxtByFileWriter(File file, String content) {
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;
		try {
			fos = new FileOutputStream(file);
			osw = new OutputStreamWriter(fos, "UTF-8");
			osw.write(content);
			osw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			close(osw);
			close(fos);
		}
    }
    
    
	/**
	 * ��UTF-8��ȡ �ı��ļ�
	 * @param path
	 * @return
	 */
	public static StringBuffer readTextFile(String path) {
		return readTextFile(path, "UTF-8");
	}
    
    public static List<String> readTextsFile(String path){
        return readTextsFile(path, "UTF-8");
    }
    
    /**
     * ��� Ҫ����� �ļ� ���ļ���
     * @param path
     * @return
     */
    public static String getDir(String path){
        
        int youindexOf = path.lastIndexOf("\\");
        int zuoindexOf = path.lastIndexOf("/");
        
        if(zuoindexOf > youindexOf){
            path = path.substring(0, path.lastIndexOf("/"));
        }else{
            path = path.substring(0, path.lastIndexOf("\\"));
        }
        
        return path + "/";
    }

    /**
     * ��� �ļ�������
     * @param path
     * @return
     */
    public static String getFileType(String path){
        return path.substring(path.lastIndexOf(".") + 1, path.length());
    }
    
    /**
     * ��� Ҫ����� �ļ� ���ļ���
     * @param path
     * @return
     */
    public static String getFileName(String path){
        
        int youindexOf = path.lastIndexOf("\\");
        int zuoindexOf = path.lastIndexOf("/");
        
        if(zuoindexOf > youindexOf){
            path = path.substring(path.lastIndexOf("/") + 1, path.length());
        }else{
            path = path.substring(path.lastIndexOf("\\") + 1, path.length());
        }
        
        return path;
    }
    
    /**
     * ���� Ҫ����� �ļ� ���ļ���
     * @param path
     */
    public static void addDirs(String path){
        try {
            File dirs = new File(getDir(path));
            if (!dirs.exists()) {
                dirs.mkdirs();
            }
        } catch (Exception e) {
            UtilHiddleException.hiddleException(e);
        }
    }

    /**
     * �ر���Դ
     * @param close
     */
    public static void close(Closeable obj) {
        if (obj != null) {
            try {
                obj.close();
            } catch (IOException e) {
                // �����ܴ��ڵ��쳣
                UtilHiddleException.hiddleException(e);
            }
        }
    }
    
    /**
     * ��ȡ �ı��ļ�
     * @param path
     * @param encoding ����
     * @return
     */
    public static List<String> readTextsFile(String path, String encoding){
        File file = new File(path);
        if (!file.exists()) return null;
        List<String> list = new ArrayList<String>();
        BufferedReader br = null;
        try {
            try {
                br = new BufferedReader(new InputStreamReader (new FileInputStream(file), encoding));
                String line = null;
                while( (line = br.readLine()) != null) {
                    list.add(line);
                }
                return list;
            }finally {
                if (br != null) {
                    br.close();
                }
            }
        } catch (Exception e) {
            UtilHiddleException.hiddleException(e);
            return null;
        }
    }
    
	/**
	 * ��ȡ �ı��ļ�
	 * @param path
	 * @param encoding ����
	 * @return
	 */
	public static StringBuffer readTextFile(String path, String encoding) {
		File file = new File(path);
		if (!file.exists()) return new StringBuffer("");
		StringBuffer str = new StringBuffer();
        List<String> list = readTextsFile(path, encoding);
        for (String string : list) {
            str.append(string);
        }
        return str;
	}
	
	/**
	 * ��� �ı��ļ� ������
	 * @param path
	 * @return
	 */
	public static long getLine(String path) {
		File file = new File(path);
		if (!file.exists()) return 0;
		long lines = 0;
		BufferedReader br = null;
		try {
			try {
				br = new BufferedReader(new FileReader(file));
				while(br.readLine() != null) {
					lines ++;
				}
				return lines;
			}finally {
				if (br != null) {
					br.close();
				}
			}
		} catch (Exception e) {
			UtilHiddleException.hiddleException(e);
			return 0;
		}
	}

	/**
	 * ��� �ı��ļ� �� �ļ��� �� �ļ� ���ж�����
	 * @param path
	 * @return
	 */
	public static long getTextFileLines(String path, String[] fileSuffix){
		List<String> list = getFileList(path, fileSuffix);
		long lines = 0;
		for (String one : list) {
			System.out.println(one + " : " + getLine(one) + " ��");
			lines += getLine(one);
		}
		return lines;
	}
	
	/**
	 * ��� �ı��ļ� �� �ļ��� �� �ļ� ���ж�����
	 * @param path
	 * @return
	 */
	public static long getTextFileFontLength(String path, String[] fileSuffix){
		List<String> list = getFileList(path, fileSuffix);
		long fontLength = 0;
		for (String one : list) {
			System.out.println(one + " : " + readTextFile(one).toString().length() +  " ��");
			fontLength += readTextFile(one).toString().length();
		}
		return fontLength;
	}

	/**
	 * ����ļ����� �� �ļ�
	 * @param strPath
	 * @return
	 */
	public static List<String> getFileList(String strPath) {
		return new FileList(strPath).getFileList();
	}

	/**
	 * ����ļ����� �� �ļ�
	 * @param strPath �ļ���·��
	 * @param fileSuffix Ҫ����ļ��ĺ�׺
	 * @return
	 */
	public static List<String> getFileList(String strPath, String fileSuffix) {
		return getFileList(strPath, new String[]{fileSuffix});
	}
	
	/**
	 * ����ļ����� �� �ļ�
	 * @param strPath �ļ���·��
	 * @param fileSuffix Ҫ����ļ��еĺ�׺
	 * @return
	 */
	public static List<String> getFileList2(String strPath, String fileSuffix) {
	    return getFileList(strPath, new String[]{fileSuffix});
	}

    /**
     * ����ļ����� �� �ļ�
     * @param strPath �ļ�·��
     * @param fileSuffix Ҫ����ļ��ĺ�׺
     * @return
     */
    public static List<String> getFileList(String strPath, String[] fileSuffix) {
        return getFileList(strPath, fileSuffix, new String[]{});
    }
    
    /**
     * ����ļ����� �� �ļ�
     * @param strPath �ļ���·��
     * @param fileSuffix Ҫ����ļ��ĺ�׺
     * @return
     */
    public static List<String> getFileList2(String strPath, String[] fildDirSuffix) {
        return getFileList(strPath, new String[]{}, fildDirSuffix);
    }
    
	/**
	 * ����ļ����� �� �ļ�
	 * @param strPath �ļ���·��
	 * @param fileSuffix Ҫ����ļ��ĺ�׺
	 * @return
	 */
	private static List<String> getFileList(String strPath, String[] fileSuffix, String[] fildDirSuffix) {
		List<String> list = new ArrayList<String>();
		for (String s : fileSuffix) {
			list.add("." + s);
		}
		List<String> list2 = new ArrayList<String>();
		for (String s : fildDirSuffix) {
		    list2.add(s);
		}
		return new FileList(strPath, list, list2).getFileList();
	}
    
	/**
	 * �ܵ��Թܵ����䣬java����copy����
	 * @param f1
	 * @param f2
	 */
	public static void copyFile(File f1, File f2) {
    	if(!f1.exists()){
    		try {
				f1.createNewFile();
			} catch (IOException e) {
			}
    	}
    	if(!f2.exists()){
    		try {
				f2.createNewFile();
			} catch (IOException e) {
			}
    		
    	}
		FileInputStream in = null;
		FileOutputStream out = null;
		try {
			int length = 2097152;
			in = new FileInputStream(f1);
			out = new FileOutputStream(f2);
			FileChannel inC = in.getChannel();
			FileChannel outC = out.getChannel();
			while (true) {
				if (inC.position() == inC.size()) {
					inC.close();
					outC.close();
					return;
				}
				if ((inC.size() - inC.position()) < 20971520)
					length = (int) (inC.size() - inC.position());
				else
					length = 20971520;
				inC.transferTo(inC.position(), length, outC);
				inC.position(inC.position() + length);
			}
		} catch (Exception e) {
			UtilHiddleException.hiddleException(e);
		} finally{
			close(in);
			close(out);
		}
	}
	
	//�����ļ���
	public static void copyDir(String oldPath, String newPath) throws IOException {
        File file = new File(oldPath);
        String[] filePath = file.list();
        
        if (!(new File(newPath)).exists()) {
            (new File(newPath)).mkdir();
        }
        
        for (int i = 0; i < filePath.length; i++) {

            File sourcePath = new File(oldPath  + file.separator + filePath[i]);
            if (sourcePath.isDirectory()) {
                copyDir(oldPath  + file.separator  + filePath[i], newPath  + file.separator + filePath[i]);
            }
            if (sourcePath.isFile()) {
            	copyFile(sourcePath, new File(newPath + file.separator + filePath[i]));
            }
            
        }
    }
    
    public static byte[] readInputStream(InputStream inStream) throws Exception{  
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
        //����һ��Buffer�ַ���  
        byte[] buffer = new byte[1024];  
        //ÿ�ζ�ȡ���ַ������ȣ����Ϊ-1������ȫ����ȡ���  
        int len = 0;  
        //ʹ��һ����������buffer������ݶ�ȡ����  
        while( (len=inStream.read(buffer)) != -1 ){  
            //���������buffer��д�����ݣ��м����������ĸ�λ�ÿ�ʼ����len�����ȡ�ĳ���  
            outStream.write(buffer, 0, len);  
        }  
        //�ر�������  
        inStream.close();  
        //��outStream�������д���ڴ�  
        return outStream.toByteArray();  
    }  
	
	/**
	 * �������ļ�����
	 * @param url ����url
	 * @param fileDir Ҫ���浽���ص��ļ��� 
	 */
	public static void saveWebFile2(final String url, final String filePath) {
		Thread thread = new Thread() {
			public void run() {
				try {
					URL urll = new URL(url);
					URLConnection uc = urll.openConnection();
					InputStream is = uc.getInputStream();
					File file = new File(filePath);
					FileOutputStream out = new FileOutputStream(file);
					int i = 0;
					while ((i = is.read()) != -1) {
						out.write(i);
					}
					is.close();
				} catch (Exception e) {
					UtilHiddleException.hiddleException(e);
				}
			}
		};
		thread.start();
	}

	/**
	 * �������ļ�����
	 * @param url ����url
	 * @param fileDir Ҫ���浽���ص��ļ��� D: ���� D:/test
	 */
	public static void saveWebFile(final String url, final String fileDir) {
		Thread thread = new Thread() {
			public void run() {
				try {
					URL urll = new URL(url);
					URLConnection uc = urll.openConnection();
					String houzhui = url.substring(url.lastIndexOf("."), url
							.length());
					InputStream is = uc.getInputStream();
					File file = new File(fileDir + "//" + "" + houzhui);
					FileOutputStream out = new FileOutputStream(file);
					int i = 0;
					while ((i = is.read()) != -1) {
						out.write(i);
					}
					is.close();
				} catch (Exception e) {
					UtilHiddleException.hiddleException(e);
				}
			}
		};
		thread.start();
	}
	
	/**
     * �ݹ�ɾ��Ŀ¼�µ������ļ�����Ŀ¼�������ļ�
     * @param dir ��Ҫɾ�����ļ�Ŀ¼
     * @return boolean Returns "true" if all deletions were successful.
     *                 If a deletion fails, the method stops attempting to
     *                 delete and returns "false".
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //�ݹ�ɾ��Ŀ¼�е���Ŀ¼��
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // Ŀ¼��ʱΪ�գ�����ɾ��
        return dir.delete();
    }
    
   
    public static boolean deleteDirChidren(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //�ݹ�ɾ��Ŀ¼�е���Ŀ¼��
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public static List<Map<String, Object>> getFilesNameAndTime(String path,int start, int end){
        List<Map<String, Object>> files=new ArrayList<Map<String,Object>>();
         File file=new File(path);
         File[] tempList = file.listFiles();
         Arrays.sort(tempList, new FileUtil.CompratorByLastModified());  
         System.out.println("��Ŀ¼�¶��������"+tempList.length);
         for (int i = start; i < end; i++) {
        	 if ( i<tempList.length) {
        		 if (tempList[i].isFile()) {
        			 Map<String,Object> filesMap=new HashMap<String, Object>();
        			 filesMap.put("TITLE", tempList[i].getName().toString().replaceAll("\\\\", "/"));
        			 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        			 Calendar cal = Calendar.getInstance();
        			 cal.setTimeInMillis(tempList[i].lastModified());
//        			 filesMap.put("TIME",  sdf.format(cal.getTime()));
        			 files.add(filesMap);
        		 }
			}
         }
        for(Map<String, Object> realfile:files){
              System.out.println("�ļ�   ·����"+realfile.get("fileUrl")+"&�ļ���:"+realfile.get("fileName"));
        }
          
        return files;
    }
  //�����ļ��޸�ʱ����бȽϵ��ڲ���
    static class CompratorByLastModified implements Comparator<File> {  
        
        public int compare(File f1, File f2) {  
            long diff = f1.lastModified() - f2.lastModified();  
            if (diff > 0) {  
                   return -1;  
            } else if (diff == 0) {  
                   return 0;  
            } else {  
                  return 1;  
            }  
        } 

}
	public static long getFileNum(String path) {
		File file=new File(path);
        File[] tempList = file.listFiles();
		return tempList.length;
	}
}

class FileList{
	private List<String> filelist = new ArrayList<String>();
	
	/**
	 * @param strPath Ҫ��ѯ���ļ���
	 * @param fileSuffix Ҫ��ѯ�ļ��ĺ�׺
	 * @param fileDirSuffix 
	 */
	public FileList(String strPath, List<String> fileSuffix, List<String> fileDirSuffix){
		findFileList(strPath, fileSuffix, fileDirSuffix);
	}

    /**
	 * @param strPath Ҫ��ѯ���ļ���
	 */
	public FileList(String strPath){
		findFileList(strPath, null, null);
	}
	
	/**
	 * ����ҵ����ļ���
	 * @return
	 */
	public List<String> getFileList(){
		return filelist;
	}
	/**
	 * �ݹ����ļ�
	 * @param strPath
	 */
	private void findFileList(String strPath, List<String> fileSuffix, List<String> fileDirSuffix) {
		File strFile = new File(strPath);
		if(!strFile.exists()) {
			return;
		}
		if(strFile.isDirectory()) {
			String dirName = strFile.getName();
			if(fileDirSuffix!= null && fileDirSuffix.contains(dirName)) {
				filelist.add(strFile.getAbsolutePath());
			}
			for (File file : strFile.listFiles()) {
				findFileList(file.getAbsolutePath(), fileSuffix, fileDirSuffix);
			}
		} else if(strFile.isFile()){
			String fileName = strFile.getName();
			if(fileName.contains(".")) {
				String suffix = fileName.substring(fileName.lastIndexOf(".")); 
				if(fileSuffix!=null && fileSuffix.contains(suffix)) {
					filelist.add(strFile.getAbsolutePath());
				}else if(fileDirSuffix == null && fileSuffix == null) {
					filelist.add(strFile.getAbsolutePath());
				}
			}else {
				filelist.add(strFile.getAbsolutePath());
			}
		}
	}
}