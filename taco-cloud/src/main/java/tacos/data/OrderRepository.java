package tacos.data;

import tacos.Order;

public interface OrderRepository {
	
	public void save(Order order);
}
