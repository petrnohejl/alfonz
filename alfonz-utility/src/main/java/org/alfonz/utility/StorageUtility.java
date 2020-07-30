package org.alfonz.utility;

import android.content.Context;
import android.os.Environment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.ArraySet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// requires android.permission.READ_EXTERNAL_STORAGE or android.permission.WRITE_EXTERNAL_STORAGE
public final class StorageUtility {
	private StorageUtility() {}

	public static boolean isAvailable() {
		String state = Environment.getExternalStorageState();
		return state.equals(Environment.MEDIA_MOUNTED) || state.equals(Environment.MEDIA_MOUNTED_READ_ONLY);
	}

	public static boolean isWritable() {
		String state = Environment.getExternalStorageState();
		return state.equals(Environment.MEDIA_MOUNTED);
	}

	public static File getStorageDirectory() {
		return Environment.getExternalStorageDirectory();
	}

	// publicDirectory can be for example Environment.DIRECTORY_PICTURES
	public static File getStorageDirectory(@NonNull String publicDirectory) {
		return Environment.getExternalStoragePublicDirectory(publicDirectory);
	}

	@Nullable
	public static File getSecondaryStorageDirectory() {
		return getSecondaryStorageDirectory(null);
	}

	// publicDirectory can be for example Environment.DIRECTORY_PICTURES
	@Nullable
	public static File getSecondaryStorageDirectory(@Nullable String publicDirectory) {
		File result = null;
		File primary = getStorageDirectory();
		Set<String> externalMounts = getExternalMounts();

		for (String s : externalMounts) {
			if (primary != null && s != null && !s.equals(primary.getAbsolutePath())) {
				File secondary = new File(s);
				if (secondary.exists() && secondary.isDirectory()) {
					String canonicalPrimary = null;
					String canonicalSecondary = null;

					try {
						canonicalPrimary = primary.getCanonicalPath();
						canonicalSecondary = secondary.getCanonicalPath();
					} catch (IOException e) {
						e.printStackTrace();
					}

					if (canonicalPrimary != null && canonicalSecondary != null && !canonicalPrimary.equals(canonicalSecondary)) {
						if (publicDirectory == null) result = secondary;
						else {
							String path = secondary.getAbsolutePath() + "/" + publicDirectory;
							result = new File(path);
						}
					}
				}
			}
		}

		return result;
	}

	@Nullable
	public static File getApplicationCacheDirectory(@NonNull Context context) {
		return context.getExternalCacheDir();
	}

	// type can be for example Environment.DIRECTORY_PICTURES
	@Nullable
	public static File getApplicationFilesDirectory(@NonNull Context context, @NonNull String type) {
		return context.getExternalFilesDir(type);
	}

	@NonNull
	public static List<File> getFiles(File directory, boolean recursive) {
		List<File> fileList = new ArrayList<>();
		if (directory != null && directory.exists() && directory.isDirectory()) {
			walkFiles(directory, recursive, null, null, fileList);
		}
		return fileList;
	}

	// pattern can be for example "(.+(\\.(?i)(jpg|jpeg))$)"
	@NonNull
	public static List<File> getFiles(File directory, boolean recursive, Pattern fileNameFilter, Pattern directoryNameFilter) {
		List<File> fileList = new ArrayList<>();
		if (directory != null && directory.exists() && directory.isDirectory()) {
			walkFiles(directory, recursive, fileNameFilter, directoryNameFilter, fileList);
		}
		return fileList;
	}

	// source: http://stackoverflow.com/questions/11281010/how-can-i-get-external-sd-card-path-for-android-4-0
	@NonNull
	public static Set<String> getExternalMounts() {
		final Set<String> externalMounts = new ArraySet<>();
		String regex = "(?i).*vold.*(vfat|ntfs|exfat|fat32|ext3|ext4).*rw.*";
		StringBuilder mountOutput = new StringBuilder();

		// run mount process
		try {
			final Process process = new ProcessBuilder().command("mount").redirectErrorStream(true).start();
			process.waitFor();
			final InputStream is = process.getInputStream();
			final byte[] buffer = new byte[1024];
			while (is.read(buffer) != -1) {
				mountOutput.append(new String(buffer));
			}
			is.close();
		} catch (@NonNull final Exception e) {
			e.printStackTrace();
		}

		// parse mount output
		final String[] lines = mountOutput.toString().split("\n");
		for (String line : lines) {
			if (!line.toLowerCase(Locale.US).contains("asec")) // skip lines with "asec"
			{
				if (line.matches(regex)) {
					String[] parts = line.split(" ");
					for (String part : parts) {
						if (part.startsWith("/")) // starts with slash
							if (!part.toLowerCase(Locale.US).contains("vold")) // not contains "vold"
								externalMounts.add(part);
					}
				}
			}
		}
		return externalMounts;
	}

	private static void walkFiles(File directory, boolean recursive, Pattern fileNameFilter, Pattern directoryNameFilter, List<File> fileList) {
		File[] list = directory.listFiles();
		if (list != null) {
			for (File f : list) {
				if (f.isDirectory()) {
					if (recursive) {
						if (validateName(f.getName(), directoryNameFilter)) {
							walkFiles(f, recursive, fileNameFilter, directoryNameFilter, fileList);
						}
					}
				} else if (validateName(f.getName(), fileNameFilter)) {
					fileList.add(f);
				}
			}
		}
	}

	private static boolean validateName(String name, Pattern pattern) {
		if (pattern == null) return true;
		else {
			Matcher matcher = pattern.matcher(name);
			return matcher.matches();
		}
	}
}
