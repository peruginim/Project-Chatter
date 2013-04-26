import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.Thread.*;

public class SerialIO {
 OutputStream out;

 public SerialIO(OutputStream out){
  this.out = out;
 }
 
 public void out(byte [] output) {
  try {
    //int i = 0;
       int c = 0;
       int i = 0;
       Thread.currentThread().sleep(2000);
       while (i < output.length){
         c = (byte)output[i];
         System.out.println(c);
         this.out.write(c);
         i++;
       }
       c = (byte)'\n';
       this.out.write(c);
  } catch (Exception e) {
     e.printStackTrace();
  }

 }
}
