import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;
/**
 * Write a description of nc_weather here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class nc_weather {
    public void testColdestHourInFile(){
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser(); 
        CSVRecord rec = coldestHourInFile(parser);
        System.out.println(rec.get("TemperatureF"));
    }
    
    public CSVRecord coldestHourInFile(CSVParser parser){
        CSVRecord minTemp = null;
        for(CSVRecord record : parser){
            double currTemp = Double.parseDouble(record.get("TemperatureF"));
            if(minTemp == null){
                minTemp = record;
            }
            else{
                if(currTemp < Double.parseDouble(minTemp.get("TemperatureF"))
                 && currTemp!=-9999){
                    minTemp = record;
                }
            }
        }
        return minTemp;
        
    }
    
    void testFileWithColdestTemperature(){
        System.out.println(fileWithColdestTemperature());
        //testColdestHourInFile();
    }
    
    String fileWithColdestTemperature(){
        CSVRecord minf = null;
        String file="";
        DirectoryResource dr = new DirectoryResource();
        for(File f : dr.selectedFiles()){
            FileResource fr = new FileResource(f);
            CSVRecord curr = coldestHourInFile(fr.getCSVParser());
            if(minf == null){
                minf = curr;
            }
            else{
                if(Double.parseDouble(curr.get("TemperatureF"))<Double.parseDouble(minf.get("TemperatureF"))){
                    minf = curr;
                    file = f.getName();
                }
            }
        }
        return file;
    }
    void testLowestHumidityInFile(){
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        CSVRecord csv = lowestHumidityInFile(parser);
        System.out.println(csv.get("Humidity") + "  " + csv.get("DateUTC"));
    }
    
    
    
    CSVRecord lowestHumidityInFile(CSVParser parser){
        CSVRecord minHum = null;
        for(CSVRecord record : parser){
            if(!record.get("Humidity").equals("N/A")){ 
                double currHum = Double.parseDouble(record.get("Humidity"));
                if(minHum == null){
                    minHum = record;
                }
                else{
                    if(currHum < Double.parseDouble(minHum.get("Humidity"))
                     && currHum!=-9999){
                        minHum = record;
                    }
                }
            }
        }
        return minHum;
        
    }
    
    void testLowestHumidityInManyFiles(){
        CSVRecord rec = lowestHumidityInManyFiles();
        System.out.println(rec.get("Humidity") + "  " + rec.get("DateUTC"));
        
    }
    
    
    
    CSVRecord lowestHumidityInManyFiles(){
        CSVRecord minH = null;
        String file="";
        DirectoryResource dr = new DirectoryResource();
        for(File f : dr.selectedFiles()){
            FileResource fr = new FileResource(f);
            CSVRecord curr = lowestHumidityInFile(fr.getCSVParser());   
            if(minH == null){
                minH = curr;
            }
            else{
                if(Double.parseDouble(curr.get("Humidity"))<Double.parseDouble(minH.get("Humidity"))){
                    minH = curr;
                    file = f.getName();
                }
            }   
        }
        return minH;
    }
    void testAverageTemperatureInFile(){
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        double avgTemp = averageTemperatureInFile(parser);
        System.out.println(avgTemp);
    }
    
    double averageTemperatureInFile(CSVParser parser){
        double sum=0;
        int count = 0;
        for(CSVRecord rec : parser){
            String temp = rec.get("TemperatureF");
            if(!temp.equals("-9999")){
                sum = sum+Double.parseDouble(temp);
                count++;
            }
        }
        return sum/count;
    }
    void testAverageTemperatureWithHighHumidityInFile(){
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        double avgTemp = averageTemperatureWithHighHumidityInFile(parser,80);
        System.out.println(avgTemp);
    }
    
    double averageTemperatureWithHighHumidityInFile(CSVParser parser, int value){
        double sum=0;
        int count=0;
        for(CSVRecord rec : parser){
            String temp = rec.get("TemperatureF");
            int hum = Integer.parseInt(rec.get("Humidity"));
            if(!temp.equals("-9999") && hum>=value){
                sum = sum+Double.parseDouble(temp);
                count++;
            }
        }
        return sum/count;
    }
}

    