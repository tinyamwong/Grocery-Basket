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

    // global lists
    private ArrayList<Recipe> mRecipeList;
    private HashMap<String, Double> mSelectedIngredients;

    // class vars for nav bar and frame
    private BottomNavigationView navigationView;

    // fragments
    private MealListFragment mealListFragment;
    private ShoppingListFragment shoppingListFragment;
    private SearchFragment searchFragment;

    // preferences for json storage
    private SharedPreferences mPrefs;
    private SharedPreferences.Editor preferenceEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // sets preferences
        mPrefs = getPreferences(MODE_PRIVATE);
        preferenceEditor = mPrefs.edit();

        // gets global lists from last time list was destroyed
        retrieveGlobalDataFromStorage();

        // init nav bar and frame
        navigationView = findViewById(R.id.main_nav);

        // init all three fragments
        mealListFragment = new MealListFragment();
        shoppingListFragment = new ShoppingListFragment();
        searchFragment = new SearchFragment();

        // set init fragment (if-else statement required as changing the portrait orientation changes onCreate / onDestroy)
        // default fragment is the MealList (if it is null)
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

        // event listener on nav bar click (either MealsList, or ShoppingList)
        navigationView.setOnNavigationItemSelectedListener(listener ->
        {
            if (listener.getItemId() == R.id.nav_meals && !mealListFragment.isVisible())
            {
                setFragment(mealListFragment);
                return true;
            }
            else if (listener.getItemId() == R.id.nav_shopping_list && !shoppingListFragment.isVisible())
            {
                setFragment(shoppingListFragment);
                return true;
            }
            return true;
        });
    }


    @Override
    protected void onPause()
    {
        //Store meals list and selected map's current state

        storeGlobalData();
        super.onPause();
    }

    /**
     * Sets fragment null case is for add buttons call of this method
     *
     * @param fragment
     */
    public void setFragment(Fragment fragment)
    {
        MealPrepPlannerApplication.setMainActivityFragment(fragment);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    /**
     * Stores meal list into JSON
     * Stores selected items into JSON
     */
    public void storeGlobalData()
    {
        Gson gson = new Gson();

        // transforms the ArrayLists into JSON Data.
        String recipeJSON = gson.toJson(mRecipeList);
        preferenceEditor.putString("recipeJSONData", recipeJSON);

        // selected ==> jason (lol leaving this typo here - Tin)
        String selectedJSON = gson.toJson(mSelectedIngredients);
        preferenceEditor.putString("selectedJSONData", selectedJSON);

        // commits the changes
        preferenceEditor.commit();
    }

    /**
     * Retrieves ArrayList from JSON
     * Retrieves selected items from JSON
     */
    public void retrieveGlobalDataFromStorage()
    {
        Gson gson = new Gson();

        mRecipeList = new ArrayList<>();
        mSelectedIngredients = new HashMap<>();

        // TODO this is broken...
//        if (mPrefs.contains("recipeJSONData"))
//        {
//            String mealsJSON = mPrefs.getString("recipeJSONData", "");
//            Type mealType = new TypeToken<Collection<Recipe>>() {}.getType();
//            mRecipeList = gson.fromJson(mealsJSON, mealType);
//        }
//        else
//        {
//            mRecipeList = new ArrayList<>();
//        }
//
//        if (mPrefs.contains("selectedJSONData"))
//        {
//            String selectedJSON = mPrefs.getString("selectedJSONData", "");
//            Type selectedType = new TypeToken<HashMap<String, Double>>() {}.getType();
//            mSelectedIngredients = gson.fromJson(selectedJSON, selectedType);
//        }
//        else
//        {
//            mSelectedIngredients = new HashMap<>();
//        }
    }


    // back button pressed = trace back
    // on first fragment = close app
    @Override
    public void onBackPressed()
    {
        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 1)
        {
            Log.v(LOGTAG, "onBackPressed......Called       COUNT = " + count);
            this.finishAffinity();
        }
        else
        {
            Log.v(LOGTAG, "onBackPressed......Called       COUNT = " + count);
            getSupportFragmentManager().popBackStack();
        }
    }

    public ArrayList<Recipe> getmRecipeList()
    {
        return mRecipeList;
    }

    public void setmRecipeList(ArrayList<Recipe> mMealsList)
    {
        this.mRecipeList = mMealsList;
    }

    public HashMap<String, Double> getmSelectedIngredients()
    {
        return mSelectedIngredients;
    }

    public void setmSelectedIngredients(HashMap<String, Double> mSelectedIngredients)
    {
        this.mSelectedIngredients = mSelectedIngredients;
    }

    public SearchFragment getSearchFragment()
    {
        return searchFragment;
    }
}
