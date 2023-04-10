import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class VoteR {

    NvraRecord[] doubleNvraArray(NvraRecord[] currentNvraArray) {
        // create a new array with twice the size
        NvraRecord[] newArray = new NvraRecord[currentNvraArray.length * 2];
        // copy elements from the old array to the new one
        for (int i = 0; i < currentNvraArray.length; i++) {
            newArray[i] = currentNvraArray[i];
        }
        return newArray;
    }

    public static void main(String[] args) {
        VoteR voteR = new VoteR();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            // for local tests
            //BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("NVRA Short Test FIle.csv")));
            //BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("NVRA Statistics July 2018 SEB DFH.csv")));
            int lineCounter = 1;
            String line;
            // skip header
            reader.readLine();
            LinkedList<Integer> recordIDs = new LinkedList<>();
            int currentPosition = 0;
            NvraRecord[] data = new NvraRecord[10];
            // read lines from input until no lines remain
            while ((line = reader.readLine()) != null) {
                // split the line using the comma as a delimiter
                String[] array = line.split(",");
                boolean valid = true;
                String[] strings = new String[3];
                int[] nums = new int[21];
                int numI = 0;
                int strI = 0;
                for (int i = 0; i < array.length; i++) {
                    // process only those fields that should be integers
                    if (i != 3 && i != 11 && i != 12) {
                        int value = Integer.parseInt(array[i]);
                        // check if the ID is unique
                        if (i == 0) {
                            for (Integer id : recordIDs) {
                                if (id == value) {
                                    valid = false;
                                    break;
                                }
                            }
                            if (!valid) {
                                System.out.println("Duplicate record ID " + value + " at line " + lineCounter++ + ".");
                                break;
                            }
                        }
                        // if the ID is negative display error message
                        if (value < 0) {
                            valid = false;
                            System.out.println("Invalid data at line " + lineCounter++ + ".");
                            break;
                        }
                        nums[numI++] = value;
                    } else {
                        strings[strI++] = array[i];
                    }
                }
                // if the record is valid add its ID to the list
                if (valid) {
                    recordIDs.add(Integer.parseInt(array[0]));
                    // check if there is a space for a new element if so add the element to the array
                    // otherwise create a new array with twice the size
                    if (currentPosition < data.length) {
                        data[currentPosition++] = new NvraRecord(nums, strings);
                    } else {
                        data = voteR.doubleNvraArray(data);
                        System.gc();
                    }
                    lineCounter++;
                }
            }
            // print the data in reverse order
            for (int i = currentPosition - 1; i >= 0; i--) {
                System.out.println(data[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
