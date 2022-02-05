package com.talha.sample.applicationwithtest;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(AndroidJUnit4.class)
public class MainActivityListViewTest {
    @Rule
    public ActivityScenarioRule<MainActivity> rule =
            new ActivityScenarioRule<MainActivity>(MainActivity.class);


    @Test
    public void testListViewAdapter() {
        ActivityScenario<MainActivity> scenerio = rule.getScenario();
        scenerio.onActivity(activity -> {
            View view = activity.findViewById(R.id.mainActivityListViewNames);
            Assert.assertThat(view, notNullValue());
            Assert.assertThat(view, instanceOf(ListView.class));
            ListView listView = (ListView) view;
            ListAdapter adapter = listView.getAdapter();
            Assert.assertThat(adapter, Matchers.notNullValue());
            Assert.assertThat(adapter, instanceOf(ArrayAdapter.class));

            Assert.assertThat(adapter.getCount(),greaterThan(2));
            Assert.assertThat(adapter.getCount(),equalTo(3));


        });

    }
}
