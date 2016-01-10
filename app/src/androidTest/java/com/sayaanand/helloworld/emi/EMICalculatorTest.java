package com.sayaanand.helloworld.emi;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Nandkishore.Powar on 29/12/2015.
 */
public class EMICalculatorTest extends TestCase {

    private EMICalculator emiCalculator;

    @Before
    public void setUp() throws Exception {
        emiCalculator = new EMICalculator();
    }

    @After
    public void tearDown() throws Exception {

    }



    public void testCalculate() throws Exception {
        double principal = 2500000;
        double interest = 10.10;
        double tenor = 15.0;
        double emi = emiCalculator.calculate(principal, interest, tenor);
        Assert.assertEquals(  27018.28, emi, 0.01);
    }
}