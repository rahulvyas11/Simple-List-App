package com.fetch.androidtakehome.UITests;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.fetch.androidtakehome.UITests.ListCardUITest.clickOnViewChild;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.fetch.androidtakehome.Adapter.ListIDAdapter;
import com.fetch.androidtakehome.Model.Item;
import com.fetch.androidtakehome.R;
import com.fetch.androidtakehome.View.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

@RunWith(AndroidJUnit4.class)

public class NameRowUITest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    private ListIDAdapter adapter;
    private RecyclerView recyclerView;

    @Before
    public void setUp() {
        List<Item> items = Arrays.asList(
                new Item(1, 1, "Item 1"),
                new Item(2, 1, "Item 2"),
                new Item(3, 2, "Item 3"),
                new Item(4, 2, "Item 4"),
                new Item(5, 3, "Item 5"),
                new Item(6, 3, "Item 6")
        );

        // Initialize the RecyclerView and Adapter in the activity
        activityScenarioRule.getScenario().onActivity(activity -> {
            recyclerView = activity.findViewById(R.id.recyclerview_main_listidcard);
            if (recyclerView != null) {
                recyclerView.setLayoutManager(new LinearLayoutManager(activity));
                adapter = new ListIDAdapter(activity, items);  // Pass the context here
                recyclerView.setAdapter(adapter);
            }
        });
    }

    // Test to check if the RecyclerView is displayed
    @Test
    public void testRecyclerViewIsDisplayed() {
        onView(withId(R.id.recyclerview_main_listidcard)).check(matches(isDisplayed()));
    }

    // Test to check the contents of the expanded items
    @Test
    public void testItemContents() {
        onView(withId(R.id.recyclerview_main_listidcard))
                .perform(actionOnItemAtPosition(0, clickOnViewChild(R.id.imgbutton_listidcard_expand)))
                .check(matches(hasDescendant(withText("Item 1"))))
                .check(matches(hasDescendant(withText("Item 2"))));

        onView(withId(R.id.recyclerview_main_listidcard))
                .perform(actionOnItemAtPosition(1, clickOnViewChild(R.id.imgbutton_listidcard_expand)))
                .check(matches(hasDescendant(withText("Item 3"))))
                .check(matches(hasDescendant(withText("Item 4"))));

        onView(withId(R.id.recyclerview_main_listidcard))
                .perform(actionOnItemAtPosition(2, clickOnViewChild(R.id.imgbutton_listidcard_expand)))
                .check(matches(hasDescendant(withText("Item 5"))))
                .check(matches(hasDescendant(withText("Item 6"))));
    }
}
