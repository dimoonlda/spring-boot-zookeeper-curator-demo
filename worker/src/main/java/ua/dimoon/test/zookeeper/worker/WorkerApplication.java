package ua.dimoon.test.zookeeper.worker;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.x.discovery.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class WorkerApplication {

	@Autowired
	Environment environment;
	public static String workerName;

	@Bean
	CommandLineRunner registerInZookeeper() throws Exception {
		return strings -> {
			String port = environment.getProperty("local.server.port");
			CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient("localhost:2181", new RetryNTimes(5, 1000));
			curatorFramework.start();
			ServiceInstance<Void> serviceInstance = ServiceInstance.<Void>builder()
					.uriSpec(new UriSpec("{scheme}://{address}:{port}"))
					.address("127.0.0.1")
					.port(Integer.valueOf(port))
					.name("worker")
					.build();

			ServiceDiscoveryBuilder.builder(Void.class)
					.basePath("load-balancing-example")
					.client(curatorFramework)
					.thisInstance(serviceInstance)
					.build()
					.start();
		};
	}

	public static void main(String[] args) {
		if (args.length < 2) {
			throw new IllegalArgumentException("params count must be two or more");
		}
		workerName = args[0];
		SpringApplication.run(WorkerApplication.class, args);
	}
}
