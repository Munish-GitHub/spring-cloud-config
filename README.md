# spring-cloud-config

Application contains two projects:
1. Config Server uses spring-cloud-config-server to send the limit response back to limit service.

Config Server confiugration:
#application name
spring.application.name=spring-cloud-config-server
#Port for config server
server.port=8888
#taking limit service from local git repo
spring.cloud.config.server.git.uri=file://{git-localconfig-repo folder path}

2. Limit Service getting used to call config server to get its limit.
Limit server configuration:
#application name
spring.application.name=limits-service
#Port for config server
spring.cloud.config.uri=http://localhost:8888
#profile
spring.profiles.active=dev

After cloning the whole repo, copy the "git-localconfig-repo" to some other location and init the git in that location. Add and commit the folder locally with files inside (dont commit to remote server) to make .get folder here. Add the folder location in place holder in server config application properties file.

git-localconfig-repo contains three files:
limits-service.properties               //default limit service limits
limits-service-dev.properties           //dev profile limit service limits
limits-service-qa.properties            //qa profile limit service limits


Import both projects in eclipse.
First run config server.
Second run limit service.
Check if there is any error on config server console. 
Hit the url:
http://localhost:8080/limits

{"minimum":1,"maximum":111}

As the dev profile is active in limit service bootstrap properties, above will be the response.

As a fault tolerant, the limit service also implements fault tolerant behaviour using Spring cloud hystrix. Check the code present in LimitConfigurationController:

	@GetMapping("/fault-tolerance-example")
	@HystrixCommand(fallbackMethod="fallbackRetrieveConf")
	public LimitsConfiguration retrieveConf(){
		throw new RuntimeException("NOt available");
	}
	
	
	public LimitsConfiguration fallbackRetrieveConf(){
		return new LimitsConfiguration(9,999);
	}
	
Hit /fault-tolerance-example with 8080 url and you will get limits as defined in fallbackRetrieveConf().
