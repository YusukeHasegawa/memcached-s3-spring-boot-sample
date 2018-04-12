package io.github.yusukehasegawa.web.api;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.yusukehasegawa.config.ApplicationProperties;
import io.github.yusukehasegawa.service.S3Service;

@RestController
@RequestMapping("/api")
public class ImageController {

	private final S3Service service;
	private final ApplicationProperties applicationProperties;

	public ImageController(S3Service service,
			ApplicationProperties applicationProperties) {
		this.service = service;
		this.applicationProperties = applicationProperties;
	}

	@RequestMapping("/image/{name}")
	public ResponseEntity<Resource> get(@PathVariable String name) throws IOException {
		final Resource r = service.getFromCache(applicationProperties.getAws().getBucket(), name);
		return ResponseEntity.ok().contentType(resolveContentType(r)).body(r);
	}

	protected static final MediaType resolveContentType(Resource r) {
		return MediaTypeFactory.getMediaType(r)
				.orElse(MediaType.APPLICATION_OCTET_STREAM);

	}
}
