package fr.asterox.PayMyBuddy.consumer;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import fr.asterox.PayMyBuddy.model.CreditBankDetails;
import fr.asterox.PayMyBuddy.model.UserAccount;

@Repository
public class CreditBankDetailsDAO {
	private static final Logger logger = LogManager.getLogger(CreditBankDetailsDAO.class);
	private DataBaseConfig dataBaseConfig;

	CreditBankDetailsDAO(DataBaseConfig dataBaseConfig) {
		this.dataBaseConfig = dataBaseConfig;
	}

	public void addCreditBankDetails(CreditBankDetails creditBankDetails, UserAccount userAccount) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = dataBaseConfig.getConnection();
			ps = con.prepareStatement(
					"INSERT INTO CREDIT_BANK_DETAILS(HOLDER_NAME, IBAN, BIC) VALUES(?,?,?) WHERE USER_ID=?");
			ps.setString(1, creditBankDetails.getHolderName());
			ps.setString(2, creditBankDetails.getIBAN());
			ps.setString(3, creditBankDetails.getBIC());
			ps.setLong(4, userAccount.getUserID());
			ps.executeUpdate();

//			ResultSet rs = ps.executeQuery(); executeQuery pour un SELECT
//			if (rs.next()) {
//				result = rs.getInt(1);
//				;

		} catch (Exception e) {
			logger.error("Error adding Credit Bank Detail", e);
		} finally {
			dataBaseConfig.closePreparedStatement(ps);
			dataBaseConfig.closeConnection(con);
		}
	}

}
