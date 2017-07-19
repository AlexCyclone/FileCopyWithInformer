package com.gmail.babanin.aleksey;

import java.io.File;

public class OperationController {
    private File in;
    private File out;
    private OperationType type;
    private ProcessInformer informer;
    private CopyFile copier;

    public OperationController(File in, File out, OperationType type) {
        super();
        this.in = in;
        this.out = out;
        this.type = type;
        startProcess();
    }

    private synchronized void startProcess() {
        switch (type) {
        case COPY:
            startCopy();
            break;
        default:
            break;
        }
    }

    private void startCopy() {
        try {
            copier = new CopyFile(in, out);
            Thread cp = new Thread(copier);
            cp.start();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        try {
            informer = new ProcessInformer(copier);
            Thread inf = new Thread(informer);
            inf.start();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return;
        }
    }

}
