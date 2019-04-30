package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient 
{
	private static final String SERVER_IP = "192.168.1.39";
	private static final int SERVER_PORT = 7000;
	
	public static void main(String[] args) 
	{
		Scanner scanner = null;
		
		Socket socket = null;
		
		try
		{
			// 0. Scanner 생성 ( 표준입력 연결)
			scanner = new Scanner(System.in);
			
			// 1. 소켓 생성
			socket = new Socket();
			
			// 1-1. 소켓 버퍼 사이즈 확인
			int receiveBufferSize = socket.getReceiveBufferSize();
			int sendBufferSize = socket.getSendBufferSize();
			System.out.println(receiveBufferSize + ":" + sendBufferSize);
			
			// 1-2. 소켓 버퍼 사이즈 변경
			// 소켓 버퍼 사이즈에 따른 속도차이는 별로 차이가없다. 서버에서 빨리 데이터를 읽는 것이 중요하다.	
			socket.setReceiveBufferSize(1024*10);
			socket.setSendBufferSize(1024*10);
			receiveBufferSize = socket.getReceiveBufferSize();
			sendBufferSize = socket.getSendBufferSize();
			System.out.println(receiveBufferSize + ":" + sendBufferSize);
			
			// 2. 서버 연결		
			socket.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT));
			log("connect ");
			
			// 3. IOStream 받아오기
			//InputStream is = socket.getInputStream();
			//OutputStream os = socket.getOutputStream();
			
			// 4. 쓰기
			//String data = "Hello World\n";
			//os.write(data.getBytes("utf-8"));
			
			// 5. 읽기
			//byte[] buffer = new byte[256];
			//int readByteCount = is.read(buffer); // blocking
			
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"utf-8"));
			
			PrintWriter pr = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),"utf-8"), true);
			
			while(true)
			{
				System.out.println(">>");
				String line = scanner.nextLine();
				if("quit".contentEquals(line))
				{
					break;
				}
				
				// 6. 데이터 쓰기
				pr.println(line);
				
				// 7. 데이터 읽기
				String data = br.readLine();
				if(data == null)
				{
					log("Closed ny server");
					break;
				}
				
				// 8. 콘솔출력
				System.out.println("<<" + data);
			}
			
			/*if(readByteCount == -1)
			{
				log("closed by server");
			}
			data = new String(buffer, 0, readByteCount, "utf-8");
			log("received : " + data);*/
		
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
	
	public static void log(String log)
	{
		System.out.println("[client] "+log);
	}
}
