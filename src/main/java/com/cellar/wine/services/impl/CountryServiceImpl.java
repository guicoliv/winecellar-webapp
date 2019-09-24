package com.cellar.wine.services.impl;

import com.cellar.wine.models.Country;
import com.cellar.wine.repositories.CountryRepository;
import com.cellar.wine.services.CountryService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {

    @Inject
    private CountryRepository countryRepository;

    @Override
    public Country findById(Long aLong) {
        return countryRepository.findById(aLong).orElse(null);
    }

    @Override
    public Country save(Country object) {
        return countryRepository.save(object);
    }

    @Override
    public void delete(Country object) {
        countryRepository.delete(object);
    }

    @Override
    public Country findByLowerCaseName(String lcName) {
        return countryRepository.findByLowerCaseName(lcName);
    }

    @Override
    public List<Country> findWithRegions() {
        return countryRepository.findWithRegions();
    }
}
