package test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class TCPServer 
{
	public static void main(String[] args) 
	{
		ServerSocket serverSocket = null;
		try 
		{
			// 1. 서버소켓 생성
			serverSocket = new ServerSocket();
			
			// 1-1. Time-wait 시간에 소켓에 포트번호 할당을 가능하게 하기 위해서
			serverSocket.setReuseAddress(true);
			
			
			// 2. 바인딩(binding)
			//  : Socket에 SocketAddress(IPAddress + Port)를 바인딩 한다.
			InetAddress inetAddress = InetAddress.getLocalHost();
			String localhost = inetAddress.getHostAddress();
			serverSocket.bind(new InetSocketAddress("0.0.0.0", 6000));
			//serverSocket.bind(new InetSocketAddress(inetAddress, 5000));
			
			// 3. accept
			//  : 클라이언트의 연결요청을 기다림.
			Socket socket = serverSocket.accept(); // blocking    * 비동기는 non-blocking
			
			InetSocketAddress inetRemoteSocketAddress =
					(InetSocketAddress)socket.getRemoteSocketAddress();
			String remoteHostAddress = inetRemoteSocketAddress.getAddress().getHostAddress();
			int remotePort = inetRemoteSocketAddress.getPort();
			System.out.println("[server]connected by client["+ 
			remoteHostAddress + ":" + remotePort +"]");
			
			// * Server소켓과 Data소켓의 예외처리를 구별해야한다.
			try
			{
				// 4. ISOtream 받아오기
				InputStream is = socket.getInputStream();
				OutputStream os = socket.getOutputStream();
				
				while(true)
				{
					// 5. 데이터 읽기
					byte[] buffer = new byte[256];
					int readByteCount = is.read(buffer);
					
					if(readByteCount == -1)
					{
						// 클라이언트가 정상종료 한 경우
						// close() 메소드호출
						System.out.println("[server] close by client");
						break;
					}
					
					String data = new String(buffer, 0, readByteCount, "utf-8");
					System.out.println("[server] receiver: " + data);
					
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//6. 데이터 쓰기
					os.write(data.getBytes("utf-8"));
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
			}
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
}
