package fr.asterox.PayMyBuddy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class CreditBankDetails {
	@Column(name = "CREDIT_ID")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long creditId;
	@ManyToOne
	@JoinColumn(name = "USER_ID", nullable = false)
	private UserAccount user;
	@Column(name = "HOLDER_NAME")
	private String holderName;
	private String iban;
	private String bic;

	public CreditBankDetails() {
		super();
	}

	public CreditBankDetails(UserAccount user, String holderName, String iban, String bic) {
		super();
		this.user = user;
		this.holderName = holderName;
		this.iban = iban;
		this.bic = bic;
	}

	public CreditBankDetails(Long creditId, UserAccount user, String holderName, String iban, String bic) {
		super();
		this.creditId = creditId;
		this.user = user;
		this.holderName = holderName;
		this.iban = iban;
		this.bic = bic;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bic == null) ? 0 : bic.hashCode());
		result = prime * result + ((creditId == null) ? 0 : creditId.hashCode());
		result = prime * result + ((holderName == null) ? 0 : holderName.hashCode());
		result = prime * result + ((iban == null) ? 0 : iban.hashCode());
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
		CreditBankDetails other = (CreditBankDetails) obj;
		if (bic == null) {
			if (other.bic != null)
				return false;
		} else if (!bic.equals(other.bic))
			return false;
		if (creditId == null) {
			if (other.creditId != null)
				return false;
		} else if (!creditId.equals(other.creditId))
			return false;
		if (holderName == null) {
			if (other.holderName != null)
				return false;
		} else if (!holderName.equals(other.holderName))
			return false;
		if (iban == null) {
			if (other.iban != null)
				return false;
		} else if (!iban.equals(other.iban))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	public Long getCreditId() {
		return creditId;
	}

	public void setCreditId(Long creditId) {
		this.creditId = creditId;
	}

	public UserAccount getUser() {
		return user;
	}

	public void setUser(UserAccount user) {
		this.user = user;
	}

	public String getHolderName() {
		return holderName;
	}

	public void setHolderName(String holderName) {
		this.holderName = holderName;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public String getBic() {
		return bic;
	}

	public void setBic(String bic) {
		this.bic = bic;
	}

}
