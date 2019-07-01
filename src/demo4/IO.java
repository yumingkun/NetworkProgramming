package demo4;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
 

public class IO {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		InputStream is=System.in;//字节流
		InputStreamReader isr=new InputStreamReader(is, "gbk");//字符流
		BufferedReader br=new BufferedReader(isr);//缓冲流
		String string=br.readLine();
		System.out.println("输入的是"+string);
	}

}
