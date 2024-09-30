package org.application;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MusicScrapper implements Runnable{

    private static final String URL = "https://www.officialcharts.com/charts/singles-chart/";
    private List<SongRecord> musicRecords=new ArrayList<>();

    /**
     * Runs this operation.
     */
    @Override
    public void run() {
        try {
            Document doc = Jsoup.connect(URL).get();
            Elements elements = doc.select(".chart-item-content");;
            for (int i = 0; i<elements.size();++i){
                Element element= elements.get(i) ;
                String title = element.select(".chart-name").text();
                String artist = element.select(".chart-artist").text();
                String[] arr= element.select(".movement").text().split(" ");

                int movement =0;
                if(i==89){
                    System.out.println(arr.length);
                }
                if(arr.length==2){
                    movement = arr[1].toLowerCase().contains("new") || !arr[1].substring(0,arr[1].length()-1).chars().allMatch(Character::isDigit) ? 0 : Integer.parseInt(arr[1].substring(0,arr[1].length()-1));
                }
                int peak = 0;
                arr = element.select(".peak").text().split(" ");
                if(arr.length==2){
                    peak = Integer.parseInt(arr[1].substring(0,arr[1].length()-1));
                }
                arr = element.select(".weeks").text().split(" ");
                int weeks= 0;
                if(arr.length==2){
                    weeks = Integer.parseInt(arr[1]);
                }
              musicRecords.add(new SongRecord(title,artist,movement,peak,weeks));
            }
            printOutData();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void printOutData(){
        System.out.println("Top 100 hottest records");
        System.out.println("---------------------------");
        for (SongRecord songRecord : musicRecords) {
            System.out.println("Title: "+ songRecord.title());
            System.out.println("Artist: "+ songRecord.artist());
            System.out.println("Movement: "+ songRecord.movement());
            System.out.println("Peak: "+ songRecord.peak());
            System.out.println("Weeks: "+ songRecord.weeks());
            System.out.println("----------------------");
        }
    }
}
