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
			// clear tables entries;
			connection.prepareStatement(
					"truncate table user_account, credit_bank_details, debit_bank_details, friends_network, payment_transaction, transfer_transaction")
					.execute();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dataBaseTestConfig.closeConnection(connection);
		}
	}
}
