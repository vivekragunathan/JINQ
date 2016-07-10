package misc;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public class DateTimeHelpers {

	public static LocalDateTime toLocalDateTime(Date date) {
		return LocalDateTime.ofInstant(
				(date == null ? new Date() : date).toInstant(),
				ZoneId.systemDefault()
		);
	}

	public static Date toDate(LocalDateTime ldt) {
		final ZonedDateTime zdt = ldt.atZone(ZoneId.systemDefault());
		return Date.from(zdt.toInstant());
	}

	public static long getDaysDifference(long epoch) {
		return getDaysDifference(new Date(epoch));
	}

	public static long getDaysDifference(long epochStart, long epochEnd) {
		if (epochStart < 0 || epochEnd < 0) {
			return -1;
		}

		return getDaysDifference(
				new Date(epochStart),
				epochEnd == 0 ? new Date() : new Date(epochEnd)
		);
	}

	public static long getDaysDifference(Date date) {
		return getDaysDifference(date, null);
	}

	public static long getDaysDifference(Date start, Date end) {
		end = end == null ? new Date() : end;
		return (end.getTime() - start.getTime()) / (1000 * 60 * 60 * 24);
	}

	public static long getLast4OfTicks() {
		final String ticks = String.valueOf(System.currentTimeMillis());

		final int totalLen   = ticks.length();
		final int numLen     = 4;
		int       tenthPower = numLen - 1;

		long finalValue = 0;

		for (int index = totalLen - numLen; index < totalLen; ++index) {
			final int number = ticks.charAt(index) - '0';
			finalValue += number * Math.pow(10, tenthPower--);
		}

		return finalValue < 1000 ? finalValue * 10 : finalValue;
	}
}
