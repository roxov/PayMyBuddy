package consumer.DBConfig;

import java.sql.Connection;

import org.springframework.stereotype.Service;

@Service
public class DataBasePrepareService {

	public DataBaseTestConfig dataBaseTestConfig;

	public DataBasePrepareService(DataBaseTestConfig dataBaseTestConfig) {
		this.dataBaseTestConfig = dataBaseTestConfig;
	}

	public void clearDataBaseEntries() {
		Connection connection = null;
		try {
			connection = dataBaseTestConfig.getConnection();
//TODO : initialisation de la DB
//			// set parking entries to available
//			connection.prepareStatement("update parking set available = true").execute();
//
//			// clear ticket entries;
//			connection.prepareStatement("truncate table ticket").execute();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dataBaseTestConfig.closeConnection(connection);
		}
	}
}
