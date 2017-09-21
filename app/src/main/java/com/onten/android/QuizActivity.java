package com.onten.android;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by zhenting on 15/1/2016.
 */
public class QuizActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    NavigationView navigation;

    List<QuestionActivity> questionList;
    int score = 0;
    int questionid = 0;
    QuestionActivity currentQ;
    TextView textQuestion;
    RadioButton radioButton_A, radioButton_B, radioButton_C, radioButton_D, radioButton_E;
    Button button_A, button_B, button_C, button_D, button_E;
    Button buttonConfirm, buttonNext, buttonExit;
    private boolean clicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        initInstances();

        // My question bank class
        DatabaseActivity db = new DatabaseActivity(this);

        // This will fetch all quetonall questions
        questionList = db.getAllQuestions();

        // The current question
        currentQ = questionList.get(questionid);

        // The textview in which the question will be displayed
        textQuestion = (TextView) findViewById(R.id.textView1);

        radioButton_A = (RadioButton) findViewById(R.id.radio0);
        radioButton_B = (RadioButton) findViewById(R.id.radio1);
        radioButton_C = (RadioButton) findViewById(R.id.radio2);
        radioButton_D = (RadioButton) findViewById(R.id.radio3);
        radioButton_E = (RadioButton) findViewById(R.id.radio4);

        //button_A = (Button)findViewById(R.id.button0);
        //button_B = (Button)findViewById(R.id.button1);
        //button_C = (Button)findViewById(R.id.button2);
        //button_D = (Button)findViewById(R.id.button3);
        //button_E = (Button)findViewById(R.id.button4);

        buttonConfirm = (Button) findViewById(R.id.buttonConfirm);

        //buttonPrevious = (Button)findViewById(R.id.buttonPrevious);

        buttonNext = (Button) findViewById(R.id.buttonNext);

        buttonExit = (Button) findViewById(R.id.buttonExit);

        setQuestionView();

        final TextView result = (TextView) findViewById(R.id.quiz_result);
        result.setTextColor(Color.parseColor("#ff9900"));
        result.setText("Your score is " + score + "/5");

        buttonConfirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                RadioGroup grp = (RadioGroup) findViewById(R.id.radioGroup1);

                RadioButton answer = (RadioButton) findViewById(grp.getCheckedRadioButtonId());
                Log.d("yourans", currentQ.getANSWER() + " " + answer.getText());


                if (currentQ.getANSWER().equals(answer.getText()) && clicked == false) {

                    score++;
                    Log.d("score", "Your score" + score);

                    final TextView result = (TextView) findViewById(R.id.quiz_result);
                    result.setTextColor(Color.parseColor("#ff9900"));
                    result.setText("Your score is " + score + "/5");


                    answer.setBackgroundColor(Color.GREEN);
                    answer.setEnabled(true);

                    final TextView message = (TextView) findViewById(R.id.quiz_message);
                    message.setTextColor(Color.parseColor("#228b22"));
                    message.setText("Correct Answer!");

                    clicked = true;

                } else if (!currentQ.getANSWER().equals(answer.getText()) && clicked == false) {

                    final TextView result = (TextView) findViewById(R.id.quiz_result);
                    result.setTextColor(Color.parseColor("#ff9900"));
                    result.setText("Your score is " + score + "/5");

                    answer.setBackgroundColor(Color.RED);

                    final TextView message = (TextView) findViewById(R.id.quiz_message);
                    message.setTextColor(Color.parseColor("#ff0000"));
                    message.setText("Incorrect Answer!");

                    clicked = true;

                }


                if (questionid == 1) {

                    radioButton_E.setBackgroundColor(Color.GREEN);


                    // Disable  all buttons
                    radioButton_A.setEnabled(false);
                    radioButton_B.setEnabled(false);
                    radioButton_C.setEnabled(false);
                    radioButton_D.setEnabled(false);
                    radioButton_E.setEnabled(false);
                }

                if (questionid == 2) {

                    radioButton_E.setBackgroundColor(Color.GREEN);


                    // Disable  all buttons
                    radioButton_A.setEnabled(false);
                    radioButton_B.setEnabled(false);
                    radioButton_C.setEnabled(false);
                    radioButton_D.setEnabled(false);
                    radioButton_E.setEnabled(false);
                }

                if (questionid == 3) {

                    radioButton_B.setBackgroundColor(Color.GREEN);


                    // Disable  all buttons
                    radioButton_A.setEnabled(false);
                    radioButton_B.setEnabled(false);
                    radioButton_C.setEnabled(false);
                    radioButton_D.setEnabled(false);
                    radioButton_E.setEnabled(false);
                }

                if (questionid == 4) {

                    radioButton_E.setBackgroundColor(Color.GREEN);


                    // Disable  all buttons
                    radioButton_A.setEnabled(false);
                    radioButton_B.setEnabled(false);
                    radioButton_C.setEnabled(false);
                    radioButton_D.setEnabled(false);
                    radioButton_E.setEnabled(false);
                }

                if (questionid == 5) {

                    radioButton_A.setBackgroundColor(Color.GREEN);


                    // Disable  all buttons
                    radioButton_A.setEnabled(false);
                    radioButton_B.setEnabled(false);
                    radioButton_C.setEnabled(false);
                    radioButton_D.setEnabled(false);
                    radioButton_E.setEnabled(false);
                }
            }
        });


        buttonNext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (clicked == true) {

                    if (questionid < 5) {

                        currentQ = questionList.get(questionid);
                        setQuestionView();
                        radioButton_A.setChecked(true);

                        // Set all button to default color
                        radioButton_A.setBackgroundColor(Color.TRANSPARENT);
                        radioButton_B.setBackgroundColor(Color.TRANSPARENT);
                        radioButton_C.setBackgroundColor(Color.TRANSPARENT);
                        radioButton_D.setBackgroundColor(Color.TRANSPARENT);
                        radioButton_E.setBackgroundColor(Color.TRANSPARENT);

                        // Enable all buttons
                        radioButton_A.setEnabled(true);
                        radioButton_B.setEnabled(true);
                        radioButton_C.setEnabled(true);
                        radioButton_D.setEnabled(true);
                        radioButton_E.setEnabled(true);


                        final TextView message = (TextView) findViewById(R.id.quiz_message);
                        message.setText(null);

                        clicked = false;

                    } else {

                        Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
                        Bundle b = new Bundle();
                        b.putInt("score", score); //Your score
                        intent.putExtras(b); //Put your score to your next Intent
                        startActivity(intent);
                        finish();

                    }
                } else {

                    final TextView message = (TextView) findViewById(R.id.quiz_message);
                    message.setTextColor(Color.parseColor("#ff0000"));
                    message.setText("You have not chose an answer!");
                }

            }
        });


        buttonExit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (clicked == true || clicked == false) {

                    Intent intent = new Intent(QuizActivity.this, MainActivity.class);
                    Bundle b = new Bundle();
                    startActivity(intent);
                    finish();

                }
            }
        });
    }

    private void initInstances() {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawerToggle = new ActionBarDrawerToggle(QuizActivity.this, drawerLayout, R.string.hello_world, R.string.hello_world);
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
                        startActivity(new Intent(QuizActivity.this, ContributorsActivity.class));
                        break;
                    case R.id.navigation_item_3:
                        startActivity(new Intent(QuizActivity.this, EditorialBoardActivity.class));
                        break;
                    case R.id.navigation_item_4:
                        startActivity(new Intent(QuizActivity.this, DisclaimersActivity.class));
                        break;
                    case R.id.navigation_item_5:
                        startActivity(new Intent(QuizActivity.this, LoginActivity.class));
                        break;
                    case R.id.navigation_item_6:
                        startActivity(new Intent(QuizActivity.this, RegistrationActivity.class));
                        break;
                    case R.id.navigation_item_10:
                        startActivity(new Intent(QuizActivity.this, QuizActivity.class));
                        break;
                    case R.id.navigation_item_11:
                        startActivity(new Intent(QuizActivity.this, QuizActivity1.class));
                        break;
                    case R.id.navigation_item_100:
                        startActivity(new Intent(QuizActivity.this, ComplexNumbersActivity.class));
                        break;
                    case R.id.navigation_item_101:
                        startActivity(new Intent(QuizActivity.this, ComplexVariablesActivity.class));
                        break;
                    case R.id.navigation_item_102:
                        startActivity(new Intent(QuizActivity.this, DeterminantsActivity.class));
                        break;
                    case R.id.navigation_item_103:
                        startActivity(new Intent(QuizActivity.this, DifferentiationAndIntegrationActivity.class));
                        break;
                    case R.id.navigation_item_104:
                        startActivity(new Intent(QuizActivity.this, GreatestCommonDivisorActivity.class));
                        break;
                    case R.id.navigation_item_105:
                        startActivity(new Intent(QuizActivity.this, LeastCommonMultipleActivity.class));
                        break;
                    case R.id.navigation_item_106:
                        startActivity(new Intent(QuizActivity.this, LinearSimultaneousEquationsActivity.class));
                        break;
                    case R.id.navigation_item_107:
                        startActivity(new Intent(QuizActivity.this, MatricesActivity.class));
                        break;
                    case R.id.navigation_item_108:
                        startActivity(new Intent(QuizActivity.this, ModuloOperationActivity.class));
                        break;
                    case R.id.navigation_item_109:
                        startActivity(new Intent(QuizActivity.this, OperationsOnComplexNumbersActivity.class));
                        break;
                    case R.id.navigation_item_110:
                        startActivity(new Intent(QuizActivity.this, PolynomialsActivity.class));
                        break;
                    case R.id.navigation_item_111:
                        startActivity(new Intent(QuizActivity.this, SeriesActivity.class));
                        break;
                    case R.id.navigation_item_112:
                        startActivity(new Intent(QuizActivity.this, TrigonometricFunctionsAndIdentitiesActivity.class));
                        break;
                    case R.id.navigation_item_113:
                        startActivity(new Intent(QuizActivity.this, VectorsActivity.class));
                        break;
                    case R.id.navigation_item_200:
                        startActivity(new Intent(QuizActivity.this, NumberSystemsActivity.class));
                        break;
                    case R.id.navigation_item_201:
                        startActivity(new Intent(QuizActivity.this, NumberConversionActivity.class));
                        break;
                    case R.id.navigation_item_202:
                        startActivity(new Intent(QuizActivity.this, FloatingPointNumberActivity.class));
                        break;
                    case R.id.navigation_item_203:
                        startActivity(new Intent(QuizActivity.this, NumberRangeActivity.class));
                        break;
                    case R.id.navigation_item_204:
                        startActivity(new Intent(QuizActivity.this, AssemblerDirectivesActivity.class));
                        break;
                    case R.id.navigation_item_205:
                        startActivity(new Intent(QuizActivity.this, GettingStartedWithKeiluVisionActivity.class));
                        break;
                    case R.id.navigation_item_206:
                        startActivity(new Intent(QuizActivity.this, LoadAndStoreInstructionsActivity.class));
                        break;
                    case R.id.navigation_item_207:
                        startActivity(new Intent(QuizActivity.this, ARMProgrammingMOVInstructionActivity.class));
                        break;
                    case R.id.navigation_item_208:
                        startActivity(new Intent(QuizActivity.this, ARMProgrammingLDRPseudoInstructionActivity.class));
                        break;
                    case R.id.navigation_item_209:
                        startActivity(new Intent(QuizActivity.this, ARMProgrammingLoopActivity.class));
                        break;
                    case R.id.navigation_item_210:
                        startActivity(new Intent(QuizActivity.this, LDMAndSTMInstructionsActivity.class));
                        break;
                    case R.id.navigation_item_211:
                        startActivity(new Intent(QuizActivity.this, PushAndPopInStackInstructionsActivity.class));
                        break;
                    case R.id.navigation_item_212:
                        startActivity(new Intent(QuizActivity.this, ARMProgrammingExceptionVectorTableActivity.class));
                        break;
                    case R.id.navigation_item_300:
                        startActivity(new Intent(QuizActivity.this, BlockDiagramsActivity.class));
                        break;
                    case R.id.navigation_item_301:
                        startActivity(new Intent(QuizActivity.this, CausalityConditionActivity.class));
                        break;
                    case R.id.navigation_item_302:
                        startActivity(new Intent(QuizActivity.this, ContinuousTimeFourierSeriesActivity.class));
                        break;
                    case R.id.navigation_item_303:
                        startActivity(new Intent(QuizActivity.this, ContinuousTimeFourierTransformActivity.class));
                        break;
                    case R.id.navigation_item_304:
                        startActivity(new Intent(QuizActivity.this, ContinuousTimeFourierTransformPropertiesActivity.class));
                        break;
                    case R.id.navigation_item_305:
                        startActivity(new Intent(QuizActivity.this, ConvolutionActivity.class));
                        break;
                    case R.id.navigation_item_306:
                        startActivity(new Intent(QuizActivity.this, DiscreteFourierTransformActivity.class));
                        break;
                    case R.id.navigation_item_307:
                        startActivity(new Intent(QuizActivity.this, DiscreteTimeFourierSeriesActivity.class));
                        break;
                    case R.id.navigation_item_308:
                        startActivity(new Intent(QuizActivity.this, DiscreteTimeFourierTransformActivity.class));
                        break;
                    case R.id.navigation_item_309:
                        startActivity(new Intent(QuizActivity.this, DiscreteTimeFourierTransformPropertiesActivity.class));
                        break;
                    case R.id.navigation_item_310:
                        startActivity(new Intent(QuizActivity.this, FrequencyResponseActivity.class));
                        break;
                    case R.id.navigation_item_311:
                        startActivity(new Intent(QuizActivity.this, GibbsPhenomenonActivity.class));
                        break;
                    case R.id.navigation_item_312:
                        startActivity(new Intent(QuizActivity.this, LaplaceTransformPropertiesActivity.class));
                        break;
                    case R.id.navigation_item_313:
                        startActivity(new Intent(QuizActivity.this, PartialFractionExpansionActivity.class));
                        break;
                    case R.id.navigation_item_314:
                        startActivity(new Intent(QuizActivity.this, StabilityConditionActivity.class));
                        break;
                    case R.id.navigation_item_315:
                        startActivity(new Intent(QuizActivity.this, TransferFunctionActivity.class));
                        break;
                    case R.id.navigation_item_316:
                        startActivity(new Intent(QuizActivity.this, zTransformActivity.class));
                        break;
                    case R.id.navigation_item_317:
                        startActivity(new Intent(QuizActivity.this, zTransformPropertiesActivity.class));
                        break;
                }
                return false;
            }
        });
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);


    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();


    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_quiz, menu);
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


        return true;
    }

    private void setQuestionView() {
        textQuestion.setText(currentQ.getQUESTION());

        radioButton_A.setText(currentQ.getOPTION_A());
        radioButton_B.setText(currentQ.getOPTION_B());
        radioButton_C.setText(currentQ.getOPTION_C());
        radioButton_D.setText(currentQ.getOPTION_D());
        radioButton_E.setText(currentQ.getOPTION_E());

        //button_A.setText(currentQ.getOPTION_A());
        //button_B.setText(currentQ.getOPTION_B());
        //button_C.setText(currentQ.getOPTION_C());
        //button_D.setText(currentQ.getOPTION_D());
        //button_E.setText(currentQ.getOPTION_E());

        questionid++;
    }

    /*public void checkAnswer(View view){

        // Disable all buttons
        //((Button)findViewById(R.id.button0)).setEnabled(false);
        //((Button)findViewById(R.id.button1)).setEnabled(false);
        //((Button)findViewById(R.id.button2)).setEnabled(false);
        //((Button)findViewById(R.id.button3)).setEnabled(false);
        //((Button)findViewById(R.id.button4)).setEnabled(false);

        button_A.getBackground().setColorFilter(null);
        button_B.getBackground().setColorFilter(null);
        button_C.getBackground().setColorFilter(null);
        button_D.getBackground().setColorFilter(null);
        button_E.getBackground().setColorFilter(null);

        Button answer = (Button) view;

        Log.d("yourans", currentQ.getANSWER()+" "+answer.getText());

        if(currentQ.getANSWER().equals(answer.getText()))

            answer.setBackgroundColor(Color.GREEN);

        else

            answer.setBackgroundColor(Color.RED);

    }*/

}
