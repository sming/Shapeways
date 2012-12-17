package psk.shapeways;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ArtistListReader {
	static List<String> read(String fileName) throws IOException {
		Path file = FileSystems.getDefault().getPath(fileName);
		return Files.readAllLines(file, StandardCharsets.ISO_8859_1);

	}

}
