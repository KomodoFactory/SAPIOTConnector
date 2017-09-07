package de.ososoft.sapecho.container;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Bestellanforderung {
	private String preq_item;
	private String quantity;
	private String unit;
	private String doc_type;
	private String material;
	private String plant;
	private String deliv_date;

	private Bestellanforderung(BestellanforderungBuilder builder) {
		this.preq_item = builder.getPrequesitionNumber();
		this.quantity = builder.getQuantity();
		this.unit = builder.getUnitName();
		this.doc_type = builder.getOrderType();
		this.material = builder.getMaterial();
		this.plant = builder.getPlant();
		this.deliv_date = builder.getDeliv_date();
	}

	public static class BestellanforderungBuilder {
		private String prequesitionNumber = "1";
		private String orderType = "NB";
		private int quantity; 
		private String unitName; 
		private String material; 
		private String plant; 
		private Date deliv_date; 

		public BestellanforderungBuilder(String material, int quantity) {
			this.quantity = quantity;
			String[] materialData = MaterialInformation.getInstance().getMaterialInformation(material.toLowerCase());
			this.material = materialData[0];
			this.unitName = materialData[1];
			this.plant = materialData[2];

			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, 7);
			this.deliv_date = cal.getTime();

		}

		public Bestellanforderung build() {
			return new Bestellanforderung(this);
		}

		private String getPrequesitionNumber() {
			return prequesitionNumber;
		}

		private String getQuantity() {
			return Integer.toString(quantity);
		}

		private String getUnitName() {
			return unitName;
		}

		private String getOrderType() {
			return orderType;
		}

		private String getMaterial() {
			return material;
		}

		private String getPlant() {
			return plant;
		}

		private String getDeliv_date() {
			DateFormat df = new SimpleDateFormat("yyyyMMdd");
			return df.format(deliv_date);
		}
	}

	public String getPreq_item() {
		return preq_item;
	}

	public String getQuantity() {
		return quantity;
	}

	public String getUnit() {
		return unit;
	}

	public String getDoc_type() {
		return doc_type;
	}

	public String getMaterial() {
		return material;
	}

	public String getPlant() {
		return plant;
	}

	public String getDeliv_date() {
		return deliv_date;
	}

}
