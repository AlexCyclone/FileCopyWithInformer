package com.gmail.babanin.aleksey;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class CopyFile implements Runnable, OperationProgress {

    private File in;
    private File out;
    private long fileSize;
    private long progress = 0;
    private boolean failed = false;
    private boolean start = false;

    public CopyFile(File in, File out) {
        super();
        this.in = checkFile(checkNull(in));
        this.out = checkNull(out);
    }

    private final File checkNull(File file) {
        if (file == null) {
            failed = true;
            throw new IllegalArgumentException();
        }
        return file;
    }

    private final File checkFile(File file) {
        if (!file.exists() || file.isDirectory()) {
            failed = true;
            throw new IllegalArgumentException();
        }
        fileSize = file.length();
        return file;
    }

    public synchronized void copyFile() throws IOException {
        start = true;
        try (FileInputStream fis = new FileInputStream(in); FileOutputStream fos = new FileOutputStream(out)) {
            byte[] buffer = new byte[1024 * 1024];
            int readByte = 0;
            while ((readByte = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, readByte);
                progress += readByte;
            }
            progress += 1;
        } catch (IOException e) {
            failed = true;
            throw e;
        }
    }

    @Override
    public void run() {
        try {
            copyFile();
        } catch (IOException e) {
            failed = true;
            e.printStackTrace();
        }
    }

    @Override
    public OperationType getType() {
        return OperationType.COPY;
    }

    @Override
    public int getProgress() {
        return (int) ((progress * 100L) / (fileSize + 1L));
    }

    @Override
    public boolean isFailed() {
        return failed;
    }

    @Override
    public boolean isStart() {
        return start;
    }

    @Override
    public File getIn() {
        return in;
    }

    @Override
    public File getOut() {
        return out;
    }

}
