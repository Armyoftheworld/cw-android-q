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

package com.commonsware.android.q.loc.fg

import android.app.Application
import android.content.Intent
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import org.koin.android.ext.android.startKoin
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

class KoinApp : Application() {
  private val koinModule = module {
    single { LocationRepository(androidContext()) }
    viewModel { MainMotor(get()) }
  }

  override fun onCreate() {
    super.onCreate()

    startKoin(this, listOf(koinModule))

    ProcessLifecycleOwner.get()
      .lifecycle
      .addObserver(object : DefaultLifecycleObserver {
        override fun onStart(owner: LifecycleOwner) {
          stopService(Intent(this@KoinApp, ForegroundService::class.java))
        }

        override fun onStop(owner: LifecycleOwner) {
          startForegroundService(Intent(this@KoinApp, ForegroundService::class.java))
        }
      })
  }
}