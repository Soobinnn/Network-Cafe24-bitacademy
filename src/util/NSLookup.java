package util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class NSLookup 
{
	public static void main(String[] args) 
	{
		Scanner sc = null;
		try 
		{
			sc = new Scanner(System.in);
			String hostname = null;
			while((hostname = sc.nextLine())!=null)
			{
				if("exit".equals(hostname))
				{
					break;
				}
				//String hostname = "www.naver.com";
				
				InetAddress[] inetAddresses;
			
				inetAddresses = InetAddress.getAllByName(hostname);
			
				for(InetAddress addr : inetAddresses)
				{
					System.out.println(addr.getHostAddress());
				}
			}
			
		} 
		catch (UnknownHostException e) 
		{
			e.printStackTrace();
		}
		finally
		{
				if(sc != null )
				{
					sc.close();
				}
		}
	}
}
