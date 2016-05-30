package org.acme.foo.consumer.console;

import org.acme.foo.api.WelcomeService;
import org.apache.felix.service.command.CommandProcessor;
import org.osgi.service.component.annotations.*;

@Component(
		service = Object.class,
		property = {
			CommandProcessor.COMMAND_SCOPE + ":String=test",
			CommandProcessor.COMMAND_FUNCTION + ":String=welcome"
		},
		immediate = true
		)
public class Consumer {

	@Reference
	WelcomeService welcome;
	
	public void welcome(String name) {
		System.out.println(welcome.hello(name));
	}

}
