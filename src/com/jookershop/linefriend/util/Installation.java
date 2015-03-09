package com.jookershop.linefriend.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.UUID;

import android.content.Context;
import android.os.Environment;

public class Installation {
    private static String sID = null;
    private static final String INSTALLATION = "INSTALLATION";

    public synchronized static String id(Context context) {
        if (sID == null) {  
            File installation = new File(context.getFilesDir(), INSTALLATION);
            try {
                if (!installation.exists())
                    writeInstallationFile(installation);
                sID = readInstallationFile(installation);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return sID;
    }
    
    public static void writeSD(String id) {
    	try {
	    	String state = Environment.getExternalStorageState();
	    	if (Environment.MEDIA_MOUNTED.equals(state)) {
	    		File mFileTemp = new File(Environment.getExternalStorageDirectory(),
	    				INSTALLATION);
	    		writeInstallationFile(mFileTemp, id);
	    	}    
    	} catch (Throwable e) {
//    		e.printStackTrace();
    	}
    }
    
    public static boolean isExistSD() {
    	try {
	    	String state = Environment.getExternalStorageState();
	    	if (Environment.MEDIA_MOUNTED.equals(state)) {
	    		File mFileTemp = new File(Environment.getExternalStorageDirectory(),
	    				INSTALLATION);
	    		return mFileTemp.exists();
	    	}    
    	} catch (Throwable e) {
    		return true;
//    		e.printStackTrace();
    	}
    	return true;
    }   

    public synchronized static String idFromSD(Context context) {
        try {
        	String state = Environment.getExternalStorageState();
        	if (Environment.MEDIA_MOUNTED.equals(state)) {
        		File mFileTemp = new File(Environment.getExternalStorageDirectory(),
        				INSTALLATION);
        		if(mFileTemp.exists()) {
        			sID = readInstallationFile(mFileTemp);
        		}
        	}                
        } catch (Throwable e) {
        	return null;
        }
        return sID;
    }
	
    private static void writeInstallationFile(File installation, String id) throws IOException {
        FileOutputStream out = new FileOutputStream(installation);
        out.write(id.getBytes());
        out.close();
    	
    }
	
    private static String readInstallationFile(File installation) throws IOException {
        RandomAccessFile f = new RandomAccessFile(installation, "r");
        byte[] bytes = new byte[(int) f.length()];
        f.readFully(bytes);
        f.close();
        return new String(bytes);
    }

    private static void writeInstallationFile(File installation) throws IOException {
        FileOutputStream out = new FileOutputStream(installation);
        String id = UUID.randomUUID().toString();
        out.write(id.getBytes());
        out.close();
    }
}