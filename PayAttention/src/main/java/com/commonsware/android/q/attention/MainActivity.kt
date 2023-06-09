/*
  Copyright (c) 2019 CommonsWare, LLC

  Licensed under the Apache License, Version 2.0 (the "License"); you may not
  use this file except in compliance with the License. You may obtain	a copy
  of the License at http://www.apache.org/licenses/LICENSE-2.0. Unless required
  by applicable law or agreed to in writing, software distributed under the
  License is distributed on an "AS IS" BASIS,	WITHOUT	WARRANTIES OR CONDITIONS
  OF ANY KIND, either express or implied. See the License for the specific
  language governing permissions and limitations under the License.

  Covered in detail in the book _Elements of Android Q

  https://commonsware.com/AndroidQ
*/

package com.commonsware.android.q.attention

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
  private val workManager by lazy { WorkManager.getInstance() }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    toolbar.title = getString(R.string.app_name)

    activity.setOnClickListener {
      workManager.enqueue(OneTimeWorkRequestBuilder<StartActivityWorker>()
        .setInitialDelay(10, TimeUnit.SECONDS)
        .build())
      finish()
    }

    notification.setOnClickListener {
      workManager.enqueue(OneTimeWorkRequestBuilder<ShowNotificationWorker>()
        .setInitialDelay(10, TimeUnit.SECONDS)
        .build())
      finish()
    }
  }
}
