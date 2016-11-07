package pl.ultrakino.model;

import java.util.List;

public interface Rateable {

	List<Rating> getRatings();
	void setRatings(List<Rating> ratings);

	Float getRating();
	void setRating(Float rating);

	Integer getTimesRated();
	void setTimesRated(Integer timesRated);

}
