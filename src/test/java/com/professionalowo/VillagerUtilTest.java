package com.professionalowo;

import com.professionalowo.framework.RegistriesExtension;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(RegistriesExtension.class)
public class VillagerUtilTest {
    @Test
    public void isTemptItemTest_shouldBeRightItem(){
        assertTrue(VillagerUtil.isTemptItem().test(VillagerUtil.TEMPT_ITEM.getDefaultStack()));
    }
}
