package com.wooridreamcardream.meaningout.service;

import com.wooridreamcardream.meaningout.repository.CarRepository;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class CarServiceTest {
    @InjectMocks
    private CarService carService;

    @Mock
    private CarRepository carRepository;

    @Test
    public void 자동차_저장() {
        //given

        //mocking
        //when
        //then
    }
}
