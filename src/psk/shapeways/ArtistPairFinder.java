package psk.shapeways;

import java.io.IOException;
import java.util.BitSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class ArtistPairFinder {

	private static final int STICKY_THRESHOLD = 50;

	private static final int numUsers = 1000;

	/**
	 * This map is central to the algorithm. It maps each unique artist (across
	 * all lists) to a bit array. Each bit in the bit array represents the
	 * presence of that artist in that list. For example: Radiohead -> [ 0 0 0 0
	 * 1 ] means that lists 1 through 4 don't have Radiohead but list 5 does.
	 */
	Map<String, BitSet> artist2List = new HashMap<String, BitSet>();

	private List<String> userLists;
	private Set<String> stickyPairs = new TreeSet<String>();

	public ArtistPairFinder(List<String> lists) {
		this.userLists = lists;
	}

	public static void main(String[] argv) {
		if (argv.length != 1) {
			System.err.println("Please supply the input music list file name");
			return;
		}

		List<String> lists = null;

		try {
			System.out.println("Reading input file");
			lists = ArtistListReader.read(argv[0]);
		} catch (IOException e) {
			System.err.println("Couldn't read file " + argv[0]);
			return;
		}

		ArtistPairFinder d = new ArtistPairFinder(lists);

		d.buildArtistBitSets();
		d.findPairs();
		ArtistPairWriter.writePairs(d.getStickyPairs());
	}

	private Collection<String> getStickyPairs() {
		return stickyPairs;
	}

	/**
	 * Perform an inner loop over all unique artists so that we can examine
	 * every pair. Get the bitset for each pair of artists and AND them. The
	 * number of TRUE bits represents the number of user lists that have both
	 * artists. If this is greater than 50, write the pair to file as specified
	 */
	private void findPairs() {

		System.out.println("Finding pairs of artists");
		
		String[] artists = artist2List.keySet().toArray(new String[0]);

		int count = 0;
		for (int i = 0; i < artists.length; i++) {
			
			String artistA = artists[i];
			BitSet bsA = artist2List.get(artistA);

			for (int j = i; j < artists.length; j++) {
				++count;
				
				String artistB = artists[j];
				
				if (artistA == artistB)
					continue; // intentional identity comparison

				BitSet bsB = artist2List.get(artistB);

				// clone A since the and operation is destructive
				BitSet bsAcopy = (BitSet) bsA.clone();
				bsAcopy.and(bsB);

				if (bsAcopy.cardinality() > STICKY_THRESHOLD) {
					if (artistA.compareTo(artistB) > 0)
						stickyPairs.add(artistA + ", " + artistB);
					else
						stickyPairs.add(artistB + ", " + artistA);
				}
			}
		}
		
		System.out.println(count + " pairs tested");
	}

	/**
	 * Peel out every artist from every list. For every artist, create a bitset
	 * numUsers large. Each bit represents whether that artist is in that user
	 * list. Set this list's bit to true for this artist. This tracks which
	 * lists have which artists.
	 */
	private void buildArtistBitSets() {
		System.out.println("Building artist list data structure");
		int listID = 0;
		for (String list : userLists) {
			String[] artists = list.split(",");

			for (String artist : artists) {
				if (artist.equals(""))
					continue; // TODO: report empty band
				BitSet bs = artist2List.get(artist);
				if (bs == null)
					bs = new BitSet(numUsers);

				bs.set(listID);
				artist2List.put(artist, bs);
			}

			++listID;
		}
	}

}
