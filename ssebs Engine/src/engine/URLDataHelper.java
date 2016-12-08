package engine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class URLDataHelper
{

	public static ArrayList<String> getStringDataAsArrayList(String url)
	{
		ArrayList<String> stringData = new ArrayList<String>();
		// github raw works
		try
		{
			URL givenUrl = new URL(url);
			URLConnection yc = givenUrl.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null)
				stringData.add(inputLine);
			in.close();
			//System.out.println("Time taken to get data: " + LogicHelper.getDelta());
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return stringData;
	}

	public static String getStringDataAsString(String url)
	{
		String stringData = "";
		// github raw works
		if (!url.startsWith("http"))
		{
			url = "http://" + url;
		}
		try
		{
			LogicHelper._lastFrame = LogicHelper.getTime();
			URL givenUrl = new URL(url);
			URLConnection urlConnection = givenUrl.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null)
			{
				if (!inputLine.startsWith("#"))
				{
					stringData += inputLine + "\n";
				}
			}
			in.close();
			//System.out.println("Time taken to get data: " + LogicHelper.getDelta());
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		return stringData;
	}

}
