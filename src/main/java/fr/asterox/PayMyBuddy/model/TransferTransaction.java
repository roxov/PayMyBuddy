package fr.asterox.PayMyBuddy.model;

public class TransferTransaction {
	private Long transferID;
	private UserAccount user;
	private double amount;
	private boolean credit;

	public TransferTransaction() {
		super();
	}

	public TransferTransaction(Long transferID, UserAccount user, double amount, boolean credit) {
		super();
		this.transferID = transferID;
		this.user = user;
		this.amount = amount;
		this.credit = credit;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (credit ? 1231 : 1237);
		result = prime * result + ((transferID == null) ? 0 : transferID.hashCode());
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
		if (transferID == null) {
			if (other.transferID != null)
				return false;
		} else if (!transferID.equals(other.transferID))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	public Long getTransferID() {
		return transferID;
	}

	public void setTransferID(Long transferID) {
		this.transferID = transferID;
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

}
