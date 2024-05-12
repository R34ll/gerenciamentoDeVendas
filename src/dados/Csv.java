package dados;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;


public class Csv {
    public List<List<String>> carregaCSV(String path) throws IOException{

        List<List<String>> recordes = new ArrayList<>();
        
        try(BufferedReader reader = new BufferedReader(new FileReader(path, StandardCharsets.UTF_8))){
            // Ignora a primeira linha(header)
            reader.readLine();
            
            String linha;
            while((linha = reader.readLine()) != null){
                recordes.add(getRecordFromLine(linha));
            }
        }catch( IOException e){
            throw new IOException("Erro ao carregar o arquivo CSV: " + e.getMessage());
        }

        return recordes;
    }

    private List<String> getRecordFromLine(String line){
        List<String> valores = new ArrayList<String>();

        try(Scanner scanner = new Scanner(line)){
            scanner.useDelimiter(",");
            while(scanner.hasNext()){
                valores.add(scanner.next());
            }
        }

        return valores;
    }



    

    public void adicionar(String conteudo, String path) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path, true), StandardCharsets.UTF_8))) {

            writer.newLine();
            writer.write(conteudo);
            System.out.println("Conteúdo salvo com sucesso.");
        } catch (IOException e) {
            throw new IOException("Erro ao adicionar conteúdo ao arquivo CSV: " + e.getMessage());
        }
    }


    public  void removePorId(String csvFilePath, int id) throws IOException {
        try {
            File inputFile = new File(csvFilePath);
            File tempFile = new File("temp.csv");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile, StandardCharsets.UTF_8));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile, StandardCharsets.UTF_8));

            String linhaAtual;
            List<String> linhas = new ArrayList<>();

            while((linhaAtual = reader.readLine()) != null){
                if(!linhaAtual.startsWith(id+",")){
                    linhas.add(linhaAtual);
                }
            }


            Iterator<String> iterator = linhas.iterator();
            while(iterator.hasNext()){
                String linha = iterator.next();
                if(iterator.hasNext()){
                    writer.write(linha + System.getProperty("line.separator"));
                }else{
                    writer.write(linha);
                }
            }
            

            writer.close();
            reader.close();

            if (!inputFile.delete()) {
                throw new IOException("Erro ao excluir o arquivo original.");
            }

            if (!tempFile.renameTo(inputFile)) {
                throw new IOException("Erro ao renomear o arquivo temporário.");
            }

            System.out.println("Registro removido com sucesso.");
        } catch (IOException e) {
            throw new IOException("Erro ao remover registro do arquivo CSV: " + e.getMessage());
        }
    }

    public int getLastID(String path) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String linha;
            String ultimaLinha = null;
            while ((linha = reader.readLine()) != null) {
                ultimaLinha = linha; 
            }
            if (ultimaLinha != null) {
                String[] tokens = ultimaLinha.split(",");
                return Integer.parseInt(tokens[0]); 
            }
        }
        return 0;
    }
    
}
