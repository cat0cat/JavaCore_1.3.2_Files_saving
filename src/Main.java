import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static String path = "D:" + File.separator + "Games(netology)" + File.separator + "savegames" + File.separator;

    public static void main(String[] args) {

        GameProgress progress1 = new GameProgress(500,4,5,234);
        GameProgress progress2 = new GameProgress(85500,76,85,4);
        GameProgress progress3 = new GameProgress(7500,34,25,23);

        saveGame(path + "save1.dat", progress1);
        saveGame(path + "save2.dat", progress2);
        saveGame(path + "save3.dat", progress3);

        ArrayList<String> list = new ArrayList<>();
        list.add(path + "save1.dat");
        list.add(path + "save2.dat");
        list.add(path + "save3.dat");

        zipFiles(path + "save.zip", list);
        deleteUnpackingFiles();

    }

    public static void saveGame(String path, GameProgress progress) {
        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(progress);
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public static void zipFiles(String path, List<String> list) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(path))) {
            int count = 1;
            int i;
            for (String s : list) {
                try (FileInputStream fis = new FileInputStream(s)) {
                    ZipEntry entry = new ZipEntry("packed_save" + count++ + ".dat");
                    zout.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer);
                    zout.closeEntry();
                } catch (Exception ex) {
                System.out.println(ex.getMessage());
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void deleteUnpackingFiles() {
        File files = new File(path);
        if(files.isDirectory()) {
            for (File f : files.listFiles()) {
                if (!f.getName().contains(".zip")) {
                    f.delete();
                }
            }
        }
    }

}
