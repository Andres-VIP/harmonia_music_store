package com.harmonia.store.service;

import com.harmonia.store.model.Instrument;
import com.harmonia.store.repository.InstrumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstrumentService {

    @Autowired
    private InstrumentRepository instrumentRepository;

    public List<Instrument> getAllInstruments() {
        return instrumentRepository.findAll();
    }

    public Instrument addInstrument(Instrument instrument) {
        return instrumentRepository.save(instrument);
    }
}
