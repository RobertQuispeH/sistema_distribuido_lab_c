package labsd;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	public static List<Product> products = new ArrayList<>(Arrays.asList(
			new Product("Lapiceros", 0.80, 100),
			new Product("Cuadernos", 3.40, 50),
			new Product("Hoja Bond", 10.50, 200),
			new Product("Gomas de Borrar", 1.20, 150),
			new Product("Sacapuntas", 0.50, 80),
			new Product("Marcadores", 2.50, 60),
			new Product("Tijeras", 3.00, 40),
			new Product("Reglas", 1.50, 70),
			new Product("Calculadoras", 15.00, 30),
			new Product("Compás", 4.00, 50)
	));
	
	public String name;
	public double price;
	public int stock;

	public Product() {
		super();
	}
	
	public Product(String name, double price, int stock) {
		super();
		this.name = name;
		this.price = price;
		this.stock = stock;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}
	
	public static List<Product> getProducts(){
		return products;
	}
	
	@Override
	public String toString() {
		return "Product [name=" + name + ", price=" + price + ", stock=" + stock + "]";
	}
}
