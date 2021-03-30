package com.rbiedrawa.hexagonal.app.business.notifications;

import java.util.Map;


public interface EmailService {

	void send(String emailAddress, String templateId, Map<String, String> templateVariables);
}
