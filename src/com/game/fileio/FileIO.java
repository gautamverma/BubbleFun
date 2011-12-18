/**
 * 
 */
package com.game.fileio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Gautam
 * @since 17/10/2011  dd/mm/yyyy
 * 
 * It gives up the normal file working methods
 *
 */
public interface FileIO {
	InputStream readAsset(String filename) throws IOException;
	InputStream readFile(String filename) throws IOException;
	OutputStream writeFile(String filename) throws IOException;
	boolean fileExist(String filename) throws IOException;
}
