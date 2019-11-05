package com.munish.microservices.limitsservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.munish.microservices.limitsservice.bean.LimitsConfiguration;
import com.munish.microservices.limitsservice.configuration.Configuration;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class LimitConfigurationController {

	@Autowired
	Configuration configuration;
	
	@GetMapping("/limits")
	public LimitsConfiguration retrieveLimitFromConfiguration(){
		return new LimitsConfiguration(configuration.getMinimum(), configuration.getMaximum());
	}
	
	@GetMapping("/fault-tolerance-example")
	@HystrixCommand(fallbackMethod="fallbackRetrieveConf")
	public LimitsConfiguration retrieveConf(){
		throw new RuntimeException("NOt available");
	}
	
	
	public LimitsConfiguration fallbackRetrieveConf(){
		return new LimitsConfiguration(9,999);
	}
}
