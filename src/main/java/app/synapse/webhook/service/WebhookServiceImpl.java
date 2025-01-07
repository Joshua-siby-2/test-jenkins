package app.synapse.webhook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WebhookServiceImpl {
	
	@Autowired
	private RestTemplate restTemplate;

	public void sendWebhook(String url, String payload) {
		System.out.println("Sending webhook data to url : " + url );
		restTemplate.postForObject(url, payload, String.class);
		
	}

}
