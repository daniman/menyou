package edu.mit.menyou;

import java.io.Serializable;
public class RestaurantObject implements Serializable {

		private String name;
		private String description;

		public RestaurantObject(String name, String description) {
			super();
			this.name = name;
			this.description = description;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

	}


