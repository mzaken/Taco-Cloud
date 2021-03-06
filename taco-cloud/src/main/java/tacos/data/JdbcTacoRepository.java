package tacos.data;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import tacos.Ingredient;
import tacos.Taco;

@Repository
public class JdbcTacoRepository {
	
	private JdbcTemplate jdbc;
	
	public JdbcTacoRepository(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	public Taco save(Taco taco) {
		long tacoId = saveTacoInfo(taco);
		taco.setId(tacoId);
		
		for (Ingredient ingredient: taco.getIngredients()) {
			saveIngredient(ingredient, tacoId);
		}
		return taco;
	}

	private void saveIngredient(Ingredient ingredient, long tacoId) {
		jdbc.update("INSERT INTO Taco_Ingredients (taco, ingredient) " +
						"values (?, ?) ",
						tacoId,
						ingredient.getId());
		
	}

	private long saveTacoInfo(Taco taco) {
		taco.setCreatedAt(new Date());
		PreparedStatementCreator psc = new PreparedStatementCreatorFactory(
				"INSERT INTO Taco (name, createdAt) values (?, ?) ",
				Types.VARCHAR, Types.TIMESTAMP).newPreparedStatementCreator(
						Arrays.asList(
								taco.getName(),
								new Timestamp(taco.getCreatedAt().getTime())));
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbc.update(psc, keyHolder);
		
		return keyHolder.getKey().longValue();
	}

}
