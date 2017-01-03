package com.sql;

public class Config {
	public static final String DRIVER="com.microsoft.sqlserver.jdbc.SQLServerDriver";
//	public static final String USER="biaozhunban";
//	public static final String PASS="biaozhunban123";
//	public static final String URL="jdbc:sqlserver://localhost:1433;databaseName=sdjz_erp_new";
	public String mysqlHost="localhost";
	public String mysqlPort="1433";
	public String mysqlDB="sdjz_erp_new";
	public String mysqlUser="biaozhunban";
	public String mysqlPassword="biaozhunban123";
	private static Config config;
	public static Config getInstance(){
		if(config==null){
			config = new Config();
		}
		return config;
	}
}
