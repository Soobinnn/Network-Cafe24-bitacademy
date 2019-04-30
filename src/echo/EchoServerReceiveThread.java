package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

public class EchoServerReceiveThread extends Thread
{
	private Socket socket;
	
	public EchoServerReceiveThread(Socket socket)
	{
		this.socket = socket;
	}

	@Override
	public void run() {
		InetSocketAddress inetRemoteSocketAddress =
				(InetSocketAddress)socket.getRemoteSocketAddress();
		String remoteHostAddress = inetRemoteSocketAddress.getAddress().getHostAddress();
		int remotePort = inetRemoteSocketAddress.getPort();
		EchoServer.log("connected by client[" + remoteHostAddress + ":" + remotePort +"]");
		
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
					EchoServer.log("close by client");
					break;
				}
				
				//String data = new String(buffer, 0, readByteCount, "utf-8");
				EchoServer.log(" received: " + data);
				
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
		}
	}
	
	
}
