package demo4;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class FileRead {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String path="D:\\1.txt";
		FileInputStream fs=new FileInputStream(path);
		InputStreamReader isr=new InputStreamReader(fs,"gbk");
		BufferedReader br=new BufferedReader(isr);//缓冲流
		
		
		String str;
		while ((str=br.readLine())!=null) {
			System.out.println(str);
		}
		
		
		fs.close();
		isr.close();
		br.close();

	}

}
