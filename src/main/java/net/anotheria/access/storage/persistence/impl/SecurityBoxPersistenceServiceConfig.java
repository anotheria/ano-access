package net.anotheria.access.storage.persistence.impl;

import org.configureme.ConfigurationManager;
import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * {@link FSSecurityBoxPersistenceServiceImpl} configuration.
 * 
 * @author Leon Rosenberg, Alexandr Bolbat
 */
@ConfigureMe(name = "ano-access-fs-security-box-persistence-service-config")
public final class SecurityBoxPersistenceServiceConfig {

	/**
	 * {@link Logger} instance.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(SecurityBoxPersistenceServiceConfig.class);

	/**
	 * {@link FSSecurityBoxPersistenceServiceImpl} configuration instance.
	 */
	private static final SecurityBoxPersistenceServiceConfig INSTANCE = new SecurityBoxPersistenceServiceConfig();

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
     * Bucket name for asg content.
     */
    @Configure
    private String bucketName;
    /**
     * Project id / S3 endpoint.
     */
    @Configure
    private String projectId;
    /**
     * Credential path for google service account file.
     */
    @Configure
    private String credentialsPath;

    /**
     * Storage type.
     */
    @Configure
    private String storageType;
    /**
     * Access key.
     */
    @Configure
    private String accessKey;

    /**
     * Secret key.
     */
    @Configure
    private String secretKey;

	/**
	 * Default constructor.
	 */
	private SecurityBoxPersistenceServiceConfig() {
		try {
			ConfigurationManager.INSTANCE.configure(this);
		} catch (IllegalArgumentException e) {
			LOGGER.error("FSSecurityBoxPersistenceServiceConfig() initializtion fail. Relaying on defaults[" + this.toString() + "].", e);
		}
	}

	/**
	 * Get {@link SecurityBoxPersistenceServiceConfig} instance.
	 * 
	 * @return {@link SecurityBoxPersistenceServiceConfig}
	 */
	public static SecurityBoxPersistenceServiceConfig getInstance() {
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

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getCredentialsPath() {
        return credentialsPath;
    }

    public void setCredentialsPath(String credentialsPath) {
        this.credentialsPath = credentialsPath;
    }

    public String getStorageType() {
        return storageType;
    }

    public void setStorageType(String storageType) {
        this.storageType = storageType;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
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
