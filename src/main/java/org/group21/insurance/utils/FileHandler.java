package org.group21.insurance.utils;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileHandler {
	
	private static final String APPLICATION_NAME = "Insurance Claim Management System - Group 21";
	private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
	private static final String SERVICE_ACCOUNT_JSON_PATH = "./src/main/resources/insurance-group-21-9a3c98d75a87.json";
	
	public static Drive getDriveService() throws GeneralSecurityException, IOException {
		NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(SERVICE_ACCOUNT_JSON_PATH))
				.createScoped(Collections.singletonList("https://www.googleapis.com/auth/drive"));
		
		return new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, new HttpCredentialsAdapter(credentials))
				.setApplicationName(APPLICATION_NAME)
				.build();
	}
	
	public static String uploadFile(Drive service, java.io.File filePath) throws IOException {
		try {
			File fileMetadata = new File();
			fileMetadata.setMimeType("application/pdf");
			fileMetadata.setName(filePath.getName());
			
			FileContent mediaContent = new FileContent("application/pdf", filePath);
			
			File file = service.files().create(fileMetadata, mediaContent)
					.setFields("id")
					.execute();
			System.out.println("File ID: " + file.getId());
			return file.getId();
		} catch (IOException e) {
			System.err.println("An error occurred during file upload: " + e);
			throw e;
		}
	}
	
	public static List<String> listFiles(Drive service) throws IOException {
		Drive.Files.List request = service.files().list().setPageSize(10)
				.setFields("nextPageToken, files(id, name)");
		
		do {
			FileList files = request.execute();
			
			for (File file : files.getFiles()) {
				System.out.printf("Found file: %s (%s)\n", file.getName(), file.getId());
			}
			
			request.setPageToken(files.getNextPageToken());
			return files.getFiles().stream().map(File::getId).toList();
		} while (request.getPageToken() != null &&
				!request.getPageToken().isEmpty());
	}
	
	public static File findFileById(Drive service, String fileId) throws IOException {
		// Attempt to retrieve the file by its ID
		try {
			return service.files().get(fileId).execute();
		} catch (IOException e) {
			System.err.println("An error occurred: " + e);
			throw e;
		}
	}
	
	public static List<File> findFilesByIds(Drive service, List<String> fileIds) {
		List<File> files = new ArrayList<>();
		for (String fileId : fileIds) {
			try {
				File file = service.files().get(fileId).execute();
				files.add(file);
			} catch (IOException e) {
				System.err.println("An error occurred while retrieving file with ID " + fileId + ": " + e);
				// Instead of throwing an error, continue to the next file
			}
		}
		return files;
	}
	
	public static void downloadFile(Drive service, String fileId, String destinationPath) throws IOException {
		// Output stream to write the downloaded file
		try (java.io.OutputStream outputStream = new java.io.FileOutputStream(destinationPath)) {
			// Execute the download
			service.files().get(fileId).executeMediaAndDownloadTo(outputStream);
		} catch (IOException e) {
			System.err.println("An error occurred during file download: " + e);
			throw e;
		}
	}
	
	public static void deleteFile(Drive service, String fileId) throws IOException {
		try {
			service.files().delete(fileId).execute();
			System.out.println("File ID: " + fileId + " has been successfully deleted.");
		} catch (IOException e) {
			System.err.println("An error occurred during file deletion: " + e);
			throw e;
		}
	}
	
	public static String replaceFile(Drive service, String fileIdToReplace, java.io.File newFilePath) throws IOException {
		// Delete the existing file
		deleteFile(service, fileIdToReplace);
		
		// Upload the new file and return its ID
		return uploadFile(service, newFilePath);
	}
	
	public static File changeFileName(Drive service, String fileId, String newName) throws IOException {
		// Retrieve the existing file
		try {
			File file = service.files().get(fileId).execute();
			file.setName(newName);
			
			return service.files().update(fileId, file).execute();
		} catch (IOException e) {
			System.err.println("An error occurred while trying to change the name of the file with ID " + fileId + ": " + e);
			throw e;
		}
	}
}
