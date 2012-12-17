package psk.shapeways;

import java.io.IOException;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Driver {
	
	private static final int numUsers = 1000;
	// Map of Artist to yes/no for each User Fave List
	// Artist -> [ 1, 1, 0, 0, 0 ... 1 ]
	Map<String, BitSet> artist2List = new HashMap<String, BitSet>();
	private List<String> lists;
	
	public Driver(List<String> lists) {
		this.lists = lists;
	}

	public static void main(String[] argv) {
		try {
			List<String> lists = ArtistListReader
					.read("C:\\Users\\Peter\\Downloads\\Artist_lists_small.txt");
			
			Driver d = new Driver(lists);
			d.buildBandBitSets();
			//System.out.println(d);
			d.findPairs();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private void findPairs() {
		Set<String> artists = artist2List.keySet();
		int count = 0;
		for (String artistA : artists) {
			for (String artistB : artists) {
				//System.out.println(artistA + ", " + artistB);
				++count;
			}			
		}
		System.out.println("Count " + count);
		
	}

	@Override
	public String toString() {
		StringBuilder bld = new StringBuilder();
		
		for (String band : artist2List.keySet()) {
			bld.append(band + " -> [ ");
			BitSet bs = artist2List.get(band);
			for (int i = 0; i < bs.length(); i++) {
				bld.append(bs.get(i) + ", ");
			}
			bld.append(" ]\n");
		}
		
		return bld.toString();
	}

	private void buildBandBitSets() {
		int listID = 0;
		for (String list : lists) {
			String[] bands = list.split(",");
			for (String band : bands) {
				BitSet bs = artist2List.get(band);
				if (bs == null) 
					bs = new BitSet(numUsers);
				
				bs.set(listID);
				artist2List.put(band, bs);
			}
			
			++listID;
		}
	}

}
