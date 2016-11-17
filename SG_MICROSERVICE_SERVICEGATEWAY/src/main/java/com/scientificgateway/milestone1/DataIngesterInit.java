package com.scientificgateway.milestone1;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.UriSpec;

@Path("/DataIngester")
public class DataIngesterInit {
	
	@GET
	@Path("/init")
	public String initZookeeper(){
		
		String endpointURI = "localhost:8080/SG_MICROSERVICE_DATAINGESTOR/webapi/dataingester/get";
	    //private final String endpointURI = "http://" + serverName + ":" + serverPort + "/catalog/resources/catalog";
	    //private final String endpointURI = "http://" + WildFlyUtil.getHostName() + ":" + WildFlyUtil.getHostPort() + "/catalog/resources/catalog";
	    String serviceName = "dataIngester";
		
	    int port = 8080;

		CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient("localhost:2181",
				new RetryNTimes(5, 1000));
		curatorFramework.start();
		try {
			
			ServiceInstance serviceInstance = ServiceInstance.builder().uriSpec(new UriSpec(endpointURI)).address("localhost").port(port).name(serviceName).build();
			ServiceDiscoveryBuilder.builder(Void.class).basePath("load-balancing-example-dataIngester").client(curatorFramework)
			.thisInstance(serviceInstance).build().start();
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    //registry.unregisterService(serviceName, endpointURI);
	    
	    //registry.discoverServiceURI(serviceName);
		return "done";
	}

}
