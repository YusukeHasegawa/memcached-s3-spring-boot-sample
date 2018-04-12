package io.github.yusukehasegawa.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.AbstractResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.WritableResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

@Service
public class S3Service {

	private final ResourceLoader resourceLoader;

	public S3Service(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	public void put(String bucketName, String key, InputStream in) throws IOException {
		this.put("s3://" + bucketName + "/" + key, in);
	}

	/**
	 * @param location e.g. s3://myBucket/rootFile.log
	 * @param in
	 * @throws IOException
	 */
	public void put(String location, InputStream in) throws IOException {
		final Resource resource = this.resourceLoader.getResource(location);
		final WritableResource writableResource = (WritableResource) resource;
		try (OutputStream out = writableResource.getOutputStream()) {
			StreamUtils.copy(in, out);
		}
	}

	public Resource get(String bucketName, String key) throws IOException {
		return this.get("s3://" + bucketName + "/" + key);
	}

	/**
	 * @param location e.g. s3://myBucket/rootFile.log
	 * @return
	 * @throws IOException
	 */
	public Resource get(String location) throws IOException {
		return this.resourceLoader.getResource(location);
	}

	@Cacheable("s3")
	public Resource getFromCache(String bucketName, String key) throws IOException {
		return this.getFromCache("s3://" + bucketName + "/" + key);
	}

	/**
	 * @param location e.g. s3://myBucket/rootFile.log
	 * @return
	 * @throws IOException
	 */
	@Cacheable("s3")
	public Resource getFromCache(String location) throws IOException {
		final Resource r = this.get(location);
		return new S3Resource(r);
	}

	public static final class S3Resource extends AbstractResource
			implements Serializable {

		private String filename;
		private byte[] byteArray;

		public S3Resource() {
		}

		public S3Resource(Resource r) throws IOException {
			this.byteArray = StreamUtils.copyToByteArray(r.getInputStream());
			this.filename = r.getFilename();
		}

		@Override
		public String getFilename() {
			return filename;
		}

		@Override
		public String getDescription() {
			return null;
		}

		@Override
		public InputStream getInputStream() throws IOException {
			return new ByteArrayInputStream(this.byteArray);
		}

	}
}
