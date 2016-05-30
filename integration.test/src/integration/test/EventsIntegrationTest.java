package integration.test;

import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.acme.foo.api.WelcomeService;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.knowhowlab.osgi.testing.assertions.ServiceAssert;
import org.knowhowlab.osgi.testing.assertions.cmpn.ConfigurationAdminAssert;
import org.knowhowlab.osgi.testing.assertions.cmpn.EventAdminAssert;
import org.knowhowlab.osgi.testing.utils.BundleUtils;
import org.knowhowlab.osgi.testing.utils.FilterUtils;
import org.knowhowlab.osgi.testing.utils.ServiceUtils;
import org.knowhowlab.osgi.testing.utils.cmpn.ConfigurationAdminUtils;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.component.runtime.ServiceComponentRuntime;
import org.osgi.service.component.runtime.dto.ComponentDescriptionDTO;

public class EventsIntegrationTest {
	private static final BundleContext bc = FrameworkUtil.getBundle(EventsIntegrationTest.class).getBundleContext();
	
	@After
	public void after() {
		ConfigurationAdminUtils.deleteConfiguration(bc, "welcome-provider", null, 0);
		ConfigurationAdminAssert.assertConfigurationDeleted("welcome-provider", null, null, 500, TimeUnit.MILLISECONDS);
	}
	
	@Test
	public void testProvider() throws InvalidSyntaxException {
		ServiceAssert.assertServiceUnavailable(WelcomeService.class);
		
		ConfigurationAdminUtils.supplyConfiguration(bc, "welcome-provider", null, new HashMap(){{
			put(".pattern", "Hi %s!");
		}}, 0);
		
		ConfigurationAdminAssert.assertConfigurationUpdated("welcome-provider", null, null, 500, TimeUnit.MILLISECONDS);
		
		ConfigurationAdminAssert.assertConfigurationAvailable("welcome-provider", (String)null, null);

		Executors.newSingleThreadExecutor().submit(() -> {
			try {
				TimeUnit.MICROSECONDS.sleep(500);
			} catch (Exception e) {
				e.printStackTrace();
			}
			ServiceUtils.getService(bc, WelcomeService.class).hello("John");
		});
		
		EventAdminAssert.assertEvent("acme/welcome", 
				FilterUtils.eq("name", "John"),
				1, TimeUnit.SECONDS);
		
	}
}