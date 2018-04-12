package io.github.yusukehasegawa.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {
	private final AWS aws = new AWS();

	public static final class AWS {
		private String bucket = "hasegawa-work";

		public String getBucket() {
			return bucket;
		}

		public void setBucket(String bucket) {
			this.bucket = bucket;
		}

	}

	public AWS getAws() {
		return aws;
	}
}
