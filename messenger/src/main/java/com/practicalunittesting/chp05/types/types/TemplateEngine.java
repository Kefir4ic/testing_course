package com.practicalunittesting.chp05.types.types;

/**
 * Practical Unit Testing with JUnit and Mockito - source code for examples.
 * Visit http://practicalunittesting.com for more information.
 *
 * @author Tomek Kaczanowski
 */
public interface TemplateEngine {
	String prepareMessage(String template, Client client);
}
