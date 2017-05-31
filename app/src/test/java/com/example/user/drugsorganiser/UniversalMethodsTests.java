package com.example.user.drugsorganiser;

import android.support.v4.util.Pair;

import com.example.user.drugsorganiser.Shared.UniversalMethods;

import org.joda.time.DateTime;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UniversalMethodsTests {
    @Test
    public void dateTimeToString_isCorrect(){
        //Arrange
        DateTime dt = new DateTime(2017, 4, 2, 3, 45);
        DateTime dt2 = new DateTime(2001, 12, 5, 12, 3);
        //Act
        String str = UniversalMethods.DateTimeToString(dt);
        String str2 = UniversalMethods.DateTimeToString(dt2);

        //Assert
        assertEquals(str, "02.04.2017 3:45");
        assertEquals(str2, "05.12.2001 12:03");
    }

    @Test
    public void computeInterval_isCorrect(){

        //Act
        int ret = UniversalMethods.computeInterval(20, 1);
        int ret2 = UniversalMethods.computeInterval(2, 2);
        int ret3 = UniversalMethods.computeInterval(54, 0);

        //Assert
        assertEquals(ret, 20*60);
        assertEquals(ret2, 2*60*24);
        assertEquals(ret3, 54);
    }

    @Test
    public void translateInterval_isCorrect(){

        //Act
        Pair<Integer, Integer> p = UniversalMethods.translateInterval(46);
        Pair<Integer, Integer> p2 = UniversalMethods.translateInterval(180);
        Pair<Integer, Integer> p3 = UniversalMethods.translateInterval(172800);
        Pair<Integer, Integer> p4 = UniversalMethods.translateInterval(46);

        //Assert
        assertEquals((int)p.first, 0); //46 min
        assertEquals((int)p.second, 46);
        assertEquals((int)p2.first, 1); //3 h
        assertEquals((int)p2.second, 3);
        assertEquals((int)p3.first, 3); //4 months
        assertEquals((int)p3.second, 4);
    }
}