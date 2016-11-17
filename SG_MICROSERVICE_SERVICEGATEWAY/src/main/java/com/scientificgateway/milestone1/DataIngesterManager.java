package com.scientificgateway.milestone1;

import java.net.URI;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

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

import com.scientificgateway.servicelayer.DataIngesterService;

@Path("/DataIngesterManager")
public class DataIngesterManager {

	private DataIngesterService dataingesterservice;

	@GET
	@Path("/delegate")
	@Produces("text/plain")
	public String getDataIngesterReplica(@QueryParam("station") String station, @QueryParam("date") String date,
			@QueryParam("hours") String hours, @QueryParam("minutes") String minutes,
			@QueryParam("seconds") String seconds) {

		System.out.println(station);
		System.out.println(station.replaceAll("\\s+", ""));

		String stationMain = station.replaceAll("\\s+", "");

		System.out.println("in delegate manager-dataingester");
		CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient("localhost:2181",
				new RetryNTimes(5, 1000));
		curatorFramework.start();

		System.out.println("in delegate manager-dataingester");

		ServiceDiscovery<Void> serviceDiscovery = ServiceDiscoveryBuilder.builder(Void.class)
				.basePath("load-balancing-example-dataIngester").client(curatorFramework).build();
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

			System.out.println("hello");

			URIBuilder builder = new URIBuilder();
			builder.setScheme("http").setHost("localhost:8080")
					.setPath("/SG_MICROSERVICE_DATAINGESTOR/webapi/dataingester/get")
					.setParameter("station", stationMain).setParameter("date", date).setParameter("hours", hours)
					.setParameter("minutes", minutes).setParameter("seconds", seconds);
			URI uri = builder.build();
			HttpGet httpget = new HttpGet(uri);
			ClientConfig clientConfig = new ClientConfig();
			Client client = ClientBuilder.newClient(clientConfig);
			String response =client.target(uri).request().get(String.class);
			System.out.println(response);
			return response;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "dataIngester - nothing";

	}

}
