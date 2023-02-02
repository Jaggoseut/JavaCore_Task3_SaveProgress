import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Main {

    static void saveGame(String s) {
        GameProgress gameProgress = new GameProgress(20, 2, 50, 954.50);
        GameProgress gameProgress1 = new GameProgress(80, 3, 55, 600.30);
        GameProgress gameProgress2 = new GameProgress(50, 5, 65, 600.30);
        try (FileOutputStream fos = new FileOutputStream(s, true); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
            oos.writeObject(gameProgress1);
            oos.writeObject(gameProgress2);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    static void zipFiles(String s) {
        try (ZipOutputStream zip = new ZipOutputStream(new FileOutputStream("F:/Game/savegames/zip.zip", true)); FileInputStream fis = new FileInputStream(s)){
            ZipEntry entry = new ZipEntry("zipSave.dat");
            zip.putNextEntry(entry);
            byte [] buffer = new byte[fis.available()];
            fis.read(buffer);
            zip.write(buffer);
            zip.closeEntry();
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
        saveGame("F:/Game/savegames/save.dat");
        zipFiles("F:/Game/savegames/save.dat");
        delete("F:/Game/savegames/save.dat");
        zipFilesRevert("F:/Game/savegames/zip.zip");
        desirealize("F:/Game/savegames/newSave.dat");

    }
}
