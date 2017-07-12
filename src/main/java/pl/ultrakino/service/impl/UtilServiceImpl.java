package pl.ultrakino.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ultrakino.repository.UtilRepository;
import pl.ultrakino.service.UtilService;

@Service
@Transactional
public class UtilServiceImpl implements UtilService {

	private UtilRepository utilRepository;

	@Autowired
	public UtilServiceImpl(UtilRepository utilRepository) {
		this.utilRepository = utilRepository;
	}

	@Override
	public <T> T merge(T t) {
		return utilRepository.merge(t);
	}
}
