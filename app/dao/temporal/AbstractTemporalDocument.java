package dao.temporal;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.databind.ObjectMapper;

import play.libs.Json;
import dao.PersistentDocument;

/**
 * A class that holds any temporal data
 * @author Jerome Baudoux
 */
@Entity
@Table( name = "TEMPORAL_DATA" )
public abstract class AbstractTemporalDocument<T> implements PersistentDocument {
	
	/**
	 * Name of the document
	 */
	@Column(name = "NAME")
	protected String name;
	
	/**
	 * Type of document (class name)
	 */
	@Column(name = "TYPE")
	protected String type;
	
	/**
	 * Date of document
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "DATE")
	protected Date date;
	
	/**
	 * JSON document
	 */
	@Column(name = "CONTENT")
	protected String document;

	/**
	 * @return name of the document
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name name of the document
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return date of the document
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date date of the document
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return Object
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public T getDocument() throws Exception {
		return (T) Json.fromJson(Json.parse(this.document), Class.forName(this.type));
	}

	/**
	 * @param document object
	 * @throws Exception
	 */
	public void setDocument(T document) throws Exception {
		this.type = document.getClass().getName();
		this.document = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(document);
	}
}
