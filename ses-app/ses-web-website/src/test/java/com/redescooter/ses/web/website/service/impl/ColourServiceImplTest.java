package com.redescooter.ses.web.website.service.impl;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.website.SesWebsiteApplicationTests;
import com.redescooter.ses.web.website.service.ColourService;
import com.redescooter.ses.web.website.vo.product.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ColourServiceImplTest extends SesWebsiteApplicationTests {

    @Autowired(required = true)
    private ColourService colourService;

    @Test
    void addColour() {

        AddColourEnter colour1 = new AddColourEnter();
        colour1.setColourName("BLACK C-S");
        colour1.setColour16("#2D2926");
        colour1.setUserId(0L);
        assertTrue(colourService.addColour(colour1));

        AddColourEnter colour2 = new AddColourEnter();
        colour2.setColourName("YB-404C-M");
        colour2.setColour16("#776E64");
        colour2.setUserId(0L);
        assertTrue(colourService.addColour(colour2));

        AddColourEnter colour3 = new AddColourEnter();
        colour3.setColourName("NH-432C-M");
        colour3.setColour16("#333F48");
        colour3.setUserId(0L);
        assertTrue(colourService.addColour(colour3));
    }

    @Test
    void modityColour() {
        ModityColourEnter colourEnter = new ModityColourEnter();
        colourEnter.setId(195728276606976L);
        colourEnter.setColourName("BLACK C-S");
        colourEnter.setColour16("#2D2926");
        colourEnter.setRemark("update");
        colourEnter.setUserId(0L);

        assertTrue(colourService.modityColour(colourEnter));
    }

    @Test
    void removeColour() {
        IdEnter enter = new IdEnter();
        enter.setId(195728276606976L);
        colourService.removeColour(enter);
    }

    @Test
    void getColourDetails() {
        IdEnter enter = new IdEnter();
        enter.setId(195728276606976L);
        ColourDetailsResult details = colourService.getColourDetails(enter);

        System.out.println(details.toString());
    }

    @Test
    void getColourList() {
        List<ColourDetailsResult> list = colourService.getColourList(new GeneralEnter());

        list.forEach(c -> {
            System.out.println(c.toString());
        });

        assertNotNull(list);
    }
}