package org.acme.foo.archiver;

import org.acme.foo.archiver.api.ArchiverService;
import org.osgi.service.event.EventConstants;
import org.osgi.service.component.annotations.*;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

@Component(
		service = {ArchiverService.class, EventHandler.class},
		property = EventConstants.EVENT_TOPIC + ":String=acme/welcome",
		immediate = true
		)
public class ArchiverComponent implements ArchiverService, EventHandler {

	private String lastName;
	
	@Override
	public void handleEvent(Event event) {
		lastName = String.valueOf(event.getProperty("name"));
	}

	@Override
	public String getLastName() {
		return lastName;
	}

}
