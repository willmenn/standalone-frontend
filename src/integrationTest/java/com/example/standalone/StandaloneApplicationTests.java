package com.example.standalone;

import ch.vorburger.mariadb4j.DB;
import ch.vorburger.mariadb4j.DBConfigurationBuilder;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StandaloneApplicationTests {

	@BeforeClass
	public static void setUp() throws Exception {
		DBConfigurationBuilder config = DBConfigurationBuilder.newBuilder();
		config.setPort(3306);
		config.addArg("--user=root");
		DB db = DB.newEmbeddedDB(config.build());
		db.start();

		String dbName = "auth";
		db.createDB(dbName, "user","root");

	}
	@Test
	public void contextLoads() {
	}

}
