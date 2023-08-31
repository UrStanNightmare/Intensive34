package ru.aston.oshchepkov_aa.task1;

import java.math.BigDecimal;

/**
 * Simple discount interface
 */
public interface Discountable {
    /**
     * Gets the discount percent. Must be in range of [0,1].
     * @return discount percent from 0.0 to 1.0
     */
    default BigDecimal getDiscountPercent(){
        return BigDecimal.ONE;
    }
}
