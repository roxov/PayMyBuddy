package fr.asterox.PayMyBuddy.consumer;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import fr.asterox.PayMyBuddy.model.DebitBankDetails;
import fr.asterox.PayMyBuddy.model.UserAccount;

@Repository
public class DebitBankDetailsDAO {
	private static final Logger LOGGER = LogManager.getLogger(DebitBankDetailsDAO.class);
	private DataBaseConfig dataBaseConfig;

	DebitBankDetailsDAO(DataBaseConfig dataBaseConfig) {
		this.dataBaseConfig = dataBaseConfig;
	}

	public void addDeditBankDetails(DebitBankDetails deditBankDetails, UserAccount userAccount) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = dataBaseConfig.getConnection();
			ps = con.prepareStatement(
					"INSERT INTO DEDIT_BANK_DETAILS(HOLDER_NAME, CARD_NUMBER, EXPIRATION_DATE, CVV) VALUES(?,?,?,?) WHERE USER_ID=?");
			ps.setString(1, deditBankDetails.getHolderName());
			ps.setInt(2, deditBankDetails.getCardNumber());
			ps.setInt(3, deditBankDetails.getExpirationDate());
			ps.setInt(4, deditBankDetails.getCVV());
			ps.setLong(5, userAccount.getUserID());
			ps.executeUpdate();
			LOGGER.info("Adding Debit Bank Details");
		} catch (Exception e) {
			LOGGER.error("Error adding Dedit Bank Details", e);
		} finally {
			dataBaseConfig.closePreparedStatement(ps);
			dataBaseConfig.closeConnection(con);
		}
	}

	public void updateDeditBankDetails(DebitBankDetails deditBankDetails, UserAccount userAccount) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = dataBaseConfig.getConnection();
			ps = con.prepareStatement(
					"UPDATE CREDIT_BANK_DETAILS SET HOLDER_NAME=?, CARD_NUMBER=?, EXPIRATION_DATE=?, CVV=? WHERE USER_ID= ?");
			ps.setString(1, deditBankDetails.getHolderName());
			ps.setInt(2, deditBankDetails.getCardNumber());
			ps.setInt(3, deditBankDetails.getExpirationDate());
			ps.setInt(4, deditBankDetails.getCVV());
			ps.setLong(5, userAccount.getUserID());

			ps.executeUpdate();
			LOGGER.info("Updating Debit Bank Details");
		} catch (Exception e) {
			LOGGER.error("Error adding Credit Bank Detail", e);
		} finally {
			dataBaseConfig.closePreparedStatement(ps);
			dataBaseConfig.closeConnection(con);
		}
	}
}
