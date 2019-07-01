package demo4;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class FileWriter {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String path="D:\\2.txt";
		FileOutputStream fs=new FileOutputStream(path);
		OutputStreamWriter ow=new OutputStreamWriter(fs, "gbk");
		BufferedWriter bw=new BufferedWriter(ow);
		
		bw.write("111\t\n");
		bw.write("222\t\n");
		bw.flush();
		
		fs.close();
		ow.close();
		bw.close();
		System.out.println("over");
	}

}
