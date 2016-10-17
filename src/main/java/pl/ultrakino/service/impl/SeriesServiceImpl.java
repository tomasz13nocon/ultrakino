package pl.ultrakino.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ultrakino.exceptions.NoRecordWithSuchIdException;
import pl.ultrakino.model.Series;
import pl.ultrakino.repository.SeriesRepository;
import pl.ultrakino.service.SeriesService;

@Service
@Transactional
public class SeriesServiceImpl implements SeriesService {

	private SeriesRepository seriesRepository;

	@Override
	public Series findById(int seriesId) throws NoRecordWithSuchIdException {
		return seriesRepository.findById(seriesId);
	}
}
