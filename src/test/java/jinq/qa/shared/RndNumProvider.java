package jinq.qa.shared;

import java.util.Iterator;
import java.util.Random;

public class RndNumProvider implements Iterable<Integer>, Iterator<Integer> {

	private final int     maxIterations;
	private final boolean allowNulls;
	private final Random rnd = new Random(3571);

	private int count = 0;

	public RndNumProvider() {
		this(false);
	}

	public RndNumProvider(boolean allowNulls) {
		this(Integer.MAX_VALUE, allowNulls);
	}

	public RndNumProvider(int maxIterations, boolean allowNulls) {
		this.maxIterations = Math.abs(maxIterations);
		this.allowNulls = allowNulls;
	}

	@Override
	public Iterator<Integer> iterator() {
		return new RndNumProvider(this.maxIterations, this.allowNulls);
	}

	@Override
	public boolean hasNext() {
		return count < maxIterations;
	}

	@Override
	public Integer next() {
		final int r = rnd.nextInt();

		++count;

		return allowNulls
		       ? r % 2 == 0 ? rnd.nextInt() : null
		       : Integer.valueOf(rnd.nextInt());

	}
}
