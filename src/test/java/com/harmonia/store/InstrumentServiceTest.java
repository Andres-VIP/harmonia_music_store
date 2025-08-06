package com.harmonia.store;

import com.harmonia.store.model.Instrument;
import com.harmonia.store.repository.InstrumentRepository;
import com.harmonia.store.service.InstrumentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class InstrumentServiceTest {

    @Test
    public void testGetAllInstruments() {
        InstrumentRepository mockRepo = Mockito.mock(InstrumentRepository.class);
        when(mockRepo.findAll()).thenReturn(Collections.singletonList(new Instrument()));
        InstrumentService service = new InstrumentService();
        service.getAllInstruments();
    }
}
