package tomy.rsa_dh.pkg;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class SMTPMain {
	public static void main(String[] args) {
		// ?????????
		String sender = "cnsmtp01@163.com";
		String receiver = "cnsmtp02@163.com";
		String password = "computer";
		
		// ????????????????Base64????
		String userBase64 = Base64Util.encryptBase64(sender.substring(0,
				sender.indexOf("@")).getBytes());
		String passBase64 = Base64Util.encryptBase64(password.getBytes());
		try {
			Socket socket = new Socket("smtp.163.com", 25);
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					inputStream));
			PrintWriter writter = new PrintWriter(outputStream, true); 
			System.out.println(reader.readLine());

			// HELO
			writter.println("HELO jikexueyuan");
			System.out.println(reader.readLine());

			// AUTH LOGIN >>>Base64
			writter.println("AUTH LOGIN");
			System.out.println(reader.readLine());
			writter.println(userBase64);
			System.out.println(reader.readLine());
			writter.println(passBase64);
			System.out.println(reader.readLine());

			// Set "MAIL FROM" and "RCPT TO"
			writter.println("MAIL FROM:<" + sender + ">");
			System.out.println(reader.readLine());
			writter.println("RCPT TO:<" + receiver + ">");
			System.out.println(reader.readLine());

			// Set "DATA"
			writter.println("DATA");
			System.out.println(reader.readLine());

			writter.println("SUBJECT:??? ??????");
			writter.println("FROM:" + sender);
			writter.println("TO:" + receiver);
			writter.println("Content-Type: text/plain;charset=\"gb2312\"");
			writter.println();
			writter.println("?????? ?§Û?????IT???????????");
			writter.println(".");
			writter.println("");
			System.out.println(reader.readLine());

			// ????????????????????
			writter.println("RSET");
			System.out.println(reader.readLine());
			writter.println("QUIT");
			System.out.println(reader.readLine());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
