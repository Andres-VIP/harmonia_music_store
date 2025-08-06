package com.harmonia.store.controller;

import com.harmonia.store.model.Instrument;
import com.harmonia.store.service.InstrumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/instruments")
public class InstrumentController {

    @Autowired
    private InstrumentService instrumentService;

    @GetMapping
    public List<Instrument> getAllInstruments() {
        return instrumentService.getAllInstruments();
    }

    @PostMapping
    public Instrument addInstrument(@RequestBody Instrument instrument) {
        return instrumentService.addInstrument(instrument);
    }
}
