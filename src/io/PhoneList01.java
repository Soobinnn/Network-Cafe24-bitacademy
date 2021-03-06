package io;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class PhoneList01 
{
	public static void main(String[] args) 
	{
		BufferedReader br = null;
		try
		{
			br = new BufferedReader(new InputStreamReader(new FileInputStream("phone.txt"), "utf-8"));
		
			String line = null;
			while((line =br.readLine()) != null)
			{
				StringTokenizer st = 
						new StringTokenizer(line, "\t ");
				
				int index =0;
				while(st.hasMoreElements())
				{
					String token = st.nextToken();
					
					System.out.print(token);
					if(index == 0)
					{
						System.out.print( ":");
					}
					else if(index == 1)
					{
						System.out.print( "-");
					}
					else if(index == 2)
					{
						System.out.print( "-");
					}
					index++;
				}
				System.out.println("");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try 
			{
				if( br!=null)
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
