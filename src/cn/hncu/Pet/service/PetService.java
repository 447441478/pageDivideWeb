package cn.hncu.Pet.service;

import java.sql.SQLException;
import java.util.Map;

import cn.hncu.Pet.dao.PetDAO;
import cn.hncu.Pet.dao.mysql.PetDaoJdbc;
import cn.hncu.domain.Pet;

public class PetService implements IPetService {

	//注入dao
	private PetDAO dao = new PetDaoJdbc();
	
	@Override
	public Map<String, Object> queryByCurrentPage(int currentPage, int size) throws SQLException {
		return dao.queryByCurrentPage(currentPage, size);
	}

	@Override
	public Map<String, Object> likeQueryByCurrentPage(int currentPage, int size, Pet pet) throws SQLException {
		return dao.likeQueryByCurrentPage(currentPage,size,pet);
	}

}
