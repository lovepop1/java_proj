package com.iiitb.imageEffectApplication.service;
import com.iiitb.imageEffectApplication.model.LogModel;
import org.apache.juli.logging.Log;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDateTime;
import java.text.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.time.format.*;
import java.time.ZoneId;


@Service
public class LoggingService {

    //Custom comparator class to define a custom compare method for sorting log entries based off their timestamps.
    static class LogComparator implements Comparator<String>
    {
        //Custom comparison logic.
        @Override
        public int compare(String line1, String line2)
        {
            try
            {
                SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                Date date1 = DATE_FORMAT.parse(line1.split(", ")[0]);
                Date date2 = DATE_FORMAT.parse(line2.split(", ")[0]);

                //Returns value based on the chronological order of the two dates. The method returns a positive value, 0, negative value based on whether date1 occurs after, on, or before date2 respectively.
                return date1.compareTo(date2);
            }
            catch(ParseException e)
            {
                e.printStackTrace();
                return 0;
            }
        }
    }

    //Method to return a LogModel object from a string containing the log information.
    public LogModel parseLog(String line)
    {
        String[] parts = line.split(", ");
        String time = parts[0];
        String filename = parts[1];
        String effectName=parts[2];
        String optionVals=parts[3];

        return new LogModel(time,filename,effectName,optionVals);
    }

    //Method to add a new Log to the existing logs so that all the logs are in chronological order of their timestamps. The most recent log is added at the bottom of all the logs in the txt file.
    public void addLog(String fileName, String effectName, String optionValues){
        //Getting the current date and time.
        LocalDateTime cur=LocalDateTime.now(ZoneId.systemDefault());

        //Formatting the current date and time to the required format.
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String formattedTime=cur.format(formatter);

        //Creating the log entry.
        String log = formattedTime + ", " + fileName + ", " + effectName + ", " + optionValues;

        //Read the existing lines from the Data.txt file and add it to the list.
        List<String> lines = new ArrayList<>();
        int added_log=0;
        try (BufferedReader reader = new BufferedReader(new FileReader("Data.txt")))
        {
            String line;
            while((line=reader.readLine())!=null)
            {
                SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                Date date1 = DATE_FORMAT.parse(line.split(", ")[0]);
                Date date2 = DATE_FORMAT.parse(log.split(", ")[0]);
                //We add the new log to the list if the timestamp of the current line is greater, and we still haven't added the new log.
                if(date1.compareTo(date2)>0 && added_log==0)
                {
                    lines.add(log);
                    added_log=1;
                }
                lines.add(line);
            }
        }
        catch(IOException | ParseException e)
        {
            e.printStackTrace();
        }

        //If the new log was never added then the value of added_log would remain zero, and hence we add the new log to the end of the list.
        if(added_log==0)
        {
            lines.add(log);
        }

        //Writing the now sorted lines along with the new log onto the Data.txt file.
        try (FileWriter writer=new FileWriter("Data.txt"))
        {
            for(String line: lines)
            {
                writer.write(line + System.lineSeparator());
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

    }

    //Method to return a list of all the logs present.
    public List<LogModel> getAllLogs() {
        List<LogModel> logs = new ArrayList<LogModel>();
        try (BufferedReader reader = new BufferedReader(new FileReader("Data.txt")))
        {
            String line;
            while((line = reader.readLine())!=null)
            {
                LogModel log=parseLog(line);
                logs.add(log);
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        return logs;
    }

    //Method to get all the logs having the same effect name as the given string.
    public List<LogModel> getLogsByEffect(String effectName) {
        List<LogModel> logs = new ArrayList<LogModel>();
        try(BufferedReader reader = new BufferedReader(new FileReader("Data.txt")))
        {
            //Reading the entire file while comparing the effectName and adding that log if there is a match.
            String line;
            while((line=reader.readLine())!=null)
            {
                String[] parts = line.split(", ");
                if(parts[2].toUpperCase().compareTo(effectName.toUpperCase())==0)       //So that the user is not restricted to use all smalls, or one Capital letter and other small letter.
                {
                    String time = parts[0];
                    String filename = parts[1];
                    String optionVals=parts[3];
                    LogModel log = new LogModel(time,filename,effectName,optionVals);
                    logs.add(log);
                }
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return logs;
    }

    //Method to clear all the logs by simply opening a new writer and not writing anything to the file. This overwrites the contents of the file to empty content.
    public void clearLogs() {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("Data.txt")))
        {}
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    //Method to return all the logs occuring between the given timestamps.
    public List<LogModel> getLogsBetweenTimestamps(LocalDateTime startTime, LocalDateTime endTime) {
        List<LogModel> logs = new ArrayList<LogModel>();
        try(BufferedReader reader=new BufferedReader(new FileReader("Data.txt")))
        {
            String line;
            int stop_reading=0;
            //Reading through the entire Data.txt file.
            while((line=reader.readLine())!=null)
            {
                //Getting the log object from the information present in the line of the Data.txt file.
                LogModel log = parseLog(line);
                if(log!=null )
                {
                    //Formatting the time of the log object.
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
                    LocalDateTime logTime=LocalDateTime.parse(log.getTimestamp(),formatter);

                    //Checking if the time of the log object lies between the given timestamps.
                    if(logTime.isAfter(startTime) && logTime.isBefore(endTime))
                    {
                        logs.add(log);
                        stop_reading=1;
                        continue;
                    }

                    //Since the logs are maintained in chronological order of their timestamps, then coming out of the if statement would mean that the logTime is not in the given range of timestamps. Hence, execution of this statement would occur only if all the logs between the timestamps have already been added.
                    if(stop_reading==1)
                    {
                        reader.close();
                        break;
                    }
                }
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        //Returning the required list of logs.
        return logs;
    }
}
