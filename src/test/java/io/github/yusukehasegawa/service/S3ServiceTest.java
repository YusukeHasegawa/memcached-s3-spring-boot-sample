package io.github.yusukehasegawa.service;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class S3ServiceTest {

	@Autowired
	S3Service service;

	InputStream image;

	@Before
	public void setUp() throws Exception {
		image = new ClassPathResource("test.gif").getInputStream();
	}

	@Test
	public void test() throws IOException {
		final String location = "s3://hasegawa-work/test.gif";
		service.put(location, this.image);
		final Resource object = service.get(location);
		Assert.assertNotNull(object);

	}

}
