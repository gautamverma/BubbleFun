/**
 * 
 */
package com.game.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.res.AssetManager;
import android.os.Environment;

import com.game.fileio.FileIO;

/**
 * @author Gautam
 * @since 19/11/2011
 * 
 * This implements the IO methods for the Game Framework.
 *
 */
public class AndroidFileIO implements FileIO {

	AssetManager assets;
	String externalStoragePath;
	
	public AndroidFileIO(AssetManager assets) {
		this.assets = assets;
		externalStoragePath = Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator;
	}
	
	/* (non-Javadoc)
	 * @see com.game.fileio.FileIO#readAsset(java.lang.String)
	 */
	@Override
	public InputStream readAsset(String fileName) throws IOException {
		return assets.open(fileName);
	}

	/* (non-Javadoc)
	 * @see com.game.fileio.FileIO#readFile(java.lang.String)
	 */
	@Override
	public InputStream readFile(String fileName) throws IOException {
		return new FileInputStream(externalStoragePath+fileName);
	}

	/* (non-Javadoc)
	 * @see com.game.fileio.FileIO#writeFile(java.lang.String)
	 */
	@Override
	public OutputStream writeFile(String fileName) throws IOException {
		return new FileOutputStream(externalStoragePath+fileName);
	}

	@Override
	public boolean fileExist(String filename) throws IOException {
		return (new File(filename)).exists();
	}

}
