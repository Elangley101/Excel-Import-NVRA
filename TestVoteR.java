import org.junit.*;


import java.io.*;

public class TestVoteR {
    static NvraRecord[] master = new NvraRecord[31];
    static ByteArrayOutputStream myOut = new ByteArrayOutputStream();

    @BeforeClass
    public static void init() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("NVRA Statistics July 2018 SEB DFH.csv")));
            reader.readLine();
            String line;
            int current = 0;
            while ((line = reader.readLine()) != null && current < 31) {
                // split the line using the comma as a delimiter
                String[] data = line.split(",");
                String[] strings = new String[3];
                int[] nums = new int[21];
                int numI = 0;
                int strI = 0;
                for (int i = 0; i < data.length; i++) {
                    if (i != 3 && i != 11 && i != 12) {
                        int value = Integer.parseInt(data[i]);
                        nums[numI++] = value;
                    } else {
                        strings[strI++] = data[i];
                    }
                }
                master[current++] = new NvraRecord(nums, strings);
            }
            reader.close();

            reader = new BufferedReader(new InputStreamReader(new FileInputStream("NVRA Short Test FIle.csv")));
            StringBuilder builder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                builder.append(line).append(System.lineSeparator());
            }
            System.setOut(new PrintStream(myOut));
            System.setIn(new ByteArrayInputStream(builder.toString().getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDoubleNvraArray() {
        VoteR voteR = new VoteR();
        int size = 10;
        NvraRecord[] testArray = new NvraRecord[size];
        for (int i = 0; i < master.length; i++) {
            if (i == testArray.length) {
                testArray = voteR.doubleNvraArray(testArray);
                size *= 2;
                Assert.assertEquals(size, testArray.length);
                for (int k = 0; k < testArray.length; k++) {
                    if (k < i) {
                        Assert.assertEquals(master[k], testArray[k]);
                    } else {
                        Assert.assertNull(testArray[k]);
                    }
                }
            }
            testArray[i] = master[i];
        }
    }

    @Test
    public void testMain() {
        String ls = System.lineSeparator();
        String expected = "Duplicate record ID 4 at line 5." + ls +
                "Invalid data at line 10." + ls +
                "12;80701;2;PA;0;0;0;0;0;0;0;02 - ALFALFA;Health Care Authority Web;0;0;0;0;0;0;0;0;0;0;0" + ls +
                "10;80700;2;PA;0;0;0;0;0;0;0;02 - ALFALFA;Health Care Authority Print;0;0;0;0;0;0;0;0;0;0;0" + ls +
                "9;34000;2;PA;0;0;0;0;0;0;0;02 - ALFALFA;Health Dept. Print;0;0;0;0;0;0;0;0;0;0;0" + ls +
                "8;10007;2;PA;0;0;0;0;0;0;0;02 - ALFALFA;Public Assistance (Unspecified);0;0;0;0;0;0;0;0;0;0;0" + ls +
                "7;83001;1;PA;2;3;0;0;0;0;0;01 - ADAIR;Dept. of Human Services Web;0;0;1;1;1;1;0;1;0;0;0" + ls +
                "6;83000;1;PA;14;10;1;2;4;6;0;01 - ADAIR;Dept. of Human Services Print;0;0;7;7;3;3;3;4;0;0;0" + ls +
                "4;80700;1;PA;0;0;0;0;0;0;0;01 - ADAIR;Health Care Authority Print;0;0;0;0;0;0;0;0;0;0;0" + ls +
                "3;34001;1;PA;0;0;0;0;0;0;0;01 - ADAIR;Health Dept. Web;0;0;0;0;0;0;0;0;0;0;0" + ls +
                "2;34000;1;PA;4;2;0;2;2;3;0;01 - ADAIR;Health Dept. Print;0;0;0;1;0;0;0;0;0;0;0" + ls +
                "1;10007;1;PA;0;0;0;0;0;0;0;01 - ADAIR;Public Assistance (Unspecified);0;0;0;0;0;0;0;0;0;0;1" + ls;
        VoteR.main(new String[]{});
        Assert.assertArrayEquals(expected.getBytes(), myOut.toByteArray());
    }
}
