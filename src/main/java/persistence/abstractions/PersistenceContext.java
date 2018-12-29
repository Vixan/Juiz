package persistence.abstractions;

import shared.abstractions.Initializer;

/**
 * The database persistence context interface.
 * Any class implementing this interface will define a way to persist the data
 * in the database and load the initial data.
 *
 * @see Initializer
 */
public interface PersistenceContext extends Initializer {
}
