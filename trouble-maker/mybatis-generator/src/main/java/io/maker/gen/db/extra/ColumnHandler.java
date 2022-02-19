package io.maker.gen.db.extra;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ColumnHandler {
	/**
	 * Test whether this <code>ColumnHandler</code> wants to handle a column
	 * targetted for a value type matching <code>propType</code>.
	 * @param propType The type of the target parameter.
	 * @return true is this property handler can/wants to handle this value; false
	 *         otherwise.
	 */
	boolean match(Class<?> propType);

	/**
	 * Do the work required to retrieve and store a column from
	 * <code>ResultSet</code> into something of type <code>propType</code>. This
	 * method is called only if this handler responded <code>true</code> after a
	 * call to {@link #match(Class)}.
	 * @param rs          The result set to get data from. This should be moved to
	 *                    the correct row already.
	 * @param columnIndex The position of the column to retrieve.
	 * @return The converted value or the original value if something doesn't work
	 *         out.
	 */
	Object apply(ResultSet rs, int columnIndex) throws SQLException;
}
