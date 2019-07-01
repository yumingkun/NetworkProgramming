package socket8_Gui_UsersChat;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.DatatypeConverter;

/**
 * Base64转换
 */
public class Base64 {

	public static void main(String[] args) {
//		String str="command1|";
//
//		String [] strs=str.split("\\|");
//
//		System.out.println(strs[0]);
//		System.out.println(strs[1]);
//		System.out.println(strs.length);
//		System.out.println(strs[0]);


		try {
			String str="ADDFJD34324\r\n一需要一直在城西 奇才";
			String strMSG=DatatypeConverter.printBase64Binary(str.getBytes("UTF-8"));
			System.out.println(strMSG);

			byte[] bmsg=DatatypeConverter.parseBase64Binary(strMSG);
			String sRec=new String(bmsg, "UTF-8");
			System.out.println(sRec);

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
