package tacos.data;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import tacos.Ingredient;
import tacos.Ingredient.Type;

@Repository
public class JdbcIngredientRepository  {
	private JdbcTemplate jdbc;
	
	@Autowired
	public JdbcIngredientRepository(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	public Iterable<Ingredient> findAll() {
		return jdbc.query("SELECT id, name, type FROM Ingredient", this::mapRowToIngredient);	
	}

	public Ingredient findOne(String id) {
		return jdbc.queryForObject("SELECT id, name, type FROM Ingredient where id = ?",
				this::mapRowToIngredient, id);
	}

	public Ingredient save(Ingredient ingredient) {
		jdbc.update(
				"INSERT INTO Ingredient (id, name, type) values (?, ?, ?) ",
				ingredient.getId(),
				ingredient.getName(),
				ingredient.getType().toString());
		return ingredient;
		
	}

	private Ingredient mapRowToIngredient(ResultSet rs, int rowNum) throws SQLException {
		return new Ingredient(	rs.getString("id"),
								rs.getString("name"),
								Ingredient.Type.valueOf(rs.getString("type")));
	}
}
