package com.fetch.androidtakehome.Adapter;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.fetch.androidtakehome.Model.Item;
import com.fetch.androidtakehome.R;
import com.fetch.androidtakehome.View.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import static androidx.test.espresso.Espresso.onView;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.UiController;
import org.hamcrest.Matcher;

@RunWith(AndroidJUnit4.class)
public class ListIDAdapterUITest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    private ListIDAdapter adapter;
    private RecyclerView recyclerView;

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

        adapter = new ListIDAdapter(items);

        activityScenarioRule.getScenario().onActivity(activity -> {
            recyclerView = activity.findViewById(R.id.recyclerview_main_listidcard);
            recyclerView.setLayoutManager(new LinearLayoutManager(activity));
            recyclerView.setAdapter(adapter);
        });
    }

    @Test
    public void testRecyclerViewIsDisplayed() {
        onView(withId(R.id.recyclerview_main_listidcard)).check(matches(isDisplayed()));
    }

    @Test
    public void testExpandCollapse() {

        onView(withId(R.id.recyclerview_main_listidcard))
                .perform(actionOnItemAtPosition(0, clickOnViewChild(R.id.imgbutton_listidcard_expand)))
                .check(matches(hasDescendant(allOf(withId(R.id.recyclerview_namecard), isDisplayed()))));


        onView(withId(R.id.recyclerview_main_listidcard))
                .perform(actionOnItemAtPosition(0, clickOnViewChild(R.id.imgbutton_listidcard_expand)))
                .check(matches(hasDescendant(allOf(withId(R.id.recyclerview_namecard), not(isDisplayed())))));
    }

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

    private static ViewAction clickOnViewChild(final int id) {
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
}
