package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class EchoServer 
{
	private static final int SERVER_PORT = 7000;
	
	public static void main(String[] args)
	{
		ServerSocket serverSocket = null;
		try 
		{
			// 1. 서버소켓 생성
			serverSocket = new ServerSocket();
		
			// 2. 바인딩(binding)
			//  : Socket에 SocketAddress(IPAddress + Port)를 바인딩 한다.
			InetAddress inetAddress = InetAddress.getLocalHost();
			String localhost = inetAddress.getHostAddress();
			serverSocket.bind(new InetSocketAddress("0.0.0.0", SERVER_PORT));
			log("server starts...[port:"+SERVER_PORT+"]");
			//serverSocket.bind(new InetSocketAddress(inetAddress, 5000));
			
			// 3. accept
			//  : 클라이언트의 연결요청을 기다림.
			while(true)
			{
				Socket socket = serverSocket.accept(); // blocking    * 비동기는 non-blocking
				
				Thread thread = new EchoServerReceiveThread(socket);
				thread.start();
			}
			//
			//Socket socket = serverSocket.accept(); // blocking    * 비동기는 non-blocking
			
			/*InetSocketAddress inetRemoteSocketAddress =
					(InetSocketAddress)socket.getRemoteSocketAddress();
			String remoteHostAddress = inetRemoteSocketAddress.getAddress().getHostAddress();
			int remotePort = inetRemoteSocketAddress.getPort();
			log("connected by client[" + remoteHostAddress + ":" + remotePort +"]");
			
			// * Server소켓과 Data소켓의 예외처리를 구별해야한다.
			try
			{
				// 4. ISOtream 생성(받아오기)
				//InputStream is = socket.getInputStream();
				//OutputStream os = socket.getOutputStream();
				
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"utf-8"));
				
				PrintWriter pr = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),"utf-8"), true);
				
				while(true)
				{
					// 5. 데이터 읽기
					String data = br.readLine();
					//byte[] buffer = new byte[256];
					//int readByteCount = is.read(buffer);
					
					if(data == null)
					{
						// 클라이언트가 정상종료 한 경우
						// close() 메소드호출
						log("close by client");
						break;
					}
					
					//String data = new String(buffer, 0, readByteCount, "utf-8");
					log(" received: " + data);
					
					//6. 데이터 쓰기
					pr.println(data);
					//os.write(data.getBytes("utf-8"));
				}
				
			}
			catch(SocketException e)
			{
				System.out.println("[server] sudden closed by client");
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
					{
						socket.close();
					}
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
			}*/
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			try 
			{
				if(serverSocket != null && serverSocket.isClosed() == false)
				{
					serverSocket.close();	
				}
			} 
			catch (IOException e) 
			{
			
				e.printStackTrace();
			}
		}
	}
	
	public static void log(String log)
	{
		System.out.println("[server#" + Thread.currentThread().getId() +" "+log);
	}
}
