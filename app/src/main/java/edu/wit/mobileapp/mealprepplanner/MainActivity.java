package edu.wit.mobileapp.mealprepplanner;

import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity
{
    private final String LOGTAG = "MYAPP";

    //global lists
    private ArrayList<Meal> mMealsList;
    private HashMap<String, Integer> mSelectedIngredients;

    //class vars for nav bar and frame
    private BottomNavigationView navigationView;

    // Both fragments (MealListFragment,
    private MealListFragment mealListFragment;
    private ShoppingListFragment shoppingListFragment;
    private SearchFragment searchFragment;

    //Preferences for json storage
    private SharedPreferences mPrefs;
    private SharedPreferences.Editor preferenceEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set preferences
        mPrefs = getPreferences(MODE_PRIVATE);
        preferenceEditor = mPrefs.edit();

        //Get global lists from last time list was destroyed
        retrieveGlobalDataFromStorage();


        //init nav bar and frame
        navigationView = findViewById(R.id.main_nav);

        //init both fragments
        mealListFragment = new MealListFragment();
        shoppingListFragment = new ShoppingListFragment();
        searchFragment = new SearchFragment();


        // XXX START - database sample code

        /*
            this technically should be in SearchActivity - not MainActivity
            this is again, just a sample

            create a Database object
            createDatabase (copies local db to system db)
            openDatabase (opens system db)

            then query as you please!

            also, remember -

            onPause() - db.close
            onResume() - db.open

            to prevent memory leak and such
        */

        // XXX DELETE THIS POST PRODUCTION - ONLY HERE FOR DEMO PURPOSES

        // Set up database
        Database database = new Database(this);

        try
        {
            database.createDatabase();
            database.openDataBase();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        // LIKE clause ignores cases, so no need to worry about the user lacking IQ when using the app
        database.getRecipes("baGeL");

        // close the database after not being used
        database.close();

        // END OF DEMO CODE

        // Set init fragment (if-else statement required as changing the portrait orientation changes onCreate / onDestroy)
        // Default fragment is the MealList (if it is null)
        if (MealPrepPlannerApplication.getMainActivityFragment() == null)
        {
            Log.v(LOGTAG, "Main Activity Fragment - NULL\n");
            setFragment(mealListFragment);
        }
        if (MealPrepPlannerApplication.getMainActivityFragment().getId() == R.id.nav_meals)
        {
            Log.v(LOGTAG, "Main Activity Fragment - MEAL\n");
            setFragment(mealListFragment);
        }
        else if (MealPrepPlannerApplication.getMainActivityFragment().getId() == R.id.nav_shopping_list)
        {
            Log.v(LOGTAG, "Main Activity Fragment - SHOPPING LIST\n");
            setFragment(shoppingListFragment);
        }


        // Event listener on nav bar click (either MealsList, or ShoppingList)
        navigationView.setOnNavigationItemSelectedListener(listener ->
        {
            if(listener.getItemId() == R.id.nav_meals && !mealListFragment.isVisible()){
                setFragment(mealListFragment);
                return true;
            }else if (listener.getItemId() == R.id.nav_shopping_list && !shoppingListFragment.isVisible()){
                setFragment(shoppingListFragment);
                return true;
            }
            return true;
        });
    }

    //Store meals list and selected map's current state
    @Override
    protected void onPause() {
        storeGlobalData();
        super.onPause();
    }

    // Sets fragment null case is for add buttons call of this method
    public void setFragment(Fragment fragment)
    {
        MealPrepPlannerApplication.setMainActivityFragment(fragment);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    //meal list -> json
    //Selected Items -> json
    public void storeGlobalData(){
        Gson gson = new Gson();
        //Transform the ArrayLists into JSON Data.
        String mealsJSON = gson.toJson(mMealsList);
        preferenceEditor.putString("mealsJSONData", mealsJSON);

        //selected ==> jason
        String selectedJSON = gson.toJson(mSelectedIngredients);
        preferenceEditor.putString("selectedJSONData", selectedJSON);

        //Commit the changes.
        preferenceEditor.commit();
    }

    //json -> array list
    //json -> selected items
    public void retrieveGlobalDataFromStorage(){
        Gson gson = new Gson();
        if(mPrefs.contains("mealsJSONData")){
            String mealsJSON = mPrefs.getString("mealsJSONData", "");
            Type mealType = new TypeToken<Collection<Meal>>() {}.getType();
            mMealsList = gson.fromJson(mealsJSON, mealType);
        }else {
            mMealsList = new ArrayList<>();
        }

        if(mPrefs.contains("selectedJSONData")){
            String selectedJSON = mPrefs.getString("selectedJSONData", "");
            Type selectedType = new TypeToken<HashMap<String, Integer>>() {}.getType();
            mSelectedIngredients = gson.fromJson(selectedJSON, selectedType);
        }
    }


    // back button pressed = trace back
    // on first fragment = close app
    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 1) {
            Log.v(LOGTAG,"onBackPressed......Called       COUNT = " + count);
            this.finishAffinity();
        } else {
            Log.v(LOGTAG,"onBackPressed......Called       COUNT = " + count);
            getSupportFragmentManager().popBackStack();
        }
    }

    public ArrayList<Meal> getmMealsList() {
        return mMealsList;
    }

    public void setmMealsList(ArrayList<Meal> mMealsList) {
        this.mMealsList = mMealsList;
    }

    public HashMap<String, Integer> getmSelectedIngredients() {
        return mSelectedIngredients;
    }

    public void setmSelectedIngredients(HashMap<String, Integer> mSelectedIngredients) {
        this.mSelectedIngredients = mSelectedIngredients;
    }

    public SearchFragment getSearchFragment() {
        return searchFragment;
    }
}
