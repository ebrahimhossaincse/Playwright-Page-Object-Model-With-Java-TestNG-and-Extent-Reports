package me.hossain.ebrahim.utilities;

import java.io.IOException;
import com.microsoft.playwright.Locator;

import me.hossain.ebrahim.basedriver.BaseDriver;

public class CommonMethods extends BaseDriver {

	public void fillField(Locator field, String value) throws IOException {
		field.fill(value);
	}
}
