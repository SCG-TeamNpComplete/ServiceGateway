package com.scientificgateway.milestone1;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.scientificgateway.BeanParams.DataIngesterParamHolder;
import com.scientificgateway.servicelayer.DataIngesterService;

@Path("/dataingester")
public class DataIngester {
	public static Logger log = Logger.getLogger(DataIngester.class.getName());
	private DataIngesterService DIservice;

	@GET
	@Path("/get")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	public String returnURL(@BeanParam DataIngesterParamHolder dip) throws IOException, URISyntaxException {
		System.out.println("in /get");
		log.info("entered Data Ingester");
		DIservice = new DataIngesterService();
		String url = DIservice.returnResponseFile(dip.getStation(), dip.getDate(), dip.getHours(), dip.getMinutes(),
				dip.getSeconds());
		log.info("retrieved URL");

		DIservice.sendURL(url);

		log.info("called sendURL() method");
		return url;

	}
	
	
	

}
