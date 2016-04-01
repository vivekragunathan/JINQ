package jinq.qa.tests;

import delegates.Func;
import jinq.Enumerable;
import jinq.GroupByEntry;
import jinq.IEnumerable;
import jinq.qa.shared.Name;
import jinq.qa.shared.Person;
import jinq.qa.shared.State;
import jinq.qa.shared.Utils;
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

		final IEnumerable<jinq.GroupByEntry<Integer, Person>> groupings = new Enumerable<>(this.persons)
				.groupBy(Person.KeySelectors.AgeFunc);

		Utils.print(null, "testSimpleGroupBy");

		for (jinq.GroupByEntry<Integer, Person> current : groupings) {
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

		Utils.print(null, "testGroupByAge");

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

		final Iterable<GroupByEntry<State, Name>> groupings = new Enumerable<>(persons)
				.groupBy(Person.KeySelectors.StateFunc, Person.ElementSelectors.NameFunc);

		Utils.print(null, "testGroupByState");

		for (GroupByEntry<State, Name> current : groupings) {

			final State          key      = current.getKey();
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

		final Func<GroupByEntry<Integer, Person>, Iterable<Person>> personsByAge = new Func<GroupByEntry<Integer, Person>, Iterable<Person>>() {
			@Override
			public Iterable<Person> apply(GroupByEntry<Integer, Person> item) {
				return item.values();
			}
		};


		final Func<GroupByEntry<Integer, Person>, Iterable<Name>> byName = new Func<GroupByEntry<Integer,Person>,Iterable<Name>>() {
			@Override
			public Iterable<Name> apply(GroupByEntry<Integer, Person> item) {
				return new Enumerable<>(item.values()).select(Person.ElementSelectors.NameFunc);
			}
		};

		final Iterable<Iterable<Name>> persons = new Enumerable<>(this.persons)
				.groupBy(Person.KeySelectors.AgeFunc)
				.select(byName);

		Utils.print(persons, "testGroupByThenSelect(groupBy(age), select(Person)");
	}

	private static void assignRandomState(List<Person> persons) {
		final Random rand = new Random(System.currentTimeMillis());

		final State[] states = State.values();

		for (Person person : persons) {
			final int index = rand.nextInt(states.length);
			person.setState(states[index]);
		}

		final Person personFrom = persons.get(rand.nextInt(persons.size()));
		final Person personTo   = persons.get(rand.nextInt(persons.size()));
		personTo.setState(personFrom.getState());
	}
}
