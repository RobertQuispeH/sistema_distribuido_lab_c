package Anexo1;
// Usadopara manejar errores específicos relacionados con el stock.
public class StockException extends Exception{
	public StockException(String msg) {
		super(msg);
	}
}
