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
	
	public void mergePortSchedule(List<Map<String,Object>> list) {
		
		try {
			SqlSession session = sessionFactory.openSession(true);
			session.update("PortMapper.mergePortSchedule",list);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
