package tacos.data;

import tacos.Ingredient;

public interface IngredientRepository {
	
	public Iterable<Ingredient> findAll();
	
	public Ingredient findOne(String id);
	
	public Ingredient save(Ingredient ingredient);

}
