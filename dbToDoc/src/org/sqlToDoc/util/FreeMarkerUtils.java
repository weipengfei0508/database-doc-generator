package org.sqlToDoc.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;
import freemarker.template.Configuration;
import freemarker.template.Template;
  
/** 
 * @Description:freemarker������
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
public class FreeMarkerUtils {  
      
    /** �Ƿ��ѳ�ʼ�� */  
    private static boolean isInit = false;  
      
    /** Ӧ������·�� */  
    private static String appPath = null;  
      
    /** �����ʽ UTF-8 */  
    private static final String ENCODING = "UTF-8";  
  
    /** FreeMarker���� */  
    private static Configuration config = new Configuration();  
      
    /** ·���ָ�� */  
    public static final String PATH_SEPARATOR = "/";  
    
    /** 
     * ��ʼ��FreeMarker���� 
     * @param applicationPath Ӧ������·�� 
     * @throws IOException 
     */  
    public static void initFreeMarker(String applicationPath) throws IOException {
        if (!(isInit)) {
            appPath = applicationPath;  
            // ����ģ��
            File file = new File(new StringBuffer(appPath).append(File.separator).toString());  
            // ����Ҫ������ģ�����ڵ�Ŀ¼��������ģ���ļ�  
            config.setDirectoryForTemplateLoading(file);
            // �����ļ�����ΪUTF-8
            config.setEncoding(Locale.getDefault(), ENCODING);  
            isInit = true;
        }  
    }  
  
    /** 
     * �����ݼ�ģ�������ļ� 
     *  
     * @param data 
     *            һ��Map�����ݽ���� 
     * @param templateFileName 
     *            ftlģ��·��(��Ĭ��ΪWEB-INF/templates,�ļ�����Դ�·��)  
     * @param outFileName 
     *            �����ļ�����(�ɴ�·��) 
     * @param isAbsPath 
     *            �Ƿ����·�� 
     * @throws Exception 
     */  
    public static void crateFile(Map<String, Object> data, String templateFileName, String outFileName, boolean isAbsPath) throws Exception {  
        if(!isInit){  
        	throw new RuntimeException("FreeMarkerģ������δ��ʼ��,��ȷ���Ѿ�����initFreeMarker()������������˳�ʼ��");
        }  
        Writer out = null;  
        try {  
            // ��ȡģ��,�����ñ��뷽ʽ������������Ҫ��ҳ���еı����ʽһ��  
            Template template = config.getTemplate(templateFileName, ENCODING);  
            template.setEncoding(ENCODING);  
              
            // �����ļ�·��  
            // ����Ǿ���·����ֱ��ʹ��  
            if(isAbsPath){  
                outFileName = new StringBuffer(outFileName).toString();  
            } else{  
                // ���·����ʹ��Ĭ�ϵ�appPath����������ļ�·��  
                outFileName = new StringBuffer(appPath).append(File.separator).append(outFileName).toString();  
            }  
            File outFile = new File(outFileName);  
            out = new OutputStreamWriter(new FileOutputStream(outFile), ENCODING);  
              
            // ����ģ��  
            template.process(data, out);  
              
            out.flush();  
        } finally{  
            try {  
                if(out != null){  
                    out.close();  
                }  
            } catch (IOException e) {  
            	e.printStackTrace();
            }  
        }  
    }  
  
}
