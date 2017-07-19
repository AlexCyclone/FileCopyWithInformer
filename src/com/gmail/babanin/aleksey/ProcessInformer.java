package com.gmail.babanin.aleksey;

public class ProcessInformer implements Runnable {
    OperationProgress operation;

    public ProcessInformer(OperationProgress operation) {
        super();
        this.operation = operation;
    }

    @Override
    public void run() {
        if (operation == null) {
            System.out.println("Operation failed");
            return;
        }
        try {
            while (!operation.isStart()) {
                if (operation.isFailed()) {
                    System.out.println("Operation failed");
                    return;
                }
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        switch (operation.getType()) {
        case COPY:
            copyInformer();
            break;
        default:
            break;
        }
    }

    private void copyInformer() {
        System.out.println("Coping file from: " + operation.getIn().getAbsolutePath());
        System.out.println("              to: " + operation.getOut().getAbsolutePath());
        int progress = 0;
        System.out.print("[");

        try {
            while (true) {
                if (operation.isFailed()) {
                    System.out.println("Operation failed");
                    return;
                }
                for (; progress < operation.getProgress() / 5; progress += 1) {
                    System.out.print('-');
                }
                if (progress >= 20) {
                    break;
                } else {
                    Thread.sleep(1000);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("]");
        System.out.println("Copying complete");
    }

}
