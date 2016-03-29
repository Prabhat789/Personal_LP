package com.mobisys.aspr.imageloadingutil;

import android.content.Context;

import java.io.File;

public class FileCache {

	private File cacheDir;

	public FileCache(Context context) {
		/*
		 * //Find the dir to save cached images if
		 * (android.os.Environment.getExternalStorageState
		 * ().equals(android.os.Environment.MEDIA_MOUNTED)) cacheDir=new
		 * File(android
		 * .os.Environment.getExternalStorageDirectory(),"TTImages_cache"); else
		 * cacheDir=context.getCacheDir(); if(!cacheDir.exists())
		 * cacheDir.mkdirs();
		 */

		cacheDir = new File(context.getFilesDir()
				+ "/nfs/guille/groce/users/nicholsk/workspace3/SQLTest");
		cacheDir.mkdirs(); // create folders where write files

	}

	public File getFile(String url) {
		// I identify images by hashcode. Not a perfect solution, good for the
		// demo.
		String filename = String.valueOf(url.hashCode());
		// Another possible solution (thanks to grantland)
		// String filename = URLEncoder.encode(url);
		File f = new File(cacheDir, filename);
		return f;

	}

	public void clear() {
		File[] files = cacheDir.listFiles();
		if (files == null)
			return;
		for (File f : files)
			f.delete();
	}

}