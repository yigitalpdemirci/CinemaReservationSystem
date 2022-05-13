import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Properties;

public class Reader {
    static int maximumErrorWithoutGettingBlocked;
    static String title;
    static int discountPercentage;
    static int blockTime;

    static ArrayList<User> userList = new ArrayList<>();
    static ArrayList<Film> filmList = new ArrayList<>();
    static ArrayList<Hall> hallList = new ArrayList<>();
    static ArrayList<Seat> seatList = new ArrayList<>();

    static String hashPassword(String password) {
        byte[] bytesOfPassword = password.getBytes(StandardCharsets.UTF_8);
        byte[] md5Digest;
        try {
            md5Digest = MessageDigest.getInstance("MD5").digest(bytesOfPassword);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        return Base64.getEncoder().encodeToString(md5Digest) ;
    }

    static void backupReader() {
        String line;
        File input = new File("assets/data/backup.dat");

        try (BufferedReader reader = new BufferedReader(new FileReader(input))) {
            while ((line = reader.readLine()) != null) {
                String[] lineArray = line.split("\t");
                switch (lineArray[0]) {
                    case "user":
                        userList.add(new User(lineArray[1],lineArray[2],Boolean.parseBoolean(lineArray[3]),Boolean.parseBoolean(lineArray[4])));
                        break;
                    case "film":
                        filmList.add(new Film(lineArray[1],lineArray[2],Integer.parseInt(lineArray[3])));
                        break;
                    case "hall":
                        hallList.add(new Hall(lineArray[1],lineArray[2],Integer.parseInt(lineArray[3]),Integer.parseInt(lineArray[4]),Integer.parseInt(lineArray[5])));
                        break;
                    case "seat":
                        seatList.add(new Seat(lineArray[1],lineArray[2],Integer.parseInt(lineArray[3]),Integer.parseInt(lineArray[4]),lineArray[5],Integer.parseInt(lineArray[6])));
                        break;
                }
            }

        } catch (FileNotFoundException IOe) {
            try {
                BufferedWriter backup = new BufferedWriter(new FileWriter("assets/data/backup.dat"));
                backup.write("user\tadmin\tX03MO1qnZdYdgyfeuILPmQ==\ttrue\ttrue\n");
                backup.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void propertiesReader(){
        try (InputStream inputStream = Files.newInputStream(Paths.get("assets/data/properties.dat"))) {
            Properties properties = new Properties();
            properties.load(inputStream);

            // get the property value and print it out
            maximumErrorWithoutGettingBlocked = Integer.parseInt(properties.getProperty("maximum-error-without-getting-blocked"));
            title = properties.getProperty("title");
            discountPercentage = Integer.parseInt(properties.getProperty("discount-percentage"));
            blockTime = Integer.parseInt(properties.getProperty("block-time"));

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}