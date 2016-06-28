package de.fhg.iais.roberta.ast.syntax.expr;

import org.junit.Test;

import de.fhg.iais.roberta.testutil.Helper;

public class MathOnListsTest {
    @Test
    public void mathOnListSum() throws Exception {
        final String a = "ArraySum({5,3,2})";

        Helper.assertCodeIsOk(a, "/syntax/math/math_on_list_sum.xml");
    }

    @Test
    public void mathOnListMin() throws Exception {
        final String a = "ArrayMin({5,3,2})";

        Helper.assertCodeIsOk(a, "/syntax/math/math_on_list_min.xml");
    }

    @Test
    public void mathOnListMax() throws Exception {
        final String a = "ArrayMax({5,3,2})";

        Helper.assertCodeIsOk(a, "/syntax/math/math_on_list_max.xml");
    }

    @Test
    public void mathOnListAverage() throws Exception {
        final String a = "ArrayMean({5,3,2})";

        Helper.assertCodeIsOk(a, "/syntax/math/math_on_list_average.xml");
    }

    @Test
    public void mathOnListMedian() throws Exception {
        final String a = "ArrayMedian({5,3,2})";

        Helper.assertCodeIsOk(a, "/syntax/math/math_on_list_median.xml");
    }

    @Test
    public void mathOnListStandardDeviatioin() throws Exception {
        final String a = "ArrayStandardDeviatioin({5,3,2})";

        Helper.assertCodeIsOk(a, "/syntax/math/math_on_list_std_dev.xml");
    }

    @Test
    public void mathOnListRandom() throws Exception {
        final String a = "ArrayRand({5,3,2})";

        Helper.assertCodeIsOk(a, "/syntax/math/math_on_list_random.xml");
    }

    @Test
    public void mathOnListMode() throws Exception {
        final String a = "ArrayMode({5,3,2})";

        Helper.assertCodeIsOk(a, "/syntax/math/math_on_list_mode.xml");
    }

}
