package com.prgm.communications.mail;

import java.util.ArrayList;
import java.util.List;

import sendinblue.ApiClient;
import sendinblue.Configuration;
import sendinblue.auth.ApiKeyAuth;

public class Utility
{
	public static ApiClient createClient()
	{
		ApiClient defaultClient = Configuration.getDefaultApiClient();

        // Configure API key authorization: api-key
        ApiKeyAuth apiKey = (ApiKeyAuth) defaultClient.getAuthentication("api-key");
        apiKey.setApiKey("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");
        
        //apiKey.setApiKeyPrefix("Token");
        
        return defaultClient;
	}
	
	public static List<String> getEmailContactsList()
	{
		List<String> emailsList = new ArrayList<String>();
		emailsList.add("tj.1283.us@gmail.com");
		emailsList.add("tj_1283_us@live.com");
		
		return emailsList;
	}
}
