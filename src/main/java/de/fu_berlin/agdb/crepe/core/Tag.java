/**
 * 
 */
package de.fu_berlin.agdb.crepe.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Tag for marking options for source files.
 * @author Ralf Oechsner
 *
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Tag {
	String value();
}
