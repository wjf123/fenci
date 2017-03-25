package logicPK;

import java.io.*;

public class FileOP {
	public String readFile(String path) throws Exception{
		String str="",line="";
		BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(path),"UTF-8"));
		while(!((line=br.readLine())==null)){
			str=str+line;
		}
		br.close();
		return str;
	}
	
	public void writeFile(String title,String name,String dir)
	{
		
		String t=title;
		if(t.length()>0)
		{
			//String path=dir+"_"+name+".txt";
			String path=dir+"_"+name;
			File f=new File(dir);
			if(!f.exists())
			{
				f.mkdirs();
			}
			
			f=new File(path);
			if(!f.exists())
			{
				try {
					f.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				FileOutputStream fs=new FileOutputStream(f);
				OutputStreamWriter os=new OutputStreamWriter(fs,"UTF-8");
				BufferedWriter bfw=new BufferedWriter(os);
				bfw.write(t);
				bfw.close();
				System.out.println("succedd");
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}

}
