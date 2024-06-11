package LabSD;

import java.util.List;

import javax.jws.WebService;

@WebService(endpointInterface = "LabSD.SOAP")
public class SOAPImpl implements SOAP{

	@Override
	public List<Product> getProducts(){
		return Product.getProducts();
	}
	
	@Override
	public void addProduct(Product product) {
		Product.getProducts().add(product);
	}
}
