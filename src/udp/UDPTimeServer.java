package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UDPTimeServer 
{
	public static final int PORT = 7000;
	public  static final int BUFFER_SIZE = 1024;
	
	public static void main(String[] args) 
	{
		DatagramSocket socket = null;
		try 
		{
			// 1. socket 생성
			socket = new DatagramSocket(PORT);
			
			
			while(true)
			{
				// 2. 데이터 수신
				DatagramPacket receivePacket = 
						new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE);
			
				socket.receive(receivePacket); //blocking
				
				byte[] data = receivePacket.getData();
				int length = receivePacket.getLength(); // 패킷안에 얼마나 있는 지 확인 후 인코딩
				String message = new String(data, 0, length, "UTF-8");
				
				System.out.println("[server] received :" + message);
				// 3. 데이터 전송
				byte[] sendData = message.getBytes("utf-8");
				
				// **** [과제]Time포멧만들기 ****
				if("".equals(message))
				{
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a");
					String timeData = format.format(new Date());
					sendData = timeData.getBytes("utf-8");
					System.out.println("[server] time : "+ timeData);
				}
				
				 DatagramPacket sendPacket=new DatagramPacket(sendData, sendData.length,receivePacket.getAddress(),receivePacket.getPort());
				 socket.send(sendPacket);
				
			}
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			if(socket != null && socket.isClosed()==false)
				socket.close();
		}
	}
}
