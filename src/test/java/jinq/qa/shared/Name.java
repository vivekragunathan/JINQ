package jinq.qa.shared;

import data.TextHelpers;
import misc.DateTimeHelpers;

public class Name implements Comparable<Name> {
	public final String firstName;
	public final String lastName;

	public Name(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return String.format("%s %s", firstName, lastName);
	}

	@Override
	public int compareTo(Name other) {
		if (other == null) {
			return -1;
		}

		final String fullName = firstName + " " + lastName;
		return fullName.compareTo(other.firstName + " " + other.lastName);
	}

	public static Name deriveName(String name) {
		final String[] parts = name.split(" ");
		int writeIndex  = 0;

		for (int index = 0; index < parts.length; ++index) {
			final String part = parts[index];

			if (TextHelpers.isNotBlank(part)) {
				++writeIndex;
			}

			if (writeIndex <= index) {
				parts[writeIndex] = parts[index];
			}
		}

		final String firstName = parts.length > 0 && TextHelpers.isNotBlank(parts[0])
		                         ? parts[0]
		                         : "F" + DateTimeHelpers.getLast4OfTicks();

		final String lastName  = parts.length > 1 && TextHelpers.isNotBlank(parts[1])
		                         ? parts[1]
		                         : "L" + DateTimeHelpers.getLast4OfTicks();

		return new Name(firstName, lastName);
	}
}