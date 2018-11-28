package comp3111.webscraper;

import org.junit.Test;
import static org.junit.Assert.*;

public class controller_task4_5test
{
	@Test
	public void testSetTitle() {
		Item i = new Item();
		i.setTitle("ABCDE");
		assertEquals(i.getTitle(), "ABCDE");
	}

}
