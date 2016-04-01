package jinq.qa.shared;

import delegates.Func;
import delegates.Predicate;

import java.util.Comparator;

public class Person implements Comparable<Person> {

	public final Name name;
	public final int age;
	public final double weight;
	private State state;
	public final boolean isAvailable;

	public Person(Name name, double weight, boolean isAvailable) {
		this(name, 0, weight, State.UNKNOWN, isAvailable);
	}

	public Person(String name, int age, double weight) {
		this(new Name(name, "?"), age, weight, State.UNKNOWN, false);
	}

	public Person(Name name,
	              int age,
	              double weight,
	              State state,
	              boolean isAvailable) {
		this.name = name;
		this.age = age;
		this.weight = weight;
		this.state = state;
		this.isAvailable = isAvailable;
	}

	@Override
	public String toString() {
		return String.format(
				"Name: %s, Age: %d, Weight: %f, Available: %s",
				name,
				age,
				weight,
				isAvailable ? "YES" : "NO"
		);
	}

	@Override
	public int compareTo(Person other) {
		return other == null ? -1 : name.compareTo(other.name);
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public static class KeySelectors {

		public static final Func<Person, Integer> AgeFunc = new Func<Person, Integer>() {
			@Override
			public Integer apply(Person item) {
				return item.age;
			}
		};

		public static final Func<Person, State> StateFunc = new Func<Person, State>() {
			@Override
			public State apply(Person item) {
				return item.state;
			}
		};

		public static final Func<Person, Double> WeightFunc = new Func<Person, Double>() {
			@Override
			public Double apply(Person item) {
				return item.weight;
			}
		};
	}

	public static class ElementSelectors {

		public static final Func<Person, Name> NameFunc = new Func<Person, Name>() {
			@Override
			public Name apply(Person person) {
				return person.name;
			}
		};

		public static final Func<Person, Double> WeightFunc = new Func<Person,Double>() {
			@Override
			public Double apply(Person person) {
				return person.weight;
			}
		};

		public static final Func<Person, State> StateFunc = new Func<Person, State>() {
			@Override
			public State apply(Person person) {
				return person.state;
			}
		};

		public static Func<Person, Boolean> AvailableFunc = new Func<Person, Boolean>() {
			@Override
			public Boolean apply(Person item) {
				return item.isAvailable;
			}
		};
	}

	public static class Predicates {

		public static final Predicate<Person> IsAvailable = new Predicate<Person>() {
			@Override
			public boolean evaluate(Person person) {
				return person.isAvailable;
			}
		};

		public static final Predicate<Person> IsEvenWeight = new Predicate<Person>() {
			@Override
			public boolean evaluate(Person person) {
				return person.weight % 2 == 0;
			}
		};

		public static final Predicate<Person> IsOddWeight = new Predicate<Person>() {
			@Override
			public boolean evaluate(Person person) {
				return person.weight % 2 != 0;
			}
		};
	}

	public static class Comparators {
		public static final Comparator<Person> ByName = new Comparator<Person>() {
			@Override
			public int compare(Person me, Person him) {
				return me.name.compareTo(him.name);
			}
		};

		public static final Comparator<Person> ByFirstName = new Comparator<Person>() {
			@Override
			public int compare(Person left, Person right) {
				if (left == null || right == null) {
					return -2;
				}

				return left.name.firstName.compareTo(right.name.firstName);
			}
		};

		public static final Comparator<Person> ByWeightDesc = new Comparator<Person>() {
			@Override
			public int compare(Person o1, Person o2) {
				final Double w2 = o2.weight;
				final Double w1 = o1.weight;
				return w2.compareTo(w1);
			}
		};

	}
}
