package com.muates.springbootbookstore.service;

import com.muates.springbootbookstore.domain.Country;
import com.muates.springbootbookstore.repository.CountryRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class CountryService {

    private final CountryRepository countryRepository;

    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public List<Country> getAllCountries(){
        return countryRepository.findAll();
    }

    public Country getCountryById(Long id){
        return countryRepository.getById(id);
    }

    public void saveCountry(Country country){
        countryRepository.save(country);
    }

    public void updateCountryById(Long id, Country country){
        Country existCountry = getCountryById(id);

        if(existCountry == null){
            throw new NoSuchElementException("User with id" + id + " does not found!");
        }

        existCountry.setCountryName(country.getCountryName());

        countryRepository.save(existCountry);
    }

    public void deleteCountryById(Long id){
        countryRepository.deleteById(id);
    }
}
