package jinq.qa.tests;

import jinq.clause.SelectIterable;
import jinq.clause.SelectManyIterable;
import jinq.core.Enumerable;
import jinq.core.GroupByEntry;
import jinq.core.IEnumerable;
import jinq.qa.shared.Name;
import jinq.qa.shared.Person;
import jinq.qa.shared.Utils;
import misc.UnitedStates;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GroupByTests {

	private final List<Person> persons = new ArrayList<>();

	@Before
	public void setUp() throws Exception {
		persons.add(new Person("Barley Mason", 28, 163.12));
		persons.add(new Person("Boots Leg", 24, 160.45));
		persons.add(new Person("Whiskers Manner", 21, 199.99));
		persons.add(new Person("Daisy Duck", 24, 123.81));

		assignRandomState(persons);
	}

	@Test
	public void testSimpleGroupBy() throws Exception {

		final IEnumerable<GroupByEntry<Integer, Person>> groupings = new Enumerable<>(this.persons)
				.groupBy(Person.KeySelectors.AgeFunc);

		Utils.print(null, 0, "testSimpleGroupBy");

		for (GroupByEntry<Integer, Person> current : groupings) {
			System.out.println(current.getKey());

			for (Person person : current.values()) {
				System.out.println("\t --> " + person);
			}

			System.out.println();
		}
	}

	@Test
	public void testGroupByAge() throws Exception {

		final IEnumerable<GroupByEntry<Integer, Name>> groupings = new Enumerable<>(this.persons)
				.groupBy(Person.KeySelectors.AgeFunc, Person.ElementSelectors.NameFunc);

		Utils.print(null, 0, "testGroupByAge");

		for (GroupByEntry<Integer, Name> current : groupings) {

			final Integer        key      = current.getKey();
			final Iterable<Name> elements = current.values();

			System.out.printf(
					"%s\t{Key@%s * Element@%s}%n",
					key,
					key.getClass(),
					elements.getClass()
			);

			for (Name name : elements) {
				System.out.printf("\t --> %s%n", name);
			}

			System.out.println();
		}
	}

	@Test
	public void testGroupByState() throws Exception {

		final Iterable<GroupByEntry<UnitedStates, Name>> groupings = new Enumerable<>(persons)
				.groupBy(Person.KeySelectors.StateFunc, Person.ElementSelectors.NameFunc);

		Utils.print(null, 0, "testGroupByState");

		for (GroupByEntry<UnitedStates, Name> current : groupings) {

			final UnitedStates   key      = current.getKey();
			final Iterable<Name> elements = current.values();

			System.out.printf(
					"%s\t{Key@%s * Element@%s}%n",
					key,
					key.getClass(),
					elements.getClass()
			);

			for (Name name : elements) {
				System.out.printf("\t --> %s%n", name);
			}

			System.out.println();
		}
	}

	@Test
	public void testGroupByThenSelect() throws Exception {

		final SelectIterable<GroupByEntry<Integer, Person>, Iterable<Person>> query = new Enumerable<>(this.persons)
				.groupBy(Person.KeySelectors.AgeFunc)
				.select(GroupByEntry::values);

		final List<Iterable<Person>> personsList = query.toList();
		final int                    count       = query.count();

		Assert.assertTrue(personsList.size() == count);

		Utils.print(personsList, count, "testGroupByThenSelect(groupBy(age), select(Person)");
	}

	@Test
	public void testGroupByThenFlatten() throws Exception {

		final SelectIterable<GroupByEntry<Integer, Person>, Iterable<Person>> query = new Enumerable<>(this.persons)
				.groupBy(Person.KeySelectors.AgeFunc)
				.select(GroupByEntry::values);

		final List<Iterable<Person>> personsList = query.toList();
		final int                    count       = query.count();
		final Iterable<Person>       persons     = new SelectManyIterable<>(personsList);

		Assert.assertTrue(personsList.size() == count);

		Utils.print(persons, count, "testGroupByThenFlatten(groupBy(age), select(Person)");
	}

	private static void assignRandomState(List<Person> persons) {
		final Random rand = new Random(System.currentTimeMillis());

		final UnitedStates[] states = UnitedStates.values();

		for (Person person : persons) {
			final int index = rand.nextInt(states.length);
			person.setState(states[ index ]);
		}

		final Person personFrom = persons.get(rand.nextInt(persons.size()));
		final Person personTo   = persons.get(rand.nextInt(persons.size()));
		personTo.setState(personFrom.getState());
	}
}
