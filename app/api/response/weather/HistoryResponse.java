package api.response.weather;

import java.util.List;

import api.objects.ConsolidatedDays;
import api.objects.WeatherDay;
import api.response.ApiResponse;

/**
 * Response of a history request
 * @author Jerome Baudoux
 */
public class HistoryResponse extends ApiResponse<HistoryResponse> {

	protected List<WeatherDay> history;
	protected ConsolidatedDays consolidation;

	/**
	 * @return the history
	 */
	public List<WeatherDay> getHistory() {
		return history;
	}

	/**
	 * @param history the history to set
	 */
	public HistoryResponse setHistory(List<WeatherDay> history) {
		this.history = history;
		return this;
	}

	/**
	 * @return the consolidation
	 */
	public ConsolidatedDays getConsolidation() {
		return consolidation;
	}

	/**
	 * @param consolidation the consolidation to set
	 */
	public HistoryResponse setConsolidation(ConsolidatedDays consolidation) {
		this.consolidation = consolidation;
		return this;
	}
}
