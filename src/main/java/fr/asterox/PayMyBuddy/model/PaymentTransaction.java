package fr.asterox.PayMyBuddy.model;

public class PaymentTransaction {
	private Long paymentID;
	private UserAccount issuer;
	private UserAccount recipient;
	private String description;
	private double amount;

	public PaymentTransaction() {
		super();
	}

	public PaymentTransaction(Long paymentID, UserAccount issuer, UserAccount recipient, String description,
			double amount) {
		super();
		this.paymentID = paymentID;
		this.issuer = issuer;
		this.recipient = recipient;
		this.description = description;
		this.amount = amount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((issuer == null) ? 0 : issuer.hashCode());
		result = prime * result + ((paymentID == null) ? 0 : paymentID.hashCode());
		result = prime * result + ((recipient == null) ? 0 : recipient.hashCode());
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
		PaymentTransaction other = (PaymentTransaction) obj;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (issuer == null) {
			if (other.issuer != null)
				return false;
		} else if (!issuer.equals(other.issuer))
			return false;
		if (paymentID == null) {
			if (other.paymentID != null)
				return false;
		} else if (!paymentID.equals(other.paymentID))
			return false;
		if (recipient == null) {
			if (other.recipient != null)
				return false;
		} else if (!recipient.equals(other.recipient))
			return false;
		return true;
	}

	public Long getPaymentID() {
		return paymentID;
	}

	public void setPaymentID(Long paymentID) {
		this.paymentID = paymentID;
	}

	public UserAccount getIssuer() {
		return issuer;
	}

	public void setIssuer(UserAccount issuer) {
		this.issuer = issuer;
	}

	public UserAccount getRecipient() {
		return recipient;
	}

	public void setRecipient(UserAccount recipient) {
		this.recipient = recipient;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

}
