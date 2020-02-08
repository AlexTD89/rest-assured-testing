package tests.day04_tests_using_Pojo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;
import pojos.Job;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class GsonDemo {

    @Test
    public void deserializeThis() throws FileNotFoundException {
        // converts the input to java object
        Gson gson = new Gson();

        //reads the file that we need to read
        FileReader reader = new FileReader("src/test/resources/it_job.json");

        //fromJson -> takes json input source and converts to object
        Job job = gson.fromJson(reader, Job.class);

        System.out.println(job);

    }

    @Test
    public void serializeThis() throws IOException {
        // Java to Json, Json to Java
        //converter
        Gson gson = new Gson();

        //format the json file
        Gson gson1 = new GsonBuilder().setPrettyPrinting().create();

        //Java Object that we want to serialize
        Job job = new Job("TE", "Teacher", 100, 100000);

        //class that writes file
        FileWriter output = new FileWriter("src/test/resources/te_job.json");
        // toJson -> serialization is happening here
        gson.toJson(job, output);

        // write into the file
        output.flush();
        output.close();
    }
}
