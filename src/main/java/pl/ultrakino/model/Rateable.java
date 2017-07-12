package pl.ultrakino.model;

import java.util.Set;

public interface Rateable {

	Set<Rating> getRatings();
	void setRatings(Set<Rating> ratings);

	Float getRating();
	void setRating(Float rating);

	Integer getTimesRated();
	void setTimesRated(Integer timesRated);

}
