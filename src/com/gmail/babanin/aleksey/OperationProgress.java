package com.gmail.babanin.aleksey;

import java.io.File;

public interface OperationProgress {
    public OperationType getType();
    public int getProgress();
    public boolean isFailed();
    public boolean isStart();
    public File getIn();
    public File getOut();
}
