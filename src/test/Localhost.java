package test;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Localhost 
{
	public static void main(String[] args) 
	{
		try 
		{
			InetAddress inetAddress = InetAddress.getLocalHost();
			
			String hostname = inetAddress.getHostName();
			String hostAddress = inetAddress.getHostAddress();
			System.out.println(hostname + " : " + hostAddress);
		
			
			//
			InetAddress[] inetAddresses = InetAddress.getAllByName(hostname);
			for(InetAddress addr : inetAddresses)
			{
				System.out.println(addr.getHostAddress());
			}
			
			// 컴퓨터 연산 : 2의 보수
			byte[] addresses = inetAddress.getAddress();
			for(byte address : addresses)
			{
				System.out.print(address & 0x000000ff);
				System.out.print(".");
			}
			
			
			
		} 
		catch (UnknownHostException e) 
		{
			e.printStackTrace();
		}
	}
}
