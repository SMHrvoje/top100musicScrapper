package org.application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Main {
    public static void main(String[] args) {


        ExecutorService service = Executors.newFixedThreadPool(1);

        service.submit(new MusicScrapper());
        service.shutdown();
    }
}