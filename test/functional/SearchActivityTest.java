package functional;

import android.test.ActivityInstrumentationTestCase2;

import com.barefoot.smsdigger.views.Search;
import com.jayway.android.robotium.solo.Solo;

public class SearchActivityTest extends ActivityInstrumentationTestCase2<Search> {
	
	private Solo solo;

	public SearchActivityTest(String pkg, Class<Search> activityClass) {
		super("com.barefoot.smsdigger.view", Search.class);
	}
	
	public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
	}
  
  public void testTextIsSaved() throws Exception {
	  	solo.enterText(1, "dude");
        solo.clickOnButton("Search");
        assertTrue(solo.searchText("No matching results found."));
  }

   @Override
   public void tearDown() throws Exception {

        try {
                solo.finalize();
        } catch (Throwable e) {

                e.printStackTrace();
        }
        getActivity().finish();
        super.tearDown();

  }
}
