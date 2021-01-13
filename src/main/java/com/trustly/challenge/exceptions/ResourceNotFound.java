package com.trustly.challenge.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Class to treat exceptions caused by not found resources.
 * Aggregates "not found" status too, so when raised inside controller,
 * this exception returns this status to caller automatically.
 * 
 * @author silvioaraujo
 *
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFound extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Class constructor.
	 * 
	 * @param clazz	the class of resource.
	 * @param id	the id of resource.
	 */
	public ResourceNotFound(Class<?> clazz, Long id) {
		super(String.format("Resource for %s with id = %d was not found",
							clazz.getSimpleName(), id));
	}

	/**
	 * Class constructor.
	 * 
	 * @param clazz	the class of resource.
	 * @param key	the key used to find the resource.
	 */
	public ResourceNotFound(Class<?> clazz, String key) {
		super(String.format("Resource for %s with key = %s was not found",
							clazz.getSimpleName(), key));
	}
}
