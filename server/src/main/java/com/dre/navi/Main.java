package com.dre.navi;

import com.dre.navi.sql.PostgresJDBC;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.SQLException;

@SpringBootApplication
public class Main {
	public static void main(String[] args) throws SQLException
	{
		System.out.println("version 2");
		SpringApplication.run(Main.class, args);
		PostgresJDBC db = PostgresJDBC.getInstance();
		db.startConnection();
		db.createTablesIfNotExists();
    }

}
