package net.anotheria.access.storage.persistence.impl;

import java.io.File;

import org.apache.log4j.Logger;
import org.configureme.ConfigurationManager;
import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;

/**
 * {@link FSSecurityBoxPersistenceServiceImpl} configuration.
 * 
 * @author Leon Rosenberg, Alexandr Bolbat
 */
@ConfigureMe(name = "ano-access-fs-security-box-persistence-service-config")
public final class FSSecurityBoxPersistenceServiceConfig {

	/**
	 * {@link Logger} instance.
	 */
	private static final Logger LOGGER = Logger.getLogger(FSSecurityBoxPersistenceServiceConfig.class);

	/**
	 * {@link FSSecurityBoxPersistenceServiceImpl} configuration instance.
	 */
	private static final FSSecurityBoxPersistenceServiceConfig INSTANCE = new FSSecurityBoxPersistenceServiceConfig();

	/**
	 * Default storage path.
	 */
	public static final String DEFAULT_EXTENSION = ".sbx";

	/**
	 * Default file extension.
	 */
	public static final String DEFAUL_STORAGE_PATH = "/tmp/anoaccess/";

	/**
	 * Default fragmentation depth for sub-directories.
	 */
	public static final int DEFAULT_FRAGMENTATION_DEPTH = 5;

	/**
	 * Default fragmentation length for sub-directories.
	 */
	public static final int DEFAULT_FRAGMENTATION_LENGTH = 2;

	/**
	 * Storage path.
	 */
	@Configure
	private String storagePath = DEFAUL_STORAGE_PATH;

	/**
	 * File extension.
	 */
	@Configure
	private String extension = DEFAULT_EXTENSION;

	/**
	 * Fragmentation depth for sub-directories.
	 */
	@Configure
	private int fragmentationDepth = DEFAULT_FRAGMENTATION_DEPTH;

	/**
	 * Fragmentation length for sub-directories.
	 */
	@Configure
	private int fragmentationLength = DEFAULT_FRAGMENTATION_LENGTH;

	/**
	 * Default constructor.
	 */
	private FSSecurityBoxPersistenceServiceConfig() {
		try {
			ConfigurationManager.INSTANCE.configure(this);
		} catch (IllegalArgumentException e) {
			LOGGER.error("FSSecurityBoxPersistenceServiceConfig() initializtion fail. Relaying on defaults[" + this.toString() + "].", e);
		}
	}

	/**
	 * Get {@link FSSecurityBoxPersistenceServiceConfig} instance.
	 * 
	 * @return {@link FSSecurityBoxPersistenceServiceConfig}
	 */
	public static FSSecurityBoxPersistenceServiceConfig getInstance() {
		return INSTANCE;
	}

	public String getStoragePath() {
		return storagePath;
	}

	public void setStoragePath(final String aStoragePath) {
		this.storagePath = aStoragePath;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(final String aExtension) {
		this.extension = aExtension;
	}

	public int getFragmentationDepth() {
		return fragmentationDepth;
	}

	public void setFragmentationDepth(final int aFragmentationDepth) {
		this.fragmentationDepth = aFragmentationDepth;
	}

	public int getFragmentationLength() {
		return fragmentationLength;
	}

	public void setFragmentationLength(final int aFragmentationLength) {
		this.fragmentationLength = aFragmentationLength;
	}

	/**
	 * Calculate full storage directory path.
	 * 
	 * @param userId
	 *            - user id
	 * @return {@link String}
	 */
	public static String calculateDirPath(final String userId) {
		String fragments[] = getFragmentation(userId, getInstance().getFragmentationDepth(), getInstance().getFragmentationLength());
		StringBuilder sb = new StringBuilder();
		sb.append(getInstance().getStoragePath());

		for (int i = 0; i < fragments.length - 1; i++) {
			sb.append(fragments[i]);
			if (i < fragments.length - 2)
				sb.append(File.separator);
		}

		return sb.toString();
	}

	/**
	 * Calculate full storage file path.
	 * 
	 * @param userId
	 *            - user id
	 * @return {@link String}
	 */
	public static String calculateFilePath(final String userId) {
		String filePath = calculateDirPath(userId) + File.separator + userId + getInstance().getExtension();

		if (LOGGER.isDebugEnabled())
			LOGGER.debug("Calculated file path for " + userId + " is " + filePath);

		return filePath;
	}

	/**
	 * Utility method for preparing path sub-directories from storage root directory.
	 * 
	 * @param userId
	 *            - user id
	 * @param fragmentationDepth
	 *            - fragmentation depth
	 * @param fragmentLength
	 *            - fragmentation length
	 * @return array of {@link String}
	 */
	private static String[] getFragmentation(final String userId, final int fragmentationDepth, final int fragmentLength) {
		String s = userId;
		// first ensure our string is long enough.
		while (s.length() < fragmentationDepth * fragmentLength)
			s = "0" + s;

		int singleLength = fragmentLength;
		String ret[] = new String[fragmentationDepth];
		for (int i = 0; i < fragmentationDepth; i++) {
			String fragment = s.substring(i * singleLength, i * singleLength + singleLength);
			ret[i] = fragment;
		}

		return ret;
	}

}
