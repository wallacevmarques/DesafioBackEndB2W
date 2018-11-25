package com.wallace.desafio;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("ws")
public class DesafioBackEndApplication extends ResourceConfig {

	public DesafioBackEndApplication() {
		packages("com.wallace.desafio");
	}
	
}
