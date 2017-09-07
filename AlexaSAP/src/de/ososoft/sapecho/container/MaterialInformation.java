package de.ososoft.sapecho.container;

import java.util.HashMap;
import java.util.Map;

public class MaterialInformation {
	private static MaterialInformation instance;
	Map<String, String[]> itemData = new HashMap<>();
	Map<String, String> itemSynonyms = new HashMap<>();

	private MaterialInformation() {
		initilizeItemData();
		initilizeItemSynonyms();
	}

	public static MaterialInformation getInstance() {
		if (instance == null) {
			instance = new MaterialInformation();
		}
		return instance;
	}

	public boolean validateMaterial(String material) {
		return itemSynonyms.containsKey(material.toLowerCase());
	}

	public String[] getMaterialInformation(String material) {
		return itemData.get(itemSynonyms.get(material));
	}

	private void initilizeItemData() {
		itemData.put("test", new String[] { "100-806", "L", "1000" });
		itemData.put("piston", new String[] { "900-009", "PC", "3800" });
		itemData.put("deckel", new String[] { "900-004", "PC", "1000" });
		itemData.put("box", new String[] { "MD100-400", "EA", "3000" });
		itemData.put("grün", new String[] { "CH-1064", "KG", "CHP2" });
		itemData.put("blau", new String[] { "CH-1060", "KG", "CHP2" });
		itemData.put("rot", new String[] { "CH-1066", "KG", "CHP2" });
		itemData.put("gelb", new String[] { "CH-1062", "KG", "CHP2" });
	}

	private void initilizeItemSynonyms() {
		itemSynonyms.put("roter farbtopf", "rot");
		itemSynonyms.put("eimer rote farbe", "rot");
		itemSynonyms.put("roter farbeimer", "rot");
		itemSynonyms.put("rote farbe", "rot");
		itemSynonyms.put("gelber farbtopf", "gelb");
		itemSynonyms.put("eimer gelbe farbe", "gelb");
		itemSynonyms.put("gelber farbeimer", "gelb");
		itemSynonyms.put("gelbe farbe", "gelb");
		itemSynonyms.put("blauer farbtopf", "blau");
		itemSynonyms.put("eimer blaue farbe", "blau");
		itemSynonyms.put("blauer farbeimer", "blau");
		itemSynonyms.put("blaue farbe", "blau");
		itemSynonyms.put("grüner farbtopf", "grün");
		itemSynonyms.put("eimer grüne farbe", "grün");
		itemSynonyms.put("grüner farbeimer", "grün");
		itemSynonyms.put("grüne farbe", "grün");
		itemSynonyms.put("verpackung", "box");
		itemSynonyms.put("karton", "box");
		itemSynonyms.put("test", "test");
		itemSynonyms.put("piston", "piston");
		itemSynonyms.put("deckel", "deckel");
		itemSynonyms.put("box", "box");
		itemSynonyms.put("grün", "grün");
		itemSynonyms.put("blau", "blau");
		itemSynonyms.put("gelb", "gelb");
	}
}
