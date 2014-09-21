package engines.weatherfetcher;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Used to parse expected query from WorldWeatherOnline.com
 * 
 * API: api.worldweatheronline.com/free/v1/weather.ashx?q=Paris&format=json&num_of_days=5&cc=no&key=<KEY>
 * 
 * @author Jerome Baudoux
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WorldWeatherOnlineResponse {

    private WorldWeatherOnlineResponseData data;

    public WorldWeatherOnlineResponseData getData () {
        return data;
    }

    public void setData (WorldWeatherOnlineResponseData data) {
        this.data = data;
    }

    /**
     * DATA
     * @author Jerome Baudoux
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
	public static class WorldWeatherOnlineResponseData {

	    private WorldWeatherOnlineResponseRequest[] request;
	    private WorldWeatherOnlineResponseWeather[] weather;

	    public WorldWeatherOnlineResponseRequest[] getRequest () {
	        return request;
	    }

	    public void setRequest (WorldWeatherOnlineResponseRequest[] request) {
	        this.request = request;
	    }

	    public WorldWeatherOnlineResponseWeather[] getWeather () {
	        return weather;
	    }

	    public void setWeather (WorldWeatherOnlineResponseWeather[] weather) {
	        this.weather = weather;
	    }
	    
	    /**
	     * REQUEST
	     * @author Jerome Baudoux
	     */
	    @JsonIgnoreProperties(ignoreUnknown = true)
	    public static class WorldWeatherOnlineResponseRequest {
	    	
	        private String query;
	        private String type;

	        public String getQuery () {
	            return query;
	        }

	        public void setQuery (String query) {
	            this.query = query;
	        }

	        public String getType () {
	            return type;
	        }

	        public void setType (String type) {
	            this.type = type;
	        }
	    }
	    
	    /**
	     * Actual Weather
	     * @author Jerome Baudoux
	     */
	    @JsonIgnoreProperties(ignoreUnknown = true)
	    public static class WorldWeatherOnlineResponseWeather {
	    	
	        private String windspeedMiles;
	        private String winddirection;
	        private String date;
	        private String precipMM;
	        private String winddir16Point;
	        private String winddirDegree;
	        private String tempMinC;
	        private String windspeedKmph;
	        private String tempMaxC;
	        private String weatherCode;
	        private String tempMaxF;
	        private String tempMinF;

	        public String getWindspeedMiles () {
	            return windspeedMiles;
	        }

	        public void setWindspeedMiles (String windspeedMiles) {
	            this.windspeedMiles = windspeedMiles;
	        }

	        public String getWinddirection () {
	            return winddirection;
	        }

	        public void setWinddirection (String winddirection) {
	            this.winddirection = winddirection;
	        }

	        public String getDate () {
	            return date;
	        }

	        public void setDate (String date) {
	            this.date = date;
	        }

	        public String getPrecipMM () {
	            return precipMM;
	        }

	        public void setPrecipMM (String precipMM) {
	            this.precipMM = precipMM;
	        }

	        public String getWinddir16Point () {
	            return winddir16Point;
	        }

	        public void setWinddir16Point (String winddir16Point) {
	            this.winddir16Point = winddir16Point;
	        }

	        public String getWinddirDegree () {
	            return winddirDegree;
	        }

	        public void setWinddirDegree (String winddirDegree) {
	            this.winddirDegree = winddirDegree;
	        }

	        public String getTempMinC () {
	            return tempMinC;
	        }

	        public void setTempMinC (String tempMinC) {
	            this.tempMinC = tempMinC;
	        }

	        public String getWindspeedKmph () {
	            return windspeedKmph;
	        }

	        public void setWindspeedKmph (String windspeedKmph) {
	            this.windspeedKmph = windspeedKmph;
	        }

	        public String getTempMaxC () {
	            return tempMaxC;
	        }

	        public void setTempMaxC (String tempMaxC) {
	            this.tempMaxC = tempMaxC;
	        }

	        public String getWeatherCode () {
	            return weatherCode;
	        }

	        public void setWeatherCode (String weatherCode) {
	            this.weatherCode = weatherCode;
	        }

	        public String getTempMaxF () {
	            return tempMaxF;
	        }

	        public void setTempMaxF (String tempMaxF) {
	            this.tempMaxF = tempMaxF;
	        }

	        public String getTempMinF () {
	            return tempMinF;
	        }

	        public void setTempMinF (String tempMinF) {
	            this.tempMinF = tempMinF;
	        }
	    }
	}
}
