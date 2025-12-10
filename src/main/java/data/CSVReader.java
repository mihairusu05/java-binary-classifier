package data;

import core.Instance;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CSVReader {
    private List<Instance<Double, String>> data = new ArrayList<>();

    public CSVReader(String path){
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(path))){
            this.data = bufferedReader.lines()
                    .map(this::csvToInstance)
                    .toList();
        }catch(FileNotFoundException e){
            throw new IllegalArgumentException("No such file exists " + e.getMessage());
        }catch(IOException e){
            throw new IllegalArgumentException("Error reading from file " + e.getMessage());
        }catch (Exception e){
            throw new IllegalArgumentException("Unexpected error " + e.getMessage());
        }
    }

    private Instance<Double, String> csvToInstance(String line){
        try{
            String[] params= line.split(",");
            List<Double> features = new ArrayList<>();
            String label = "" ;

            for (String param : params){
                try{
                    Double feature = Double.parseDouble(param);
                    features.add(feature);
                }catch(NumberFormatException e){
                    label = param;
                }
            }
            return new Instance<Double, String>(features, label);
        }catch(Exception e){
            throw new IllegalArgumentException("Error reading data from csv" + e.getMessage());
        }
    }

    public List<Instance<Double, String>> getData() {
        return data;
    }
}
