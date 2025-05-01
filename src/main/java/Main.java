import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
  public static void main(String[] args){
    if (args.length < 2) {
      System.out.println("Missing <database path> and <command>");
      return;
    }

    String databaseFilePath = args[0];
    String command = args[1];

    switch (command) {
      case ".dbinfo" -> {
        try {
          FileInputStream databaseFile = new FileInputStream(new File(databaseFilePath));
          
          databaseFile.skip(16); // Skip the first 16 bytes of the header
          byte[] pageSizeBytes = new byte[2]; // The following 2 bytes are the page size
          databaseFile.read(pageSizeBytes);
          short pageSizeSigned = ByteBuffer.wrap(pageSizeBytes).getShort();
          int pageSize = Short.toUnsignedInt(pageSizeSigned);
          // You can use print statements as follows for debugging, they'll be visible when running tests.
          System.err.println("Logs from your program will appear here!");
          databaseFile.skip(82); // 100 - (16 + 2)
          databaseFile.skip(3);
          byte[] numCellsBytes = new byte[2];
          databaseFile.read(numCellsBytes);
          short numCellsSigned = ByteBuffer.wrap(numCellsBytes).getShort();
          int numCells = Short.toUnsignedInt(numCellsSigned);
          
          // Uncomment this block to pass the first stage
          System.out.println("database page size: " + pageSize);
          System.out.println("number of tables: " + numTables);
        } catch (IOException e) {
          System.out.println("Error reading file: " + e.getMessage());
        }
      }
      default -> System.out.println("Missing or invalid command passed: " + command);
    }
  }
}
