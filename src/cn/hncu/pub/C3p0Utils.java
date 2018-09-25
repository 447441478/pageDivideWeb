package cn.hncu.pub;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * &emsp;&emsp;c3p0工具<br/>
 * &emsp;&emsp;<b>注意</b>：必须在src下面有一个配置文件：c3p0-config.xml，名称和路径都是写死的！！！
 * <br/><br/><b>CreateTime:</b><br/>&emsp;&emsp;&emsp; 2018年9月24日 上午8:00:29	
 * @author 宋进宇&emsp;<a href='mailto:447441478@qq.com'>447441478@qq.com</a>
 */
public class C3p0Utils {
	//数据库连接池
	private static final DataSource DS;
	//线程局部变量池
	private static final ThreadLocal<Connection> THREAD_LOCAL_POOL;
	//初始化数据库连接池和线程局部变量池
	static {
		//注意：必须在src下面有一个配置文件：c3p0-config.xml，名称和路径都是写死的！！！
		DS = new ComboPooledDataSource();
		//初始化线程局部变量池
		THREAD_LOCAL_POOL = new ThreadLocal<>();
	}
	/**
	 * 获得c3p0连接池
	 * @return c3p0连接池对象
	 */
	public static DataSource getDataSource() {
		return DS;
	}
	
	/**
	 * 获取一个数据库连接
	 * @return 数据库连接对象
	 * @throws SQLException 
	 */
	public static Connection getConnection() throws SQLException {
		//先从线程局部变量池中获取当前线程拥有数据库连接
		Connection con = THREAD_LOCAL_POOL.get();
		//如果当前线程拥有的数据库连接为null或者是closed状态,那么从连接池中获取一个连接
		if( con == null || con.isClosed() ) {
			//从连接池中获取一个连接
			con = DS.getConnection();
			//把获取到的连接放到线程局部变量池中，以便同一个线程共享一个数据库连接。
			THREAD_LOCAL_POOL.set(con);
		}
		return con;
	}
}
