package LabSD;

import javax.xml.ws.Endpoint;

public class PublishServices {

	public static void main(String[] args) {
		Endpoint.publish("http://localhost:1516/WS/Products", new SOAPImpl());
	}
}
