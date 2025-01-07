package app.synapse.webhook.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.synapse.webhook.service.WebhookServiceImpl;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/webhook")
public class DynamicWebhookController {
	
	private final WebhookServiceImpl webhookService;
	
	@PostMapping("{orgId}/{applnName}")
	public ResponseEntity<String> receiveWebhook(@PathVariable String orgId, @PathVariable String applnName, @RequestBody String payload) {
		System.out.println("Received webhook data for org : " + orgId + " with payload :" + payload);
		return ResponseEntity.ok("Received webhook data for org : " + orgId + " with payload :" + payload);
	}
	
	@GetMapping
	public ResponseEntity<String> sendWebhook(@RequestBody String url) {
		System.out.println(url);
		webhookService.sendWebhook(url, "name:john");
		return ResponseEntity.ok("webhook send success");
	}
	
	

}
