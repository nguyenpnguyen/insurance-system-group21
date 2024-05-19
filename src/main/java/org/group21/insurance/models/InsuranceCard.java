package org.group21.insurance.models;

/**
 * This class represents an insurance card in the Insurance Claim Management System.
 * It includes information such as card number, policy owner, card holder, and expiration date.
 * Each insurance card is associated with a specific policy owner and card holder.
 *
 * @author Group 21
 */

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity(name = "InsuranceCard")
@Table(name = "insurance_cards")
public class InsuranceCard implements Serializable {
	@Id
	@GeneratedValue(generator = "card-number-generator")
	@GenericGenerator(name = "card-number-generator",
			strategy = "org.group21.insurance.utils.IdGenerator",
			parameters = {
					@org.hibernate.annotations.Parameter(name = "prefix", value = "ic"),
					@org.hibernate.annotations.Parameter(name = "length", value = "10")
			})
	@Column(name = "card_number")
	private String cardNumber;

	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
	@JoinColumn(name = "policy_owner_id", referencedColumnName = "customer_id")
	private PolicyOwner policyOwner;

	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
	@JoinColumn(name = "card_holder_id", referencedColumnName = "customer_id")
	private Beneficiary cardHolder;

	@Column(name = "expiration_date")
	private LocalDate expirationDate;

	/**
	 * Default constructor for the InsuranceCard class.
	 */
	public InsuranceCard() {
	}

	/**
	 * Constructor for the InsuranceCard class.
	 *
	 * @param cardNumber The card number.
	 */
	public InsuranceCard(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	/**
	 * This method returns the card number.
	 *
	 * @return The card number.
	 */
	public String getCardNumber() {
		return cardNumber;
	}

	/**
	 * This method sets the card number.
	 *
	 * @param cardNumber The card number.
	 */
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	/**
	 * This method returns the policy owner.
	 *
	 * @return The policy owner.
	 */
	public PolicyOwner getPolicyOwner() {
		return policyOwner;
	}

	/**
	 * This method sets the policy owner.
	 *
	 * @param policyOwner The policy owner.
	 */
	public void setPolicyOwner(PolicyOwner policyOwner) {
		this.policyOwner = policyOwner;
	}

	/**
	 * This method returns the card holder.
	 *
	 * @return The card holder.
	 */
	public Beneficiary getCardHolder() {
		return cardHolder;
	}

	/**
	 * This method sets the card holder.
	 *
	 * @param cardHolder The card holder.
	 */
	public void setCardHolder(Beneficiary cardHolder) {
		this.cardHolder = cardHolder;
	}

	/**
	 * This method returns the expiration date of the card.
	 *
	 * @return The expiration date of the card.
	 */
	public LocalDate getExpirationDate() {
		return expirationDate;
	}

	/**
	 * This method sets the expiration date of the card.
	 *
	 * @param expirationDate The expiration date of the card.
	 */
	public void setExpirationDate(LocalDate expirationDate) {
		this.expirationDate = expirationDate;
	}

	/**
	 * This method checks if the current object is equal to the given object.
	 *
	 * @param o The object to be compared with the current object.
	 * @return True if the objects are equal, false otherwise.
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		InsuranceCard that = (InsuranceCard) o;
		return Objects.equals(getCardNumber(), that.getCardNumber());
	}

	/**
	 * This method returns the hash code of the current object.
	 *
	 * @return The hash code of the current object.
	 */
	@Override
	public int hashCode() {
		return Objects.hashCode(getCardNumber());
	}

	/**
	 * This method returns a string representation of the current object.
	 *
	 * @return A string representation of the current object.
	 */
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Card Number: ");
		sb.append(cardNumber);
		return sb.toString();
	}
}