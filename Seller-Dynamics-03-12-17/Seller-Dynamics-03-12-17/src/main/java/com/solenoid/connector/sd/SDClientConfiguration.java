/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */
package com.solenoid.connector.sd;

import javax.xml.soap.SOAPException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class SDClientConfiguration {

	@Bean
	public Jaxb2Marshaller marshaller() throws SOAPException {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setPackagesToScan("com.solenoid.connector.sd.beans");
		return marshaller;
	}

	@Bean
	public SDApiClient sDApiClient(Jaxb2Marshaller marshaller) {
		SDApiClient client = new SDApiClient();
		client.setDefaultUri(SDConstants.SD_API);
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		return client;
	}
}
