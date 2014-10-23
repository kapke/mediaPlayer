package com.studia.someplayer;

import android.os.Environment;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by kapke on 23.10.14.
 */
public class FilesystemAudioProvider implements AudioProvider {
    private String[] audioExtensions;
    private Queue<File> checkQueue;
    private List<File> foundFiles;

    public FilesystemAudioProvider() {
        this.foundFiles = new ArrayList<File>();
        this.checkQueue = new ConcurrentLinkedQueue<File>();
    }

    @Override
    public void setAudioExtensions(String[] exts) {
        this.audioExtensions = exts;
    }

    @Override
    public List<URI> getFiles() {
        String externalState = Environment.getExternalStorageState();
        List<URI> outputList = new ArrayList<URI>();
        if(Environment.MEDIA_MOUNTED.equals(externalState) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(externalState)) {
            File root = Environment.getExternalStorageDirectory();
            File musicDir = new File(root, Environment.DIRECTORY_MUSIC);
            this.checkDirectory(root);
            for(File subdirectory : this.checkQueue) {
                this.checkDirectory(subdirectory);
            }
            for(File audioFile : this.foundFiles) {
                outputList.add(audioFile.toURI());
            }
        }
        return outputList;
    }

    private void checkDirectory (File directory) {
        if(directory.isDirectory()) {
            File[] list = directory.listFiles();
            if(list != null) {
                for (File file : list) {
                    if (file.isDirectory()) {
                        this.checkQueue.add(file);
                    } else if (file.isFile() && this.isAudioFile(file)) {
                        this.foundFiles.add(file);
                    }
                }
            }
        }
    }

    private Boolean isAudioFile (File file) {
        String name = file.getName();
        for(String extension : this.audioExtensions) {
            if(name.endsWith(extension)) {
                return true;
            }
        }
        return false;
    }
}
