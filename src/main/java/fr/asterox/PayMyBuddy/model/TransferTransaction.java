package fr.asterox.PayMyBuddy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class TransferTransaction {
	@Column(name = "TRANSFER_ID")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long transferId;
	@ManyToOne
	@JoinColumn(name = "USER_ID", nullable = false)
	private UserAccount user;
	private double amount;
	private boolean credit;
	@ManyToOne
	@JoinColumn(name = "CREDIT_ID", nullable = true)
	private CreditBankDetails creditBankDetails;
	@ManyToOne
	@JoinColumn(name = "DEBIT_ID", nullable = true)
	private DebitBankDetails debitBankDetails;

	public TransferTransaction() {
		super();
	}

	public TransferTransaction(UserAccount user, double amount, boolean credit, CreditBankDetails creditBankDetails,
			DebitBankDetails debitBankDetails) {
		super();
		this.user = user;
		this.amount = amount;
		this.credit = credit;
		this.creditBankDetails = creditBankDetails;
		this.debitBankDetails = debitBankDetails;
	}

	public TransferTransaction(Long transferId, UserAccount user, double amount, boolean credit,
			CreditBankDetails creditBankDetails, DebitBankDetails debitBankDetails) {
		super();
		this.transferId = transferId;
		this.user = user;
		this.amount = amount;
		this.credit = credit;
		this.creditBankDetails = creditBankDetails;
		this.debitBankDetails = debitBankDetails;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (credit ? 1231 : 1237);
		result = prime * result + ((creditBankDetails == null) ? 0 : creditBankDetails.hashCode());
		result = prime * result + ((debitBankDetails == null) ? 0 : debitBankDetails.hashCode());
		result = prime * result + ((transferId == null) ? 0 : transferId.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TransferTransaction other = (TransferTransaction) obj;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		if (credit != other.credit)
			return false;
		if (creditBankDetails == null) {
			if (other.creditBankDetails != null)
				return false;
		} else if (!creditBankDetails.equals(other.creditBankDetails))
			return false;
		if (debitBankDetails == null) {
			if (other.debitBankDetails != null)
				return false;
		} else if (!debitBankDetails.equals(other.debitBankDetails))
			return false;
		if (transferId == null) {
			if (other.transferId != null)
				return false;
		} else if (!transferId.equals(other.transferId))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	public Long getTransferId() {
		return transferId;
	}

	public void setTransferId(Long transferId) {
		this.transferId = transferId;
	}

	public UserAccount getUser() {
		return user;
	}

	public void setUser(UserAccount user) {
		this.user = user;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public boolean isCredit() {
		return credit;
	}

	public void setCredit(boolean credit) {
		this.credit = credit;
	}

	public CreditBankDetails getCreditBankDetails() {
		return creditBankDetails;
	}

	public void setCreditBankDetails(CreditBankDetails creditBankDetails) {
		this.creditBankDetails = creditBankDetails;
	}

	public DebitBankDetails getDebitBankDetails() {
		return debitBankDetails;
	}

	public void setDebitBankDetails(DebitBankDetails debitBankDetails) {
		this.debitBankDetails = debitBankDetails;
	}

}
