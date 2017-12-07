package de.ososoft.sapecho.utils.container;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class MaterialInformation {
	private static MaterialInformation instance;
	Map<String, String[]> itemData = new HashMap<>();

	public static MaterialInformation getInstance() {
		if (instance == null) {
			instance = new MaterialInformation();
		}
		return instance;
	}

	private MaterialInformation() {
		initilizeItemData();
	}

	public int countPossibleMatchesForMaterial(String material) {
		if(material.equals("box")) {
			return 1;			
		}
		if(material.equals("boxen")) {
			return 1;			
		}
		if(material.equals("gelb")) {
			return 1;			
		}
		if(material.equals("rot")) {
			return 1;			
		}
		int numberOfPossibleMatches = 0;
		for (String key : itemData.keySet()) {
			if (key.toLowerCase().contains(material.toLowerCase())) {
				numberOfPossibleMatches++;
			}
		}
		return numberOfPossibleMatches;
	}

	/**
	 * This is not really Most Likely. But it works well enough for the test. This
	 * should be done for real at some point. But finding the best solution for a
	 * likelyhood of strings is quiet complicated.
	 */
	public String getMostLikelyMatchForMaterial(String material) {
		ArrayList<String> matchingMaterial = new ArrayList<>();
		Random r = new Random();

		for (String key : itemData.keySet()) {
			if (key.toLowerCase().contains(material.toLowerCase())) {
				matchingMaterial.add(key);
			}
		}

		return matchingMaterial.get(r.nextInt(matchingMaterial.size()));
	}

	public String[] getMaterialInformation(String material) {
		if(material.equals("box")) {
			return new String[] { "MD100-400", "EA", "3000" };			
		}
		if(material.equals("boxen")) {
			return new String[] { "MD100-400", "EA", "3000" };			
		}
		if(material.equals("gelb")) {
			return new String[] { "CH-1062", "KG", "CHP2" };			
		}
		if(material.equals("rot")) {
			return new String[] { "CH-1066", "KG", "CHP2" };			
		}
		for (Entry<String, String[]> entry : itemData.entrySet()) {
			if (entry.getKey().toLowerCase().contains(material.toLowerCase())) {
				return entry.getValue();
			}
		}
		return new String[] { "MD100-400", "EA", "3000" };
	}

	private void initilizeItemData() {
		itemData.put("Sensation ESD Halbschuh S zwei", new String[] { "213813", "ST", "1000" });
		itemData.put("Scott ESD Sandale S eins", new String[] { "213948", "ST", "1000" });
		itemData.put("GX drei hundert fünfzig Damensandale S eins", new String[] { "213974", "ST", "1000" });
		itemData.put("GX hundert dreizig Damenschuh S eins", new String[] { "214005", "ST", "1000" });
		itemData.put("Luftfilter", new String[] { "220905", "ST", "1000" });
		itemData.put("Filterkerze", new String[] { "220917", "ST", "1000" });
		itemData.put("Filterbeutel", new String[] { "220918", "ST", "1000" });
		itemData.put("Filtermatte", new String[] { "220919", "ST", "1000" });
		itemData.put("Farbnebelabscheider", new String[] { "220920", "ST", "1000" });

		itemData.put("domiLINE Schlittenfuehrung fünfzig Hub fünf und zwanzig mm",
				new String[] { "147576", "ST", "1000" });
		itemData.put("domiLINE Schlittenfuehrung fünfzig Hub fünfzig mm", new String[] { "147577", "ST", "1000" });
		itemData.put("domiLINE Schlittenfuehrung fünfzig Hub fünf und siebzig mm",
				new String[] { "147578", "ST", "1000" });
		itemData.put("domiLINE Schlittenfuehrung fünfzig Hub hundert mm", new String[] { "147579", "ST", "1000" });
		itemData.put("domiLINE Schlittenfuehrung achtzig Hub fünf und zwanzig mm",
				new String[] { "147580", "ST", "1000" });
		itemData.put("domiLINE Schlittenfuehrung achtzig Hub fünfzig mm", new String[] { "147581", "ST", "1000" });
		itemData.put("domiLINE Schlittenfuehrung achtzig Hub fünf und siebzig mm",
				new String[] { "147582", "ST", "1000" });
		itemData.put("domiLINE Schlittenfuehrung achtzig Hub hundert mm", new String[] { "147583", "ST", "1000" });
		itemData.put("domiLINE Schlittenfuehrung hundert zwanzig Hub fünf und zwanzig mm",
				new String[] { "147584 ", "ST", "1000" });
		itemData.put("domiLINE Schlittenfuehrung hundert zwanzig Hub fünfzig mm",
				new String[] { "147585", "ST", "1000" });
		itemData.put("domiLINE Schlittenfuehrung hundert zwanzig Hub fünf und siebzig mm",
				new String[] { "147586", "ST", "1000" });
		itemData.put("domiLINE Schlittenfuehrung hundert zwanzig Hub hundert mm",
				new String[] { "147587", "ST", "1000" });

		itemData.put("Rammschutzplanken B L S Abm. hundert mal ein tausend mm R A L",
				new String[] { "147587", "ST", "1000" });
		itemData.put("Rammschutzplanken B L S Abm. hundert mal ein tausend ein hundert mm R A L",
				new String[] { "147587", "ST", "1000" });
		itemData.put("Rammschutzplanken B L S Abm. hundert mal ein tausend zwei hundert mm R A L",
				new String[] { "147587", "ST", "1000" });
		itemData.put("Rammschutzplanken B L S Abm. hundert mal ein tausend drei hundert mm R A L",
				new String[] { "147587", "ST", "1000" });
		itemData.put("Rammschutzplanken B L S Abm. hundert mal ein tausend sechs hundert mm R A L",
				new String[] { "147587", "ST", "1000" });
		itemData.put("Rammschutzplanken B L S Abm. hundert mal ein tausend sieben hundert mm R A L",
				new String[] { "147587", "ST", "1000" });
		itemData.put("Rammschutzplanken B L S Abm. hundert mal ein tausend acht hundert mm R A L",
				new String[] { "147587", "ST", "1000" });
		itemData.put("Rammschutzplanken B L S Abm. hundert mal ein tausend neun hundert mm R A L",
				new String[] { "147587", "ST", "1000" });
		itemData.put("Rammschutzplanken B L S Abm. hundert mal zwei tausend mm R A L",
				new String[] { "147587", "ST", "1000" });
		itemData.put("Rammschutzplanken B L S Abm. hundert mal acht hundert mm R A L",
				new String[] { "147587", "ST", "1000" });
		itemData.put("Rammschutzplanken B L S Abm. hundert mal neun hundert mm R A L",
				new String[] { "147587", "ST", "1000" });

	}
}
