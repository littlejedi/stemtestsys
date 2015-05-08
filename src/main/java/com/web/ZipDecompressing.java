package com.web;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.*;
/**
 * ����ʵ����ZIPѹ��������Ϊ2���� ��
 * ѹ����compression�����ѹ��decompression��
 * <p>
 * ���¹��ܰ������˶�̬���ݹ��JAVA���ļ��������ԶԵ����ļ������⼶���ļ��н���ѹ���ͽ�ѹ��
 * ���ڴ������Զ���Դ����·����Ŀ�����·����
 * <p>
 * �ڱ��δ����У�ʵ�ֵ��ǽ�ѹ���֣�ѹ�����ּ����compression���֡�
 * @author HAN
 *
 */
public class ZipDecompressing {
	
	
	public void decompressing(File zipFile,String descDir)
	{
		File pathFile = new File(descDir);  
        if(!pathFile.exists()){  
            pathFile.mkdirs();  
        }  
        ZipFile zip;
		try {
			zip = new ZipFile(zipFile);
		
        for(Enumeration entries = zip.entries();entries.hasMoreElements();){  
            ZipEntry entry = (ZipEntry)entries.nextElement();  
            String zipEntryName = entry.getName();  
            InputStream in = zip.getInputStream(entry);  
            String outPath = (descDir+zipEntryName).replaceAll("\\*", "/");;  
            //�ж�·���Ƿ����,�������򴴽��ļ�·��  
            File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));  
            if(!file.exists()){  
                file.mkdirs();  
            }  
            //�ж��ļ�ȫ·���Ƿ�Ϊ�ļ���,����������Ѿ��ϴ�,����Ҫ��ѹ  
            if(new File(outPath).isDirectory()){  
                continue;  
            }  
            //����ļ�·����Ϣ  
           // System.out.println(outPath);  
              
            OutputStream out = new FileOutputStream(outPath);  
            byte[] buf1 = new byte[1024];  
            int len;  
            while((len=in.read(buf1))>0){  
                out.write(buf1,0,len);  
            }  
            in.close();  
            out.close();  
            }  
       // System.out.println("******************��ѹ���********************"); 
		} catch (ZipException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}

}

