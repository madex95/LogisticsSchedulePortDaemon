package setting;

import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class SqlSessionFactorySetting {

	private static SqlSessionFactory sqlSessionFactory;
	
	static {
		try {
			String config = "config/MybatisConfig.xml";
			Reader reader = Resources.getResourceAsReader(config);
			
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		} catch (Exception e){
			e.printStackTrace();
		} // catch end	
	} // static end
	
    public static SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }
    
}
