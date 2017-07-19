package com.gmail.babanin.aleksey;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        File in = new File("Fargo.S03E07.720p.WEB.rus.LostFilm.TV.mp4");
        File out = new File("test.mp4");
        new OperationController(in, out, OperationType.COPY);
    }
    
}
