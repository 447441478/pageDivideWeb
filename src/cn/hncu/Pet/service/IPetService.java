package cn.hncu.Pet.service;

import java.sql.SQLException;
import java.util.Map;

import cn.hncu.domain.Pet;

public interface IPetService {
	/**
	 * 通过currentPage查询当前页内容
	 * @param currentPage 所查页码,必须大于等于1
	 * @param size 查询的记录数量,必须大于等于1
	 * @return map集合，封装了datas、CountPage
	 */
	Map<String, Object> queryByCurrentPage(int currentPage,int size) throws SQLException;

	Map<String, Object> likeQueryByCurrentPage(int currentPage, int size, Pet pet) throws SQLException;

}
