package cn.hncu.Pet.dao.mysql;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.hncu.Pet.dao.PetDAO;
import cn.hncu.domain.Pet;
import cn.hncu.pub.C3p0Utils;
/*
CREATE TABLE pet(
	id INT PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(20),
	color VARCHAR(10)
);
 */
public class PetDaoJdbc implements PetDAO {
	/*
	 * 所需数据：
	 * datas：List< Map<String,Object> > 一个Map对象存放一条记录
	 * countPage:总页数
	 * 封装到 result 中
	 */
	@Override
	public Map<String, Object> queryByCurrentPage( int currentPage,int size ) throws SQLException {
		HashMap<String, Object> result = new HashMap<String,Object>();
		
		//获得数据库连接池
		DataSource ds = C3p0Utils.getDataSource();
		//获取 QueryRunner的实例
		QueryRunner qr = new QueryRunner(ds);
		
		//先查询总的记录数
		Integer count = Integer.valueOf( ""+qr.query("select count(1) from pet", new ScalarHandler()) );
		
		//计算总的页数
		int countPage = (count-1)/size+1;
		result.put("countPage", countPage);
		
		//查询当前页的记录
		int startIdx = (currentPage-1)*size;
		List<Map<String, Object>> datas = qr.query("select * from pet limit ?,?", new MapListHandler(),startIdx,size);
		result.put("datas", datas);
		
		return result;
	}

	@Override
	public Map<String, Object> likeQueryByCurrentPage(int currentPage, int size, Pet pet) throws SQLException {
		HashMap<String, Object> result = new HashMap<String,Object>();
		
		//获得数据库连接池
		DataSource ds = C3p0Utils.getDataSource();
		//获取 QueryRunner的实例
		QueryRunner qr = new QueryRunner(ds);
		
		
		////////拼装模糊查询语句和数据////////////
		String sql1 = "select count(1) from pet where 1=1 ";
		String sql2 = "select * from pet where 1=1 ";
		List<Object> conditions = new ArrayList<Object>();
		String name = pet.getName();
		if( name != null && name.trim().length() != 0 ) {
			sql1 += "and name like ? ";
			sql2 += "and name like ? ";
			conditions.add("%"+name+"%");
		}
		String color = pet.getColor();
		if( color != null && color.trim().length() != 0 ) {
			sql1 += "and color like ? ";
			sql2 += "and color like ? ";
			conditions.add("%"+color+"%");
		}
		
		sql2 += "limit ?,?"; //执行该sql语句前需要把额外条件封装进conditions中！！！
		///////////////////////////////
		
		//先查询总的记录数
		Integer count = Integer.valueOf( ""+qr.query( sql1, new ScalarHandler(), conditions.toArray() ) );
		
		//计算总的页数
		int countPage = (count-1)/size+1;
		result.put("countPage", countPage);
		
		//查询当前页的记录
		int startIdx = (currentPage-1)*size;
		
		////////补充条件////////
		conditions.add(startIdx); //起始位置
		conditions.add(size); //查询的记录数量
		/////////////////////
		
		List<Map<String, Object>> datas = qr.query( sql2, new MapListHandler(), conditions.toArray() );
		result.put("datas", datas);
		
		return result;
	}

}
