package service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ZipFile {
    private File sourceFile;
    private String zipContain = "";

    public String getZipContain() {
        return zipContain;
    }

    public ZipFile(String path) throws IOException {
        sourceFile = new File(path);
        if (!sourceFile.exists())
            throw new IOException("your file is not available ");
        if (!sourceFile.canRead())
            throw new IOException("your file is writable");
    }

    public void generateZipContent(int number, byte... val) throws IOException {
        if (number != 0) {
            zipContain += String.format("%s(%d)", new String(val), number);
        } else {
            zipContain += new String(val);
        }
    }

    public void zipCurrentFile() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(sourceFile);
        byte[] byteArray = new byte[5];
        int length, counter = 0;
        Byte temp = null;

        while ((length = fileInputStream.read(byteArray)) != -1) {
            for (int j = 0; j < length; j++) {
                if (temp == null) temp = byteArray[j];
                if (byteArray[j] != temp) {
                    if (temp == 13 && byteArray[j] == 10) {
                        generateZipContent(0, temp, byteArray[j]);
                        temp = null;
                        counter = 0;
                    } else {
                        generateZipContent(counter, temp);
                        temp = byteArray[j];
                        counter = 1;
                    }
                } else counter++;
            }
        }
        if (temp != null)
            generateZipContent(counter, temp);
    }
}
