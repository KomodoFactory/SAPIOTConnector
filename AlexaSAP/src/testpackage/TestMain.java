package testpackage;

import java.io.IOException;

import com.google.gson.Gson;

import de.ososoft.sapecho.container.Bestellanforderung;

public class TestMain {

	public static void main(String[] args) throws IOException {

		Gson gson = new Gson();
		String json = gson.toJson(new Bestellanforderung.BestellanforderungBuilder("Test", 1).build());
		
		System.out.println(json);
	}
}