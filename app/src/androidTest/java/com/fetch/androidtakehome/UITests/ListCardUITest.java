package com.fetch.androidtakehome.UITests;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.fetch.androidtakehome.Adapter.ListIDAdapter;
import com.fetch.androidtakehome.Model.Item;
import com.fetch.androidtakehome.R;
import com.fetch.androidtakehome.View.MainActivity;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class ListCardUITest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    private ListIDAdapter adapter;
    private RecyclerView recyclerView;

    // Custom ViewAction to click on a child view within a RecyclerView item
    protected static ViewAction clickOnViewChild(final int id) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isClickable();
            }

            @Override
            public String getDescription() {
                return "Click on a child view with specified id.";
            }

            @Override
            public void perform(UiController uiController, View view) {
                View v = view.findViewById(id);
                v.performClick();
            }
        };
    }

    // Set up the RecyclerView and Adapter with mock data before each test
    @Before
    public void setUp() {

        List<Item> items = Arrays.asList(
                new Item(1, 1, "Item 1"),
                new Item(2, 1, "Item 2"),
                new Item(15, 4, "Item 15"),
                new Item(8, 4, "Item 8"),
                new Item(3, 2, "Item 3"),
                new Item(47, 4, "Item 47"),
                new Item(5, 4, "Item 5")
        );


        activityScenarioRule.getScenario().onActivity(activity -> {
            recyclerView = activity.findViewById(R.id.recyclerview_main_listidcard);
            recyclerView.setLayoutManager(new LinearLayoutManager(activity));
            adapter = new ListIDAdapter(activity, items);  // Pass the context here
            recyclerView.setAdapter(adapter);
        });
    }

    // Test to check if the RecyclerView is displayed
    @Test
    public void testRecyclerViewIsDisplayed() {
        onView(withId(R.id.recyclerview_main_listidcard)).check(matches(isDisplayed()));
    }

    // Test to check the expand and collapse functionality of list items
    @Test
    public void testExpandCollapse() {
// Expand the first item and check if the nested RecyclerView is displayed
        onView(withId(R.id.recyclerview_main_listidcard))
                .perform(actionOnItemAtPosition(0, clickOnViewChild(R.id.imgbutton_listidcard_expand)))
                .check(matches(hasDescendant(allOf(withId(R.id.recyclerview_namecard), isDisplayed()))));

// Collapse the first item and check if the nested RecyclerView is not displayed
        onView(withId(R.id.recyclerview_main_listidcard))
                .perform(actionOnItemAtPosition(0, clickOnViewChild(R.id.imgbutton_listidcard_expand)))
                .check(matches(hasDescendant(allOf(withId(R.id.recyclerview_namecard), not(isDisplayed())))));
    }

    // Test to check if the list titles are displayed correctly
    @Test
    public void testListTitle() {

        onView(withId(R.id.recyclerview_main_listidcard))
                .perform(scrollToPosition(0))
                .check(matches(hasDescendant(withText("List 1"))));

        onView(withId(R.id.recyclerview_main_listidcard))
                .perform(scrollToPosition(1))
                .check(matches(hasDescendant(withText("List 2"))));

        onView(withId(R.id.recyclerview_main_listidcard))
                .perform(scrollToPosition(2))
                .check(matches(hasDescendant(withText("List 4"))));
    }
}
