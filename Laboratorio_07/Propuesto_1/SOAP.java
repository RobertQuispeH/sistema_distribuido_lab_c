package labsd;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface SOAP {

	@WebMethod
	public List<Product> getProducts();
	
	@WebMethod
	public void addProduct(Product product);
	
	@WebMethod
	public String purchaseProduct(String name, int quantity);
}
