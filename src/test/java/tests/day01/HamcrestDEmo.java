package tests.day01;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class HamcrestDEmo {



    @Test
    public void test1(){
        String one = "text";
        String two = "text";
        String three = " text";
        // usually we use assertEquals

        // with hamcrest we have these methods:
        assertThat(one, equalTo(two)); // >> like assertEquals
        assertThat(one, is(two)); // same as first one
        assertThat(one, is(equalToCompressingWhiteSpace(three))); // deleting the spaces in between or in the middle, Ignore the spaces
//        assertThat(one, not(equalToCompressingWhiteSpace(three))); // not equal ...
//        assertThat(12, greaterThan(13));
        assertThat(12, lessThan(13));
        assertThat(Math.sqrt(-1), is(notANumber()));

        List<Integer> list = Arrays.asList(1,2,3,4);
        assertThat(list, hasSize(4));
        assertThat(list, hasItem(1));
        assertThat(list, contains(1,2,3,4));

        assertThat(list, everyItem(greaterThan(0)));


    }

    @Test
    public void test2(){


    }
}
