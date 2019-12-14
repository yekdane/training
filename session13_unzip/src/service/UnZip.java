package service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class UnZip {
    private File sourceFile;
    private String unZipContain = "";

    public String getUnZipContain() {
        return unZipContain;
    }

    public UnZip(String path) throws IOException {
        sourceFile = new File(path);
    }

    public void generateZipContent(int number, byte... val) throws IOException {
        if (number != 0) {
            unZipContain += (String.valueOf((char) val[0])).repeat(number);
        } else {
            unZipContain += new String(val);
        }
    }

    public void unZipCurrentFile() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(sourceFile);
        byte[] byteArray = new byte[1024];
        int length;
        Byte val = null;
        String counter = "";
        State state = State.ready;
        while ((length = fileInputStream.read(byteArray)) != -1) {
            for (int j = 0; j < length; j++) {
                switch (state) {
                    case ready:
                        if (byteArray[j] == '\r') {
                            state = State.readCharR;
                        } else {
                            state = State.readVal;
                        }
                        val = byteArray[j];
                        break;
                    case readVal:
                        if (byteArray[j] == '(')
                            state = State.openParantes;
                        break;
                    case openParantes:
                        if (byteArray[j] == ')') {
                            generateZipContent(Integer.valueOf(counter), val);
                            state = State.ready;
                            counter="";
                        } else {
                            counter += (char) (byteArray[j]);
                        }
                        break;
                    case readCharR:
                        if (byteArray[j] == '\n') {
                            generateZipContent(0, val, byteArray[j]);
                            state = State.ready;
                        }
                        break;
                    default:
                        state = State.readVal;
                        break;
                }
            }
        }
    }

    enum State {
        ready, openParantes, readVal, readCharR;
    }
}
