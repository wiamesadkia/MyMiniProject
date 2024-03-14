package com.MyMiniProject.MiniProject;

import com.MyMiniProject.MiniProject.utilities.DatabaseUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MiniProjectApplication implements CommandLineRunner {

	@Autowired
	DatabaseUtility databaseUtility;

	public static void main(String[] args) {
		SpringApplication.run(MiniProjectApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception{
		databaseUtility.initDatabase();
		databaseUtility.initUsers();
		databaseUtility.initRoles();
	}
}
