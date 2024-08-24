import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileSearch {
    //在指定的目录下搜索所有文件
    public static List<String> searchFiles(String directory, String[] extensions) {
        //初始化一个 ArrayList 来存储文件路径
        List<String> files = new ArrayList<>();
        File dir = new File(directory);
        //确保传入的目录路径是有效的目录。
        if (dir.isDirectory()) {
            searchFilesRecursive(dir, extensions, files);
        }
        //返回包含所有符合条件文件路径的列表
        return files;
    }

    //递归地搜索指定目录及其子目录中的所有文件，找出具有特定扩展名的文件，并将这些文件的绝对路径添加到files中。
    private static void searchFilesRecursive(File directory, String[] extensions, List<String> files) {
        File[] fileList = directory.listFiles();//用于获取目录中的文件和子目录的数组。
        if (fileList != null) {
            for (File file : fileList) {
                if (file.isDirectory()) {
                    searchFilesRecursive(file, extensions, files);
                } else {
                    String filename = file.getName();
                    for (String extension : extensions) {
                        if (filename.endsWith(extension)) {
                            files.add(file.getAbsolutePath());
                            break;
                        }
                    }
                }
            }
        }
    }

    //在指定文件中查找包含特定内容的行，并返回这些行及其行号的列表。
    public static List<String> searchContent(String filePath, String content) {
        List<String> matchingLines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNum = 1;
            while ((line = reader.readLine()) != null) {
                if (line.contains(content)) {
                    matchingLines.add("Line " + lineNum + ": " + line);
                }
                lineNum++;
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + filePath);
            e.printStackTrace();
        }
        return matchingLines;
    }

    public static void writeToFile(String outputFilePath, String filePath, List<String> matchingLines) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath, true))) {
            writer.write("[+] File Path: " + filePath + "\n");
            writer.write("[=] Line Rows: " + matchingLines.size() + "\n");
            for (String line : matchingLines) {
                writer.write("[~] " + line + "\n");
            }
            writer.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println("  _   _   _   _   _   _   _  \n" +
                " / \\ / \\ / \\ / \\ / \\ / \\ / \\ \n" +
                "( F | i | n | d | i | n | g )\n" +
                " \\_/ \\_/ \\_/ \\_/ \\_/ \\_/ \\_/ ");
        String directory = "D:\\DeliveryOptimization\\Cache"; // 在这里指定要搜索的目录
        String[] extensions = {".txt",".xml"}; // 在这里添加指定要搜索的文件扩展名
        String[] contents = {"keyword","name"};// 在这里添加指定要搜索的关键字
        String outfile="C:\\Users\\Desktop\\re.txt";// 在这里添加指定要输出的路径
        List<String> foundFiles = searchFiles(directory, extensions);
        for (String filePath : foundFiles) {
            for (String content:contents){
                List<String> foundLines = searchContent(filePath, content);
                if (foundLines.size()>0){
                    writeToFile(outfile,filePath,foundLines);
                }
            }
            }
        }
    }


