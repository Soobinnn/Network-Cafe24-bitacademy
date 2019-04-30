package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
/*
 *  InputStreamReader로 구현한 nslookup
 */
public class NSLookup2 
{
	public static void main(String[] args) 
	{
	
		BufferedReader br = null;
		
		try 
		{
			InputStreamReader isr = new InputStreamReader(System.in, "utf-8");
			br = new BufferedReader(isr);
			
			String hostname = null;
			while((hostname = br.readLine()) != null)
			{
				if("exit".equals(hostname))
				{
					break;
				}
				InetAddress[] inetAddresses;
				
				inetAddresses = InetAddress.getAllByName(hostname);
				
				for(InetAddress addr : inetAddresses)
				{
					System.out.println(addr.getHostAddress());
				}
			}
			
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try 
			{
				if(br != null)
				{	
					br.close();
				} 
			}
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
	}
}
