package com.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Vector;

public class ConnectionPool {
	private String jdbcDriver="";	//驱动
	private String dbURL="";//连接数据库的URL
	private String userName="";	//用户名
	private String passWord="";	//密码
	private String testTable="";	//测试表名称
	private int initalConNum=1;	//连接池的初始大小
	private int increatmentalConNum=5;		//自动增长的数量
	private int maxCon=20;		//连接池最大的大小
	private Vector<PooledConnection> connections=null;		//存放连接池中链接数据库的向量
	
	/*
	 * 构造函数
	 * @param jdbcDriver	驱动
	 * @param dbURL			连接
	 * @param  username
	 * @param	password
	 */
	public ConnectionPool(String jdbcDriver,String dbURL,String username,String password){
		this.jdbcDriver=jdbcDriver;
		this.dbURL=dbURL;
		this.userName=username;
		this.passWord=password;
		
		try {
			createPool();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//返回连接池的初始大小
	public int getInitialCons(){
		return this.initalConNum;
	}
	
	//返回连接池的自动增加的大小
	public int getIncreatmentalNum(){
		return this.increatmentalConNum;
	}
	
	//返回连接池中最大的可用连接数量
	public int getMaxCons(){
		return this.maxCon;
	}
	
	//设置最大连接数量
	public void setMaxCons(int maxCon){
		this.maxCon=maxCon;
	}
	
	//获取测试表的名称
	public String getTestTable(){
		return this.testTable;
	}
	
	//设置测试表的名称
	public void setTestTable(String testTable){
		this.testTable=testTable;
	}
	
	//创建一个数据库连接池
	public synchronized void createPool() throws Exception {
		//确保连接池没有创建
		//若已经创建,则保证其不为空
		if(connections!=null){
			return;
		}
		
		//实例化数据库驱动
		Driver driver=(Driver)Class.forName(this.jdbcDriver).newInstance();
		//注册驱动
		DriverManager.registerDriver(driver);
		//创建保存连接的向量
		this.connections=new Vector<PooledConnection>();
		//根据初始化中设置的值来创建连接
		createConnections(this.initalConNum);
	}
	
	//创建连接,numConnections为要创建的连接的数量
	private void createConnections(int numConnections){
		//循环创建指定数目的连接
		for(int i=0;i<numConnections;i++){
			//maxCon<=0表示连接数量没有上限
			//判断连接的数量，若已经达到最大值则推出
			if(this.maxCon>0&&this.connections.size()>=this.maxCon)
				break;
			//若满足条件则添加一个连接到连接池中
			try {
				this.connections.addElement(new PooledConnection(newConnection()));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
		}
	}
	
	//创建一个新的数据库连接并返回它
	private Connection newConnection() throws SQLException{
		//创建一个连接
		Connection conn=DriverManager.getConnection(this.dbURL, this.userName, this.passWord);
		//如果是第一次创建数据库,即检查数据库,获取此数据库允许支持的最大连接数目
		if(connections.size()==0){
			DatabaseMetaData metaData=conn.getMetaData();
			int driverMaxConnections=metaData.getMaxConnections();
			if(driverMaxConnections>0&&this.maxCon>driverMaxConnections)
				this.maxCon=driverMaxConnections;
		}
		return conn;
	}
	
	//
	public synchronized PooledConnection getConnection(){
		//确保连接池已经被创建
		if(connections==null)
			return null;
		//获取一个可用的数据库连接
		PooledConnection conn=getFreeConnection();
		while(conn==null){		//conn==null表示没有可用的连接,表示所有连接正在使用当中
			wait(250);
			conn=getFreeConnection();		//重新获取连接，知道获取为止
		}
		return conn;
	}
	
	//打印连接池中所有连接的状态
	public void print(){
		System.out.println("total connection:"+this.connections.size());
		for(int i=0;i<this.connections.size();i++){
			System.out.println(i+":"+this.connections.get(i).isBusy());
		}
	}
	
	//
	private PooledConnection getFreeConnection() {
		//从连接池中获取一个可用的数据库连接
		PooledConnection conn = findFreeConnection();
		
		if(conn==null){
			System.out.println("目前数据库中没有可用的连接,可用创建一些连接");
			createConnections(this.increatmentalConNum);
			conn=findFreeConnection();
			//若创建之后仍然得不到可用的连接,则返回null
			if(conn==null)
				return null;
		}
		return conn;
	}

	private PooledConnection findFreeConnection() {
		//从所有的连接中查找一个可用的连接
		for(int i=0;i<this.connections.size();i++){
			PooledConnection pc=this.connections.elementAt(i);
			if(!pc.isBusy()){
				Connection con=pc.getConnection();		//获取其连接
				pc.setBusy(true);
				//测试此连接是否可用,如果不可用则创建一个新的连接
				try {
					if(!con.isValid(3000)){
						//连接不可用则创建一个新的连接
						try {
							con=newConnection();
							//同时替换掉这个不可用的连接对象,若创建失败则删除此连接同时遍历下一个连接
							pc.setConnection(con);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							connections.remove(i--);
							continue;
						}
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return pc;		//找到一个可用的连接,退出循环
			}
		}
		return null;
	}
	
	//判断连接是否可用
	public boolean isValid(Connection con){
		try {
			return con.isValid(3000);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public void returnConnection(Connection conn){
		//确保连接池存在,若不存在则直接返回
		if(connections==null){
			return;
		}
		PooledConnection pConn=null;
		Enumeration<PooledConnection> enumerate=connections.elements();
		while(enumerate.hasMoreElements()){
			pConn=enumerate.nextElement();
			//先找到连接池中要返回的对象
			if(conn==pConn.getConnection()){
				pConn.setBusy(false);
				break;
			}
		}
	}
	
	//刷新所有连接池的对象
	public synchronized void refreshConnections(){
		if(connections==null){
			System.out.println("连接池不存在,不能够刷新!");
			return;
		}
		PooledConnection pConn=null;
		Enumeration<PooledConnection> enumerate=connections.elements();
		while(enumerate.hasMoreElements()){
			pConn=enumerate.nextElement();
			while(pConn.isBusy()){		//等待对象结束
				wait(1000);
			}
			//关闭一个连接,用新的连接来替代它
			closeConnection(pConn.getConnection());
			try {
				pConn.setConnection(newConnection());
				pConn.setBusy(false);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	//关闭池中所有连接并清空连接池
	public void closeConnectionPool() {
		if(connections==null){
			return;
		}
		PooledConnection pConn=null;
		Enumeration<PooledConnection> enumerate=connections.elements();
		while(enumerate.hasMoreElements()){
			pConn=enumerate.nextElement();
			if(pConn.isBusy()){		//等待对象结束
				wait(5000);
			}
			//关闭一个连接,用新的连接来替代它
			closeConnection(pConn.getConnection());
			//从连接池向量中直接删除它
			connections.removeElement(pConn);
		}
		//置连接池为空
		connections=null;
	}
	
	//关闭一个数据库连接
	private void closeConnection(Connection conn){
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//使程序等待指定的秒数
	private void wait(int seconds){
		try {
			Thread.sleep(seconds);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	class PooledConnection{
		private Connection connection=null;
		private boolean isBusy;
		
		//构造函数,根据一个Connection构造一个PooledConnection对象
		PooledConnection(Connection connection) {
			this.connection = connection;
		}
		
		//执行查询操作
		public ResultSet executeQuery(String sql) throws SQLException{
			return connection.createStatement().executeQuery(sql);
		}
		
		//执行更新操作
		public int executeUpdate(String sql) throws SQLException{
			return connection.createStatement().executeUpdate(sql);
		}
		
		//返回这个对象中的连接
		public Connection getConnection(){
			return connection;
		}

		public void setConnection(Connection con) {
			// TODO Auto-generated method stub
			this.connection=con;
		}

		public void setBusy(boolean busy) {
			// TODO Auto-generated method stub
			this.isBusy=busy;
		}

		public boolean isBusy() {
			// TODO Auto-generated method stub
			return this.isBusy;
		}
	}

}
