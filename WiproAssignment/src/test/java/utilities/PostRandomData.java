package utilities;

import org.apache.commons.lang3.RandomStringUtils;

public class PostRandomData {
	public static String getName()
	{
		String getname = RandomStringUtils.randomAlphabetic(1);
		return ("Shashi"+getname);
	}
	
	
	public static String getPassword()
	{
		String genpassword = RandomStringUtils.randomAlphabetic(5);
		return ("PA"+genpassword);
	}
	
	public static String getEmail()
	{
		String genemail = RandomStringUtils.randomAlphabetic(3);
		return ("Shashi"+genemail+"@gmail.com");
	}

}
