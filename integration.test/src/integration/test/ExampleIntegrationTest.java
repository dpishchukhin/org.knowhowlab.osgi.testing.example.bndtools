package integration.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.knowhowlab.osgi.testing.assertions.ServiceAssert;
import org.mockito.runners.MockitoJUnitRunner;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.packageadmin.PackageAdmin;

public class ExampleIntegrationTest {

	private final BundleContext context = FrameworkUtil.getBundle(ExampleIntegrationTest.class).getBundleContext();

	@Before
	public void before() {
		// TODO add test setup here
	}

	@After
	public void after() {
		// TODO add test clear up here
	}

	@Test
	public void testExample() {
		ServiceAssert.assertServiceAvailable(PackageAdmin.class);
	}

}