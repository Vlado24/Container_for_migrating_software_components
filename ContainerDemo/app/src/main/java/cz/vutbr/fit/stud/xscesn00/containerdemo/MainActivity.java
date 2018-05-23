package cz.vutbr.fit.stud.xscesn00.containerdemo;

import android.os.*;

import android.support.v7.app.*;

import timber.log.*;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    Timber.d("onCreate()");
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    if (BuildConfig.DEBUG) {
      Timber.plant(new Timber.DebugTree());
    }
  }
}

