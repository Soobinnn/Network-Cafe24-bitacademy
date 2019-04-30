package test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class TCPClient 
{
	// Java에선 상수의 의미가 없고 final을 선언하면 값이 마지막이란 의미
	private static final String SERVER_IP = "192.168.1.39";
	private static final int SERVER_PORT = 6000;
	
	// method에 final이 붙으면 오버라이딩을 하지말라는 의미.
	// class에 fianl이 붙으면 상속을 하지말라는 의미
	public static void main(String[] args) 
	{
		// 1. 소켓 생성
		Socket socket = new Socket();
		
		try
		{
	
			// 2. 서버 연결		
			socket.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT));
			System.out.println("[Client] connect ");
			
			// 3. IOStream 받아오기
			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();
			
			// 4. 쓰기
			String data = "Hello World\n";
			os.write(data.getBytes("utf-8"));
			
			// 5. 읽기
			byte[] buffer = new byte[256];
			int readByteCount = is.read(buffer); // blocking
			
			if(readByteCount == -1)
			{
				System.out.println("[Client] closed by server");
			}
			data = new String(buffer, 0, readByteCount, "utf-8");
			System.out.println("[Client] received : " + data);
			
		
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try 
			{
				if(socket != null && socket.isClosed() == false)
					socket.close();
			} catch (IOException e) 
			{
				e.printStackTrace();
			} 
		}

		
	}
}
