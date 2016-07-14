package jinq.qa.tests;

import jinq.clause.SelectIterable;
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
		final SelectIterable<Person, Boolean> query = new Enumerable<>(persons)
				.where(p -> p != null)
				.select(Person.ElementSelectors.AvailableFunc);

		final Iterable<Boolean> set   = Iterables.distinct(query);
		final int               count = query.count();

		Utils.print(set, count, "testDistinct {makeTruthyPredicate, availableTransformer}");
	}

	@Test
	public void testToList() {
		final SelectIterable<Person, Person> query = new Enumerable<>(persons)
				.where(Person.Predicates.IsEvenWeight)
				.select();

		final Iterable<Person> evenWeightPersons = query.toList();
		final int              count             = query.count();

		Utils.print(evenWeightPersons, count, "testToList {evenWeightPredicate,makeIdentityTransformer}");
	}
}