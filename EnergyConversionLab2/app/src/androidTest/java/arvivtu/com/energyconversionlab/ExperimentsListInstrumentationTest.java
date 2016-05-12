package arvivtu.com.energyconversionlab;


import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.widget.ListView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import domain.ECLab;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ExperimentsListInstrumentationTest {

    @Rule
    public ActivityTestRule<ExperimentsList> main = new ActivityTestRule<>(
            ExperimentsList.class);

    @Test
    public void testNumberOfItemsOnList() throws Exception
    {
        ListView listview = (ListView) main.getActivity().findViewById(R.id.experiments_lv);
        assertThat(listview.getCount(), is(5));
        // Counts the number of items on the list and asserts.
        // The number 5 should change as we add more items to the list
    }

    @Test
    public void testNameOfItemOnTheList() throws Exception
    {
        // testing the title on the list item
        ListView listview = (ListView) main.getActivity().findViewById(R.id.experiments_lv);
        ECLab eclab = (ECLab) listview.getItemAtPosition(0);
        assertThat(eclab.getDescription(), is("Junkers Gas Calorimeter"));
        eclab = (ECLab) listview.getItemAtPosition(1);
        assertThat(eclab.getDescription(), is("Boys Gas Calorimeter"));
        eclab = (ECLab) listview.getItemAtPosition(2);
        assertThat(eclab.getDescription(), is("Redwood Viscometer"));
        eclab = (ECLab) listview.getItemAtPosition(3);
        assertThat(eclab.getDescription(), is("Saybolt Viscometer"));
        eclab = (ECLab) listview.getItemAtPosition(4);
        assertThat(eclab.getDescription(), is("Performance test of 2 stroke, single cylinder petrol engine"));
    }

    @Test
    public void testOnItemClickEvent() throws Exception
    {
        // testing on click of list item
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        final ListView listview = (ListView) main.getActivity().findViewById(R.id.experiments_lv);

        instrumentation.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                int position = 0;
                listview.performItemClick(listview.getChildAt(position), position, listview.getAdapter().getItemId(position));
            }
        });

        Instrumentation.ActivityMonitor monitor = instrumentation.addMonitor(Experiment.class.getName(), null, false);
        Activity itemDetailActivity = instrumentation.waitForMonitorWithTimeout(monitor, 5000);
    }

}
