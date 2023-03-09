package net.anotheria.access.storage.persistence.impl;

import net.anotheria.access.storage.persistence.SecurityBoxPersistenceService;
import net.anotheria.access.storage.persistence.StorageType;
import net.anotheria.anoprise.metafactory.ServiceFactory;

/**
 * Factory for instantiating {@link FSSecurityBoxPersistenceServiceImpl}.
 *
 * @author Alexandr Bolbat
 */
public class SecurityBoxPersistenceServiceFactory implements ServiceFactory<SecurityBoxPersistenceService> {

    @Override
    public SecurityBoxPersistenceService create() {
        SecurityBoxPersistenceServiceConfig config = SecurityBoxPersistenceServiceConfig.getInstance();
        switch (StorageType.getByTypeValue(config.getStorageType())) {
            case S3:
                return new S3SecurityBoxPersistenceServiceImpl(config.getBucketName(), config.getAccessKey(), config.getSecretKey(), config.getProjectId());
            case GCS:
                return new GcsSecurityBoxPersistenceServiceImpl(config.getBucketName(), config.getCredentialsPath(), config.getProjectId());
            case FS:
            default:
                return new FSSecurityBoxPersistenceServiceImpl();
        }
    }

}
