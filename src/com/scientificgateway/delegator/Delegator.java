package com.scientificgateway.delegator;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.util.LinkedList;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.ServiceProvider;
import org.apache.curator.x.discovery.UriSpec;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.glassfish.jersey.client.ClientConfig;


import com.scientificgateway.serviceLayer.DataIngesterService;
import com.scientificgateway.serviceLayer.forecastDetectorService;
import com.scientificgateway.serviceLayer.forecastService;
import com.scientificgateway.serviceLayer.stormClusteringService;
import com.scientificgateway.serviceLayer.stormDetectionService;
import Exception.NoZooKeeperServerUpException;

@Path("/servicegateway")
public class Delegator {

	private static String availableIpAddress = null;
	public Delegator() {

		List<String> ipaddresses = new LinkedList<>();
		ipaddresses.add("ec2-35-161-48-143.us-west-2.compute.amazonaws.com");
		ipaddresses.add("ec2-35-160-137-157.us-west-2.compute.amazonaws.com");
		ipaddresses.add("ec2-52-15-90-97.us-east-2.compute.amazonaws.com");

		URI uri = null;
		String response = null;
		int count = 0;
		for (String ip : ipaddresses) {
			count++;
			try {
				if (InetAddress.getByName(ip).isReachable(3000)){
					availableIpAddress=ip;
					break;
				}
			} catch (IOException e) {				
				System.out.println(e.toString());
			}
		}
		if (count == ipaddresses.size() && availableIpAddress == null){
			try {
				throw new NoZooKeeperServerUpException();
			} catch (NoZooKeeperServerUpException e) {
				System.out.println(e.toString());
			}
		}
		
	}

	@GET
	@Path("/dataingester")
	@Produces("text/plain")
	public String dataIngesterManager(@QueryParam("station") String station, @QueryParam("date") String date,
			@QueryParam("hours") String hours, @QueryParam("minutes") String minutes,
			@QueryParam("seconds") String seconds) {

		String stationMain = station.replaceAll("\\s+", "");

		System.out.println("in delegate manager-dataingester");
		CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient(availableIpAddress + ":2181",
				new RetryNTimes(5, 1000));
		curatorFramework.start();

		//System.out.println("in delegate manager-dataingester");

		ServiceDiscovery<Void> serviceDiscovery = ServiceDiscoveryBuilder.builder(Void.class)
				.basePath("load-balancing-example").client(curatorFramework).build();
		try {
			serviceDiscovery.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ServiceProvider<Void> serviceProvider = serviceDiscovery.serviceProviderBuilder().serviceName("dataIngester")
				.build();
		try {
			serviceProvider.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// ServiceProvider serviceProvider;
		ServiceInstance instance;
		// InstanceProvider instanceProvider;

		System.out.println("before try");
		try {
			List<ServiceInstance<Void>> instances = (List<ServiceInstance<Void>>) serviceProvider.getAllInstances();
			if (instances.size() == 0) {
				System.out.println("list empty");
				return null;
			}

			int thisIndex = DataIngesterService.getIndex();
			DataIngesterService.setIndex(thisIndex + 1);

			System.out.println("thisIndex" + thisIndex);
			System.out.println(instances.get(thisIndex % instances.size()));

			String address = instances.get(thisIndex % instances.size()).getId();
			UriSpec address1 = instances.get(thisIndex % instances.size()).getUriSpec();
			String url = address1.build();
			System.out.println(url);

			URIBuilder builder = new URIBuilder();
			builder.setScheme("http").setHost(availableIpAddress+":2181")
					.setPath("/SG_MICROSERVICE_DATAINGESTOR/webapi/dataingester/get")
					.setParameter("station", stationMain).setParameter("date", date).setParameter("hours", hours)
					.setParameter("minutes", minutes).setParameter("seconds", seconds);
			URI uri = builder.build();
			HttpGet httpget = new HttpGet(uri);
			ClientConfig clientConfig = new ClientConfig();
			Client client = ClientBuilder.newClient(clientConfig);
			String response = client.target(uri).request().get(String.class);
			System.out.println(response);
			return response;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "nothing bro -> dataIngesterManager";
	}

	@GET
	@Path("/stormdetector")
	@Produces("text/plain")
	public String stormDetectorManager() {
		System.out.println("in delegate manager-stromdetector");

		CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient(availableIpAddress+":2181",
				new RetryNTimes(5, 1000));
		try{
		curatorFramework.start();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ServiceDiscovery<Void> serviceDiscovery = ServiceDiscoveryBuilder.builder(Void.class)
				.basePath("load-balancing-example").client(curatorFramework).build();
		try {
			serviceDiscovery.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ServiceProvider<Void> serviceProvider = serviceDiscovery.serviceProviderBuilder().serviceName("stormDetection")
				.build();
		try {
			serviceProvider.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// ServiceProvider serviceProvider;
		ServiceInstance instance;
		// InstanceProvider instanceProvider;

		try {
			List<ServiceInstance<Void>> instances = (List<ServiceInstance<Void>>) serviceProvider.getAllInstances();
			if (instances.size() == 0) {
				System.out.println("no instances found for stormDetector");
				return null;
			}

			int thisIndex = stormDetectionService.getIndex();
			stormDetectionService.setIndex(thisIndex + 1);
			System.out.println("thisIndex" + thisIndex);
			System.out.println(instances.get(thisIndex % instances.size()));

			String address = instances.get(thisIndex % instances.size()).getId();
			UriSpec address1 = instances.get(thisIndex % instances.size()).getUriSpec();
			String url = address1.build();

			System.out.println("response from storm detector manager");
			System.out.println(url);

			ClientConfig clientConfig = new ClientConfig();
			Client client = ClientBuilder.newClient(clientConfig);
			
			//send request to storm detector service
			String response = client.target(url).request().get(String.class);
			System.out.println("response from storm clustering");
			System.out.println(response);

			// sd.generateKML(url);

			return response;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "nothing bro - stormDetectorManager";
	}

	@GET
	@Path("/stormcluster")
	@Produces("text/plain")
	public String stormClusterManager() {
		System.out.println("inside stormcluster manager");

		CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient(availableIpAddress+":2181",
				new RetryNTimes(5, 1000));
		curatorFramework.start();

		ServiceDiscovery<Void> serviceDiscovery = ServiceDiscoveryBuilder.builder(Void.class)
				.basePath("load-balancing-example").client(curatorFramework).build();
		try {
			serviceDiscovery.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ServiceProvider<Void> serviceProvider = serviceDiscovery.serviceProviderBuilder().serviceName("stormCluster")
				.build();
		try {
			serviceProvider.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// ServiceProvider serviceProvider;
		ServiceInstance instance;

		try {
			List<ServiceInstance<Void>> instances = (List<ServiceInstance<Void>>) serviceProvider.getAllInstances();
			if (instances.size() == 0) {
				return "no instances found";
			}

			int thisIndex = stormClusteringService.getIndex();
			stormClusteringService.setIndex(thisIndex + 1);
			System.out.println("thisIndex" + thisIndex);
			System.out.println(instances.get(thisIndex % instances.size()));

			String address = instances.get(thisIndex % instances.size()).getId();
			UriSpec address1 = instances.get(thisIndex % instances.size()).getUriSpec();
			String url = address1.build();
			System.out.println("URL from storm cluster manager, instance picked is");
			System.out.println(url);

			ClientConfig clientConfig = new ClientConfig();
			Client client = ClientBuilder.newClient(clientConfig);
			
			
			// send request to storm clustering service
			String response = client.target(url).request().get(String.class);
			System.out.println("Response from Strom Clustering service");
			System.out.println(response);

			return address;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "nothing bro - stormcluster";
	}

	@GET
	@Path("/forecast")
	@Produces("text/plain")
	public String forecastManager() {
		System.out.println("forecast manager");
		CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient(availableIpAddress+":2181", new RetryNTimes(5, 1000));
		curatorFramework.start();

		ServiceDiscovery<Void> serviceDiscovery = ServiceDiscoveryBuilder.builder(Void.class)
				.basePath("load-balancing-example").client(curatorFramework).build();
		try {
			serviceDiscovery.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ServiceProvider<Void> serviceProvider = serviceDiscovery.serviceProviderBuilder().serviceName("forecast")
				.build();
		try {
			serviceProvider.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// ServiceProvider serviceProvider;
		ServiceInstance instance;

		try {
			List<ServiceInstance<Void>> instances = (List<ServiceInstance<Void>>) serviceProvider.getAllInstances();
			if (instances.size() == 0) {
				return "no instances found";
			}

			int thisIndex = forecastService.getIndex();
			forecastService.setIndex(thisIndex + 1);
			System.out.println("thisIndex" + thisIndex);
			System.out.println(instances.get(thisIndex % instances.size()));

			String address = instances.get(thisIndex % instances.size()).getId();
			UriSpec address1 = instances.get(thisIndex % instances.size()).getUriSpec();
			String url = address1.build();
			System.out.println("URL from forecast manager, instance picked is");
			System.out.println(url);
			
			MessageToForecast msg=new MessageToForecast();
			msg.setReqId(10);
	    	//msg.setUserId("soumya");
			
			
			
			ClientConfig config1 = new ClientConfig();
			//System.out.println("ClientConfig config1 ");
			Client client1 = ClientBuilder.newClient(config1);
			//System.out.println("Client client1 ");
			WebTarget target1 = client1.target(url);
			System.out.println("WebTarget");
			
			
			//Response response1 = target1.request().post(Entity.entity("hii", "application/xml"),Response.class);
			String responsefrom;
			responsefrom=target1.request().post(Entity.entity(msg, "application/json"),String.class);

			/*ClientConfig clientConfig = new ClientConfig();
			Client client = ClientBuilder.newClient(clientConfig);
			
			// send request to storm clustering service
			String response = client.target(url).request().get(String.class);
			System.out.println("Response from forecast service");*/
			System.out.println(responsefrom);

			return address;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "nothing bro - forecastManager";
	}

	@GET
	@Path("/forecastdetector")
	@Produces("text/plain")
	public String forecastDetectorManager() {
		System.out.println("forecastdetector manager");
		CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient(availableIpAddress+":2181", new RetryNTimes(5, 1000));
		curatorFramework.start();

		ServiceDiscovery<Void> serviceDiscovery = ServiceDiscoveryBuilder.builder(Void.class)
				.basePath("load-balancing-example").client(curatorFramework).build();
		try {
			serviceDiscovery.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ServiceProvider<Void> serviceProvider = serviceDiscovery.serviceProviderBuilder().serviceName("forecastDetector")
				.build();
		try {
			serviceProvider.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// ServiceProvider serviceProvider;
		ServiceInstance instance;

		try {
			List<ServiceInstance<Void>> instances = (List<ServiceInstance<Void>>) serviceProvider.getAllInstances();
			if (instances.size() == 0) {
				return "no instances found";
			}

			int thisIndex = forecastDetectorService.getIndex();
			forecastDetectorService.setIndex(thisIndex + 1);
			System.out.println("thisIndex" + thisIndex);
			System.out.println(instances.get(thisIndex % instances.size()));

			String address = instances.get(thisIndex % instances.size()).getId();
			UriSpec address1 = instances.get(thisIndex % instances.size()).getUriSpec();
			String url = address1.build();
			
			
			MessageToForecast msg=new MessageToForecast();
			msg.setReqId(10);
	    	msg.setUserId("soumya");
			
			
			
			ClientConfig config1 = new ClientConfig();
			//System.out.println("ClientConfig config1 ");
			Client client1 = ClientBuilder.newClient(config1);
			//System.out.println("Client client1 ");
			WebTarget target1 = client1.target(url);
			System.out.println("WebTarget");
			
			
			//Response response1 = target1.request().post(Entity.entity("hii", "application/xml"),Response.class);
			String responsefrom;
			responsefrom=target1.request().post(Entity.entity(msg, "application/json"),String.class);
			
			
			
			
			/*System.out.println("URL from forecast detector manager, instance picked is");
			System.out.println(url);

			ClientConfig clientConfig = new ClientConfig();
			Client client = ClientBuilder.newClient(clientConfig);
			
			// send request to storm clustering service
			String response = client.target(url).request().get(String.class);*/
			System.out.println("Response from forecast detector service");
			System.out.println(responsefrom);

			return address;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "nothing bro - forecastDetectorManager";
	}

}
