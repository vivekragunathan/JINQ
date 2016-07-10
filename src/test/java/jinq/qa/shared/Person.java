package jinq.qa.shared;

import delegates.Func;
import delegates.Predicate;
import misc.UnitedStates;

import java.util.Comparator;

public class Person implements Comparable<Person> {

	private final Name         name;
	private final int          age;
	private final double       weight;
	private final boolean      available;
	private       UnitedStates state;

	public Person(Name name, double weight, boolean available) {
		this(name, 0, weight, UnitedStates.CALIFORNIA, available);
	}

	public Person(String name, int age, double weight) {
		this(new Name(name, "?"), age, weight, UnitedStates.CALIFORNIA, false);
	}

	public Person(Name name,
	              int age,
	              double weight,
	              UnitedStates state,
	              boolean available) {
		this.name = name;
		this.age = age;
		this.weight = weight;
		this.state = state;
		this.available = available;
	}

	@Override
	public String toString() {
		return String.format(
				"Name: %s, Age: %d, Weight: %f, Available: %s",
				name,
				age,
				weight,
				available ? "YES" : "NO"
		);
	}

	@Override
	public int compareTo(Person other) {
		return name.compareTo(other.name);
	}

	public Name getName() {
		return this.name;
	}

	public UnitedStates getState() {
		return state;
	}

	public boolean isAvailable() {
		return this.available;
	}

	public void setState(UnitedStates state) {
		this.state = state;
	}

	public static class KeySelectors {

		public static final Func<Person, Integer> AgeFunc = item -> item.age;

		public static final Func<Person, UnitedStates> StateFunc = item -> item.state;

		public static final Func<Person, Double> WeightFunc = item -> item.weight;
	}

	public static class ElementSelectors {

		public static final Func<Person, Name> NameFunc = person -> person.name;

		public static final Func<Person, Double> WeightFunc = person -> person.weight;

		public static final Func<Person, UnitedStates> StateFunc = person -> person.state;

		public static Func<Person, Boolean> AvailableFunc = item -> item.available;
	}

	public static class Predicates {

		public static final Predicate<Person> IsAvailable = person -> person.available;

		public static final Predicate<Person> IsEvenWeight = person -> person.weight % 2 == 0;

		public static final Predicate<Person> IsOddWeight = person -> person.weight % 2 != 0;
	}

	public static class Comparators {
		public static final Comparator<Person> ByName = (me, him) -> me.name.compareTo(him.name);

		public static final Comparator<Person> ByFirstName = (left, right) -> {
			if (left == null || right == null) {
				return -2;
			}

			return left.name.firstName.compareTo(right.name.firstName);
		};

		public static final Comparator<Person> ByWeightDesc = (o1, o2) -> {
			final Double w2 = o2.weight;
			final Double w1 = o1.weight;
			return w2.compareTo(w1);
		};
	}
}
