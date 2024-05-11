package dados;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Csv {
    public List<List<String>> loadCSV(String path){

        List<List<String>> records = new ArrayList<>();

        try(Scanner scanner = new Scanner(new File(path),  StandardCharsets.UTF_8)){
            while(scanner.hasNextLine()){
                records.add(getRecordFromLine(scanner.nextLine()));
            }
        }catch(IOException e){
            System.err.println(e);
        }

        records.remove(0);// remove table names(id, name etc)

        return records;
    }

    private List<String> getRecordFromLine(String line){
        List<String> values = new ArrayList<String>();

        try(Scanner scanner = new Scanner(line)){
            scanner.useDelimiter(",");
            while(scanner.hasNext()){
                values.add(scanner.next());
            }
        }

        return values;
    }



    

    public void adicionar(String conteudo, String path) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, true))) {
            writer.newLine();
            writer.write(conteudo);
            System.out.println("Conteudo salvo");
        } catch (IOException e) {
            System.err.println("Erro ocorreu: " + e.getMessage());
            throw e; // Re-throw the exception to let the caller handle it
        }
    }


    public  void removePorId(String csvFilePath, int id) throws IOException {
        File file = new File(csvFilePath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
        StringBuilder content = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            // Assuming each line in the file represents a record with an ID
            // Check if the ID in the line matches the ID to be removed
            // If it matches, skip this line
            if (!line.startsWith(id + ",")) {
                content.append(line).append("\n");
            }
        }
        reader.close();

        // Write the modified content back to the file
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
        writer.write(content.toString());
        writer.close();
    }
    
}
