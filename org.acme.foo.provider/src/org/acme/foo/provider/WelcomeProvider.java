package org.acme.foo.provider;

import java.util.HashMap;

import org.acme.foo.api.WelcomeService;
import org.osgi.service.component.annotations.*;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@Component(
		configurationPolicy = ConfigurationPolicy.REQUIRE,
		configurationPid = "welcome-provider",
		service = WelcomeService.class
		)
@Designate(
		ocd = WelcomeProvider.Config.class
		)
public class WelcomeProvider implements WelcomeService {

	@Reference
	EventAdmin ea;
	
	@ObjectClassDefinition(
			name = "Welcome Provider"
			)
	@interface Config {
		@AttributeDefinition(
				name = ".pattern",
				type = AttributeType.STRING,
				defaultValue = "Welcome %s!"
				)
		String _pattern();
	}

	private String pattern;

	@Activate
	public void activate(Config config) {
		pattern = config._pattern();
	}
	
	@Override
	public String hello(String name) {
		ea.postEvent(new Event("acme/welcome", new HashMap() {{
			put("name", name);
		}}));
		
		
		return String.format(pattern, name);
	}

}
