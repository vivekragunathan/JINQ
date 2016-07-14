package jinq.qa.tests;

import jinq.core.Enumerable;
import jinq.qa.shared.Person;
import jinq.qa.shared.Utils;
import jodash.Iterables;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TailEndTests {

	private final List<Person> persons = new ArrayList<>();

	@Before
	public void setUp() throws Exception {
		final List<Person> persons = Utils.getPersons();
		this.persons.clear();
		this.persons.addAll(persons);
	}

	@After
	public void tearDown() throws Exception {
		this.persons.clear();
	}

	@Test
	public void testDistinct() {
		final Iterable<Boolean> booleans = new Enumerable<>(persons)
				.where(Utils.makeTruthyPredicate())
				.select(Person.ElementSelectors.AvailableFunc);

		final Iterable<Boolean> distinctBooleans = Iterables.distinct(booleans);

		Utils.print(distinctBooleans, "testDistinct {makeTruthyPredicate, availableTransformer}");

	}

	@Test
	public void testToList() {
		final Iterable<Person> evenWeightPersons = new Enumerable<>(persons)
				.where(Person.Predicates.IsEvenWeight)
				.select();

		final Iterable<Person> listOfPersons = new Enumerable<>(evenWeightPersons).toList();

		Utils.print(listOfPersons, "testToList {evenWeightPredicate,makeIdentityTransformer}");
	}
}
