package org.acme.foo.test;

import org.acme.foo.archiver.api.ArchiverService;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.knowhowlab.osgi.testing.assertions.ServiceAssert;
import org.knowhowlab.osgi.testing.utils.ServiceUtils;
import org.knowhowlab.osgi.testing.utils.cmpn.EventAdminUtils;
import org.mockito.runners.MockitoJUnitRunner;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@RunWith(MockitoJUnitRunner.class)
public class ArchiverIntegrationTest {
	private static final BundleContext bc = FrameworkUtil.getBundle(ArchiverIntegrationTest.class).getBundleContext();
	
	@Test
	public void testProvider() throws InvalidSyntaxException, InterruptedException {
		ServiceAssert.assertServiceAvailable(ArchiverService.class);
		
		EventAdminUtils.postEvent(bc, "acme/welcome", new HashMap(){{
			put("name", "Sherlock");
		}}, 0, TimeUnit.MICROSECONDS);
		
		ArchiverService as = ServiceUtils.getService(bc, ArchiverService.class);

		TimeUnit.MICROSECONDS.sleep(1000);
		
		Assert.assertThat(as.getLastName(), CoreMatchers.is("Sherlock"));
	}
}