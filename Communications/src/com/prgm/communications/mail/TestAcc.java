package com.prgm.communications.mail;

import sendinblue.ApiClient;

public class TestAcc
{
	public static void main(String[] args) throws Exception
	{
		ApiClient client = Utility.createClient();
		
		// get account info
		Accounts account = new Accounts();
		account.getAccountInfo(client);
		
		Contacts contacts = new Contacts(client);
		contacts.createAndAddContactsToList(Utility.getEmailContactsList(), "ALL CONTACTS", 1L);
		
		// create and send campaign
		Emailer email = new Emailer(client);
		/*Long campaignId = email.createCampaign(email.createSender("PRGM", "tj_1283_us@live.com"), email.createRecipient(15L));
		System.out.println("campaign id = " + campaignId);*/
		
		// send campaign
		//email.sendEmailCampaign(13L);
	}
}
