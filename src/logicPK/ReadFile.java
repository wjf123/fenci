package logicPK;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class ReadFile {
        public ReadFile() {
        }
        /**
         * ��ȡĳ���ļ����µ������ļ�
         */
        public List<String> readfile(String filepath) throws FileNotFoundException, IOException {
        	List<String> pathList=new ArrayList<>();
                

                        File file = new File(filepath);
                        if (!file.isDirectory()) {
                                System.out.println("�ļ�");
                                System.out.println("path=" + file.getPath());
                                System.out.println("absolutepath=" + file.getAbsolutePath());
                                System.out.println("name=" + file.getName());

                        } else{
                                System.out.println("�ļ���");
                                String[] filelist = file.list();
                                for (int i = 0; i < filelist.length; i++) {
                                        File readfile = new File(filepath + "\\" + filelist[i]);
                                        if (!readfile.isDirectory()) {
                                        	pathList.add(readfile.getAbsolutePath());
                                        	System.out.println("path=" + readfile.getPath());
                                            System.out.println("absolutepath=" + readfile.getAbsolutePath());
                                            System.out.println("name=" + readfile.getName());
                                        	
                                            
                                        } 
                                }
                                System.out.println(filelist.length);

                        }

                return pathList;
        }

        /**
         * ɾ��ĳ���ļ����µ������ļ��к��ļ�
         */
        
        
        /*public static boolean deletefile(String delpath)
                        throws FileNotFoundException, IOException {
                try {

                        File file = new File(delpath);
                        if (!file.isDirectory()) {
                                System.out.println("1");
                                file.delete();
                        } else if (file.isDirectory()) {
                                System.out.println("2");
                                String[] filelist = file.list();
                                for (int i = 0; i < filelist.length; i++) {
                                        File delfile = new File(delpath + "\\" + filelist[i]);
                                        if (!delfile.isDirectory()) {
                                                System.out.println("path=" + delfile.getPath());
                                                System.out.println("absolutepath="
                                                                + delfile.getAbsolutePath());
                                                System.out.println("name=" + delfile.getName());
                                                delfile.delete();
                                                System.out.println("ɾ���ļ��ɹ�");
                                        } else if (delfile.isDirectory()) {
                                                deletefile(delpath + "\\" + filelist[i]);
                                        }
                                }
                                file.delete();

                        }

                } catch (FileNotFoundException e) {
                        System.out.println("deletefile()   Exception:" + e.getMessage());
                }
                return true;
        }*/
        
        public static void main(String[] args) {
        	ReadFile rf=new ReadFile();
                try {
                        rf.readfile("F:\\opr\\other\\");
                        // deletefile("D:/file");
                } catch (FileNotFoundException ex) {
                } catch (IOException ex) {
                }
                System.out.println("ok");
        }

}