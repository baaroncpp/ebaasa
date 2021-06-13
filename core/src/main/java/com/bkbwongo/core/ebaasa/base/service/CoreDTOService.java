package com.bkbwongo.core.ebaasa.base.service;

import com.bkbwongo.core.ebaasa.base.dto.CountryDto;
import com.bkbwongo.core.ebaasa.base.dto.DistrictDto;
import com.bkbwongo.core.ebaasa.base.jpa.models.TCountry;
import com.bkbwongo.core.ebaasa.base.jpa.models.TDistrict;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author bkaaron
 * @created on 22/05/2021
 * @project ebaasa-sms
 */
@Service
public class CoreDTOService {

    @Autowired
    ModelMapper modelMapper;

    public TCountry convertDTOToTCountry(CountryDto countryDto){
        return modelMapper.map(countryDto, TCountry.class);
    }

    public CountryDto convertTCountryToDTO(TCountry tCountry){
        return modelMapper.map(tCountry, CountryDto.class);
    }

    public TDistrict convertDTOToTDistrict(DistrictDto districtDto){
        return modelMapper.map(districtDto, TDistrict.class);
    }

    public DistrictDto convertTDistrictToDTO(TDistrict tDistrict){
        return modelMapper.map(tDistrict, DistrictDto.class);
    }
}
