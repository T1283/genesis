package com.prgm.communications.mail;

import java.util.ArrayList;
import java.util.List;

import sendinblue.ApiClient;
import sendinblue.Configuration;
import sendinblue.auth.ApiKeyAuth;
import sibApi.AccountApi;
import sibApi.EmailCampaignsApi;
import sibApi.ListsApi;
import sibModel.AddContactToList;
import sibModel.CreateEmailCampaign;
import sibModel.CreateEmailCampaignRecipients;
import sibModel.CreateEmailCampaign.TypeEnum;
import sibModel.CreateEmailCampaignSender;
import sibModel.CreateList;
import sibModel.CreateModel;
import sibModel.GetAccount;
import sibModel.PostContactInfo;
import sibModel.SendSmtpEmailCc;

public class Accounts
{
	private ApiClient createClient()
	{
		ApiClient defaultClient = Configuration.getDefaultApiClient();

        // Configure API key authorization: api-key
        ApiKeyAuth apiKey = (ApiKeyAuth) defaultClient.getAuthentication("api-key");
        apiKey.setApiKey("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");
        // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
        //apiKey.setApiKeyPrefix("Token");
        
        return defaultClient;
	}
	
	public void getAccountInfo(ApiClient client) throws Exception
	{
		AccountApi api = new AccountApi();
		api.setApiClient(client);
        GetAccount account = api.getAccount();
        System.out.println("Successfully retrieved account info\n" + account.toString());
 	}
	
	public void emailer() throws Exception
	{
		EmailCampaignsApi emailApi = new EmailCampaignsApi();
		emailApi.setApiClient(createClient());
		
		CreateEmailCampaignSender sender = new CreateEmailCampaignSender();
		sender.setName("PRGM");
		sender.setEmail("tj_1283_us@live.com");
		
		CreateEmailCampaignRecipients recipients = new CreateEmailCampaignRecipients();
		List<Long> listIds = new ArrayList<Long>();
		listIds.add(createRecipientList());
		
		recipients.setListIds(listIds);
		
		CreateEmailCampaign emailCampaigns = new CreateEmailCampaign();
		emailCampaigns.setToField("tj.1283.us@gmail.com");
		emailCampaigns.setName("Campaing for weekend volunteers");
		emailCampaigns.setSender(sender);
		emailCampaigns.subject("would you like to volunteer this weekend ?");
		emailCampaigns.setType(TypeEnum.CLASSIC);
		emailCampaigns.setHtmlContent("this is campaign content");
		emailCampaigns.setRecipients(recipients);
		
        CreateModel response = emailApi.createEmailCampaign(emailCampaigns);
        if (response.getId() != null)
        {
        	emailApi.sendEmailCampaignNow(response.getId());
        }
    }
	
	public Long createRecipientList() throws Exception
	{
		ListsApi listApi = new ListsApi();
		listApi.setApiClient(createClient());
		
		CreateList contactList = new CreateList();
		contactList.setName("All Contacts");
		contactList.setFolderId(1L);
		
		CreateModel listCreateResponse = listApi.createList(contactList);
		
		
		List<String> emailsList = new ArrayList<String>();
		emailsList.add("tj.1283.us@gmail.com");
		emailsList.add("tj_1283_us@live.com");
		
		AddContactToList contacts = new AddContactToList();
		contacts.setEmails(emailsList);
		
		PostContactInfo response = listApi.addContactToList(listCreateResponse.getId(), contacts);
		System.out.println(response.toString());
		
		return listCreateResponse.getId();
		
	}
}