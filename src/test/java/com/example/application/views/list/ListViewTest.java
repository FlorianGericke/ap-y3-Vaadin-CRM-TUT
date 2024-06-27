package com.example.application.views.list;

import com.example.application.data.Contact;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.ListDataProvider;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
public class ListViewTest {
    @Autowired
    private ListView listView;

    @Test
    public void fromShownWhenContactSelected() {
        Grid<Contact> grid = listView.grid;
        var firstContact = getFirstItem(grid);

        var form = listView.form;

        Assertions.assertFalse(form.isVisible());
        grid.asSingleSelect().setValue(firstContact);

        Assertions.assertTrue(form.isVisible());
        Assertions.assertEquals(firstContact.getFirstName(), form.firstName.getValue());
    }

    private Contact getFirstItem(Grid<Contact> grid) {
        return ((ListDataProvider<Contact>)grid.getDataProvider()).getItems().iterator().next();
    }
}
