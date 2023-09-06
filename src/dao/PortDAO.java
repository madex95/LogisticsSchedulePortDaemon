package dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import setting.SqlSessionFactorySetting;

public class PortDAO {
	
	private static PortDAO portDao;
	
	private SqlSessionFactory sessionFactory = SqlSessionFactorySetting.getSqlSessionFactory();
	
	public static PortDAO getInstance() {
		if (portDao == null)
			portDao = new PortDAO();
		return portDao;
	}

}
