package fr.asterox.PayMyBuddy.consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class TransferTransactionDAO {
	private static final Logger LOGGER = LogManager.getLogger(TransferTransactionDAO.class);
	private DataBaseConfig dataBaseConfig;

	TransferTransactionDAO(DataBaseConfig dataBaseConfig) {
		this.dataBaseConfig = dataBaseConfig;
	}
}
