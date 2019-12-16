package tacos;

public interface IngredientRepository {
	
	public Iterable<Ingredient> findAll();
	
	public Ingredient findOne(String id);
	
	public Ingredient save(Ingredient ingredient);

}
