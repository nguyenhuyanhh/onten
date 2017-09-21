package com.onten.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Created by zhenting on 16/1/2016.
 */
public class ResultActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    NavigationView navigation;

    Button buttonRestartQuiz;
    Button buttonHomePage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // get rating bar object
        RatingBar bar = (RatingBar) findViewById(R.id.ratingBar1);
        bar.setNumStars(5);
        bar.setStepSize(0.5f);

        // get text view
        TextView resultScore = (TextView) findViewById(R.id.textResult);

        buttonRestartQuiz = (Button) findViewById(R.id.buttonRestartQuiz);

        buttonHomePage = (Button) findViewById(R.id.buttonHomePage);

        // get score
        Bundle b = getIntent().getExtras();
        int score = b.getInt("score");

        // display score of quiz
        bar.setRating(score);
        switch (score) {
            case 1:
                resultScore.setText("You scored 1 mark! \nYou need to study harder for it!");

                break;

            case 2:
                resultScore.setText("You scored 2 mark! \nYou need to study harder for it!");

                break;

            case 3:
                resultScore.setText("You scored 3 mark! \nYou have just pass. Try harder!");

                break;

            case 4:
                resultScore.setText("You scored 4 mark! \nNot bad!");

                break;

            case 5:
                resultScore.setText("You scored full mark! \nWell Done!");

                break;

        }


        buttonRestartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), QuizActivity.class);
                startActivity(intent);
            }
        });

        buttonHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }


    private void initInstances() {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawerToggle = new ActionBarDrawerToggle(ResultActivity.this, drawerLayout, R.string.hello_world, R.string.hello_world);
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
                        startActivity(new Intent(ResultActivity.this, ContributorsActivity.class));
                        break;
                    case R.id.navigation_item_3:
                        startActivity(new Intent(ResultActivity.this, EditorialBoardActivity.class));
                        break;
                    case R.id.navigation_item_4:
                        startActivity(new Intent(ResultActivity.this, DisclaimersActivity.class));
                        break;
                    case R.id.navigation_item_5:
                        startActivity(new Intent(ResultActivity.this, LoginActivity.class));
                        break;
                    case R.id.navigation_item_6:
                        startActivity(new Intent(ResultActivity.this, RegistrationActivity.class));
                        break;
                    case R.id.navigation_item_10:
                        startActivity(new Intent(ResultActivity.this, QuizActivity.class));
                        break;
                    case R.id.navigation_item_11:
                        startActivity(new Intent(ResultActivity.this, QuizActivity1.class));
                        break;
                    case R.id.navigation_item_100:
                        startActivity(new Intent(ResultActivity.this, ComplexNumbersActivity.class));
                        break;
                    case R.id.navigation_item_101:
                        startActivity(new Intent(ResultActivity.this, ComplexVariablesActivity.class));
                        break;
                    case R.id.navigation_item_102:
                        startActivity(new Intent(ResultActivity.this, DeterminantsActivity.class));
                        break;
                    case R.id.navigation_item_103:
                        startActivity(new Intent(ResultActivity.this, DifferentiationAndIntegrationActivity.class));
                        break;
                    case R.id.navigation_item_104:
                        startActivity(new Intent(ResultActivity.this, GreatestCommonDivisorActivity.class));
                        break;
                    case R.id.navigation_item_105:
                        startActivity(new Intent(ResultActivity.this, LeastCommonMultipleActivity.class));
                        break;
                    case R.id.navigation_item_106:
                        startActivity(new Intent(ResultActivity.this, LinearSimultaneousEquationsActivity.class));
                        break;
                    case R.id.navigation_item_107:
                        startActivity(new Intent(ResultActivity.this, MatricesActivity.class));
                        break;
                    case R.id.navigation_item_108:
                        startActivity(new Intent(ResultActivity.this, ModuloOperationActivity.class));
                        break;
                    case R.id.navigation_item_109:
                        startActivity(new Intent(ResultActivity.this, OperationsOnComplexNumbersActivity.class));
                        break;
                    case R.id.navigation_item_110:
                        startActivity(new Intent(ResultActivity.this, PolynomialsActivity.class));
                        break;
                    case R.id.navigation_item_111:
                        startActivity(new Intent(ResultActivity.this, SeriesActivity.class));
                        break;
                    case R.id.navigation_item_112:
                        startActivity(new Intent(ResultActivity.this, TrigonometricFunctionsAndIdentitiesActivity.class));
                        break;
                    case R.id.navigation_item_113:
                        startActivity(new Intent(ResultActivity.this, VectorsActivity.class));
                        break;
                    case R.id.navigation_item_200:
                        startActivity(new Intent(ResultActivity.this, NumberSystemsActivity.class));
                        break;
                    case R.id.navigation_item_201:
                        startActivity(new Intent(ResultActivity.this, NumberConversionActivity.class));
                        break;
                    case R.id.navigation_item_202:
                        startActivity(new Intent(ResultActivity.this, FloatingPointNumberActivity.class));
                        break;
                    case R.id.navigation_item_203:
                        startActivity(new Intent(ResultActivity.this, NumberRangeActivity.class));
                        break;
                    case R.id.navigation_item_204:
                        startActivity(new Intent(ResultActivity.this, AssemblerDirectivesActivity.class));
                        break;
                    case R.id.navigation_item_205:
                        startActivity(new Intent(ResultActivity.this, GettingStartedWithKeiluVisionActivity.class));
                        break;
                    case R.id.navigation_item_206:
                        startActivity(new Intent(ResultActivity.this, LoadAndStoreInstructionsActivity.class));
                        break;
                    case R.id.navigation_item_207:
                        startActivity(new Intent(ResultActivity.this, ARMProgrammingMOVInstructionActivity.class));
                        break;
                    case R.id.navigation_item_208:
                        startActivity(new Intent(ResultActivity.this, ARMProgrammingLDRPseudoInstructionActivity.class));
                        break;
                    case R.id.navigation_item_209:
                        startActivity(new Intent(ResultActivity.this, ARMProgrammingLoopActivity.class));
                        break;
                    case R.id.navigation_item_210:
                        startActivity(new Intent(ResultActivity.this, LDMAndSTMInstructionsActivity.class));
                        break;
                    case R.id.navigation_item_211:
                        startActivity(new Intent(ResultActivity.this, PushAndPopInStackInstructionsActivity.class));
                        break;
                    case R.id.navigation_item_212:
                        startActivity(new Intent(ResultActivity.this, ARMProgrammingExceptionVectorTableActivity.class));
                        break;
                    case R.id.navigation_item_300:
                        startActivity(new Intent(ResultActivity.this, BlockDiagramsActivity.class));
                        break;
                    case R.id.navigation_item_301:
                        startActivity(new Intent(ResultActivity.this, CausalityConditionActivity.class));
                        break;
                    case R.id.navigation_item_302:
                        startActivity(new Intent(ResultActivity.this, ContinuousTimeFourierSeriesActivity.class));
                        break;
                    case R.id.navigation_item_303:
                        startActivity(new Intent(ResultActivity.this, ContinuousTimeFourierTransformActivity.class));
                        break;
                    case R.id.navigation_item_304:
                        startActivity(new Intent(ResultActivity.this, ContinuousTimeFourierTransformPropertiesActivity.class));
                        break;
                    case R.id.navigation_item_305:
                        startActivity(new Intent(ResultActivity.this, ConvolutionActivity.class));
                        break;
                    case R.id.navigation_item_306:
                        startActivity(new Intent(ResultActivity.this, DiscreteFourierTransformActivity.class));
                        break;
                    case R.id.navigation_item_307:
                        startActivity(new Intent(ResultActivity.this, DiscreteTimeFourierSeriesActivity.class));
                        break;
                    case R.id.navigation_item_308:
                        startActivity(new Intent(ResultActivity.this, DiscreteTimeFourierTransformActivity.class));
                        break;
                    case R.id.navigation_item_309:
                        startActivity(new Intent(ResultActivity.this, DiscreteTimeFourierTransformPropertiesActivity.class));
                        break;
                    case R.id.navigation_item_310:
                        startActivity(new Intent(ResultActivity.this, FrequencyResponseActivity.class));
                        break;
                    case R.id.navigation_item_311:
                        startActivity(new Intent(ResultActivity.this, GibbsPhenomenonActivity.class));
                        break;
                    case R.id.navigation_item_312:
                        startActivity(new Intent(ResultActivity.this, LaplaceTransformPropertiesActivity.class));
                        break;
                    case R.id.navigation_item_313:
                        startActivity(new Intent(ResultActivity.this, PartialFractionExpansionActivity.class));
                        break;
                    case R.id.navigation_item_314:
                        startActivity(new Intent(ResultActivity.this, StabilityConditionActivity.class));
                        break;
                    case R.id.navigation_item_315:
                        startActivity(new Intent(ResultActivity.this, TransferFunctionActivity.class));
                        break;
                    case R.id.navigation_item_316:
                        startActivity(new Intent(ResultActivity.this, zTransformActivity.class));
                        break;
                    case R.id.navigation_item_317:
                        startActivity(new Intent(ResultActivity.this, zTransformPropertiesActivity.class));
                        break;
                }
                return false;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_result, menu);
        return true;
    }

}
