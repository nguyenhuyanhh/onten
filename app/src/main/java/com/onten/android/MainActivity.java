package com.onten.android;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.BaseGameUtils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.onten.android.fragment.AllQuizResult;
import com.onten.android.fragment.Detailviewfragment2;
import com.onten.android.fragment.LoginFragment;
import com.onten.android.fragment.MainFragment;
import com.onten.android.fragment.SecurePreferences;
import com.onten.android.fragment.SignupFragment;

import java.lang.reflect.Method;


public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    NavigationView navigation;
    WebView myWebView;
    private RelativeLayout container;
    private Button nextButton, closeButton;
    private EditText findSearch;
    private FirebaseDatabase database;
    private DatabaseReference databaseRef;

    private static int RC_SIGN_IN = 9001;

    private boolean mResolvingConnectionFailure = false;
    private boolean mSignInClicked = false;
    public static GoogleApiClient mGoogleApiClient;
    private boolean mAutoStartSignInFlow = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initInstances();

        // Retrieve UI elements
        myWebView = (WebView) findViewById(R.id.webView1);

        // Initialize the WebView
        myWebView.getSettings().setJavaScriptEnabled(true);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);


        nextButton = (Button) findViewById(R.id.nextButton);
        closeButton = (Button) findViewById(R.id.closeButton);
        findSearch = (EditText) findViewById(R.id.findSearch);
        findSearch.setSingleLine(true);
        findSearch.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && ((keyCode == KeyEvent.KEYCODE_ENTER))) {
                    myWebView.findAll(findSearch.getText().toString());

                    try {
                        // Can't use getMethod() as it's a private method
                        for (Method m : WebView.class.getDeclaredMethods()) {
                            if (m.getName().equals("setFindIsUp")) {
                                m.setAccessible(true);
                                m.invoke(myWebView, true);
                                break;
                            }
                        }

                    } catch (Exception ignored) {

                    } finally {
                        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        // check if no view has focus:
                        View vv = getCurrentFocus();
                        if (vv != null) {
                            inputManager.hideSoftInputFromWindow(v.getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                        }
                    }
                }
                return false;
            }
        });

        // Create the Google Api Client with access to the Play Games services
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                // add other APIs and scopes here as needed
                .build();

        nextButton.setOnClickListener(this);
        closeButton.setOnClickListener(this);
        changeFragment(new MainFragment());

    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    private void initInstances() {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, R.string.hello_world, R.string.hello_world);
        drawerLayout.setDrawerListener(drawerToggle);

        navigation = (NavigationView) findViewById(R.id.navigation_view);

        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                // Handle navigation view item clicks here.
                int id = menuItem.getItemId();

                if (id == R.id.navigation_view_category_3) {

                    navigation.getMenu().clear();
                    navigation.inflateMenu(R.menu.navigation_submenu_quiz);

                } else if (id == R.id.back_to_menu) {

                    navigation.getMenu().clear();
                    navigation.inflateMenu(R.menu.navigation_items);

                }
                if (id == R.id.logout) {
                    SecurePreferences.savePreferences(getApplicationContext(), "isLogin", false);
                    Intent intent = new Intent(getApplicationContext(), MainLoginActivity.class);
                    startActivity(intent);
                    MainActivity.this.finish();

                }
                if (id == R.id.navigation_view_category_1) {
                    navigation.getMenu().clear();
                    navigation.inflateMenu(R.menu.navigation_submenu_content);

                } else if (id == R.id.back_to_menu) {
                    navigation.getMenu().clear();
                    navigation.inflateMenu(R.menu.navigation_items);
                }
                if (id == R.id.navigation_view_category_4) {
                    navigation.getMenu().clear();
                    navigation.inflateMenu(R.menu.navigation_submenu_mathematics);

                } else if (id == R.id.back_to_menu) {
                    navigation.getMenu().clear();
                    navigation.inflateMenu(R.menu.navigation_items);
                }

                if (id == R.id.navigation_view_category_5) {
                    navigation.getMenu().clear();
                    navigation.inflateMenu(R.menu.navigation_submenu_microprocessor);

                } else if (id == R.id.back_to_menu) {
                    navigation.getMenu().clear();
                    navigation.inflateMenu(R.menu.navigation_items);
                }

                if (id == R.id.navigation_view_category_6) {
                    navigation.getMenu().clear();
                    navigation.inflateMenu(R.menu.navigation_submenu_signals_and_systems);

                } else if (id == R.id.back_to_menu) {
                    navigation.getMenu().clear();
                    navigation.inflateMenu(R.menu.navigation_items);
                }

                switch (id) {

                    case R.id.navigation_item_2:
                        clearBackStack();
                        drawerLayout.closeDrawers();
                        changeFragment(new ContributorsActivity());
                        break;
                    case R.id.navigation_item_3:
                        clearBackStack();
                        drawerLayout.closeDrawers();
                        changeFragment(new EditorialBoardActivity());
                        break;
                    case R.id.navigation_item_4:
                        clearBackStack();
                        drawerLayout.closeDrawers();
                        changeFragment(new DisclaimersActivity());
                        break;
                    case R.id.navigation_item_5:
                        clearBackStack();
                        drawerLayout.closeDrawers();
                        changeFragment(new LoginFragment());
                        break;
                    case R.id.navigation_item_6:
                        clearBackStack();
                        drawerLayout.closeDrawers();
                        changeFragment(new SignupFragment());
                        break;
                    case R.id.navigation_item_9:
                        clearBackStack();
//                        drawerLayout.closeDrawers();
//                        changeFragment(new LeaderboardFragment());
                        drawerLayout.closeDrawers();
                        changeFragment(new AllQuizResult());
                        break;
                    case R.id.navigation_item_10:
                        clearBackStack();
//                        startActivity(new Intent(MainActivity.this, QuizActivity.class));
                        drawerLayout.closeDrawers();
                        Detailviewfragment2 detailviewfragment2 = new Detailviewfragment2();
                        Bundle bundle = new Bundle();
                        bundle.putString("qtype", getResources().getString(R.string.navigation_view_item_10));
                        detailviewfragment2.setArguments(bundle);
                        changeFragment(detailviewfragment2);
                        break;
                    case R.id.navigation_item_11:
                        clearBackStack();
//                        startActivity(new Intent(MainActivity.this, QuizActivity1.class));
                        drawerLayout.closeDrawers();
                        Detailviewfragment2 detailviewfragment3 = new Detailviewfragment2();
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("qtype", getResources().getString(R.string.navigation_view_item_11));
                        detailviewfragment3.setArguments(bundle1);
                        changeFragment(detailviewfragment3);
                        break;
                    case R.id.navigation_item_12:
                        clearBackStack();
                        drawerLayout.closeDrawers();
                        Detailviewfragment2 detailviewfragment4 = new Detailviewfragment2();
                        Bundle bundle2 = new Bundle();
                        bundle2.putString("qtype", getResources().getString(R.string.navigation_view_item_12));
                        detailviewfragment4.setArguments(bundle2);
                        changeFragment(detailviewfragment4);
                        break;
                    case R.id.navigation_item_13:
                        clearBackStack();
                        drawerLayout.closeDrawers();
                        Detailviewfragment2 detailviewfragment5 = new Detailviewfragment2();
                        Bundle bundle3 = new Bundle();
                        bundle3.putString("qtype", getResources().getString(R.string.navigation_view_item_13));
                        detailviewfragment5.setArguments(bundle3);
                        changeFragment(detailviewfragment5);
                        break;
                    case R.id.navigation_item_14:
                        clearBackStack();
                        drawerLayout.closeDrawers();
                        Detailviewfragment2 detailviewfragment6 = new Detailviewfragment2();
                        Bundle bundle4 = new Bundle();
                        bundle4.putString("qtype", getResources().getString(R.string.navigation_view_item_14));
                        detailviewfragment6.setArguments(bundle4);
                        changeFragment(detailviewfragment6);
                        break;
                    case R.id.navigation_item_16:
                        clearBackStack();
                        drawerLayout.closeDrawers();
                        changeFragment(new AllQuizResult());
                        break;
                    case R.id.navigation_item_100:
                        clearBackStack();
                        drawerLayout.closeDrawers();
                        changeFragment(new ComplexNumbersActivity());
                        break;
                    case R.id.navigation_item_101:
                        clearBackStack();
                        drawerLayout.closeDrawers();
                        changeFragment(new ComplexVariablesActivity());
                        break;
                    case R.id.navigation_item_102:
                        clearBackStack();
                        drawerLayout.closeDrawers();
                        changeFragment(new DeterminantsActivity());
                        break;
                    case R.id.navigation_item_103:
                        clearBackStack();
                        drawerLayout.closeDrawers();
                        changeFragment(new DifferentiationAndIntegrationActivity());
                        break;
                    case R.id.navigation_item_104:
                        clearBackStack();
                        drawerLayout.closeDrawers();
                        changeFragment(new GreatestCommonDivisorActivity());
                        break;
                    case R.id.navigation_item_105:
                        clearBackStack();
                        drawerLayout.closeDrawers();
                        changeFragment(new LeastCommonMultipleActivity());
                        break;
                    case R.id.navigation_item_106:
                        clearBackStack();
                        drawerLayout.closeDrawers();
                        changeFragment(new LinearSimultaneousEquationsActivity());
                        break;
                    case R.id.navigation_item_107:
                        clearBackStack();
                        drawerLayout.closeDrawers();
                        changeFragment(new MatricesActivity());
                        break;
                    case R.id.navigation_item_108:
                        clearBackStack();
                        drawerLayout.closeDrawers();
                        changeFragment(new ModuloOperationActivity());
                        break;
                    case R.id.navigation_item_109:
                        clearBackStack();
                        drawerLayout.closeDrawers();
                        changeFragment(new OperationsOnComplexNumbersActivity());
                        break;
                    case R.id.navigation_item_110:
                        clearBackStack();
                        drawerLayout.closeDrawers();
                        changeFragment(new PolynomialsActivity());
                        break;
                    case R.id.navigation_item_111:
                        clearBackStack();
                        drawerLayout.closeDrawers();
                        changeFragment(new SeriesActivity());
                        break;
                    case R.id.navigation_item_112:
                        clearBackStack();
                        drawerLayout.closeDrawers();
                        changeFragment(new TrigonometricFunctionsAndIdentitiesActivity());
                        break;
                    case R.id.navigation_item_113:
                        clearBackStack();
                        drawerLayout.closeDrawers();
                        changeFragment(new VectorsActivity());
                        break;
                    case R.id.navigation_item_200:
                        clearBackStack();
                        drawerLayout.closeDrawers();
                        changeFragment(new NumberSystemsActivity());
                        break;
                    case R.id.navigation_item_201:
                        clearBackStack();
                        drawerLayout.closeDrawers();
                        changeFragment(new NumberConversionActivity());
                        break;
                    case R.id.navigation_item_202:
                        clearBackStack();
                        drawerLayout.closeDrawers();
                        changeFragment(new FloatingPointNumberActivity());
                        break;
                    case R.id.navigation_item_203:
                        clearBackStack();
                        drawerLayout.closeDrawers();
                        changeFragment(new NumberRangeActivity());
                        break;
                    case R.id.navigation_item_204:
                        clearBackStack();
                        drawerLayout.closeDrawers();
                        changeFragment(new AssemblerDirectivesActivity());
                        break;
                    case R.id.navigation_item_205:
                        clearBackStack();
                        drawerLayout.closeDrawers();
                        changeFragment(new GettingStartedWithKeiluVisionActivity());
                        break;
                    case R.id.navigation_item_206:
                        clearBackStack();
                        drawerLayout.closeDrawers();
                        changeFragment(new LoadAndStoreInstructionsActivity());
                        break;
                    case R.id.navigation_item_207:
                        clearBackStack();
                        drawerLayout.closeDrawers();
                        changeFragment(new ARMProgrammingMOVInstructionActivity());
                        break;
                    case R.id.navigation_item_208:
                        clearBackStack();
                        drawerLayout.closeDrawers();
                        changeFragment(new ARMProgrammingLDRPseudoInstructionActivity());
                        break;
                    case R.id.navigation_item_209:
                        clearBackStack();
                        drawerLayout.closeDrawers();
                        changeFragment(new ARMProgrammingLoopActivity());
                        break;
                    case R.id.navigation_item_210:
                        clearBackStack();
                        drawerLayout.closeDrawers();
                        changeFragment(new LDMAndSTMInstructionsActivity());
                        break;
                    case R.id.navigation_item_211:
                        clearBackStack();
                        drawerLayout.closeDrawers();
                        changeFragment(new PushAndPopInStackInstructionsActivity());
                        break;

                    case R.id.navigation_item_212:
                        clearBackStack();
                        drawerLayout.closeDrawers();
                        changeFragment(new ARMProgrammingExceptionVectorTableActivity());
                        break;
                    case R.id.navigation_item_300:
                        clearBackStack();
                        drawerLayout.closeDrawers();
                        changeFragment(new BlockDiagramsActivity());
                        break;
                    case R.id.navigation_item_301:
                        clearBackStack();
                        drawerLayout.closeDrawers();
                        changeFragment(new CausalityConditionActivity());
                        break;
                    case R.id.navigation_item_302:
                        clearBackStack();
                        drawerLayout.closeDrawers();
                        changeFragment(new CausalityConditionActivity());
                        break;
                    case R.id.navigation_item_303:
                        clearBackStack();
                        drawerLayout.closeDrawers();
                        changeFragment(new ContinuousTimeFourierTransformActivity());
                        break;
                    case R.id.navigation_item_304:
                        clearBackStack();
                        drawerLayout.closeDrawers();
                        changeFragment(new ContinuousTimeFourierTransformPropertiesActivity());
                        break;
                    case R.id.navigation_item_305:
                        clearBackStack();
                        drawerLayout.closeDrawers();
                        changeFragment(new ConvolutionActivity());
                        break;
                    case R.id.navigation_item_306:
                        clearBackStack();
                        drawerLayout.closeDrawers();
                        changeFragment(new DiscreteFourierTransformActivity());
                        break;
                    case R.id.navigation_item_307:
                        clearBackStack();
                        drawerLayout.closeDrawers();
                        changeFragment(new DiscreteTimeFourierSeriesActivity());
                        break;
                    case R.id.navigation_item_308:
                        clearBackStack();
                        drawerLayout.closeDrawers();
                        changeFragment(new DiscreteTimeFourierTransformActivity());
                        break;
                    case R.id.navigation_item_309:
                        clearBackStack();
                        drawerLayout.closeDrawers();
                        changeFragment(new DiscreteTimeFourierTransformPropertiesActivity());
                        break;
                    case R.id.navigation_item_310:
                        clearBackStack();
                        drawerLayout.closeDrawers();
                        changeFragment(new FrequencyResponseActivity());
                        break;
                    case R.id.navigation_item_311:
                        clearBackStack();
                        drawerLayout.closeDrawers();
                        changeFragment(new GibbsPhenomenonActivity());
                        break;
                    case R.id.navigation_item_312:
                        clearBackStack();
                        drawerLayout.closeDrawers();
                        changeFragment(new LaplaceTransformPropertiesActivity());
                        break;
                    case R.id.navigation_item_313:
                        clearBackStack();
                        drawerLayout.closeDrawers();
                        changeFragment(new PartialFractionExpansionActivity());
                        break;
                    case R.id.navigation_item_314:
                        clearBackStack();
                        drawerLayout.closeDrawers();
                        changeFragment(new StabilityConditionActivity());
                        break;
                    case R.id.navigation_item_315:
                        clearBackStack();
                        drawerLayout.closeDrawers();
                        changeFragment(new TransferFunctionActivity());
                        break;
                    case R.id.navigation_item_316:
                        clearBackStack();
                        drawerLayout.closeDrawers();
                        changeFragment(new zTransformActivity());
                        break;
                    case R.id.navigation_item_317:
                        clearBackStack();
                        drawerLayout.closeDrawers();
                        changeFragment(new zTransformPropertiesActivity());
                        break;
                }
                return false;
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the state of the WebView
        myWebView.saveState(outState);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();

        // Restore the state of the WebView
        myWebView.restoreState(savedInstanceState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        //return true;

        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.searchview_menu, menu);

        /*MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search");
        int searchPlateId = searchView.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
        View searchPlate = searchView.findViewById(searchPlateId);
        if (searchPlate!=null) {
            searchPlate.setBackgroundColor(Color.DKGRAY);
            int searchTextId = searchPlate.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
            TextView searchText = (TextView) searchPlate.findViewById(searchTextId);
            if (searchText!=null) {
                searchText.setTextColor(Color.WHITE);
                searchText.setHintTextColor(Color.WHITE);
            }
        }*/
        return true;

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item))
            return true;

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        //return super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.action_search:
                search();
                return true;
        }
        return true;

    }


    public void search() {
        container = (RelativeLayout) findViewById(R.id.layoutId);

        if (container.getVisibility() == RelativeLayout.GONE) {
            container.setVisibility(RelativeLayout.VISIBLE);
        } else if (container.getVisibility() == RelativeLayout.VISIBLE) {
            container.setVisibility(RelativeLayout.GONE);
        }

    }

    @Override
    public void onClick(View v) {

        if (v == nextButton) {
            myWebView.findNext(true);
        } else if (v == closeButton) {
            container.setVisibility(RelativeLayout.GONE);
            myWebView.clearMatches();
        }

    }


    public void changeFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_replaceFrame, fragment, fragment.getClass().getName())
                .addToBackStack(fragment.getClass().getName()).commit();
    }

    public void clearBackStack() {
        int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        if (backStackEntryCount >= 1) {
            for (int i = 1; i < backStackEntryCount; i++) {
                FragmentManager.BackStackEntry first = getSupportFragmentManager().getBackStackEntryAt(i);
                getSupportFragmentManager().popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == RC_SIGN_IN) {
            mSignInClicked = false;
            mResolvingConnectionFailure = false;
            if (resultCode == RESULT_OK) {
                mGoogleApiClient.connect();
            } else {
                // Bring up an error dialog to alert the user that sign-in
                // failed. The R.string.signin_failure should reference an error
                // string in your strings.xml file that tells the user they
                // could not be signed in, such as "Unable to sign in."
                BaseGameUtils.showActivityResultError(this,
                        requestCode, resultCode, R.string.signin_failure);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //connection cehck

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {
        // Attempt to reconnect
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (mResolvingConnectionFailure) {
            // already resolving
            return;
        }

        // if the sign-in button was clicked or if auto sign-in is enabled,
        // launch the sign-in flow
        if (mSignInClicked || mAutoStartSignInFlow) {
            mAutoStartSignInFlow = false;
            mSignInClicked = false;
            mResolvingConnectionFailure = true;


            if (!BaseGameUtils.resolveConnectionFailure(this,
                    mGoogleApiClient, connectionResult,
                    RC_SIGN_IN, getString(R.string.signin_other_error))) {
                mResolvingConnectionFailure = false;
            }
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        MainActivity.this.finish();
    }
}
