package org.group21.insurance.data;

/**
 * @author Group 21
 */

import org.group21.insurance.authentication.PasswordAuthenticator;
import org.group21.insurance.controllers.*;
import org.group21.insurance.models.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Class to seed data into database, upload documents
 *
 * @author Group 21
 */

public class DataSeeder {
	
	/**
	 * Seed data into database
	 */
	public static void seedData() {
		// Delete all data
		SystemAdminController systemAdminController = SystemAdminController.getInstance();
		PolicyOwnerController policyOwnerController = PolicyOwnerController.getInstance();
		InsuranceProviderController insuranceProviderController = InsuranceProviderController.getInstance();
		InsuranceCardController insuranceCardController = InsuranceCardController.getInstance();
		DocumentController documentController = DocumentController.getInstance();
		ClaimController claimController = ClaimController.getInstance();
		BeneficiaryController beneficiaryController = BeneficiaryController.getInstance();
		BankingInfoController bankingInfoController = BankingInfoController.getInstance();
		
		systemAdminController.deleteAll();
		insuranceProviderController.deleteAll();
		bankingInfoController.deleteAll();
		insuranceCardController.deleteAll();
		policyOwnerController.deleteAll();
		beneficiaryController.deleteAll();
		documentController.deleteAll();
		claimController.deleteAll();
		
		// Seed data
		PasswordAuthenticator passwordAuthenticator = new PasswordAuthenticator();
		// Create 1 system admin
		SystemAdmin systemAdmin = new SystemAdmin();
		systemAdmin.setUsername("admin");
		systemAdmin.setHashedPassword(passwordAuthenticator.hash("admin".toCharArray()));
		systemAdminController.create(systemAdmin);
		
		// Create 10 policy owners
		String[] policyOwnerNames = {"RMIT", "FPT", "Vietnam Airlines", "Bamboo Airways", "Vietjet Air",
				"Viettel", "VinGroup", "VNPT", "Petrolimex", "SunGroup"};
		
		for (int i = 0; i < 10; i++) {
			PolicyOwner policyOwner = new PolicyOwner();
			policyOwner.setUsername("policyowner" + i);
			policyOwner.setHashedPassword(passwordAuthenticator.hash(("policyowner" + i).toCharArray()));
			policyOwner.setFullName(policyOwnerNames[i]);
			
			// Set email as lowercase no space name
			policyOwner.setEmail(policyOwner.getFullName().toLowerCase().replaceAll("\\s+", "") + "@example.com");
			policyOwner.setAddress("3" + i + "Example St, Example City");
			policyOwner.setPhoneNumber("223456789" + i);
			
			// Set 1 policy holder for each policy owner
			
			policyOwnerController.create(policyOwner);
		}
		
		List<PolicyOwner> policyOwners = policyOwnerController.readAll();
		
		// Create 10 policy holders
		String[] policyHolderNames = generateRandomNames(10);
		for (int i = 0; i < 10; i++) {
			Beneficiary policyHolder = new Beneficiary();
			policyHolder.setUsername("policyholder" + i);
			policyHolder.setHashedPassword(passwordAuthenticator.hash(("policyholder" + i).toCharArray()));
			policyHolder.setFullName(policyHolderNames[i]);
			policyHolder.setIsPolicyHolder(true);
			policyHolder.setEmail("policyholder" + i + "@example.com");
			policyHolder.setAddress("1" + i + "Example St, Example City");
			policyHolder.setPhoneNumber("023456789" + i);
			policyHolder.setInsuranceRate(ThreadLocalRandom.current().nextLong(10_000L, 100_000L) * 1000L);
			policyHolder.setPolicyOwner(policyOwners.get(i));
			beneficiaryController.create(policyHolder);
		}
		
		List<Beneficiary> policyHolders = beneficiaryController.readAllPolicyHolders();
		
		// Create 20 dependents
		String[] dependentNames = generateRandomNames(20);
		for (int i = 0; i < 20; i++) {
			Beneficiary dependent = new Beneficiary();
			dependent.setUsername("dependent" + i);
			dependent.setHashedPassword(passwordAuthenticator.hash(("dependent" + i).toCharArray()));
			dependent.setIsPolicyHolder(false);
			dependent.setFullName(dependentNames[i]);
			dependent.setEmail("dependent" + i + "@example.com");
			
			// Set 2 dependents for each policy holder
			dependent.setPolicyHolder(policyHolders.get(i < 10 ? i : i - 10));
			
			dependent.setAddress("2" + i + "Example St, Example City");
			dependent.setPhoneNumber("123456789" + i);
			dependent.setInsuranceRate();
			dependent.setPolicyOwner(dependent.getPolicyHolder().getPolicyOwner());
			beneficiaryController.create(dependent);
		}
		
		List<Beneficiary> beneficiaries = beneficiaryController.readAll();
		// Create 30 insurance cards
		for (int i = 0; i < 30; i++) {
			InsuranceCard insuranceCard = new InsuranceCard();
			insuranceCard.setCardHolder(beneficiaries.get(i));
			insuranceCard.setPolicyOwner(insuranceCard.getCardHolder().getPolicyOwner());
			insuranceCard.setExpirationDate(LocalDate.now().
					plusYears(ThreadLocalRandom.current().nextInt(0, 5)).
					plusDays(ThreadLocalRandom.current().nextInt(1, 365)));
			insuranceCardController.create(insuranceCard);
		}
		
		// Set insurance cards for all beneficiaries based on the insurance card created
		List<InsuranceCard> insuranceCards = insuranceCardController.readAll();
		for (int i = 0; i < 30; i++) {
			beneficiaries.get(i).setInsuranceCard(insuranceCards.get(i));
			beneficiaryController.update(beneficiaries.get(i));
		}
		
		// Create 15 insurance managers
		String[] insuranceManagerNames = generateRandomNames(15);
		for (int i = 0; i < 15; i++) {
			InsuranceProvider insuranceManager = new InsuranceProvider();
			insuranceManager.setUsername("manager" + i);
			insuranceManager.setHashedPassword(passwordAuthenticator.hash(("manager" + i).toCharArray()));
			insuranceManager.setInsuranceManager(true);
			insuranceManager.setFullName(insuranceManagerNames[i]);
			insuranceManager.setEmail("manager" + i + "@example.com");
			insuranceManager.setPhoneNumber("323456789" + i);
			insuranceProviderController.create(insuranceManager);
		}
		
		// Create 15 insurance surveyors
		String[] insuranceSurveyorNames = generateRandomNames(15);
		for (int i = 0; i < 15; i++) {
			InsuranceProvider insuranceSurveyor = new InsuranceProvider();
			insuranceSurveyor.setUsername("surveyor" + i);
			insuranceSurveyor.setHashedPassword(passwordAuthenticator.hash(("surveyor" + i).toCharArray()));
			insuranceSurveyor.setInsuranceManager(false);
			insuranceSurveyor.setFullName(insuranceSurveyorNames[i]);
			insuranceSurveyor.setEmail("surveyor" + i + "@example.com");
			insuranceSurveyor.setPhoneNumber("423456789" + i);
			insuranceProviderController.create(insuranceSurveyor);
		}
		
		// Create 30 banking infos
		String[] banks = {"BIDV", "Vietcombank", "Vietinbank", "Agribank", "Techcombank"
				, "MBBank", "VPBank", "Sacombank", "ACB", "SHB"};
		for (int i = 0; i < 30; i++) {
			BankingInfo bankingInfo = new BankingInfo();
			bankingInfo.setAccountNumber("987654321" + i);
			bankingInfo.setBank(banks[ThreadLocalRandom.current().nextInt(0, banks.length)]);
			bankingInfo.setName(beneficiaries.get(i).getFullName());
			bankingInfoController.create(bankingInfo);
		}
		
		List<BankingInfo> bankingInfos = bankingInfoController.readAll();
		// Create 30 claims
		for (int i = 0; i < 30; i++) {
			Claim claim = new Claim();
			claim.setStatus(Claim.ClaimStatus.values()[ThreadLocalRandom.current().nextInt(0, Claim.ClaimStatus.values().length)]);
			claim.setClaimDate(LocalDate.now().minusDays(ThreadLocalRandom.current().nextInt(1, 365)));
			claim.setExamDate(claim.getClaimDate().plusDays(ThreadLocalRandom.current().nextInt(1, 365)));
			claim.setClaimAmount(ThreadLocalRandom.current().nextLong(1_000L, 100_000L) * 1000L);
			claim.setInsuredPerson(beneficiaries.get(i));
			claim.setInsuranceCard(claim.getInsuredPerson().getInsuranceCard());
			
			for (BankingInfo bi : bankingInfos) {
				if (bi.getName().equals(claim.getInsuredPerson().getFullName())) {
					claim.setReceiverBankingInfo(bi);
					break;
				}
			}
			claimController.create(claim);
		}
		
		// Clone sample documents
		final String sourceFileName = "./src/main/resources/sample-docs/sample-doc-1.pdf";
		final String baseName = "./src/main/resources/sample-docs/sample-doc";
		final int numCopies = 30;
		cloneFiles(sourceFileName, baseName, numCopies);
		
		List<Claim> claims = claimController.readAll();
		// Upload 30 documents and assign to 30 claims
		for (int i = 1; i < 31; i++) {
			try {
				documentController.uploadDocument(new File("./src/main/resources/sample-docs/sample-doc-" + i + ".pdf"),
						claims.get(i - 1));
			} catch (IOException | GeneralSecurityException e) {
				System.err.println("An error occurred during document upload: " + e.getMessage());
			}
		}
		
		// Remove cloned documents
		removeClonedFiles(baseName, numCopies);
	}
	
	/**
	 * Generate random names
	 *
	 * @param count Number of names to generate
	 *
	 * @return Array of random names
	 */
	private static String[] generateRandomNames(int count) {
		
		String[] firstNames = {"Nguyen", "Ly", "Tran", "Le", "Pham",
				"Huynh", "Hoang", "Phan", "Vu", "Vo",
				"Dang", "Bui", "Do", "Ho", "Ngo",
				"Duong", "Ly", "Chu", "Vuong", "Dinh",
				"Luong", "Tang", "Trinh", "Truong"};
		
		String[] middleNames = {"Van", "Anh", "Thi", "Phuc", "Tuan",
				"Thuy", "Minh", "Dinh", "Huu", "Thao", "Hai", "Hoa"};
		
		String[] lastNames = {"An", "Bao", "Binh", "Chau", "Chi", "Chinh",
				"Chung", "Cuong", "Dai", "Dang", "Dat", "Diep", "Dinh", "Dong",
				"Duc", "Dung", "Duong", "Giang", "Ha", "Hai", "Han", "Hang",
				"Hanh", "Hao", "Hau", "Hoa", "Hoai", "Hoan", "Huong",
				"Hung", "Huy", "Khanh", "Khoa", "Khuong", "Lam", "Lan", "Linh",
				"Loan", "Long", "Luong", "Luyen", "Ly", "Minh", "My", "Nam",
				"Nga", "Ngan", "Ngoc", "Nhan", "Nhat", "Nhi", "Nhung", "Phat",
				"Phuc", "Phung", "Phuong", "Quan", "Quoc", "Quynh", "Sang",
				"Son", "Tai", "Tam", "Tan", "Tien", "Thach", "Thai", "Thanh",
				"Thao", "Thinh", "Thu", "Thuan", "Thuy", "Tien", "Tin", "Tinh",
				"Toan", "Tuan", "Tuong", "Van", "Vinh", "Xuan"};
		
		String[] names = new String[count];
		// Generate random names
		for (int i = 0; i < count; i++) {
			String name = firstNames[(int) (Math.random() * firstNames.length)] + " " +
					middleNames[(int) (Math.random() * middleNames.length)] + " " +
					lastNames[(int) (Math.random() * lastNames.length)];
			names[i] = name;
		}
		return names;
	}
	
	/**
	 * Clone files
	 *
	 * @param sourceFileName Source file name
	 * @param baseName       Base name for cloned files
	 * @param numCopies      Number of copies to create
	 */
	public static void cloneFiles(String sourceFileName, String baseName, int numCopies) {
		Path sourceFilePath = Paths.get(sourceFileName);
		for (int i = 2; i <= numCopies + 1; i++) {
			Path newFilePath = Paths.get(baseName + "-" + i + ".pdf");
			try {
				Files.copy(sourceFilePath, newFilePath);
				System.out.println("Created: " + newFilePath);
			} catch (IOException e) {
				System.err.println("An error occurred during file cloning: " + e.getMessage());
			}
		}
		System.out.println("Cloning completed.");
	}
	
	/**
	 * Remove cloned files
	 *
	 * @param baseName  Base name for cloned files
	 * @param numCopies Number of copies to remove
	 */
	public static void removeClonedFiles(String baseName, int numCopies) {
		for (int i = 2; i <= numCopies + 1; i++) {
			Path filePath = Paths.get(baseName + "-" + i + ".pdf");
			try {
				boolean deleted = Files.deleteIfExists(filePath);
				if (deleted) {
					System.out.println("Deleted: " + filePath);
				}
			} catch (IOException e) {
				System.err.println("An error occurred during file deletion: " + e.getMessage());
			}
		}
		System.out.println("File removal completed.");
	}
}
