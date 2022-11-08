package com.ninjaone.backendinterviewproject.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SampleServiceTest {
    /*public static final String ID = "12345";

    @Mock
    private SampleRepository sampleRepository;

    @InjectMocks
    private SampleService testObject;

    private Device sampleEntity;

    @BeforeEach
    void setup(){
        sampleEntity = new Device(ID, "value");
    }

    @Test
    void getSampleData() {
        when(sampleRepository.findById(ID)).thenReturn(Optional.of(sampleEntity));
        Optional<Device> sampleEntityOptional = testObject.getSampleEntity(ID);
        Device actualEntity = sampleEntityOptional.orElse(null);
        assert actualEntity != null;
        assertEquals(sampleEntity.getValue(), actualEntity.getValue());
    }

    @Test
    void saveSampleData() {
        when(sampleRepository.save(sampleEntity)).thenReturn(sampleEntity);
        assertEquals(sampleEntity, testObject.saveSampleEntity(sampleEntity));
    }

    @Test
    void deleteSampleData(){
        doNothing().when(sampleRepository).deleteById(ID);
        testObject.deleteSampleEntity(ID);
        Mockito.verify(sampleRepository, times(1)).deleteById(ID);
    }
    */
}
