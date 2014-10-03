package dao;

/**
 * A base class for DAOs
 * @author Jerome Baudoux
 *
 */
public interface AbstractDao {

	/**
	 * Create or update the document
	 * @param document document to create/update
	 */
	void createOrUpdate(PersistentDocument document);

	/**
	 * Delete a document
	 * @param document document to delete
	 */
	void delete(PersistentDocument document);
	
	/**
	 * Fetch a document
	 * @param document document to fetch
	 * @return document fetched
	 */
	PersistentDocument get(PersistentDocument document);
}
