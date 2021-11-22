// --== CS400 Project Two File Header ==--
// Make: FNU Simran
// Email: simran4@wisc.edu
// Team: Red
// Group: DQ
// TA: Yuye J
// Lecturer: Florian Heimerl

import java.util.LinkedList;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.Arrays;
import java.io.File;

interface CarLoaderInterface {
    public List<CarData> loadFile(String csvFilePath) throws FileNotFoundException;
    public List<CarData> loadAllFilesInDirectory(String directoryPath) throws FileNotFoundException;
}

public class CarLoader implements CarLoaderInterface {

    /**
     * This Class is used to extract information from the CSV files and load it into CarData object
     *
     * @author Simran
     *
     */

    @Override
    public List<CarData> loadFile(String csvFilePath) throws FileNotFoundException {

        List<CarData> Carlist = new LinkedList<CarData>();

        File csv = new File(csvFilePath);

        if (!csv.isFile()) throw new FileNotFoundException("File not found");

        int make = 0;
        int model = 0;
        int price = 0;
        int counter = 0;
        boolean even = true;
        Scanner sc = new Scanner(csv);
        if(!sc.hasNextLine()) {
            sc.close();
            throw new FileNotFoundException("Try Again");
        }
        String Heading[] = sc.nextLine().split(",");
        for (int i = 0; i < Heading.length; i++) {
            // System.out.println(Heading[i]);

            if (Heading[i].equals("make"))  {
                make = i;
            }
            if (Heading[i].equals("model")) {
                model = i;
            }
            if (Heading[i].equals("price")) {
                price = i;
            }

        }

        while (sc.hasNextLine()) {
            int start = 0;
            int j = 0;
            String[] words = new String[Heading.length];
            String vals = sc.nextLine();
            for (int i = 0; i < vals.length(); i++) {

                String word; // Substring, Concat or something like that

                if (vals.charAt(i) == ',') {

                    if (even == true) {
                        word = vals.substring(start, i);
                        start = i;
                        words[j] = word;
                        j++;

                    }

                }

            }

            int pr;
            String mk;
            String mdl;

            pr = Integer.parseInt(words[price].replace(",", ""));
            mk = words[make].replaceFirst(",", "");
            mdl = words[model].replaceFirst(",", "");

            CarData cd = new CarData(mk, mdl, pr);
            Carlist.add(cd);

        }

        sc.close();

        return Carlist;
    }


    /*
     * Class to load all files in a dir
     */
    @Override
    public List<CarData> loadAllFilesInDirectory(String directoryPath) throws FileNotFoundException {

        List<CarData> Carlist = new LinkedList<CarData>();
        File directory = new File(directoryPath);

        if (!directory.isDirectory()) throw new FileNotFoundException("Dir not found");

        String[] files = directory.list();

        for (int i = 0; i < files.length; i++) {

            String dir = directoryPath + "/" + files[i];
            Carlist.addAll(loadFile(dir));

        }

        return Carlist;
    }

}


