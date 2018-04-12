package io.github.yusukehasegawa.web.error;

import java.io.FileNotFoundException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import org.zalando.problem.StatusType;
import org.zalando.problem.spring.web.advice.ProblemHandling;

import com.amazonaws.AmazonServiceException;

@ControllerAdvice
public class ExceptionHandling implements ProblemHandling {
	@ExceptionHandler({ AmazonServiceException.class })
	public ResponseEntity<Problem> handleAmazonClientException(AmazonServiceException ex,
			NativeWebRequest request) {
		// @formatter:off
		final Problem problem = Problem.builder()
				.withStatus(new AwsStatusType(ex))
				.withTitle(ex.getErrorCode())
				.withDetail(ex.getMessage())
				.build();
		return create(ex, problem, request);
		// @formatter:on
	}

	@ExceptionHandler({ FileNotFoundException.class })
	public ResponseEntity<Problem> handleAmazonClientException(FileNotFoundException ex,
			NativeWebRequest request) {
		// @formatter:off
		final Problem problem = Problem.builder()
				.withStatus(Status.NOT_FOUND)
				.withTitle(Status.NOT_FOUND.name())
				.withDetail(ex.getMessage())
				.build();
		return create(ex, problem, request);
		// @formatter:on
	}

	static final class AwsStatusType implements StatusType {
		private final AmazonServiceException ex;

		AwsStatusType(AmazonServiceException ex) {
			super();
			this.ex = ex;
		}

		@Override
		public int getStatusCode() {
			return ex.getStatusCode();
		}

		@Override
		public String getReasonPhrase() {
			return ex.getMessage();
		}

	}

}
