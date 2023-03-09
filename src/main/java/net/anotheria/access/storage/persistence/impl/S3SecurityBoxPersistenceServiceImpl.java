package net.anotheria.access.storage.persistence.impl;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
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
import java.util.List;


/**
 * S3 cloud storage implementation of {@link SecurityBoxPersistenceService}.
 *
 * @author asamoilich
 */
public class S3SecurityBoxPersistenceServiceImpl implements SecurityBoxPersistenceService {

    /**
     * {@link Logger} instance.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(S3SecurityBoxPersistenceServiceImpl.class);

    /**
     * Bucket name.
     */
    private final String bucketName;
    /**
     * {@link AmazonS3} instance.
     */
    private final AmazonS3 conn;

    public S3SecurityBoxPersistenceServiceImpl(String bucketName, String accessKey, String secretKey, String endPoint) {
        this.bucketName = bucketName;
        try {
            AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
            conn = new AmazonS3Client(credentials);
            conn.setEndpoint(endPoint);
            System.out.println("LINODE  BOX SECURE !!!!!!!!!!!");
            initializeBucket();
        } catch (Exception e) {
            throw new RuntimeException("Unable to initialize google storage. ", e);
        }
    }

    private void initializeBucket() {
        final List<Bucket> buckets = conn.listBuckets();
        for (Bucket bucket : buckets) {
            if (bucket.getName().equals(bucketName)) {
                return;
            }
        }
        conn.createBucket(bucketName);
    }

    @Override
    public SecurityBox create(SecurityBox t) throws CrudServiceException {
        return save(t);
    }

    @Override
    public SecurityBox read(final SaveableID boxOwner) throws CrudServiceException {
        String filePath = SecurityBoxPersistenceServiceConfig.calculateFilePath(boxOwner.getSaveableId());
        if (!conn.doesObjectExist(bucketName, filePath))
            throw new ItemNotFoundException(boxOwner.getSaveableId());
        ObjectInputStream oIn = null;
        try {
            synchronized (this) {
                S3Object object = conn.getObject(new GetObjectRequest(bucketName, filePath));
                ByteArrayInputStream bis = new ByteArrayInputStream(IOUtils.readBytes(object.getObjectContent()));
                oIn = new ObjectInputStream(bis);
                return (SecurityBox) oIn.readObject();
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
        return conn.doesObjectExist(bucketName, filePath);
    }


    @Override
    public SecurityBox update(final SecurityBox t) throws CrudServiceException {
        return save(t);
    }

    @Override
    public void delete(final SecurityBox box) throws CrudServiceException {
        String owner = box.getOwnerId();
        String filePath = SecurityBoxPersistenceServiceConfig.calculateFilePath(owner);
        if (LOGGER.isDebugEnabled())
            LOGGER.info("Deleting file[" + filePath + "] for user [" + owner + "]...");

        if (!isFileExists(filePath))
            return;
        conn.deleteObject(bucketName, filePath);
        if (isFileExists(filePath))
            throw new CrudServiceException("Deletion of file [" + filePath + "for user [" + owner + "] failed.");
    }

    @Override
    public SecurityBox save(final SecurityBox box) throws CrudServiceException {
        String filePath = SecurityBoxPersistenceServiceConfig.calculateFilePath(box.getUserId());
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("save(" + box + ") Saving... ");

        ObjectOutputStream oOut = null;
        try {
            synchronized (this) {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                oOut = new ObjectOutputStream(bos);
                oOut.writeObject(box);
                oOut.flush();
                ByteArrayInputStream input = new ByteArrayInputStream(bos.toByteArray());
                ObjectMetadata metadata = new ObjectMetadata();
                metadata.setContentLength(input.available());
                PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, filePath, input, metadata);
                conn.putObject(putObjectRequest);
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
