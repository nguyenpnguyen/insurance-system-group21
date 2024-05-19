package org.group21.insurance.utils;

/**
 * @author Group 21
 */

import org.group21.insurance.controllers.ClaimController;
import org.group21.insurance.models.Beneficiary;
import org.group21.insurance.models.Claim;
import org.group21.insurance.models.PolicyOwner;

import java.time.LocalDate;

public class ClaimCalculator {
	
	public static long calculateYearlyClaimPayment(PolicyOwner policyOwner) {
		long totalClaim = 0;
		for (Beneficiary b : policyOwner.getBeneficiaries()) {
			totalClaim += b.getInsuranceRate();
		}
		return totalClaim;
	}
	
	public static long calculateTotalSuccessfulClaimAmount() {
		ClaimController claimController = ClaimController.getInstance();
		
		long totalSuccessfulClaimAmount = 0;
		for (Claim c : claimController.getClaimByStatus(Claim.ClaimStatus.DONE)) {
			totalSuccessfulClaimAmount += c.getClaimAmount();
		}
		return totalSuccessfulClaimAmount;
	}
	
	public static long calculateTotalSuccessfulClaimAmount(Beneficiary beneficiary) {
		ClaimController claimController = ClaimController.getInstance();
		
		long totalSuccessfulClaimAmount = 0;
		for (Claim c : claimController.getClaimByStatus(Claim.ClaimStatus.DONE)) {
			// Check if the claim belongs to the specified beneficiary and falls within the date range
			if (c.getInsuredPerson().equals(beneficiary)) {
				totalSuccessfulClaimAmount += c.getClaimAmount();
			}
		}
		return totalSuccessfulClaimAmount;
	}
	
	public static long calculateTotalSuccessfulClaimAmount(PolicyOwner policyOwner) {
		ClaimController claimController = ClaimController.getInstance();
		
		long totalSuccessfulClaimAmount = 0;
		for (Claim c : claimController.getClaimByStatus(Claim.ClaimStatus.DONE)) {
			// Check if the claim belongs to the specified beneficiary and falls within the date range
			if (c.getInsuredPerson().getPolicyOwner().equals(policyOwner)) {
				totalSuccessfulClaimAmount += c.getClaimAmount();
			}
		}
		return totalSuccessfulClaimAmount;
	}
	
	public static long calculateTotalSuccessfulClaimAmount(LocalDate startDate, LocalDate endDate) {
		ClaimController claimController = ClaimController.getInstance();
		
		long totalSuccessfulClaimAmount = 0;
		for (Claim c : claimController.getClaimByStatus(Claim.ClaimStatus.DONE)) {
			// Check if the claim falls within the date range
			if (!c.getClaimDate().isBefore(startDate) && !c.getClaimDate().isAfter(endDate)) {
				totalSuccessfulClaimAmount += c.getClaimAmount();
			}
		}
		return totalSuccessfulClaimAmount;
	}
	
	public static long calculateTotalSuccessfulClaimAmount(Beneficiary beneficiary, LocalDate startDate, LocalDate endDate) {
		ClaimController claimController = ClaimController.getInstance();
		
		long totalSuccessfulClaimAmount = 0;
		for (Claim c : claimController.getClaimByStatus(Claim.ClaimStatus.DONE)) {
			// Check if the claim belongs to the specified beneficiary and falls within the date range
			if (c.getInsuredPerson().equals(beneficiary) &&
					!c.getClaimDate().isBefore(startDate) &&
					!c.getClaimDate().isAfter(endDate)) {
				totalSuccessfulClaimAmount += c.getClaimAmount();
			}
		}
		return totalSuccessfulClaimAmount;
	}
	
	public static long calculateTotalSuccessfulClaimAmount(PolicyOwner policyOwner, LocalDate startDate, LocalDate endDate) {
		ClaimController claimController = ClaimController.getInstance();
		
		long totalSuccessfulClaimAmount = 0;
		for (Claim c : claimController.getClaimByStatus(Claim.ClaimStatus.DONE)) {
			// Check if the claim belongs to the specified policy owner and falls within the date range
			if (c.getInsuredPerson().getPolicyOwner().equals(policyOwner) &&
					!c.getClaimDate().isBefore(startDate) &&
					!c.getClaimDate().isAfter(endDate)) {
				totalSuccessfulClaimAmount += c.getClaimAmount();
			}
		}
		return totalSuccessfulClaimAmount;
	}
}
