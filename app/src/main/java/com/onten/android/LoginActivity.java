package com.onten.android;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
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
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import java.lang.reflect.Method;

/**
 * Created by zhenting on 29/10/2015.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    NavigationView navigation;
    WebView myWebView;
    private RelativeLayout container;
    private Button nextButton, closeButton;
    private EditText findSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        initInstances();

        // Retrieve UI elements
        myWebView = (WebView) findViewById(R.id.webView1);

        // Initialize the WebView
        myWebView.getSettings().setJavaScriptEnabled(true);
        //myWebView.loadUrl("http://onten.eee.ntu.edu.sg/user");
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Load the URLs inside the WebView, not in the external web browser
        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                myWebView.loadUrl("javascript:(function() { " +
                        //hide the main menu and courses
                        "document.getElementsByClassName('region region-sidebar-first sidebar')[0].style.display='none'; " +
                        //hide the footer
                        "document.getElementsByTagName('footer')[0].style.display='none'; " +
                        //hide the survey feedback
                        "document.getElementsByClassName('webform-grid webform-grid-5 sticky-enabled')[0].style.display='none'; " +
                        //align and justify the text
                        "document.getElementsByClassName('node-content')[0].style.textAlign = 'justify'; " +
                        //spaces between the text height
                        "document.getElementsByClassName('node-content')[0].style.lineHeight = '200%'; " +
                        "})()");
            }
        });

        if (savedInstanceState == null) {
            // Load a page
            myWebView.loadUrl("http://onteneee.ddns.net/user");

            //myWebView.loadUrl("http://onten.eee.ntu.edu.sg/user");
        }

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

        nextButton.setOnClickListener(this);
        closeButton.setOnClickListener(this);

    }

    private void initInstances() {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawerToggle = new ActionBarDrawerToggle(LoginActivity.this, drawerLayout, R.string.hello_world, R.string.hello_world);
        drawerLayout.setDrawerListener(drawerToggle);

        navigation = (NavigationView) findViewById(R.id.navigation_view);
        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                // Handle navigation view item clicks here.
                int id = menuItem.getItemId();

//                if (id == R.id.navigation_view_category_2) {
//
//                    navigation.getMenu().clear();
//                    navigation.inflateMenu(R.menu.navigation_submenu_login);
//
//                } else if (id == R.id.back_to_menu) {
//
//                    navigation.getMenu().clear();
//                    navigation.inflateMenu(R.menu.navigation_items);
//
//                }

                if (id == R.id.navigation_view_category_3) {

                    navigation.getMenu().clear();
                    navigation.inflateMenu(R.menu.navigation_submenu_quiz);

                } else if (id == R.id.back_to_menu) {

                    navigation.getMenu().clear();
                    navigation.inflateMenu(R.menu.navigation_items);

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
                        startActivity(new Intent(LoginActivity.this, ContributorsActivity.class));
                        break;
                    case R.id.navigation_item_3:
                        startActivity(new Intent(LoginActivity.this, EditorialBoardActivity.class));
                        break;
                    case R.id.navigation_item_4:
                        startActivity(new Intent(LoginActivity.this, DisclaimersActivity.class));
                        break;
                    case R.id.navigation_item_5:
                        startActivity(new Intent(LoginActivity.this, LoginActivity.class));
                        break;
                    case R.id.navigation_item_6:
                        startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
                        break;
                    case R.id.navigation_item_10:
                        startActivity(new Intent(LoginActivity.this, QuizActivity.class));
                        break;
                    case R.id.navigation_item_11:
                        startActivity(new Intent(LoginActivity.this, QuizActivity1.class));
                        break;
                    case R.id.navigation_item_100:
                        startActivity(new Intent(LoginActivity.this, ComplexNumbersActivity.class));
                        break;
                    case R.id.navigation_item_101:
                        startActivity(new Intent(LoginActivity.this, ComplexVariablesActivity.class));
                        break;
                    case R.id.navigation_item_102:
                        startActivity(new Intent(LoginActivity.this, DeterminantsActivity.class));
                        break;
                    case R.id.navigation_item_103:
                        startActivity(new Intent(LoginActivity.this, DifferentiationAndIntegrationActivity.class));
                        break;
                    case R.id.navigation_item_104:
                        startActivity(new Intent(LoginActivity.this, GreatestCommonDivisorActivity.class));
                        break;
                    case R.id.navigation_item_105:
                        startActivity(new Intent(LoginActivity.this, LeastCommonMultipleActivity.class));
                        break;
                    case R.id.navigation_item_106:
                        startActivity(new Intent(LoginActivity.this, LinearSimultaneousEquationsActivity.class));
                        break;
                    case R.id.navigation_item_107:
                        startActivity(new Intent(LoginActivity.this, MatricesActivity.class));
                        break;
                    case R.id.navigation_item_108:
                        startActivity(new Intent(LoginActivity.this, ModuloOperationActivity.class));
                        break;
                    case R.id.navigation_item_109:
                        startActivity(new Intent(LoginActivity.this, OperationsOnComplexNumbersActivity.class));
                        break;
                    case R.id.navigation_item_110:
                        startActivity(new Intent(LoginActivity.this, PolynomialsActivity.class));
                        break;
                    case R.id.navigation_item_111:
                        startActivity(new Intent(LoginActivity.this, SeriesActivity.class));
                        break;
                    case R.id.navigation_item_112:
                        startActivity(new Intent(LoginActivity.this, TrigonometricFunctionsAndIdentitiesActivity.class));
                        break;
                    case R.id.navigation_item_113:
                        startActivity(new Intent(LoginActivity.this, VectorsActivity.class));
                        break;
                    case R.id.navigation_item_200:
                        startActivity(new Intent(LoginActivity.this, NumberSystemsActivity.class));
                        break;
                    case R.id.navigation_item_201:
                        startActivity(new Intent(LoginActivity.this, NumberConversionActivity.class));
                        break;
                    case R.id.navigation_item_202:
                        startActivity(new Intent(LoginActivity.this, FloatingPointNumberActivity.class));
                        break;
                    case R.id.navigation_item_203:
                        startActivity(new Intent(LoginActivity.this, NumberRangeActivity.class));
                        break;
                    case R.id.navigation_item_204:
                        startActivity(new Intent(LoginActivity.this, AssemblerDirectivesActivity.class));
                        break;
                    case R.id.navigation_item_205:
                        startActivity(new Intent(LoginActivity.this, GettingStartedWithKeiluVisionActivity.class));
                        break;
                    case R.id.navigation_item_206:
                        startActivity(new Intent(LoginActivity.this, LoadAndStoreInstructionsActivity.class));
                        break;
                    case R.id.navigation_item_207:
                        startActivity(new Intent(LoginActivity.this, ARMProgrammingMOVInstructionActivity.class));
                        break;
                    case R.id.navigation_item_208:
                        startActivity(new Intent(LoginActivity.this, ARMProgrammingLDRPseudoInstructionActivity.class));
                        break;
                    case R.id.navigation_item_209:
                        startActivity(new Intent(LoginActivity.this, ARMProgrammingLoopActivity.class));
                        break;
                    case R.id.navigation_item_210:
                        startActivity(new Intent(LoginActivity.this, LDMAndSTMInstructionsActivity.class));
                        break;
                    case R.id.navigation_item_211:
                        startActivity(new Intent(LoginActivity.this, PushAndPopInStackInstructionsActivity.class));
                        break;
                    case R.id.navigation_item_212:
                        startActivity(new Intent(LoginActivity.this, ARMProgrammingExceptionVectorTableActivity.class));
                        break;
                    case R.id.navigation_item_300:
                        startActivity(new Intent(LoginActivity.this, BlockDiagramsActivity.class));
                        break;
                    case R.id.navigation_item_301:
                        startActivity(new Intent(LoginActivity.this, CausalityConditionActivity.class));
                        break;
                    case R.id.navigation_item_302:
                        startActivity(new Intent(LoginActivity.this, ContinuousTimeFourierSeriesActivity.class));
                        break;
                    case R.id.navigation_item_303:
                        startActivity(new Intent(LoginActivity.this, ContinuousTimeFourierTransformActivity.class));
                        break;
                    case R.id.navigation_item_304:
                        startActivity(new Intent(LoginActivity.this, ContinuousTimeFourierTransformPropertiesActivity.class));
                        break;
                    case R.id.navigation_item_305:
                        startActivity(new Intent(LoginActivity.this, ConvolutionActivity.class));
                        break;
                    case R.id.navigation_item_306:
                        startActivity(new Intent(LoginActivity.this, DiscreteFourierTransformActivity.class));
                        break;
                    case R.id.navigation_item_307:
                        startActivity(new Intent(LoginActivity.this, DiscreteTimeFourierSeriesActivity.class));
                        break;
                    case R.id.navigation_item_308:
                        startActivity(new Intent(LoginActivity.this, DiscreteTimeFourierTransformActivity.class));
                        break;
                    case R.id.navigation_item_309:
                        startActivity(new Intent(LoginActivity.this, DiscreteTimeFourierTransformPropertiesActivity.class));
                        break;
                    case R.id.navigation_item_310:
                        startActivity(new Intent(LoginActivity.this, FrequencyResponseActivity.class));
                        break;
                    case R.id.navigation_item_311:
                        startActivity(new Intent(LoginActivity.this, GibbsPhenomenonActivity.class));
                        break;
                    case R.id.navigation_item_312:
                        startActivity(new Intent(LoginActivity.this, LaplaceTransformPropertiesActivity.class));
                        break;
                    case R.id.navigation_item_313:
                        startActivity(new Intent(LoginActivity.this, PartialFractionExpansionActivity.class));
                        break;
                    case R.id.navigation_item_314:
                        startActivity(new Intent(LoginActivity.this, StabilityConditionActivity.class));
                        break;
                    case R.id.navigation_item_315:
                        startActivity(new Intent(LoginActivity.this, TransferFunctionActivity.class));
                        break;
                    case R.id.navigation_item_316:
                        startActivity(new Intent(LoginActivity.this, zTransformActivity.class));
                        break;
                    case R.id.navigation_item_317:
                        startActivity(new Intent(LoginActivity.this, zTransformPropertiesActivity.class));
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
}
