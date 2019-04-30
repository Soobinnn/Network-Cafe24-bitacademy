package udp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class UDPEchoClient 
{
	private static final String SERVERIP = "192.168.1.39";
	public static void main(String[] args) 
	{
		Scanner scanner = null;
		DatagramSocket socket = null;
		
		try
		{
			//1. Scanner 생성(표준입출력 연결)
			scanner=new Scanner(System.in);
			//2. 소켓생성
			socket= new DatagramSocket();
			  
			
			while(true) 
			{
				//3. keyboard 입력받기
				System.out.print(">>");
				String line=scanner.nextLine();
				if("quit".contentEquals(line)) {
					break;
				}
				
				//4. 데이터 쓰기
				 byte[] sendData= line.getBytes();
				 DatagramPacket sendPacket=new DatagramPacket(sendData, sendData.length, new InetSocketAddress(SERVERIP,UDPEchoServer.PORT));
				 socket.send(sendPacket);
				
				//5. 데이터 읽기
				  DatagramPacket receivePacket=new DatagramPacket(new byte[UDPEchoServer.BUFFER_SIZE], UDPEchoServer.BUFFER_SIZE);
					 socket.receive(receivePacket); //blocking
					 String message=new String(receivePacket.getData(),0,receivePacket.getLength(),"UTF-8");
				//8. console 출력
				System.out.println("<<"+message);
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			 if(scanner!=null) 
			 {
				 scanner.close();
			 }
			 if(socket!=null && !socket.isClosed() ) 
			 {
				 socket.close();
			 }
		}
	}
	
	public static void log(String log)
	{
		System.out.println("[client] "+log);
	}
}
