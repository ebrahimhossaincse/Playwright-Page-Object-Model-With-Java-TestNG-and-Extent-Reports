package ebrahim.hossain.sqa.utilities;

import java.io.IOException;
import com.microsoft.playwright.Locator;
import ebrahim.hossain.sqa.basedriver.BaseDriver;

public class CommonMethods extends BaseDriver {

	public void fillField(Locator field, String value) throws IOException {
		field.fill(value);
	}
}
