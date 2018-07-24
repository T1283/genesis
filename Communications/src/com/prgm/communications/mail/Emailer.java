package com.prgm.communications.mail;

import java.util.ArrayList;
import java.util.List;

import sendinblue.ApiClient;
import sendinblue.ApiException;
import sibApi.EmailCampaignsApi;
import sibModel.CreateEmailCampaign;
import sibModel.CreateEmailCampaign.TypeEnum;
import sibModel.CreateEmailCampaignRecipients;
import sibModel.CreateEmailCampaignSender;
import sibModel.CreateModel;

public class Emailer
{
	EmailCampaignsApi emailApi = null;
	ApiClient client = null;
	
	public Emailer(ApiClient client)
	{
		emailApi = new EmailCampaignsApi();
		emailApi.setApiClient(client);
		this.client = client;
	}
	
	public Long createCampaign(CreateEmailCampaignSender sender, CreateEmailCampaignRecipients recipient) throws Exception
	{
		try
		{
			CreateEmailCampaign emailCampaigns = new CreateEmailCampaign();
			emailCampaigns.setToField("tj.1283.us@gmail.com");
			emailCampaigns.setName("Campaing for weekend volunteers");
			emailCampaigns.setSender(sender);
			emailCampaigns.subject("would you like to volunteer this weekend ?");
			emailCampaigns.setType(TypeEnum.CLASSIC);
			emailCampaigns.setHtmlContent("this is campaign content");
			emailCampaigns.setRecipients(recipient);
			
	        CreateModel response = emailApi.createEmailCampaign(emailCampaigns);
	        return response.getId();
		}
		catch(ApiException apiEx)
		{
			System.err.println("Error while creating email campaign\n" + apiEx.getResponseBody());
			throw new Exception(apiEx.getResponseBody());
		}
    }
	
	public void sendEmailCampaign(Long campaignId) throws ApiException
	{
		emailApi.sendEmailCampaignNow(campaignId);
	}
	
	public CreateEmailCampaignSender createSender(String senderName, String senderEmail) 
	{
		CreateEmailCampaignSender sender = new CreateEmailCampaignSender();
		sender.setName(senderName);
		sender.setEmail(senderEmail);
		
		return sender;
	}
	
	/**
	 * Create the recipient object that contains the list ids of the recipients
	 * @param client
	 * @return
	 * @throws Exception
	 */
	public CreateEmailCampaignRecipients createRecipient(Long id) throws Exception
	{
		CreateEmailCampaignRecipients recipients = new CreateEmailCampaignRecipients();
		List<Long> listIds = new ArrayList<Long>();
		listIds.add(id);
		recipients.setListIds(listIds);
		return recipients;
	}
	
	
}