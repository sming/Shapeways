package psk.shapeways;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Collection;


public class ArtistPairWriter {
	static void writePairs(Collection<String> stickyPairs) {
		
		System.out.println("Writing output to file");
		String fileName = "Artist_sticky_pairs.txt";
		String sep = System.getProperty("line.separator");
		
		Writer out = null;
		try {
			out = new BufferedWriter(new FileWriter(fileName, false));
			for (String pair : stickyPairs) {
				out.write(pair);
				out.write(sep);
			}

		} catch (IOException e) {
			System.err.println("Problem writing to the output file");
		} finally {
			try {
				out.close();
				System.out.println("Wrote output pairs to " + fileName);
			} catch (IOException e) {
				System.err.println("Problem closing the output file");
			}
		}

	}

}
