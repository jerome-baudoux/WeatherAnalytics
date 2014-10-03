package dao;

/**
 * A base class for DAOs
 * @author Jerome Baudoux
 *
 */
public abstract class AbstractDaoImpl {

	/**
	 * Create or update the document
	 * @param document document to create/update
	 */
	public void createOrUpdate(PersistentDocument document) {
		
	}

	/**
	 * Delete a document
	 * @param document document to delete
	 */
	public void delete(PersistentDocument document) {
		
	}
	
	/**
	 * Fetch a document
	 * @param document document to fetch
	 * @return document fetched
	 */
	public PersistentDocument get(PersistentDocument document) {
		return null;
	}
}
