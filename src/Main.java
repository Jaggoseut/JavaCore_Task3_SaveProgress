import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Main {

    static void saveGame(String s, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(s, true); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    static void zipFiles(String s, List<String> listSave) {
//        try (ZipOutputStream zip = new ZipOutputStream(new FileOutputStream("F:/Game/savegames/zip.zip", true)); FileInputStream fis = new FileInputStream(s)){
//            ZipEntry entry = new ZipEntry("zipSave.dat");
//            zip.putNextEntry(entry);
//            byte [] buffer = new byte[fis.available()];
//            fis.read(buffer);
//            zip.write(buffer);
//            zip.closeEntry();
//        } catch (Exception ex) {
//            System.out.println(ex.getMessage());

 //       }
        try (ZipOutputStream zip = new ZipOutputStream(new FileOutputStream("F:/Game/savegames/save.zip"))){

            for(String file  : listSave) {
                File fileToZip = new File(file);
                FileInputStream fis = new FileInputStream(fileToZip);
                zip.putNextEntry(new ZipEntry(fileToZip.getName()));
                int length;

                byte[] b = new byte[2048];

                while((length = fis.read(b)) > 0) {
                    zip.write(b, 0, length);
                }
                fis.close();
                zip.close();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    static void zipFilesRevert(String s) {
        try(ZipInputStream zip = new ZipInputStream(new FileInputStream(s))){
            ZipEntry entry;
            while ((entry = zip.getNextEntry()) != null) {
                FileOutputStream fil = new FileOutputStream("F:/Game/savegames/newSave.dat", true);
                for (int i = zip.read(); i != -1; i = zip.read()) {
                    fil.write(i);
                }
                fil.flush();
                zip.closeEntry();
                fil.close();
            }
        }catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    static void desirealize(String s){
        GameProgress gameProgress = null;
        GameProgress gameProgress1 = null;
        GameProgress gameProgress2 = null;
        try (FileInputStream fil = new FileInputStream(s); ObjectInputStream ois = new ObjectInputStream(fil)){
            gameProgress = (GameProgress) ois.readObject();
            gameProgress1 = (GameProgress) ois.readObject();
            gameProgress2 = (GameProgress) ois.readObject();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println(gameProgress);
        System.out.println(gameProgress1);
        System.out.println(gameProgress2);
    }

    static void delete(String s){
        File f = new File(s);
        f.delete();
    }

    public static void main(String[] args) {
        List<String> listSave = Arrays.asList("F:/Game/savegames/save1.dat", "F:/Game/savegames/save2.dat", "F:/Game/savegames/save3.dat");
        saveGame(listSave.get(0), new GameProgress(20, 2, 50, 954.50));
        saveGame(listSave.get(1), new GameProgress(80, 3, 55, 600.30));
        saveGame(listSave.get(2), new GameProgress(50, 5, 65, 600.30));
        zipFiles("F:/Game/savegames/zip.zip", listSave);
        delete("F:/Game/savegames/save.dat");
        zipFilesRevert("F:/Game/savegames/zip.zip");
        desirealize("F:/Game/savegames/newSave.dat");

    }
}
