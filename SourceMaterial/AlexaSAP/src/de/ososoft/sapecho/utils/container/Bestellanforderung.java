package de.ososoft.sapecho.utils.container;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.google.gson.annotations.SerializedName;

public class Bestellanforderung {
	@SerializedName("doc_type")
	protected String orderType;
	@SerializedName("preq_item")
	protected String articleNumber;
	protected String quantity;
	@SerializedName("unit")
	protected String unitName;
	protected String material;
	protected String plant;
	@SerializedName("deliv_date")
	protected String deliveryDate;

	public Bestellanforderung(String material, int quantity) {
		this.articleNumber = "1";
		this.orderType = "NB";
		this.quantity = Integer.toString(quantity);
		String[] materialData = MaterialInformation.getInstance().getMaterialInformation(material.toLowerCase());
		this.material = materialData[0];
		this.unitName = materialData[1];
		this.plant = materialData[2];
		this.deliveryDate = formatDate();

	}

	private String formatDate() {
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 7);
		return df.format(cal.getTime());
	}
}
