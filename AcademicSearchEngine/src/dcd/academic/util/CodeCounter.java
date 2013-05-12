package dcd.academic.util;

import java.io.BufferedReader;  
import java.io.File;  
import java.io.FileReader;  
import java.io.IOException;  
/** 
 * 统计代码量程序 
 * @author LiangGzone 
 */  
public class CodeCounter {  
    // 定义代码行数  
    private static long codeLines = 0;  
    // 定义注释行数  
    private static long commentLines = 0;  
    // 定义空白行数  
    private static long blankLines = 0;  
    // 定义路径  
    private static String path = "E:\\GitFolder\\VerticleSearchEngine";  
  
  
      
    /** 
     * 主函数 
     * @param args 
     */  
    public static void main(String[] args){   
        File file = new File(path);  
        selectAllFiles(file);  
        System.out.println("代码行数："+codeLines);  
        System.out.println("注释行数："+commentLines);  
        System.out.println("空白行数："+blankLines);  
        System.out.println("总代码量："+(codeLines+commentLines+blankLines));  
    }  
      
    /** 
     * 代码处理 
     * @param file 
     */  
    public static void parse(File file){  
        BufferedReader br = null;  
        boolean comment = false;  
        try {  
            br = new BufferedReader(new FileReader(file));  
            String line = null;  
            while((line=br.readLine()) != null){  
                // 忽略前导空白和尾部空白  
                line = line.trim();  
                if(line.matches("^[\\s&&[^\\n]]*")){  
                    blankLines++;  
                }else if(line.startsWith("/*") && !line.endsWith("*/")){  
                    commentLines++;  
                    comment = true;  
                }else if(comment){  
                    commentLines++;  
                    if(line.endsWith("*/")){  
                        comment = false;  
                    }  
                }else if(line.startsWith("/*") && line.endsWith("*/")){  
                    commentLines++;  
                }else if(line.startsWith("//")){  
                    commentLines++;  
                }else {  
                    codeLines++;  
                }  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally{  
            if(br != null){  
                try {  
                    br.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
            br = null;  
        }  
    }  
      
  
  
    /** 
     * 递归遍历文件夹 
     * @param file 
     */  
    private static void selectAllFiles(File file) {  
        File[] files = file.listFiles();  
        for (File f : files) {  
            if(f.getName().matches(".*\\.java")){  
                parse(f);  
            }  
            if(f.isDirectory()){  
                selectAllFiles(f);  
            }  
        }  
    }  
}