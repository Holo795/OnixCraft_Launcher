package ru.onixcraft.paulin.launcher.utils;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.json.JSONException;
import org.json.JSONObject;

import re.alwyn974.openlauncherlib.minecraft.GameInfos;
import re.alwyn974.openlauncherlib.minecraft.GameTweak;
import re.alwyn974.openlauncherlib.minecraft.GameType;
import re.alwyn974.openlauncherlib.minecraft.GameVersion;
import ru.onixcraft.paulin.launcher.ui.panels.JavaProgressBarPanel;
import ru.onixcraft.paulin.launcher.ui.panels.include.JavaProgressBar;

public class Utils {

	private static GameVersion version = new GameVersion("1.7.10", GameType.V1_7_10);
	private static GameInfos infos = new GameInfos("OnixCraft", version, new GameTweak[] { GameTweak.FORGE });
	public static File dir = infos.getGameDir();

	private static HttpURLConnection con;
	public static Config config = new Config(dir, "launcher.properties");
	
	private static JSONObject jsonObject;

	private static int getFileSize(URL url) {
		HttpURLConnection conn = null;
		try {
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("HEAD");
			return (int) conn.getContentLengthLong();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
	}

	public static String downloadJava(String url, String fileName) throws IOException {
		InputStream is = null;
		FileOutputStream fos = null;
		Long javaFolders = folderSize(new File(dir, "java/bin")) + folderSize(new File(dir, "java/lib"))
				+ folderSize(new File(dir, "java/legal"));
		Double tauxCompr = 0.36456951049711556;
		String outputPath = dir + "/java/" + fileName;

		URL u = new URL(url);
		int size = 0;
		if (javaFolders * tauxCompr != getFileSize(new URL(url))) {
			if (new File(outputPath).length() == getFileSize(new URL(url))) {
				unZip(fileName);
			} else {
				JavaProgressBar.init();
				try {
					// connect
					URLConnection urlConn = u.openConnection();

					// get inputstream from connection
					is = urlConn.getInputStream();
					fos = new FileOutputStream(outputPath);

					// 4KB buffer
					byte[] buffer = new byte[4096];
					int length;

					// read from source and write into local file
					JavaProgressBarPanel.getProgressBar().setMaximum(getFileSize(new URL(url)));
					while ((length = is.read(buffer)) > 0) {
						JavaProgressBarPanel.getProgressBar().setValue(size);
						size += length;
						// JavaProgressBar.update(76914724.0/76914724.0);
						fos.write(buffer, 0, length);
					}
					return outputPath;
				} finally {
					try {
						if (is != null) {
							is.close();
						}
					} finally {
						if (fos != null) {
							fos.close();
						}
					}

					unZip(fileName);
				}
			}
			return outputPath;
		}
		if (new File(outputPath).exists())
			new File(outputPath).delete();
		return outputPath;
	}

	public static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {

		File destFile = new File(destinationDir, zipEntry.getName());

		String destDirPath = destinationDir.getCanonicalPath();
		String destFilePath = destFile.getCanonicalPath();

		if (!destFilePath.startsWith(destDirPath + File.separator)) {
			throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
		}

		return destFile;
	}

	public static void unZip(String fileName) {
		String fileZip = dir + "/java/" + fileName;
		File destDir = new File(dir, "java");

		byte[] buffer = new byte[1024];
		try {
			@SuppressWarnings("resource")
			ZipInputStream zis = new ZipInputStream(new FileInputStream(fileZip));
			ZipEntry zipEntry = zis.getNextEntry();
			while (zipEntry != null) {
				File newFile = newFile(destDir, zipEntry);
				if (zipEntry.isDirectory()) {
					if (!newFile.isDirectory() && !newFile.mkdirs()) {
						throw new IOException("Failed to create directory " + newFile);
					}
				} else {
					// fix for Windows-created archives
					File parent = newFile.getParentFile();
					if (!parent.isDirectory() && !parent.mkdirs()) {
						throw new IOException("Failed to create directory " + parent);
					}

					// write file content
					FileOutputStream fos1 = new FileOutputStream(newFile);
					int len;
					while ((len = zis.read(buffer)) > 0) {
						fos1.write(buffer, 0, len);
					}
					fos1.close();
				}
				zipEntry = zis.getNextEntry();
			}

			zis.closeEntry();
			zis.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if (new File(fileZip).exists())
			new File(fileZip).delete();

		JavaProgressBar.getJavaProgressBar().dispose();
	}

	public static long folderSize(File directory) {
		if (directory.exists()) {
			long length = 0;
			for (File file : directory.listFiles()) {
				if (file.isFile())
					length += file.length();
				else
					length += folderSize(file);
			}
			return length;
		}
		return 0;
	}

	public static void openUrl(String url) {
		try {
			Desktop.getDesktop().browse(new URI(url));
		} catch (IOException | URISyntaxException e) {
			System.err.println(e.getMessage());
		}
	}

	public static boolean auth(String username, String password) throws IOException, NoSuchAlgorithmException {

		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] encodedhash = digest.digest(password.getBytes(StandardCharsets.UTF_8));

		String url = "https://onix-craft.ru/client/auth_api.php";
		String urlParameters = "u=" + username + "&p=" + bytesToHex(encodedhash);
		byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);

		try {

			URL myurl = new URL(url);
			con = (HttpURLConnection) myurl.openConnection();

			con.setDoOutput(true);
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", "Java client");
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

			try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {

				wr.write(postData);
			}

			StringBuilder content;

			try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()))) {

				String line;
				content = new StringBuilder();

				while ((line = br.readLine()) != null) {
					content.append(line);
					content.append(System.lineSeparator());
				}
			}
			if (!content.toString().contains("__ok__")) {
				con.disconnect();
				return false;
			}

		} finally {
			con.disconnect();
		}

		return true;
	}

	private static String bytesToHex(byte[] hash) {
		StringBuilder hexString = new StringBuilder(2 * hash.length);
		for (int i = 0; i < hash.length; i++) {
			String hex = Integer.toHexString(0xff & hash[i]);
			if (hex.length() == 1) {
				hexString.append('0');
			}
			hexString.append(hex);
		}
		return hexString.toString();
	}

	public static JSONObject getInfo(String username) throws IOException, JSONException {
		String url = "https://onix-craft.ru/client/info_get.php";
		String urlParameters = "u=" + username;
		byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);

		try {

			URL myurl = new URL(url);
			con = (HttpURLConnection) myurl.openConnection();

			con.setDoOutput(true);
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", "Java client");
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

			try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {

				wr.write(postData);
			}

			StringBuilder content;

			try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()))) {

				String line;
				content = new StringBuilder();

				while ((line = br.readLine()) != null) {
					content.append(line);
					content.append(System.lineSeparator());
				}
			}

			jsonObject = new JSONObject(content.toString());
			return jsonObject;

		} finally {

			con.disconnect();
		}

	}
	
	public static JSONObject getInfoJson() {
		return jsonObject;
	}

	public static boolean reAuth(String username, String token) throws IOException, NoSuchAlgorithmException {

		String url = "https://onix-craft.ru/client/auth_api.php";
		String urlParameters = "u=" + username + "&t=" + token;
		byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);

		try {

			URL myurl = new URL(url);
			con = (HttpURLConnection) myurl.openConnection();

			con.setDoOutput(true);
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", "Java client");
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

			try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {

				wr.write(postData);
			}

			StringBuilder content;

			try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()))) {

				String line;
				content = new StringBuilder();

				while ((line = br.readLine()) != null) {
					content.append(line);
					content.append(System.lineSeparator());
				}
			}
			if (!content.toString().contains("__ok__")) {
				con.disconnect();
				return false;

			}

		} finally {
			con.disconnect();
		}
		
		return true;
	}

}
