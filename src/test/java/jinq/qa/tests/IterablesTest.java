package jinq.qa.tests;

import delegates.Func2;
import jodash.IterableArray;
import jinq.qa.shared.Numbers;
import jinq.qa.shared.Utils;
import jodash.Iterables;
import org.junit.Assert;
import org.junit.Test;

public class IterablesTest {

	private static Iterable<Integer> DEFAULT_INPUT_ITERABLE = new IterableArray<>(new Integer[] { 1, 2, 3, 4, 5 });

	private static final Func2<Integer, Integer, Integer> addFunc2 = (item, aggregate) -> {
		System.out.printf("%d + ", item);
		return aggregate == null ? 0 : item + aggregate;
	};

	private static final Func2<Integer, Integer, Integer> subtractFunc2 = (item, aggregate) -> {
		System.out.printf("%d - ", item);
		return aggregate == null ? item : aggregate - item;
	};

	@Test
	public void testFoldLeftAddition() throws Exception {
		final int folderInitial = 0;
		final int actual        = Iterables.foldLeft(DEFAULT_INPUT_ITERABLE, folderInitial, addFunc2);

		System.out.print(" => " + actual + "\n");

		final int expected = Numbers.sum(DEFAULT_INPUT_ITERABLE, folderInitial);

		Assert.assertEquals(
				String.format("Actual: %d, Expected: %d", actual, expected),
				expected,
				actual
		);
	}

	@Test
	public void testFoldRightSubtractionWithNullInitial() throws Exception {
		final Integer folderInitial = null;
		final int     actual        = Iterables.foldRight(DEFAULT_INPUT_ITERABLE, folderInitial, subtractFunc2);

		System.out.print(" => " + actual + "\n");

		final int expected = Numbers.diffRight(DEFAULT_INPUT_ITERABLE, folderInitial);

		Assert.assertEquals(
				String.format("Actual: %d, Expected: %d", actual, expected),
				expected,
				actual
		);
	}

	@Test
	public void testFoldRightSubtractionWithZeroInitial() throws Exception {
		final Integer           folderInitial = 0;
		final Iterable<Integer> iterable      = Utils.toList(Utils.toStack(DEFAULT_INPUT_ITERABLE));
		final int               actual        = Iterables.foldRight(iterable, folderInitial, subtractFunc2);

		System.out.print(" => " + actual + "\n");

		final int expected = Numbers.diffRight(iterable, folderInitial);

		Assert.assertEquals(
				String.format("Actual: %d, Expected: %d", actual, expected),
				expected,
				actual
		);
	}
}
