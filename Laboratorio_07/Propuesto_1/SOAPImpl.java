package labsd;

import java.util.List;
import javax.jws.WebService;

@WebService(endpointInterface = "labsd.SOAP")
public class SOAPImpl implements SOAP {

	@Override
	public List<Product> getProducts() {
		return Product.getProducts();
	}

	@Override
	public void addProduct(Product product) {
		Product.products.add(product);
	}

	@Override
	public String purchaseProduct(String name, int quantity) {
		for (Product product : Product.products) {
			if (product.name.equalsIgnoreCase(name)) {
				if (product.stock >= quantity) {
					product.stock -= quantity;
					return "Purchase successful: " + quantity + " units of " + name;
				} else {
					return "Insufficient stock for product: " + name;
				}
			}
		}
		return "Product not found: " + name;
	}
}
