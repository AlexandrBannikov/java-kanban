package test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<String> list = Arrays.asList("Банан","Груша","Слива","Виноград","Яблоко");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("resources/text.csv"));
            for (String s : list) {
                writer.write(s);
                writer.write(" ");
            }
            //writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println("Файл не создан!");
        }
    }
}
