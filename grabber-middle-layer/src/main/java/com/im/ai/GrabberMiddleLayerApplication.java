package com.im.ai;

import com.im.ai.grabber.components.CustomEmbedding;
import com.im.ai.grabber.components.TikaFileReaderConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.simple.JdbcClient;

@SpringBootApplication
public class GrabberMiddleLayerApplication  implements CommandLineRunner{
	private static final Logger log = LoggerFactory.getLogger(GrabberMiddleLayerApplication.class);

	private final JdbcClient jdbcClient;
	private final TikaFileReaderConfig tikaFileReaderConfig;
	private final CustomEmbedding customEmbedding;



	/*@Value("classpath:Demo.csv")
	private Resource csvResource;*/
	@Value("classpath:/docs/all_star_services.txt")
	private Resource allStarServicesResource;



	public GrabberMiddleLayerApplication(TikaFileReaderConfig tikaFileReaderConfig, JdbcClient jdbcClient, CustomEmbedding customEmbedding) {
		this.tikaFileReaderConfig = tikaFileReaderConfig;
		this.jdbcClient = jdbcClient;
		this.customEmbedding = customEmbedding;
	}

	public static void main(String[] args) {
		SpringApplication.run(GrabberMiddleLayerApplication.class, args);
	}

	/*@Override
	public void run(String... args) throws Exception {
		customEmbedding.processData();
	}*/



	@Override
	public void run(String... args) {
		Integer count = jdbcClient.sql("select count(*) from vector_store")
				.query(Integer.class)
				.single();

		log.info("Current count of the Vector Store: {}",count);
		if (count == 0){
			log.info("Loading custom data into vector database...");
			tikaFileReaderConfig.addResource(allStarServicesResource);

			log.info("Application ready!!!");
		}else{
			log.info("Vector DB is already having data...");
		}
	}
}
