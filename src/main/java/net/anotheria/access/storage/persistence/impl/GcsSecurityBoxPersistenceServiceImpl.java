package net.anotheria.access.storage.persistence.impl;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageClass;
import com.google.cloud.storage.StorageOptions;
import net.anotheria.access.impl.SecurityBox;
import net.anotheria.access.storage.persistence.SecurityBoxPersistenceService;
import net.anotheria.anoprise.dualcrud.CrudServiceException;
import net.anotheria.anoprise.dualcrud.ItemNotFoundException;
import net.anotheria.anoprise.dualcrud.Query;
import net.anotheria.anoprise.dualcrud.SaveableID;
import net.anotheria.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.List;
import java.util.Objects;


/**
 * Google cloud storage implementation of {@link SecurityBoxPersistenceService}.
 *
 * @author asamoilich
 */
public class GcsSecurityBoxPersistenceServiceImpl implements SecurityBoxPersistenceService {

    /**
     * {@link Logger} instance.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GcsSecurityBoxPersistenceServiceImpl.class);

    /**
     * Bucket name.
     */
    private final String bucketName;
    /**
     * {@link Storage} instance.
     */
    private final Storage cloudStorage;

    public GcsSecurityBoxPersistenceServiceImpl(String bucketName, String credentialsPath, String projectId) {
        this.bucketName = bucketName;
        try {
            URL url = getClass().getClassLoader().getResource(credentialsPath);
            cloudStorage = StorageOptions.newBuilder()
                    .setCredentials(GoogleCredentials.fromStream(Objects.requireNonNull(url).openStream()))
                    .setProjectId(projectId)
                    .build()
                    .getService();

            initializeBucket();
        } catch (Exception e) {
            throw new RuntimeException("Unable to initialize google storage. ", e);
        }
    }

    private void initializeBucket() {
        Bucket bucket = cloudStorage.get(bucketName, Storage.BucketGetOption.fields(Storage.BucketField.NAME));
        if (bucket == null) {
            //create bucket
            bucket = cloudStorage.create(BucketInfo.newBuilder(bucketName)
                    .setStorageClass(StorageClass.STANDARD)
                    .setLocation("EU")
                    .build());
            LOGGER.info("Bucket created: " + bucket.toString());
        }
    }

    @Override
    public SecurityBox create(SecurityBox t) throws CrudServiceException {
        return save(t);
    }

    @Override
    public SecurityBox read(final SaveableID boxOwner) throws CrudServiceException {
        String filePath = SecurityBoxPersistenceServiceConfig.calculateDirPath(boxOwner.getSaveableId());
        if (!isFileExists(filePath))
            throw new ItemNotFoundException(boxOwner.getSaveableId());
        ObjectInputStream oIn = null;
        try {
            synchronized (this) {
                ByteArrayInputStream bis = new ByteArrayInputStream(cloudStorage.readAllBytes(bucketName, filePath));
                oIn = new ObjectInputStream(bis);
                SecurityBox ret = (SecurityBox) oIn.readObject();
                oIn.close();
                return ret;
            }
        } catch (IOException e) {
            LOGGER.warn("read(" + boxOwner + ") fail. Target file: " + filePath, e);
            throw new CrudServiceException("Reading fail.", e);
        } catch (ClassNotFoundException e) {
            LOGGER.warn("read(" + boxOwner + ") fail. Target file: " + filePath, e);
            throw new CrudServiceException("Reading fail. Class not found: " + e.getMessage());
        } finally {
            IOUtils.closeIgnoringException(oIn);
        }
    }

    private boolean isFileExists(String filePath) {
        return cloudStorage.get(bucketName, filePath) != null;
    }


    @Override
    public SecurityBox update(final SecurityBox t) throws CrudServiceException {
        return save(t);
    }

    @Override
    public void delete(final SecurityBox box) throws CrudServiceException {
        String owner = box.getOwnerId();
        String filePath = SecurityBoxPersistenceServiceConfig.calculateDirPath(owner);
        if (LOGGER.isDebugEnabled())
            LOGGER.info("Deleting file[" + filePath + "] for user [" + owner + "]...");

        if (!isFileExists(filePath))
            return;

        if (!cloudStorage.delete(bucketName, filePath))
            throw new CrudServiceException("Deletion of file [" + filePath + "for user [" + owner + "] failed.");
    }

    @Override
    public SecurityBox save(final SecurityBox box) throws CrudServiceException {
        String filePath = SecurityBoxPersistenceServiceConfig.calculateDirPath(box.getUserId());
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("save(" + box + ") Saving... ");

        ObjectOutputStream oOut = null;
        try {
            synchronized (this) {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                oOut = new ObjectOutputStream(bos);
                oOut.writeObject(box);
                oOut.flush();
                BlobId blobId = BlobId.of(bucketName, filePath);
                BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
                cloudStorage.create(blobInfo, bos.toByteArray());
            }
        } catch (IOException e) {
            LOGGER.warn("save(" + box + ") final. Target file: " + filePath, e);
            throw new CrudServiceException("Saving fail.", e);
        } finally {
            IOUtils.closeIgnoringException(oOut);
        }
        return box;
    }

    @Override
    public boolean exists(SecurityBox t) throws CrudServiceException {
        throw new UnsupportedOperationException("yet unimplemented");
    }

    @Override
    public List<SecurityBox> query(Query q) throws CrudServiceException {
        throw new UnsupportedOperationException("yet unimplemented");
    }

}
