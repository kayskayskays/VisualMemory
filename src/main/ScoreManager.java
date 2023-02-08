package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class ScoreManager {

    File scoreDirectory;
    File recordsFile;
    final int recordsRows = 61;
    public List<Integer> records = new ArrayList<>();

    String recPath = "records.txt";

    public ScoreManager() {
        createRecords();
        readRecords();
    }

    public void createRecords() {

        try {
            recordsFile = new File(recPath);

            if (!recordsFile.exists()) {

                recordsFile.createNewFile();

                FileWriter fw = new FileWriter(recordsFile.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);

                for (int i = 0; i < recordsRows - 1; i++) {
                    bw.write("0 \n");
                }
                bw.write('0');
                bw.close();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readRecords() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(recordsFile.getAbsoluteFile()));

            for (int i = 0; i < recordsRows; i++) {
                int score = Integer.parseInt(br.readLine().split(" ")[0]);
                records.add(score);
            }

            br.close();
            recordsFile.delete();
            recordsFile = null;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void writeRecords() {

        try {
            recordsFile = new File(recPath);
            recordsFile.createNewFile();

            FileWriter fw = new FileWriter(recordsFile.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);

            for (int i = 0; i < records.size() - 1; i++) {
                bw.write(records.get(i).toString() + '\n');
            }
            bw.write(records.get(records.size() - 1).toString());

            bw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
