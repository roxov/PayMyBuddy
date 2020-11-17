package fr.asterox.PayMyBuddy.consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentTransactionDAO {
	private static final Logger LOGGER = LogManager.getLogger(PaymentTransactionDAO.class);
	private DataBaseConfig dataBaseConfig;

	PaymentTransactionDAO(DataBaseConfig dataBaseConfig) {
		this.dataBaseConfig = dataBaseConfig;
	}
}
